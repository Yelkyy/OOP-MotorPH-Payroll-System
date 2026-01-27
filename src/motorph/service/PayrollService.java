package motorph.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import motorph.model.EmployeeDetails;
import motorph.model.EmployeeTimeLogs;
import motorph.utils.EmployeeDataUtil;
import motorph.utils.EmployeeDataUtil.DeductionBreakdown;

public class PayrollService {

    // Government / Tax Constants
    private static final double SSS_MAX_SALARY = 24750;
    private static final double SSS_MAX_CONTRIBUTION = 1125.00;
    private static final double SSS_RATE = 0.045;

    private static final double PHILHEALTH_LOWER_LIMIT = 10000;
    private static final double PHILHEALTH_UPPER_LIMIT = 59999.99;
    private static final double PHILHEALTH_MAX_PREMIUM = 1800;
    private static final double PHILHEALTH_RATE = 0.03;

    private static final double PAGIBIG_RATE = 0.02;
    private static final double PAGIBIG_MAX_CONTRIBUTION = 100;

    private static final double TAX_BRACKET_1_LIMIT = 20832;
    private static final double TAX_BRACKET_2_LIMIT = 33333;
    private static final double TAX_BRACKET_3_LIMIT = 66667;
    private static final double TAX_BRACKET_4_LIMIT = 166667;
    private static final double TAX_BRACKET_5_LIMIT = 666667;

    // Date Parsing
    private static final DateTimeFormatter TIMELOG_DATE_FMT =
            DateTimeFormatter.ofPattern("MM/dd/uuuu", Locale.US).withResolverStyle(ResolverStyle.STRICT);

    private static LocalDate parseLogDate(String raw) {
        return LocalDate.parse(raw == null ? "" : raw.trim(), TIMELOG_DATE_FMT);
    }

    /**
     * Computes a complete deduction breakdown (late/undertime + govt contrib + tax + totals + net pay)
     * based on monthYear and payPeriod.
     */
    public static DeductionBreakdown computeDeductions(
            EmployeeDetails employee,
            List<EmployeeTimeLogs> logs,
            String monthYear,
            int payPeriod
    ) {
        DeductionBreakdown result = new DeductionBreakdown();

        YearMonth yearMonth = YearMonth.parse(monthYear, DateTimeFormatter.ofPattern("MM-yyyy"));
        int lastDayOfMonth = yearMonth.lengthOfMonth();

        int startDay = (payPeriod == 1) ? 1 : 16;
        int endDay = (payPeriod == 1) ? 15 : lastDayOfMonth;

        boolean hasDeductions = (endDay != 15);

        double semiMonthlyBasic = employee.getBasicSalary() / 2.0;
        double totalAllowances = employee.getRiceSubsidy()
                + employee.getPhoneAllowance()
                + employee.getClothingAllowance();

        // Use only logs inside the period
        List<EmployeeTimeLogs> filteredLogs = filterLogsByDateRange(logs, monthYear, startDay, endDay);

        // Late/Undertime is based on the period logs
        double lateUndertime = hasDeductions
                ? calculateLateUndertimeTotal(employee, filteredLogs)
                : 0.0;

        result.lateUndertime = lateUndertime;
        result.sss = hasDeductions ? calculateSSS(semiMonthlyBasic) : 0.0;
        result.philhealth = hasDeductions ? calculatePhilHealth(semiMonthlyBasic) : 0.0;
        result.pagibig = hasDeductions ? calculatePagIbig(semiMonthlyBasic) : 0.0;

        double nonTaxDeductions = result.lateUndertime + result.sss + result.philhealth + result.pagibig;

        double taxableIncome = Math.max(0.0, semiMonthlyBasic - nonTaxDeductions);
        result.tax = hasDeductions ? calculateTax(taxableIncome) : 0.0;

        result.totalDeductions = nonTaxDeductions + result.tax;
        result.netPay = semiMonthlyBasic + totalAllowances - result.totalDeductions;

        return result;
    }

    /**
     * Convenience overload when you store payroll by cutoff date.
     */
    public static DeductionBreakdown computeDeductionsByCutoff(
            EmployeeDetails employee,
            List<EmployeeTimeLogs> allEmployeeLogs,
            LocalDate cutoffDate
    ) {
        String monthYear = cutoffDate.format(DateTimeFormatter.ofPattern("MM-yyyy"));
        int payPeriod = (cutoffDate.getDayOfMonth() <= 15) ? 1 : 2;
        return computeDeductions(employee, allEmployeeLogs, monthYear, payPeriod);
    }

    /**
     * Convenience overload that fetches time logs automatically via EmployeeDataUtil.
     */
    public static DeductionBreakdown computeDeductions(EmployeeDetails employee, String monthYear, int payPeriod) {
        List<EmployeeTimeLogs> logs = EmployeeDataUtil.getTimeLogsForEmployee(employee.getEmployeeNumber());
        return computeDeductions(employee, logs, monthYear, payPeriod);
    }

