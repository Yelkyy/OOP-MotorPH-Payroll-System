package motorph.contracts;

import java.time.LocalDate;
import java.util.List;
import motorph.model.EmployeeDetails;
import motorph.model.EmployeeTimeLogs;
import motorph.model.payroll.DeductionBreakdown;

/**
 * Contract interface defining payroll calculation operations including
 * deductions and net pay computation.
 */
public interface PayrollCalculator {

    DeductionBreakdown computeDeductions(
            EmployeeDetails employee,
            List<EmployeeTimeLogs> logs,
            String monthYear,
            int payPeriod);

    default DeductionBreakdown computeDeductionsByCutoff(
            EmployeeDetails employee,
            List<EmployeeTimeLogs> logs,
            LocalDate cutoffDate) {
        String monthYear = cutoffDate.format(java.time.format.DateTimeFormatter.ofPattern("MM-yyyy"));
        int payPeriod = (cutoffDate.getDayOfMonth() <= 15) ? 1 : 2;
        return computeDeductions(employee, logs, monthYear, payPeriod);
    }
}
