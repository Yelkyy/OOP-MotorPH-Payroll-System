package motorph.model;

import java.util.Collections;
import java.util.List;

public class User {

    private String username;
    private String firstName;
    private String lastName;
    private Role role;
    private String employeeNumber;

    public User(String username, String firstName, String lastName, Role role, String employeeNumber) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.employeeNumber = employeeNumber;
    }

    // ===== Basic Getters =====
    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public Role getRole() {
        return role;
    }

    public String getemployeeNumber() {
        return employeeNumber;
    }

    // ===== Menu / Role Behavior =====
    // Overridden by AdminUser, HrUser, FinanceUser, EmployeeUser
    public List<String> getMenuItems() {
        return Collections.emptyList();
    }
}
