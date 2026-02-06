package motorph.model.users;

import java.util.List;

import motorph.model.Role;
import motorph.model.core.Employee;

/**
 * Concrete employee subclass representing a finance department user.
 * Extends Employee to provide access to payroll management features
 * like generating employee payroll, creating payslips, and viewing payroll
 * reports.
 */
public class FinanceUser extends Employee {

    public FinanceUser(String employeeNumber, String lastName, String firstName, String birthday,
            String address, String phoneNumber, String sssNumber, String philhealthNumber,
            String tinNumber, String pagIbigNumber, String status, String position,
            String immediateSupervisor, double basicSalary, double riceSubsidy,
            double phoneAllowance, double clothingAllowance, double grossSemiMonthlyRate,
            double hourlyRate) {
        super(employeeNumber, lastName, firstName, birthday, address, phoneNumber,
                sssNumber, philhealthNumber, tinNumber, pagIbigNumber, status, position,
                immediateSupervisor, basicSalary, riceSubsidy, phoneAllowance,
                clothingAllowance, grossSemiMonthlyRate, hourlyRate, Role.FINANCE);
    }

    @Override
    public List<String> getMenuItems() {
        return List.of(
                "Dashboard",
                "My Profile",
                "Payroll Management", // Generate Employee Payroll", Generate Payslip, View Payroll Reports
                "Print Payslip");
    }
}
