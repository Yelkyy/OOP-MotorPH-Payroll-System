package motorph.model;

import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Data model for employee leave requests containing request dates, leave type,
 * and approval status.
 * NOTE: Not yet fully implemented. Intended for employee role leave request
 * submission and approval workflow.
 */
public class LeaveRequest {
    private String employeeNumber;
    private Date requestDate;
    private Date from;
    private Date to;
    private String reason;
    private String Leavetype;
    private String status;

    public LeaveRequest(String employeeNumber, Date requestDate, Date from, Date to, String reason, String Leavetype,
            String status) {
        this.employeeNumber = employeeNumber;
        this.requestDate = requestDate;
        this.from = from;
        this.to = to;
        this.reason = reason;
        this.Leavetype = Leavetype;
        this.status = status;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
    }

    public String getNote() {
        return reason;
    }

    public String getType() {
        return Leavetype;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String[] toCSVArray() {
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        return new String[] {
                employeeNumber,
                Leavetype,
                df.format(from),
                df.format(to),
                reason,
                status
        };
    }
}
