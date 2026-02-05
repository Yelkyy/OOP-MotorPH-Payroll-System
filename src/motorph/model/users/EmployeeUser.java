package motorph.model.users;

import java.util.List;

import motorph.model.EmployeeDetails;
import motorph.model.Role;
import motorph.model.core.Employee;

/**
 * Represents a regular employee user in the MotorPH payroll system.
 * Child class of Employee that provides access to employee-specific features
 * like
 * viewing profile, recording attendance, submitting leave requests, and viewing
 * payslips.
 */
public class EmployeeUser extends Employee {
    public EmployeeUser(EmployeeDetails details) {
        super(details, Role.EMPLOYEE);
    }

    @Override
    public List<String> getMenuItems() {
        return List.of(
                "My Profile",
                "Attendance", // time-in, time-out, attendance list
                "Leave", // leave request fill up and table
                "View Payslip",
                "Print Payslip");

        public boolean canAccessMenuItem(String menuName) {
            return switch (menuName) {
                case "My Profile" -> true;
                case "My Attendance" -> true;
                case "Leave" -> true;
                case "My Payslip" -> true;
                default -> false;
            };
        }
    }

    // DO's
    public boolean canViewOwnProfile() {
        return true;
    }

    public boolean canRecordOwnAttendance() {
        return true;
    }

    public boolean canViewOwnAttendance() {
        return true;
    }

    public boolean canSubmitLeaveRequest() {
        return true;
    }

    public boolean canViewOwnLeaveHistory() {
        return true;
    }

    public boolean canViewOwnPayslip() {
        return true;
    }

    public boolean canPrintOwnPayslip() {
        return true;
    }

    public boolean canUpdateOwnContactInfo() {
        return true;
    }

    // DON'Ts
    public boolean canViewOtherEmployeeProfiles() {
        return false;
    }

    public boolean canViewOtherPayslips() {
        return false;
    }

    public boolean canViewOtherAttendance() {
        return false;
    }

    public boolean canApproveLeaveRequests() {
        return false;
    }

    public boolean canModifyAttendance() {
        return false;
    }

    public boolean canProcessPayroll() {
        return false;
    }

    public boolean canGeneratePayslips() {
        return false;
    }

    public boolean canManageEmployees() {
        return false;
    }

    public boolean canManageUserAccounts() {
        return false;
    }

    public boolean canManageRoles() {
        return false;
    }

    public boolean canConfigureSystem() {
        return false;
    }

    public boolean canViewPayrollReports() {
        return false;
    }

    public boolean canModifyOwnSalary() {
        return false;
    }
}
