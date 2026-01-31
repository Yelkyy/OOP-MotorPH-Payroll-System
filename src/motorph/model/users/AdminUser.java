package motorph.model.users;

import java.util.List;

import motorph.model.User;
import motorph.model.Role;

public class AdminUser extends User {
    public AdminUser(String username, String firstName, String lastName, String employeeId) {
        super(username, firstName, lastName, Role.ADMIN, employeeId);
    }

    @Override
    public List<String> getMenuItems() {
        return List.of(
                "Manage User Accounts",
                "Update Role/Access Rights",
                "System Reports");
    }
}
