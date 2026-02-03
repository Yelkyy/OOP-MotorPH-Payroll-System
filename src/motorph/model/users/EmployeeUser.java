package motorph.model.users;

import java.util.List;

import motorph.model.EmployeeDetails;
import motorph.model.Role;
import motorph.model.core.Employee;

public class EmployeeUser extends Employee {
    public EmployeeUser(EmployeeDetails details) {
        super(details, Role.EMPLOYEE);
    }

    @Override
    public List<String> getMenuItems() {
        return List.of(
                "My Profle",
                "Change Password",
                "Record Attendance",
                "Submit Leave Request",
                "View Payslip",
                "Print Payslip");
    }
}
