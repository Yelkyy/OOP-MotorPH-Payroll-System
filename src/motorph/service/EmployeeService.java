package motorph.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import motorph.model.Role;
import motorph.model.core.Employee;
import motorph.model.EmployeeTimeLogs;
import motorph.model.users.AdminUser;
import motorph.model.users.EmployeeUser;
import motorph.model.users.FinanceUser;
import motorph.model.users.HrUser;
import motorph.dao.DataHandler;

/**
 * Service class for employee management operations.
 * Provides CRUD operations for employee data, time log retrieval, and employee
 * role creation.
 */
public class EmployeeService {

    // UI safe for finding one employee using one employee id
    public static Employee getEmployeeById(String employeeNumber) {
        if (employeeNumber == null)
            return null;

        Object[] data = DataHandler.readEmployeeData(employeeNumber);
        if (data == null) {
            return null;
        }

        return createEmployeeFromData(data, Role.EMPLOYEE);
    }

    // Get all employees (UI-safe)
    public static List<Employee> getAllEmployees() {
        return DataHandler.readAllEmployeeData()
                .stream()
                .map(data -> createEmployeeFromData(data, Role.EMPLOYEE))
                .collect(Collectors.toList());
    }

    // Get time logs for an employee (Payroll/UI-safe)
    public static List<EmployeeTimeLogs> getTimeLogsForEmployee(String employeeNumber) {
        if (employeeNumber == null)
            return List.of();

        return DataHandler.readEmployeeTimeLogs()
                .stream()
                .filter(log -> employeeNumber.equals(log.getEmployeeNumber()))
                .collect(Collectors.toList());
    }

    // Generate a new employee id
    public static String generateNewEmployeeId() {
        return DataHandler.generateNewEmpId();
    }

    public static Employee loadEmployeeForRole(String employeeNumber, Role role) {
        if (employeeNumber == null || role == null) {
            return null;
        }

        Object[] data = DataHandler.readEmployeeData(employeeNumber);
        if (data == null) {
            return null;
        }

        return createEmployeeFromData(data, role);
    }

    private static Employee createEmployeeFromData(Object[] params, Role role) {
        String employeeNumber = (String) params[0];
        String lastName = (String) params[1];
        String firstName = (String) params[2];
        String birthday = (String) params[3];
        String address = (String) params[4];
        String phoneNumber = (String) params[5];
        String sssNumber = (String) params[6];
        String philhealthNumber = (String) params[7];
        String tinNumber = (String) params[8];
        String pagIbigNumber = (String) params[9];
        String status = (String) params[10];
        String position = (String) params[11];
        String immediateSupervisor = (String) params[12];
        double basicSalary = (double) params[13];
        double riceSubsidy = (double) params[14];
        double phoneAllowance = (double) params[15];
        double clothingAllowance = (double) params[16];
        double grossSemiMonthlyRate = (double) params[17];
        double hourlyRate = (double) params[18];

        switch (role) {
            case ADMIN:
                return new AdminUser(employeeNumber, lastName, firstName, birthday, address,
                        phoneNumber, sssNumber, philhealthNumber, tinNumber, pagIbigNumber,
                        status, position, immediateSupervisor, basicSalary, riceSubsidy,
                        phoneAllowance, clothingAllowance, grossSemiMonthlyRate, hourlyRate);
            case HR:
                return new HrUser(employeeNumber, lastName, firstName, birthday, address,
                        phoneNumber, sssNumber, philhealthNumber, tinNumber, pagIbigNumber,
                        status, position, immediateSupervisor, basicSalary, riceSubsidy,
                        phoneAllowance, clothingAllowance, grossSemiMonthlyRate, hourlyRate);
            case FINANCE:
                return new FinanceUser(employeeNumber, lastName, firstName, birthday, address,
                        phoneNumber, sssNumber, philhealthNumber, tinNumber, pagIbigNumber,
                        status, position, immediateSupervisor, basicSalary, riceSubsidy,
                        phoneAllowance, clothingAllowance, grossSemiMonthlyRate, hourlyRate);
            case EMPLOYEE:
            default:
                return new EmployeeUser(employeeNumber, lastName, firstName, birthday, address,
                        phoneNumber, sssNumber, philhealthNumber, tinNumber, pagIbigNumber,
                        status, position, immediateSupervisor, basicSalary, riceSubsidy,
                        phoneAllowance, clothingAllowance, grossSemiMonthlyRate, hourlyRate);
        }
    }

    // ==========================================
    // ==========================================
    // CRUD Operations
    // ==========================================

    public void add(Employee employee) {
        DataHandler.addEmployeeToCSV(employee);
    }

    public void update(Employee employee) {
        DataHandler.updateEmployeeInCSV(employee);
    }

    public void delete(String employeeNumber) {
        if (isAdminEmployee(employeeNumber)) {
            throw new IllegalStateException(
                    "Admin employee cannot be deleted. System must always have an administrator.");
        }
        DataHandler.removeEmployeeFromCSV(employeeNumber);
        DataHandler.deleteTimeLogsByEmployeeNumber(employeeNumber);
    }

    public Employee findById(String employeeNumber) {
        return getEmployeeById(employeeNumber);
    }

    public List<Employee> findAll() {
        return getAllEmployees();
    }

    // ==========================================
    // Alternate method names (for convenience)
    // ==========================================

    public void addEmployee(Employee employee) {
        add(employee);
    }

    public void updateEmployee(Employee employee) {
        update(employee);
    }

    public void deleteEmployee(String employeeNumber) {
        delete(employeeNumber);
    }

    public Employee findEmployeeById(String employeeNumber) {
        return findById(employeeNumber);
    }

    public List<Employee> findAllEmployees() {
        return findAll();
    }

    // ==========================================
    // Helper methods
    // ==========================================

    private boolean isAdminEmployee(String employeeNumber) {
        Path filePath = Paths.get("resources", "MotorPH Users.csv");

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length < 6)
                    continue;

                String role = parts[4].trim();
                String empNo = parts[5].trim();

                if (empNo.equals(employeeNumber)
                        && role.equalsIgnoreCase("ADMIN")) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading Users.csv: " + e.getMessage());
        }

        return false;
    }

}
