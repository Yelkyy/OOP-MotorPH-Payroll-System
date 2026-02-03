package motorph.model;

public class User {

    private String username;
    private Role role;
    private String employeeNumber;

    private String firstName;
    private String lastName;

    public User(String username, String firstName, String lastName, Role role, String employeeNumber) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.employeeNumber = employeeNumber;
    }

    public String getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
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
}
