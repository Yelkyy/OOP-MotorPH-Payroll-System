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
    private String requestId;
    private String employeeNumber;
    private Date requestDate;
    private Date from;
    private Date to;
    private String reason;
    private String Leavetype;
    private String status;
    private String reviewedBy;
    private Date dateReviewed;

    public LeaveRequest(String requestId, String employeeNumber, Date requestDate, Date from, Date to, String reason,
            String Leavetype,
            String status) {
        this(requestId, employeeNumber, requestDate, from, to, reason, Leavetype, status, null, null);
    }

    public LeaveRequest(String requestId, String employeeNumber, Date requestDate, Date from, Date to, String reason,
            String Leavetype, String status, String reviewedBy, Date dateReviewed) {
        this.requestId = requestId;
        this.employeeNumber = employeeNumber;
        this.requestDate = requestDate;
        this.from = from;
        this.to = to;
        this.reason = reason;
        this.Leavetype = Leavetype;
        this.status = status;
        this.reviewedBy = reviewedBy;
        this.dateReviewed = dateReviewed;
    }

    public String getRequestId() {
        return requestId;
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

    public String getReason() {
        return reason;
    }

    public String getLeavetype() {
        return Leavetype;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(String reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public Date getDateReviewed() {
        return dateReviewed;
    }

    public void setDateReviewed(Date dateReviewed) {
        this.dateReviewed = dateReviewed;
    }

    // Formatted date getters for UI display
    public String getFormattedRequestDate() {
        if (requestDate == null)
            return "";
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        return df.format(requestDate);
    }

    public String getFormattedFromDate() {
        if (from == null)
            return "";
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        return df.format(from);
    }

    public String getFormattedToDate() {
        if (to == null)
            return "";
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        return df.format(to);
    }

    public long getNumberOfDays() {
        if (from == null || to == null || to.before(from)) {
            return 0;
        }
        long diffMs = to.getTime() - from.getTime();
        return java.util.concurrent.TimeUnit.MILLISECONDS.toDays(diffMs) + 1;
    }

    public String[] toCSVArray() {
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        return new String[] {
                requestId,
                employeeNumber,
                df.format(requestDate),
                Leavetype,
                df.format(from),
                df.format(to),
                reason,
                status,
                reviewedBy != null ? reviewedBy : "",
                dateReviewed != null ? df.format(dateReviewed) : ""
        };
    }
}
