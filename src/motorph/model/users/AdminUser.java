package motorph.model.users;

import java.util.List;

import motorph.model.EmployeeDetails;
import motorph.model.Role;
import motorph.model.core.Employee;

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
