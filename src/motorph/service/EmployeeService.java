package motorph.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import motorph.contracts.EmployeeCrudOperations;
import motorph.model.EmployeeDetails;
import motorph.model.EmployeeTimeLogs;
import motorph.repository.DataHandler;

public class EmployeeService implements EmployeeCrudOperations {

    // UI safe for finding one employee using one employee id
    public static EmployeeDetails getEmployeeById(String employeeNumber) {
        if (employeeNumber == null)
            return null;

        return DataHandler.readEmployeeDetails()
                .stream()
                .filter(e -> employeeNumber.equals(e.getEmployeeNumber()))
                .findFirst()
                .orElse(null);
    }

    // Get all employees (UI-safe)
    public static List<EmployeeDetails> getAllEmployees() {
        return DataHandler.readEmployeeDetails();
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

    // ==========================================
    // Interface implementation (instance methods)
    // These satisfy EmployeeCrudOperations contract.
    // They delegate to DataHandler.
    // ===========================================

    @Override
    public void addEmployee(EmployeeDetails employee) {
        DataHandler.addEmployeeToCSV(employee);
    }

    @Override
    public void updateEmployee(EmployeeDetails employee) {
        DataHandler.updateEmployeeInCSV(employee);
    }

    @Override
    public void deleteEmployee(String employeeNumber) {

        if (isAdminEmployee(employeeNumber)) {
            throw new IllegalStateException(
                    "Admin employee cannot be deleted. System must always have an administrator.");
        }

        DataHandler.removeEmployeeFromCSV(employeeNumber);
        DataHandler.deleteTimeLogsByEmployeeNumber(employeeNumber);
    }

    @Override
    public EmployeeDetails findEmployeeById(String employeeNumber) {
        return getEmployeeById(employeeNumber);
    }

    @Override
    public List<EmployeeDetails> findAllEmployees() {
        return getAllEmployees();
    }

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
