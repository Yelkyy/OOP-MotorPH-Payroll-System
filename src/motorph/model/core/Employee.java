package motorph.model.core;

import java.util.List;

import motorph.model.EmployeeDetails;
import motorph.model.Role;

/**
 * Abstract parent class representing an employee in the MotorPH payroll system.
 * Encapsulates common employee data and provides access to employee details and
 * role information.
 * Subclasses must implement specific employee types with their respective
 * behaviors.
 */
public abstract class Employee {

    private final EmployeeDetails details;
    private final Role role;

    protected Employee(EmployeeDetails details, Role role) {
        if (details == null)
            throw new IllegalArgumentException("EmployeeDetails cannot be null");
        if (role == null)
            throw new IllegalArgumentException("Role cannot be null");
        this.details = details;
        this.role = role;
    }

    // Role / identity
    public Role getRole() {
        return role;
    }

    public String getEmployeeNumber() {
        return details.getEmployeeNumber();
    }

    // Common employee info
    public String getFullName() {
        return details.getFullName();
    }

    public String getFirstName() {
        return details.getFirstName();
    }

    public String getLastName() {
        return details.getLastName();
    }

    public String getBirthday() {
        return details.getBirthday();
    }

    public String getAddress() {
        return details.getAddress();
    }

    public String getPhoneNumber() {
        return details.getPhoneNumber();
    }

    public String getStatus() {
        return details.getStatus();
    }

    public boolean isRegular() {
        return details.getStatus() != null
                && details.getStatus().equalsIgnoreCase("Regular");
    }

    public boolean isProbationary() {
        return details.getStatus() != null
                && (details.getStatus().equalsIgnoreCase("Probationary")
                        || details.getStatus().equalsIgnoreCase("Probation"));
    }

    public String getPosition() {
        return details.getPosition();
    }

    public String getImmediateSupervisor() {
        return details.getImmediateSupervisor();
    }

    // Note: add more getters only when needed by UI/screens
    public String getSssNumber() {
        return details.getSssNumber();
    }

    public String getPhilhealthNumber() {
        return details.getPhilhealthNumber();
    }

    public String getTinNumber() {
        return details.getTinNumber();
    }

    public String getPagIbigNumber() {
        return details.getPagIbigNumber();
    }

    public double getBasicSalary() {
        return details.getBasicSalary();
    }

    public double getRiceSubsidy() {
        return details.getRiceSubsidy();
    }

    public double getPhoneAllowance() {
        return details.getPhoneAllowance();
    }

    public double getClothingAllowance() {
        return details.getClothingAllowance();
    }

    public double getHourlyRate() {
        return details.getHourlyRate();
    }

    // Menu list
    public abstract List<String> getMenuItems();

}
