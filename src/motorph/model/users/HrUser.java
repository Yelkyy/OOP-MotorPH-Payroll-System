
package motorph.model.users;

import java.util.List;

import motorph.model.User;
import motorph.model.Role;

public class HrUser extends User {
    public HrUser(String username, String firstName, String lastName, String employeeId) {
        super(username, firstName, lastName, Role.HR, employeeId);
    }

    @Override
    public List<String> getMenuItems() {
        return List.of(
                "Manage Employee Records",
                "View Employee Timesheet",
                "Review Leave Requests",
                "Process Payroll");
    }
}