    /**
     * Returns only the net pay value (your tables use this).
     * NOTE: returns 0.0 if there are no logs for the period (keeps your original behavior).
     */
    public static double calculateNetPay(
            EmployeeDetails employee,
            List<EmployeeTimeLogs> logs,
            String monthYear,
            int payPeriod
    ) {
        YearMonth yearMonth = YearMonth.parse(monthYear, DateTimeFormatter.ofPattern("MM-yyyy"));
        int lastDayOfMonth = yearMonth.lengthOfMonth();

        int startDay = (payPeriod == 1) ? 1 : 16;
        int endDay = (payPeriod == 1) ? 15 : lastDayOfMonth;

        boolean hasDeductions = (endDay != 15);

        double semiMonthlyBasic = employee.getBasicSalary() / 2.0;
        double totalAllowances = employee.getRiceSubsidy()
                + employee.getPhoneAllowance()
                + employee.getClothingAllowance();

        List<EmployeeTimeLogs> filteredLogs = filterLogsByDateRange(logs, monthYear, startDay, endDay);

        if (filteredLogs.isEmpty()) {
            return 0.0;
        }

        double lateUndertime = hasDeductions
                ? calculateLateUndertimeTotal(employee, filteredLogs)
                : 0.0;

        double sss = hasDeductions ? calculateSSS(semiMonthlyBasic) : 0.0;
        double philhealth = hasDeductions ? calculatePhilHealth(semiMonthlyBasic) : 0.0;
        double pagibig = hasDeductions ? calculatePagIbig(semiMonthlyBasic) : 0.0;

        double nonTaxDeductions = lateUndertime + sss + philhealth + pagibig;

        double taxableIncome = Math.max(0.0, semiMonthlyBasic - nonTaxDeductions);
        double tax = hasDeductions ? calculateTax(taxableIncome) : 0.0;

        double totalDeductions = nonTaxDeductions + tax;
        return semiMonthlyBasic + totalAllowances - totalDeductions;
    }

    // Internal Helpers (logic only)
    private static List<EmployeeTimeLogs> filterLogsByDateRange(
            List<EmployeeTimeLogs> logs,
            String monthYear,
            int startDay,
            int endDay
    ) {
        List<EmployeeTimeLogs> filteredLogs = new ArrayList<>();
        DateTimeFormatter ymFormatter = DateTimeFormatter.ofPattern("MM-yyyy");

        for (EmployeeTimeLogs log : logs) {
            try {
                LocalDate logDate = parseLogDate(log.getDate());
                String logMonthYear = logDate.format(ymFormatter);

                if (logMonthYear.equals(monthYear)
                        && logDate.getDayOfMonth() >= startDay
                        && logDate.getDayOfMonth() <= endDay) {
                    filteredLogs.add(log);
                }
            } catch (DateTimeParseException e) {
                // ignore bad rows
            }
        }
        return filteredLogs;
    }

    private static double calculateLateUndertimeTotal(EmployeeDetails employee, List<EmployeeTimeLogs> logs) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");

        LocalTime scheduledIn = LocalTime.parse("09:00", formatter);
        LocalTime scheduledOut = LocalTime.parse("18:00", formatter);

        double hourlyRate = employee.getHourlyRate();
        double totalDeduction = 0.0;

        for (EmployeeTimeLogs log : logs) {
            try {
                LocalTime actualIn = LocalTime.parse(log.getLogIn().trim(), formatter);
                LocalTime actualOut = LocalTime.parse(log.getLogOut().trim(), formatter);

                long minutesLate = Math.max(0, Duration.between(scheduledIn, actualIn).toMinutes());
                long minutesUndertime = Math.max(0, Duration.between(actualOut, scheduledOut).toMinutes());

                totalDeduction += ((minutesLate + minutesUndertime) / 60.0) * hourlyRate;

            } catch (Exception e) {
                // ignore invalid time rows
            }
        }

        return totalDeduction;
    }

    private static double calculateSSS(double basicSalary) {
        if (basicSalary > SSS_MAX_SALARY) {
            return SSS_MAX_CONTRIBUTION;
        }
        return basicSalary * SSS_RATE;
    }

    private static double calculatePhilHealth(double basicSalary) {
        double premium;
        if (basicSalary <= PHILHEALTH_LOWER_LIMIT) {
            premium = 300;
        } else if (basicSalary <= PHILHEALTH_UPPER_LIMIT) {
            premium = Math.min(basicSalary * PHILHEALTH_RATE, PHILHEALTH_MAX_PREMIUM);
        } else {
            premium = PHILHEALTH_MAX_PREMIUM;
        }
        return premium / 2.0;
    }

    private static double calculatePagIbig(double basicSalary) {
        return Math.min(PAGIBIG_MAX_CONTRIBUTION, basicSalary * PAGIBIG_RATE);
    }

    private static double calculateTax(double taxableIncome) {
        double tax;

        if (taxableIncome <= TAX_BRACKET_1_LIMIT) {
            tax = 0.0;
        } else if (taxableIncome <= TAX_BRACKET_2_LIMIT) {
            tax = (taxableIncome - TAX_BRACKET_1_LIMIT) * 0.20;
        } else if (taxableIncome <= TAX_BRACKET_3_LIMIT) {
            tax = 2500 + (taxableIncome - TAX_BRACKET_2_LIMIT) * 0.25;
        } else if (taxableIncome <= TAX_BRACKET_4_LIMIT) {
            tax = 10833 + (taxableIncome - TAX_BRACKET_3_LIMIT) * 0.30;
        } else if (taxableIncome <= TAX_BRACKET_5_LIMIT) {
            tax = 40833.33 + (taxableIncome - TAX_BRACKET_4_LIMIT) * 0.32;
        } else {
            tax = 200833.33 + (taxableIncome - TAX_BRACKET_5_LIMIT) * 0.35;
        }

        return Math.round(tax * 100.0) / 100.0;
    }
}
