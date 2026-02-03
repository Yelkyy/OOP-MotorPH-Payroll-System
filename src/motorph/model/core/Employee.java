package motorph.model.core;

import motorph.model.EmployeeDetails;
import motorph.model.Role;

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
    public abstract java.util.List<String> getMenuItems();
}
