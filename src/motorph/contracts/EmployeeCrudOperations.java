package motorph.contracts;

import java.util.List;
import motorph.model.EmployeeDetails;

/** Contract interface defining CRUD operations for employee management. */
public interface EmployeeCrudOperations {
    void addEmployee(EmployeeDetails employee);

    void updateEmployee(EmployeeDetails employee);

    void deleteEmployee(String employeeNumber);

    EmployeeDetails findEmployeeById(String employeeNumber);

    List<EmployeeDetails> findAllEmployees();
}
