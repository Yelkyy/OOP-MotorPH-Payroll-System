package motorph.service;

public class ContributionCalculator {

    private static final double SSS_MAX_SALARY = 24750;
    private static final double SSS_MAX_CONTRIBUTION = 1125.00;
    private static final double SSS_RATE = 0.045;

    private static final double PHILHEALTH_LOWER_LIMIT = 10000;
    private static final double PHILHEALTH_UPPER_LIMIT = 59999.99;
    private static final double PHILHEALTH_MAX_PREMIUM = 1800;
    private static final double PHILHEALTH_RATE = 0.03;

    private static final double PAGIBIG_RATE = 0.02;
    private static final double PAGIBIG_MAX_CONTRIBUTION = 100;

    private ContributionCalculator() {
    }

    public static double calculateSSS(double basicSalary) {
        if (basicSalary > SSS_MAX_SALARY) {
            return SSS_MAX_CONTRIBUTION;
        }
        return basicSalary * SSS_RATE;
    }

    public static double calculatePhilHealth(double basicSalary) {
        double premium;
        if (basicSalary <= PHILHEALTH_LOWER_LIMIT) {
            premium = 300;
        } else if (basicSalary <= PHILHEALTH_UPPER_LIMIT) {
            premium = Math.min(basicSalary * PHILHEALTH_RATE, PHILHEALTH_MAX_PREMIUM);
        } else {
            premium = PHILHEALTH_MAX_PREMIUM;
        }
        return premium / 2.0; // employee share per cutoff
    }

    public static double calculatePagIbig(double basicSalary) {
        return Math.min(PAGIBIG_MAX_CONTRIBUTION, basicSalary * PAGIBIG_RATE);
    }
}
