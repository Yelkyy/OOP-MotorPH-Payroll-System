package motorph.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.Duration;
import java.time.format.ResolverStyle;
import java.util.Locale;

import motorph.model.EmployeeDetails;
import motorph.model.EmployeeTimeLogs;
import motorph.utils.EmployeeDataUtil;
import motorph.utils.EmployeeDataUtil.DeductionBreakdown;

public class PayrollService {

    private static final Scanner scanner = new Scanner(System.in);

    // Constants
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

    private static final DateTimeFormatter TIMELOG_DATE_FMT =
            DateTimeFormatter.ofPattern("MM/dd/uuuu", Locale.US).withResolverStyle(ResolverStyle.STRICT);

    private static LocalDate parseLogDate(String raw) {
        return LocalDate.parse(raw == null ? "" : raw.trim(), TIMELOG_DATE_FMT);
    }

    // Main method to handle payroll based on user choice
    public static void processPayroll(EmployeeDetails employee, List<EmployeeTimeLogs> logs, String monthYear) {

        int choice = choosePayPeriod();
        if (choice == -1) return;

        YearMonth yearMonth = YearMonth.parse(monthYear, DateTimeFormatter.ofPattern("MM-yyyy"));
        int lastDayOfMonth = yearMonth.lengthOfMonth();

        int startDay = (choice == 1) ? 1 : 16;
        int endDay = (choice == 1) ? 15 : lastDayOfMonth;

        System.out.printf("\nProcessing Payroll for %s | Days %d to %d\n", monthYear, startDay, endDay);

        List<EmployeeTimeLogs> filteredLogs = filterLogsByDateRange(logs, monthYear, startDay, endDay);
        printPayrollSummary(employee, filteredLogs, monthYear, startDay, endDay);
    }

