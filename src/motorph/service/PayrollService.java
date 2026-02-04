package motorph.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import motorph.contracts.PayrollCalculator;
import motorph.model.EmployeeDetails;
import motorph.model.EmployeeTimeLogs;
import motorph.model.payroll.DeductionBreakdown;

/**
 * Service class providing payroll calculation operations for the MotorPH
 * system.
 * Delegates to PayrollCalculator implementations and handles input validation
 * for payroll processing.
 */
public class PayrollService {

    private static final DateTimeFormatter MONTH_YEAR_FMT = DateTimeFormatter.ofPattern("MM-yyyy");

    // One shared calculator instance (standard rules)
    private static final PayrollCalculator CALCULATOR = new StandardPayrollCalculator();

    private PayrollService() {
        // utility/service facade; prevent instantiation for now
    }

    // Public API (UI-safe)

    public static DeductionBreakdown computeDeductions(EmployeeDetails employee,
            List<EmployeeTimeLogs> logs,
            String monthYear,
            int payPeriod) {
        validateInputs(employee, monthYear, payPeriod);
        return CALCULATOR.computeDeductions(employee, logs, monthYear.trim(), payPeriod);
    }

    public static DeductionBreakdown computeDeductionsByCutoff(EmployeeDetails employee,
            List<EmployeeTimeLogs> logs,
            LocalDate cutoffDate) {
        if (cutoffDate == null) {
            throw new IllegalArgumentException("cutoffDate must not be null");
        }
        String monthYear = cutoffDate.format(MONTH_YEAR_FMT);
        int payPeriod = (cutoffDate.getDayOfMonth() <= 15) ? 1 : 2;
        return computeDeductions(employee, logs, monthYear, payPeriod);
    }

    public static DeductionBreakdown computeDeductions(EmployeeDetails employee,
            String monthYear,
            int payPeriod) {
        validateInputs(employee, monthYear, payPeriod);
        List<EmployeeTimeLogs> logs = EmployeeService.getTimeLogsForEmployee(employee.getEmployeeNumber());
        return computeDeductions(employee, logs, monthYear, payPeriod);
    }

    public static double calculateNetPay(EmployeeDetails employee,
            List<EmployeeTimeLogs> logs,
            String monthYear,
            int payPeriod) {
        DeductionBreakdown breakdown = computeDeductions(employee, logs, monthYear, payPeriod);
        return breakdown.netPay;
    }

    // Validation helpers

    private static void validateInputs(EmployeeDetails employee, String monthYear, int payPeriod) {
        if (employee == null) {
            throw new IllegalArgumentException("employee must not be null");
        }
        if (monthYear == null || monthYear.isBlank()) {
            throw new IllegalArgumentException("monthYear must not be blank");
        }
        if (payPeriod != 1 && payPeriod != 2) {
            throw new IllegalArgumentException("payPeriod must be 1 or 2");
        }
    }
}
