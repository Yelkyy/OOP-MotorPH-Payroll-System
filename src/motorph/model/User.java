package motorph.model;

public class User {
    private String username;
    private String firstName;
    private String lastName;
    private Role role;
    private String employeeId;

    public String getUsername() {
        return username;
    }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Role getRole() {
        return role;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public User(String username, String firstName, String lastName, Role role, String employeeId) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.employeeId = employeeId;
    }


}
