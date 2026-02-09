package motorph.model.payroll;

import java.time.LocalDate;
import java.util.List;

import motorph.model.EmployeeTimeLogs;

/**
 * Interface defining the payroll contract for all payrollable entities.
 * Specifies required operations: calculating gross salary and computing
 * deductions.
 * Ensures consistent payroll processing across all employee types (Admin, HR,
 * Finance, Employee).
 */
public interface Payable {

        /** Calculates and returns the gross salary for the employee. */
        double getGrossSalary();

        /**
         * Computes deductions (SSS, PhilHealth, Pag-IBIG, tax, late/undertime) for the
         * given month/period.
         */
        PayrollCalculator.PayrollResult computeDeductions(
                        List<EmployeeTimeLogs> logs,
                        String monthYear,
                        int payPeriod);

        /**
         * Computes deductions by cutoff date (automatically determines month and
         * period).
         */
        PayrollCalculator.PayrollResult computeDeductionsByCutoff(
                        List<EmployeeTimeLogs> logs,
                        LocalDate cutoffDate);

}
