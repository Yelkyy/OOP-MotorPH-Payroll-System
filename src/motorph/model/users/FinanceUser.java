package motorph.model.users;

import java.util.List;

import motorph.model.EmployeeDetails;
import motorph.model.Role;
import motorph.model.core.Employee;

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
