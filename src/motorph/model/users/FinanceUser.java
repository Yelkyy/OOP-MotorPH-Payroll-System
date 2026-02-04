package motorph.model.users;

import java.util.List;

import motorph.model.EmployeeDetails;
import motorph.model.Role;
import motorph.model.core.Employee;

/**
 * Represents a finance department user in the MotorPH payroll system.
 * Child class of Employee that provides access to payroll management features
 * like
 * generating employee payroll, creating payslips, and viewing payroll reports.
 */
public class FinanceUser extends Employee {
    public FinanceUser(EmployeeDetails details) {
        super(details, Role.FINANCE);
    }

    @Override
    public List<String> getMenuItems() {
        return List.of(
                "My Profile",
                "Generate Employee Payroll",
                "Generate Payslip",
                "View Payroll Reports",
                "Print Payslip");
    }
}
