package motorph.service;

import motorph.model.core.Employee;
import motorph.model.users.AdminUser;
import motorph.model.users.EmployeeUser;
import motorph.model.users.FinanceUser;
import motorph.model.users.HrUser;
import motorph.dao.DataHandler;

/**
 * HRService - Handles HR operations like employee management and leave
 * approvals.
 * Centralizes business logic for adding and updating employees.
 */
public class HRService {

    public String addEmployee(Employee employee) {
        DataHandler.addEmployeeToCSV(employee);

        System.out.println("Success! New Employee Added by HR");

        return employee.getEmployeeNumber();
    }

    public void updateEmployee(Employee employee) {
        EmployeeService employeeService = new EmployeeService();
        employeeService.updateEmployee(employee);
        System.out.println("Employee updated by HR");
    }

    public void updateSalary(Employee employee, double basicSalary, double riceSubsidy,
            double phoneAllowance, double clothingAllowance) {

        System.out.println("Salary and allowances updated by HR");

        EmployeeService employeeService = new EmployeeService();
        employeeService.updateEmployee(employee);
    }

    public Employee createEmployeeOfSameType(Employee originalEmployee, String employeeNumber,
            String lastName, String firstName, String birthday,
            String address, String phoneNumber, String sssNumber,
            String philhealthNumber, String tinNumber,
            String pagIbigNumber, String status, String position,
            String immediateSupervisor, double basicSalary,
            double riceSubsidy, double phoneAllowance,
            double clothingAllowance, double grossSemiMonthlyRate,
            double hourlyRate) {

        if (originalEmployee instanceof AdminUser) {
            return new AdminUser(employeeNumber, lastName, firstName, birthday, address,
                    phoneNumber, sssNumber, philhealthNumber, tinNumber, pagIbigNumber,
                    status, position, immediateSupervisor, basicSalary, riceSubsidy,
                    phoneAllowance, clothingAllowance, grossSemiMonthlyRate, hourlyRate);
        } else if (originalEmployee instanceof HrUser) {
            return new HrUser(employeeNumber, lastName, firstName, birthday, address,
                    phoneNumber, sssNumber, philhealthNumber, tinNumber, pagIbigNumber,
                    status, position, immediateSupervisor, basicSalary, riceSubsidy,
                    phoneAllowance, clothingAllowance, grossSemiMonthlyRate, hourlyRate);
        } else if (originalEmployee instanceof FinanceUser) {
            return new FinanceUser(employeeNumber, lastName, firstName, birthday, address,
                    phoneNumber, sssNumber, philhealthNumber, tinNumber, pagIbigNumber,
                    status, position, immediateSupervisor, basicSalary, riceSubsidy,
                    phoneAllowance, clothingAllowance, grossSemiMonthlyRate, hourlyRate);
        } else {
            return new EmployeeUser(employeeNumber, lastName, firstName, birthday, address,
                    phoneNumber, sssNumber, philhealthNumber, tinNumber, pagIbigNumber,
                    status, position, immediateSupervisor, basicSalary, riceSubsidy,
                    phoneAllowance, clothingAllowance, grossSemiMonthlyRate, hourlyRate);
        }
    }

    public void deleteEmployee(String employeeNumber) {
        EmployeeService employeeService = new EmployeeService();
        employeeService.deleteEmployee(employeeNumber);
        System.out.println("Employee deleted by HR");
    }

    public void approveLeaveRequest(String requestId, String reviewedBy) {
        LeaveService leaveService = new LeaveService();
        leaveService.updateLeaveStatusByRequestId(requestId, "Approved", reviewedBy);
        System.out.println("Leave approved by HR");
    }

    public void rejectLeaveRequest(String requestId, String reviewedBy) {
        LeaveService leaveService = new LeaveService();
        leaveService.updateLeaveStatusByRequestId(requestId, "Rejected", reviewedBy);
        System.out.println("Leave rejected by HR");
    }
}
