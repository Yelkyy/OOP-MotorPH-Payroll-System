package motorph.model.core;

import java.time.LocalDate;
import java.util.List;
import motorph.model.Role;
import motorph.model.payroll.Payable;
import motorph.model.payroll.PayrollCalculator;
import motorph.model.EmployeeTimeLogs;

/**
 * Abstract parent class representing an employee in the MotorPH payroll system.
 * Encapsulates common employee data and enforces payroll contract through
 * Payable interface.
 * Subclasses must implement specific employee types (Admin, HR, Finance,
 * Employee roles).
 * All employees participate in payroll processing.
 */
public abstract class Employee implements Payable {

    // Personal Information
    protected String employeeNumber;
    protected String lastName;
    protected String firstName;
    protected String birthday;
    protected String address;
    protected String phoneNumber;

    // Government IDs
    protected String sssNumber;
    protected String philhealthNumber;
    protected String tinNumber;
    protected String pagIbigNumber;

    // Employment Information
    protected String status;
    protected String position;
    protected String immediateSupervisor;
    protected Role role;

    // Salary Components
    protected double basicSalary;
    protected double riceSubsidy;
    protected double phoneAllowance;
    protected double clothingAllowance;
    protected double grossSemiMonthlyRate;
    protected double hourlyRate;

    /**
     * Constructor for Employee class.
     * Subclasses must call this constructor using super().
     */
    protected Employee(String employeeNumber, String lastName, String firstName, String birthday,
            String address, String phoneNumber, String sssNumber, String philhealthNumber,
            String tinNumber, String pagIbigNumber, String status, String position,
            String immediateSupervisor, double basicSalary, double riceSubsidy,
            double phoneAllowance, double clothingAllowance, double grossSemiMonthlyRate,
            double hourlyRate, Role role) {

        if (employeeNumber == null || employeeNumber.trim().isEmpty())
            throw new IllegalArgumentException("Employee number cannot be null or empty");
        if (role == null)
            throw new IllegalArgumentException("Role cannot be null");

        this.employeeNumber = employeeNumber;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthday = birthday;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.sssNumber = sssNumber;
        this.philhealthNumber = philhealthNumber;
        this.tinNumber = tinNumber;
        this.pagIbigNumber = pagIbigNumber;
        this.status = status;
        this.position = position;
        this.immediateSupervisor = immediateSupervisor;
        this.basicSalary = basicSalary;
        this.riceSubsidy = riceSubsidy;
        this.phoneAllowance = phoneAllowance;
        this.clothingAllowance = clothingAllowance;
        this.grossSemiMonthlyRate = grossSemiMonthlyRate;
        this.hourlyRate = hourlyRate;
        this.role = role;
    }

    // ==================== ABSTRACT METHODS ====================

    /**
     * Calculates the gross salary for this employee.
     * Gross salary = basic salary + all allowances (rice, phone, clothing).
     * 
     * Implements Payable contract. Employee owns this payroll behavior.
     * Child classes inherit this implementation unless they need different logic.
     *
     * @return the gross salary amount
     */
    @Override
    public double getGrossSalary() {
        return basicSalary + riceSubsidy + phoneAllowance + clothingAllowance;
    }

    /**
     * Returns the menu items available for this employee type.
     */
    public abstract List<String> getMenuItems();

    // ==================== GETTERS ====================

    public Role getRole() {
        return role;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSssNumber() {
        return sssNumber;
    }

    public String getPhilhealthNumber() {
        return philhealthNumber;
    }

    public String getTinNumber() {
        return tinNumber;
    }

    public String getPagIbigNumber() {
        return pagIbigNumber;
    }

    public String getStatus() {
        return status;
    }

    public String getPosition() {
        return position;
    }

    public String getImmediateSupervisor() {
        return immediateSupervisor;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public double getRiceSubsidy() {
        return riceSubsidy;
    }

    public double getPhoneAllowance() {
        return phoneAllowance;
    }

    public double getClothingAllowance() {
        return clothingAllowance;
    }

    public double getGrossSemiMonthlyRate() {
        return grossSemiMonthlyRate;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    // ==================== CONVENIENCE METHODS ====================

    public boolean isRegular() {
        return status != null && status.equalsIgnoreCase("Regular");
    }

    public boolean isProbationary() {
        return status != null && (status.equalsIgnoreCase("Probationary")
                || status.equalsIgnoreCase("Probation"));
    }

    // ==================== SETTERS ====================

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setSssNumber(String sssNumber) {
        this.sssNumber = sssNumber;
    }

    public void setPhilhealthNumber(String philhealthNumber) {
        this.philhealthNumber = philhealthNumber;
    }

    public void setTinNumber(String tinNumber) {
        this.tinNumber = tinNumber;
    }

    public void setPagIbigNumber(String pagIbigNumber) {
        this.pagIbigNumber = pagIbigNumber;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setImmediateSupervisor(String immediateSupervisor) {
        this.immediateSupervisor = immediateSupervisor;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public void setRiceSubsidy(double riceSubsidy) {
        this.riceSubsidy = riceSubsidy;
    }

    public void setPhoneAllowance(double phoneAllowance) {
        this.phoneAllowance = phoneAllowance;
    }

    public void setClothingAllowance(double clothingAllowance) {
        this.clothingAllowance = clothingAllowance;
    }

    public void setGrossSemiMonthlyRate(double grossSemiMonthlyRate) {
        this.grossSemiMonthlyRate = grossSemiMonthlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    @Override
    public PayrollCalculator.PayrollResult computeDeductions(
            List<EmployeeTimeLogs> logs,
            String monthYear,
            int payPeriod) {
        PayrollCalculator calculator = new PayrollCalculator();
        return calculator.computeDeductions(this, logs, monthYear, payPeriod);
    }

    @Override
    public PayrollCalculator.PayrollResult computeDeductionsByCutoff(
            List<EmployeeTimeLogs> logs,
            LocalDate cutoffDate) {
        PayrollCalculator calculator = new PayrollCalculator();
        return calculator.computeDeductionsByCutoff(this, logs, cutoffDate);
    }

    @Override
    public String toString() {
        return "Employee #" + employeeNumber + " | Name: " + firstName + " " + lastName +
                " | Position: " + position + " | Role: " + role;
    }
}
