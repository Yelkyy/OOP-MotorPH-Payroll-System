package motorph.model.payroll;

/**
 * Data model storing detailed breakdown of payroll deductions
 * including government contributions, tax, and net pay calculation.
 */
public class DeductionBreakdown {

    public double lateUndertime;
    public double sss;
    public double philhealth;
    public double pagibig;
    public double tax;

    public double totalDeductions;
    public double netPay;

    public DeductionBreakdown() {
    }

    public DeductionBreakdown(
            double lateUndertime,
            double sss,
            double philhealth,
            double pagibig,
            double tax,
            double totalDeductions,
            double netPay) {
        this.lateUndertime = lateUndertime;
        this.sss = sss;
        this.philhealth = philhealth;
        this.pagibig = pagibig;
        this.tax = tax;
        this.totalDeductions = totalDeductions;
        this.netPay = netPay;
    }
}
