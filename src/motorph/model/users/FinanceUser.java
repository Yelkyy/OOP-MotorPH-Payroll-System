package motorph.model.users;

import java.util.List;

import motorph.model.Role;
import motorph.model.User;

public class FinanceUser extends User {
    public FinanceUser(String username, String firstName, String lastName, String employeeId) {
        super(username, firstName, lastName, Role.FINANCE, employeeId);
    }

    @Override
    public List<String> getMenuItems() {
        return List.of(
                "Generate Employee Payroll",
                "Generate Payslip",
                "View Payroll Reports",
                "Print Payslip");
    }
}
