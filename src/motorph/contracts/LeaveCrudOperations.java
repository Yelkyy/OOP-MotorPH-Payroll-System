package motorph.contracts;

import java.util.Date;
import java.util.List;
import motorph.model.LeaveRequest;

public interface LeaveCrudOperations {

    void submitLeaveRequest(LeaveRequest request);

    void updateLeaveStatus(String employeeNumber, Date requestDate, String status);

    List<LeaveRequest> findAllLeaveRequests();

    List<LeaveRequest> findLeaveRequestsByEmployee(String employeeNumber);

    List<LeaveRequest> findPendingLeaveRequests();
}
