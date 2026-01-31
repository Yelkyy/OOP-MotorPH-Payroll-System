package motorph.service;

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

    // Generate a new employee id (optional)
    public static String generateNewEmployeeId() {
        return DataHandler.generateNewEmpId();
    }

    // =========================
    // Interface implementation (instance methods)
    // These satisfy EmployeeCrudOperations contract.
    // They delegate to DataHandler.
    // =========================

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
        DataHandler.removeEmployeeFromCSV(employeeNumber);
    }

    @Override
    public EmployeeDetails findEmployeeById(String employeeNumber) {
        return getEmployeeById(employeeNumber);
    }

    @Override
    public List<EmployeeDetails> findAllEmployees() {
        return getAllEmployees();
    }
}
