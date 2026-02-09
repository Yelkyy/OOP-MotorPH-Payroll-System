package motorph.model;

/**
 * Enumeration defining the different user roles in the MotorPH payroll system.
 * Each role has specific permissions and features:
 * - ADMIN: System administration and user management
 * - HR: Employee management, attendance, and leave approvals
 * - EMPLOYEE: View personal profile, attendance, and payslips
 * - FINANCE: Payroll processing and report generation
 */
public enum Role {
    ADMIN,
    HR,
    EMPLOYEE,
    FINANCE,
}
