package motorph.model.users;

import java.util.List;

import motorph.model.Role;
import motorph.model.User;

public class EmployeeUser extends User {
    public EmployeeUser(String username, String firstName, String lastName, String employeeId) {
        super(username, firstName, lastName, Role.EMPLOYEE, employeeId);
    }

    @Override
    public List<String> getMenuItems() {
        return List.of(
                "View/Edit Personal",
                "Change Password",
                "Record Attendance",
                "Submit Leave Request",
                "View Payslip",
                "Print Payslip");
    }
}
