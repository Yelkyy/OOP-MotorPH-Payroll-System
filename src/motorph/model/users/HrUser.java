package motorph.model.users;

import java.util.List;

import motorph.model.EmployeeDetails;
import motorph.model.Role;
import motorph.model.core.Employee;

public class HrUser extends Employee {

    public HrUser(EmployeeDetails details) {
        super(details, Role.HR);
    }

    @Override
    public List<String> getMenuItems() {
        return List.of(
                "My Profile",
                "My Payslip",
                "Employee", // CRUD
                "Attendance", // view all
                "Leave Approval" // approve/reject
        );
    }
}
