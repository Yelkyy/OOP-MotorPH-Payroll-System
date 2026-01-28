package motorph.model.core;

import java.util.List;
import motorph.model.EmployeeDetails;
import motorph.model.EmployeeTimeLogs;

public abstract class Employee {

    protected EmployeeDetails details;

    protected Employee(EmployeeDetails details) {
        this.details = details;
    }

    public EmployeeDetails getDetails() {
        return details;
    }

    public String getEmployeeNumber() {
        return details.getEmployeeNumber();
    }

    public String getFullName() {
        return details.getFullName();
    }

    public abstract double computeNetPay(
            List<EmployeeTimeLogs> logs,
            String monthYear,
            int payPeriod
    );
}
