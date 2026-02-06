package motorph.model.users;

import java.util.List;

import motorph.model.Role;
import motorph.model.core.Employee;

/**
 * Concrete employee subclass representing an administrator user.
 * Extends Employee to provide access to administrative features like
 * user account management, role assignments, and system reports.
 */
public class AdminUser extends Employee {

    public AdminUser(String employeeNumber, String lastName, String firstName, String birthday,
            String address, String phoneNumber, String sssNumber, String philhealthNumber,
            String tinNumber, String pagIbigNumber, String status, String position,
            String immediateSupervisor, double basicSalary, double riceSubsidy,
            double phoneAllowance, double clothingAllowance, double grossSemiMonthlyRate,
            double hourlyRate) {
        super(employeeNumber, lastName, firstName, birthday, address, phoneNumber,
                sssNumber, philhealthNumber, tinNumber, pagIbigNumber, status, position,
                immediateSupervisor, basicSalary, riceSubsidy, phoneAllowance,
                clothingAllowance, grossSemiMonthlyRate, hourlyRate, Role.ADMIN);
    }

    @Override
    public List<String> getMenuItems() {
        return List.of(
                "Dashboard",
                "Manage User Accounts",
                "Update Role/Access Rights",
                "System Reports");
    }
}
