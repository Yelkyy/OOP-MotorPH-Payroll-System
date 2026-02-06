package motorph.model.users;

import java.util.List;

import motorph.model.Role;
import motorph.model.core.Employee;

/**
 * Concrete employee subclass representing a Human Resources department user.
 * Extends Employee to provide access to HR features like
 * employee management, attendance tracking, and leave request approval.
 */
public class HrUser extends Employee {

    public HrUser(String employeeNumber, String lastName, String firstName, String birthday,
            String address, String phoneNumber, String sssNumber, String philhealthNumber,
            String tinNumber, String pagIbigNumber, String status, String position,
            String immediateSupervisor, double basicSalary, double riceSubsidy,
            double phoneAllowance, double clothingAllowance, double grossSemiMonthlyRate,
            double hourlyRate) {
        super(employeeNumber, lastName, firstName, birthday, address, phoneNumber,
                sssNumber, philhealthNumber, tinNumber, pagIbigNumber, status, position,
                immediateSupervisor, basicSalary, riceSubsidy, phoneAllowance,
                clothingAllowance, grossSemiMonthlyRate, hourlyRate, Role.HR);
    }

    @Override
    public List<String> getMenuItems() {
        return List.of(
                "Dashboard",
                "My Profile",
                "Attendance Overview",
                "Employee Management",
                "Leave Management");
    }
}
