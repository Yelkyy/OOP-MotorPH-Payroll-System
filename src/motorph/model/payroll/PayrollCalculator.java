package motorph.model.payroll;

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

import motorph.model.core.Employee;
import motorph.model.EmployeeTimeLogs;

/**
 * Utility class providing the core payroll calculation engine.
 * Responsibility: Calculate all statutory deductions and penalties:
 * - Statutory Deductions: SSS, PhilHealth, Pag-IBIG, Withholding Tax
 * - Penalties: Late/Undertime deductions based on attendance logs
 * 
 * Note: Gross salary is obtained from Employee.getGrossSalary().
 * Returns PayrollResult containing itemized deductions and net pay.
 */
public class PayrollCalculator {

    public static class PayrollResult {
        public double lateUndertime;
        public double sss;
        public double philhealth;
        public double pagibig;
        public double tax;
        public double totalDeductions;
        public double netPay;

        public PayrollResult() {
        }
    }

    // ========== TAX CONSTANTS ==========

    private static final double TAX_BRACKET_1_LIMIT = 20832;
    private static final double TAX_BRACKET_2_LIMIT = 33333;
    private static final double TAX_BRACKET_3_LIMIT = 66667;
    private static final double TAX_BRACKET_4_LIMIT = 166667;
    private static final double TAX_BRACKET_5_LIMIT = 666667;

    // ========== CONTRIBUTION CONSTANTS ==========

    // SSS
    private static final double SSS_MAX_SALARY = 24750;
    private static final double SSS_MAX_CONTRIBUTION = 1125.00;
    private static final double SSS_RATE = 0.045;

    // PhilHealth
    private static final double PHILHEALTH_LOWER_LIMIT = 10000;
    private static final double PHILHEALTH_UPPER_LIMIT = 59999.99;
    private static final double PHILHEALTH_MAX_PREMIUM = 1800;
    private static final double PHILHEALTH_RATE = 0.03;

    // Pag-IBIG
    private static final double PAGIBIG_RATE = 0.02;
    private static final double PAGIBIG_MAX_CONTRIBUTION = 100;

    // ========== DATE PARSING ==========

    private static final DateTimeFormatter TIMELOG_DATE_FMT = DateTimeFormatter.ofPattern("MM/dd/uuuu", Locale.US)
            .withResolverStyle(ResolverStyle.STRICT);

    private static LocalDate parseLogDate(String raw) {
        return LocalDate.parse(raw == null ? "" : raw.trim(), TIMELOG_DATE_FMT);
    }

    // ========== MAIN COMPUTATION METHOD ==========

    public PayrollResult computeDeductions(
            Employee employee,
            List<EmployeeTimeLogs> logs,
            String monthYear,
            int payPeriod) {

        PayrollResult result = new PayrollResult();

        YearMonth yearMonth = YearMonth.parse(monthYear, DateTimeFormatter.ofPattern("MM-yyyy"));
        int lastDayOfMonth = yearMonth.lengthOfMonth();

        int startDay = (payPeriod == 1) ? 1 : 16;
        int endDay = (payPeriod == 1) ? 15 : lastDayOfMonth;

        boolean hasDeductions = (endDay != 15);

        double semiMonthlyBasic = employee.getBasicSalary() / 2.0;
        double totalAllowances = employee.getRiceSubsidy()
                + employee.getPhoneAllowance()
                + employee.getClothingAllowance();

        // Logs inside the period only
        List<EmployeeTimeLogs> filteredLogs = filterLogsByDateRange(logs, monthYear, startDay, endDay);

        // Late/Undertime
        double lateUndertime = hasDeductions
                ? calculateLateUndertimeTotal(employee, filteredLogs)
                : 0.0;

        result.lateUndertime = lateUndertime;

        // Government contributions (inline calculations)
        result.sss = hasDeductions ? calculateSSS(semiMonthlyBasic) : 0.0;
        result.philhealth = hasDeductions ? calculatePhilHealth(semiMonthlyBasic) : 0.0;
        result.pagibig = hasDeductions ? calculatePagIbig(semiMonthlyBasic) : 0.0;

        double nonTaxDeductions = result.lateUndertime + result.sss + result.philhealth + result.pagibig;

        // Tax
        double taxableIncome = Math.max(0.0, semiMonthlyBasic - nonTaxDeductions);
        result.tax = hasDeductions ? calculateTax(taxableIncome) : 0.0;

        // Totals
        result.totalDeductions = nonTaxDeductions + result.tax;
        result.netPay = semiMonthlyBasic + totalAllowances - result.totalDeductions;

        return result;
    }

    public PayrollResult computeDeductionsByCutoff(
            Employee employee,
            List<EmployeeTimeLogs> logs,
            LocalDate cutoffDate) {
        String monthYear = cutoffDate.format(DateTimeFormatter.ofPattern("MM-yyyy"));
        int payPeriod = (cutoffDate.getDayOfMonth() <= 15) ? 1 : 2;
        return computeDeductions(employee, logs, monthYear, payPeriod);
    }

    // ========== GOVERNMENT CONTRIBUTION CALCULATIONS ==========

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
        return premium / 2.0; // employee share per cutoff
    }

    private static double calculatePagIbig(double basicSalary) {
        return Math.min(PAGIBIG_MAX_CONTRIBUTION, basicSalary * PAGIBIG_RATE);
    }

    // ========== INTERNAL HELPERS ==========

    private static List<EmployeeTimeLogs> filterLogsByDateRange(
            List<EmployeeTimeLogs> logs,
            String monthYear,
            int startDay,
            int endDay) {

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

    private static double calculateLateUndertimeTotal(Employee employee, List<EmployeeTimeLogs> logs) {
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
