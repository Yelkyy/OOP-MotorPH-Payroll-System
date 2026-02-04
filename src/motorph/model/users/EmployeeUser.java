package motorph.model.users;

import java.util.List;

import motorph.model.EmployeeDetails;
import motorph.model.Role;
import motorph.model.core.Employee;

/**
 * Represents a regular employee user in the MotorPH payroll system.
 * Child class of Employee that provides access to employee-specific features
 * like
 * viewing profile, recording attendance, submitting leave requests, and viewing
 * payslips.
 */
public class EmployeeUser extends Employee {
    public EmployeeUser(EmployeeDetails details) {
        super(details, Role.EMPLOYEE);
    }

    @Override
    public List<String> getMenuItems() {
        return List.of(
                "My Profile",
                "Attendance", //time-in, time-out, attendance list
                "Leave", // leave request fill up and table
                "View Payslip",
                "Print Payslip");
    }
}
