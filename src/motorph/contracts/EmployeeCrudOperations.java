package motorph.contracts;

import java.util.List;
import motorph.model.EmployeeDetails;

public interface EmployeeCrudOperations {
    void addEmployee(EmployeeDetails employee);

    void updateEmployee(EmployeeDetails employee);

    void deleteEmployee(String employeeNumber);

    EmployeeDetails findEmployeeById(String employeeNumber);

    List<EmployeeDetails> findAllEmployees();
}