    private static int choosePayPeriod() {
        System.out.println("Select Pay Period: [1] 1-15 or [2] 16-EndOfMonth");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice != 1 && choice != 2) {
            System.out.println("Invalid choice. Returning to menu.");
            return -1;
        }
        return choice;
    }

    // Filter the logs based on the month/year and the week range
    private static List<EmployeeTimeLogs> filterLogsByDateRange(List<EmployeeTimeLogs> logs, String monthYear, int startDay, int endDay) {
        List<EmployeeTimeLogs> filteredLogs = new ArrayList<>();
        DateTimeFormatter ymFormatter = DateTimeFormatter.ofPattern("MM-yyyy");

        for (EmployeeTimeLogs log : logs) {
            try {
                LocalDate logDate = parseLogDate(log.getDate());
                String logMonthYear = logDate.format(ymFormatter);

                if (logMonthYear.equals(monthYear) && logDate.getDayOfMonth() >= startDay && logDate.getDayOfMonth() <= endDay) {
                    filteredLogs.add(log);
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format in logs: " + e.getMessage());
            }
        }
        return filteredLogs;
    }

    // Display the payroll summary for the employee
    private static void printPayrollSummary(EmployeeDetails employee, List<EmployeeTimeLogs> logs,
                                            String monthYear, int startDay, int endDay) {
        boolean hasDeductions = (endDay != 15); // Deduct only on second half or full month
        double semiMonthlyBasic = employee.getBasicSalary() / 2;
        double totalCompensation = employee.getRiceSubsidy() + employee.getPhoneAllowance() + employee.getClothingAllowance();

        List<String[]> lateDeductions = hasDeductions ? calculateLateUndertime(logs) : new ArrayList<>();
        double totalLateUndertime = hasDeductions ? extractTotalLateDeductions(lateDeductions) : 0.0;

        double sss = hasDeductions ? calculateSSS(semiMonthlyBasic) : 0.0;
        double philhealth = hasDeductions ? calculatePhilHealth(semiMonthlyBasic) : 0.0;
        double pagibig = hasDeductions ? calculatePagIbig(semiMonthlyBasic) : 0.0;

        double nonTaxDeductions = totalLateUndertime + sss + philhealth + pagibig;
        double taxableIncome = semiMonthlyBasic - nonTaxDeductions;
        taxableIncome = Math.max(0, taxableIncome);

        double tax = hasDeductions ? calculateTax(taxableIncome) : 0.0;

        double totalDeductions = nonTaxDeductions + tax;
        double netPay = semiMonthlyBasic + totalCompensation - totalDeductions;

        System.out.println("=============================================");
        System.out.println("          PAYROLL SUMMARY          ");
        System.out.println("=============================================");
        System.out.printf("Employee        : %s (%s)%n", employee.getFullName(), employee.getEmployeeNumber());
        System.out.printf("Payroll Period  : %s | Days %d - %d%n", monthYear, startDay, endDay);
        System.out.println("-------------------------------------");
        System.out.printf("Basic Salary       : PHP %,10.2f%n", semiMonthlyBasic);
        System.out.printf("Rice Subsidy       : PHP %,10.2f%n", employee.getRiceSubsidy());
        System.out.printf("Phone Allowance    : PHP %,10.2f%n", employee.getPhoneAllowance());
        System.out.printf("Clothing Allowance : PHP %,10.2f%n", employee.getClothingAllowance());
        System.out.println("-------------------------------------");
        System.out.printf("Total Compensation : PHP %,10.2f%n", totalCompensation);
        System.out.println("-------------------------------------");

        if (hasDeductions) {
            printLateUndertimeDetails(lateDeductions);
            System.out.printf("Late & Undertime   : PHP %,10.2f%n", totalLateUndertime);
            System.out.printf("SSS Contribution   : PHP %,10.2f%n", sss);
            System.out.printf("PhilHealth         : PHP %,10.2f%n", philhealth);
            System.out.printf("Pag-IBIG           : PHP %,10.2f%n", pagibig);
            System.out.printf("Withholding Tax    : PHP %,10.2f%n", tax);
            System.out.println("-------------------------------------");
            System.out.printf("Total Deductions   : PHP %,10.2f%n", totalDeductions);
            System.out.println("-------------------------------------");
        }

        System.out.printf("Net Pay            : PHP %,10.2f%n", netPay);
        System.out.println("=============================================");
    }

    // Print detailed late & undertime report
    private static void printLateUndertimeDetails(List<String[]> lateDeductions) {
        if (lateDeductions.isEmpty()) return;

        System.out.println("\nLate & Undertime Details:");
        System.out.println("-----------------------------------------");
        System.out.printf("%-12s %-14s %-18s %-12s%n", "Date", "Minutes Late", "Minutes Undertime", "Deduction");
        System.out.println("-----------------------------------------");

        for (int i = 0; i < lateDeductions.size() - 1; i++) {
            String[] row = lateDeductions.get(i);
            System.out.printf("%-12s %-14s %-18s PHP %10s%n", row[0], row[1], row[2], row[3]);
        }

        String[] totalRow = lateDeductions.get(lateDeductions.size() - 1);
        System.out.println("-----------------------------------------");
        System.out.printf("%-44s PHP %10s%n", "TOTAL", totalRow[3]);
        System.out.println();
    }

    // Calculate late and undertime deductions based on time logs
    private static List<String[]> calculateLateUndertime(List<EmployeeTimeLogs> logs) {
        List<String[]> lateDeductions = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
        LocalTime scheduledIn = LocalTime.parse("09:00", formatter);
        LocalTime scheduledOut = LocalTime.parse("18:00", formatter);
        double totalDeduction = 0.0;

        for (EmployeeTimeLogs log : logs) {
            try {
                LocalTime actualIn = LocalTime.parse(log.getLogIn().trim(), formatter);
                LocalTime actualOut = LocalTime.parse(log.getLogOut().trim(), formatter);

                long minutesLate = Math.max(0, Duration.between(scheduledIn, actualIn).toMinutes());
                long minutesUndertime = Math.max(0, Duration.between(actualOut, scheduledOut).toMinutes());

                double deduction = ((minutesLate + minutesUndertime) / 60.0) * 100;
                totalDeduction += deduction;

                // Add deduction details for each log
                lateDeductions.add(new String[] {
                        log.getDate(),
                        String.valueOf(minutesLate),
                        String.valueOf(minutesUndertime),
                        String.format("%,.2f", deduction)
                });

            } catch (DateTimeParseException e) {
                System.out.println(
                        "Invalid time format for Employee #" + log.getEmployeeNumber() + ": " + e.getMessage());
            }
        }

        // Add total deduction at the end of the list
        lateDeductions.add(new String[] { "TOTAL", "", "", String.format("%,.2f", totalDeduction) });
        return lateDeductions;
    }

    // Calculate the SSS contribution
    private static double calculateSSS(double basicSalary) {
        if (basicSalary > SSS_MAX_SALARY) {
            return SSS_MAX_CONTRIBUTION;
        }
        return basicSalary * SSS_RATE;
    }

    // Calculate the PhilHealth contribution
    private static double calculatePhilHealth(double basicSalary) {
        double premium;

        if (basicSalary <= PHILHEALTH_LOWER_LIMIT) {
            premium = 300; // Fixed premium for salary <= 10,000
        } else if (basicSalary <= PHILHEALTH_UPPER_LIMIT) {
            premium = Math.min(basicSalary * PHILHEALTH_RATE, PHILHEALTH_MAX_PREMIUM);
        } else {
            premium = PHILHEALTH_MAX_PREMIUM;
        }

        return premium / 2; // Employee pays 50% of the premium
    }

    // Calculate the Pag-IBIG contribution
    private static double calculatePagIbig(double basicSalary) {
        return Math.min(PAGIBIG_MAX_CONTRIBUTION, basicSalary * PAGIBIG_RATE);
    }

    // Calculate the withholding tax based on taxable income
    private static double calculateTax(double taxableIncome) {
        double tax = 0.0;

        if (taxableIncome <= TAX_BRACKET_1_LIMIT) {
            tax = 0;
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

    public static double calculateNetPay(EmployeeDetails employee, List<EmployeeTimeLogs> logs,
            String monthYear, int payPeriod){

        YearMonth yearMonth = YearMonth.parse(monthYear, DateTimeFormatter.ofPattern("MM-yyyy"));
        int lastDayOfMonth = yearMonth.lengthOfMonth();

        int startDay = (payPeriod == 1) ? 1 : 16;
        int endDay = (payPeriod == 1) ? 15 : lastDayOfMonth;

        boolean hasDeductions = (endDay != 15);

        double semiMonthlyBasic = employee.getBasicSalary() / 2;
        double totalCompensation = employee.getRiceSubsidy() +
                                   employee.getPhoneAllowance() +
                                   employee.getClothingAllowance();

        List<EmployeeTimeLogs> filteredLogs = filterLogsByDateRange(logs, monthYear, startDay, endDay);

        if (filteredLogs.isEmpty()) {
            return 0.0;
        }

        List<String[]> lateDeductions = hasDeductions ? calculateLateUndertime(filteredLogs) : new ArrayList<>();
        double totalLateUndertimeDeductions = hasDeductions ? extractTotalLateDeductions(lateDeductions) : 0.0;

        double sss = hasDeductions ? calculateSSS(semiMonthlyBasic) : 0.0;
        double philhealth = hasDeductions ? calculatePhilHealth(semiMonthlyBasic) : 0.0;
        double pagibig = hasDeductions ? calculatePagIbig(semiMonthlyBasic) : 0.0;

        double nonTaxDeductions = totalLateUndertimeDeductions + sss + philhealth + pagibig;

        double taxableIncome = semiMonthlyBasic - nonTaxDeductions;
        taxableIncome = Math.max(0, taxableIncome);

        double tax = hasDeductions ? calculateTax(taxableIncome) : 0.0;

        double totalDeductions = nonTaxDeductions + tax;
        double netPay = semiMonthlyBasic + totalCompensation - totalDeductions;

        return netPay;
    }

    private static double extractTotalLateDeductions(List<String[]> lateDeductions) {
        if (lateDeductions.isEmpty()) return 0.0;

        String[] totalRow = lateDeductions.get(lateDeductions.size() - 1);
        try {
            return Double.parseDouble(totalRow[3].replace(",", ""));
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Error extracting total late deduction: " + e.getMessage());
            return 0.0;
        }
    }

    public static DeductionBreakdown computeDeductions(EmployeeDetails employee,
            List<EmployeeTimeLogs> logs, String monthYear, int payPeriod) {
        DeductionBreakdown result = new DeductionBreakdown();

        YearMonth yearMonth = YearMonth.parse(monthYear, DateTimeFormatter.ofPattern("MM-yyyy"));
        int lastDayOfMonth = yearMonth.lengthOfMonth();
        int startDay = (payPeriod == 1) ? 1 : 16;
        int endDay = (payPeriod == 1) ? 15 : lastDayOfMonth;
        boolean hasDeductions = (endDay != 15);

        double semiMonthlyBasic = employee.getBasicSalary() / 2;
        double totalComp = employee.getRiceSubsidy() + employee.getPhoneAllowance() + employee.getClothingAllowance();

        List<EmployeeTimeLogs> filteredLogs = filterLogsByDateRange(logs, monthYear, startDay, endDay);
        List<String[]> lateDeductions = hasDeductions ? calculateLateUndertime(filteredLogs) : new ArrayList<>();
        double lateUndertime = hasDeductions ? extractTotalLateDeductions(lateDeductions) : 0.0;

        result.lateUndertime = lateUndertime;
        result.sss = hasDeductions ? calculateSSS(semiMonthlyBasic) : 0.0;
        result.philhealth = hasDeductions ? calculatePhilHealth(semiMonthlyBasic) : 0.0;
        result.pagibig = hasDeductions ? calculatePagIbig(semiMonthlyBasic) : 0.0;

        double nonTaxDeductions = result.lateUndertime + result.sss + result.philhealth + result.pagibig;
        double taxableIncome = Math.max(0, semiMonthlyBasic - nonTaxDeductions);

        result.tax = hasDeductions ? calculateTax(taxableIncome) : 0.0;
        result.totalDeductions = nonTaxDeductions + result.tax;
        result.netPay = semiMonthlyBasic + totalComp - result.totalDeductions;

        return result;
    }

    public static DeductionBreakdown computeDeductions(EmployeeDetails emp, String monthYear, int payPeriod) {
        List<EmployeeTimeLogs> logs = EmployeeDataUtil.getTimeLogsForEmployee(emp.getEmployeeNumber());
        return computeDeductions(emp, logs, monthYear, payPeriod);
    }
}
