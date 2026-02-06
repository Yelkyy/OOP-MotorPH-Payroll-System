package motorph.service;

import java.time.LocalDate;
import java.util.List;

import motorph.model.core.Employee;
import motorph.model.payroll.PayrollCalculator.PayrollResult;
import motorph.model.EmployeeTimeLogs;

/**
 * Service class providing payroll calculation operations for the MotorPH
 * system.
 * 
 * Responsibilities:
 * - Orchestrates payroll operations (collect inputs, validate, coordinate
 * calculations)
 * - Calls Employee.computeDeductions() via Payable interface (Employee
 * delegates to PayrollCalculator)
 * - Returns results to UI/panels
 * 
 * Architecture:
 * - Employee implements Payable and owns payroll calculation behavior
 * - PayrollCalculator is the math engine that Employee delegates to
 * - PayrollService coordinates the workflow
 */
public class PayrollService {

    private PayrollService() {
        // utility/service facade; prevent instantiation
    }

    // Public API (UI-safe)

    public static PayrollResult computeDeductions(Employee employee,
            List<EmployeeTimeLogs> logs,
            String monthYear,
            int payPeriod) {
        validateInputs(employee, monthYear, payPeriod);
        // Use Payable interface contract
        return employee.computeDeductions(logs, monthYear.trim(), payPeriod);
    }

    public static PayrollResult computeDeductionsByCutoff(Employee employee,
            List<EmployeeTimeLogs> logs,
            LocalDate cutoffDate) {
        if (cutoffDate == null) {
            throw new IllegalArgumentException("cutoffDate must not be null");
        }
        // Use Payable interface contract
        return employee.computeDeductionsByCutoff(logs, cutoffDate);
    }

    public static PayrollResult computeDeductions(Employee employee,
            String monthYear,
            int payPeriod) {
        validateInputs(employee, monthYear, payPeriod);
        List<EmployeeTimeLogs> logs = EmployeeService.getTimeLogsForEmployee(employee.getEmployeeNumber());
        return computeDeductions(employee, logs, monthYear, payPeriod);
    }

    public static double calculateNetPay(Employee employee,
            List<EmployeeTimeLogs> logs,
            String monthYear,
            int payPeriod) {
        PayrollResult breakdown = computeDeductions(employee, logs, monthYear, payPeriod);
        return breakdown.netPay;
    }

    // Validation helpers

    private static void validateInputs(Employee employee, String monthYear, int payPeriod) {
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
