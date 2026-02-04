package motorph.model.users;

import java.util.List;

import motorph.model.EmployeeDetails;
import motorph.model.Role;
import motorph.model.core.Employee;

/**
 * Represents a Human Resources department user in the MotorPH payroll system.
 * Child class of Employee that provides access to HR features like
 * employee management, attendance tracking, and leave request approval.
 */
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
