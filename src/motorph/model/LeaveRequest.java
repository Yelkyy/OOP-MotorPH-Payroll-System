package motorph.model;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Data model for employee leave requests containing request dates, leave type,
 * and approval status.
 * NOTE: Not yet fully implemented. Intended for employee role leave request
 * submission and approval workflow.
 */
public class LeaveRequest {
    private Date requestDate;
    private Date from;
    private Date to;
    private String reason;
    private String Leavetype;
    private String status;

    public LeaveRequest(Date requestDate, Date from, Date to, String reason, String Leavetype, String status) {
        this.requestDate = requestDate;
        this.from = from;
        this.to = to;
        this.reason = reason;
        this.Leavetype = Leavetype;
        this.status = status;
    }

    // Getters
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

    // Setters
    public void setStatus(String status) {
        this.status = status;
    }

    // CSV Save Method
    public static void saveToCSV(String employeeId, LeaveRequest leave) {
        try (FileWriter writer = new FileWriter("MotorPH_LeaveRequest.csv", true)) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            String from = df.format(leave.getFrom());
            String to = df.format(leave.getTo());
            String requestDate = df.format(leave.getRequestDate());

            // CSV format: EmployeeID,LeaveType,StartDate,EndDate,Reason,Status
            writer.append(employeeId).append(",")
                    .append(leave.getType()).append(",")
                    .append(from).append(",")
                    .append(to).append(",")
                    .append(leave.getNote().replace(",", " ")).append(",")
                    .append(leave.getStatus()).append("\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
