package motorph.contracts;

import java.util.Date;
import java.util.List;
import motorph.model.LeaveRequest;

public interface LeaveCrudOperations {

    void submitLeaveRequest(LeaveRequest request);

    void updateLeaveStatusByRequestId(String requestId, String status);

    List<LeaveRequest> findAllLeaveRequests();

    List<LeaveRequest> findLeaveRequestsByEmployee(String employeeNumber);
}
