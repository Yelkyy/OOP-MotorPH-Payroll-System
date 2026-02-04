package motorph.model.users;

import java.util.List;

import motorph.model.EmployeeDetails;
import motorph.model.Role;
import motorph.model.core.Employee;

/**
 * Represents an administrator user in the MotorPH payroll system.
 * Child class of Employee that provides access to administrative features like
 * user account management, role assignments, and system reports.
 */
public class AdminUser extends Employee {
    public AdminUser(EmployeeDetails details) {
        super(details, Role.ADMIN);
    }

    @Override
    public List<String> getMenuItems() {
        return List.of(
                "Manage User Accounts",
                "Update Role/Access Rights",
                "System Reports");
    }
}
