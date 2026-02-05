package motorph.service;

import java.util.Date;
import java.util.List;
import motorph.contracts.LeaveCrudOperations;
import motorph.model.LeaveRequest;
import motorph.model.EmployeeDetails;
import motorph.repository.DataHandler;

public class LeaveService implements LeaveCrudOperations {

    @Override
    public void submitLeaveRequest(LeaveRequest request) {
        if (request.getFrom().after(request.getTo())) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }

        DataHandler.addLeaveRequest(request);
    }

    @Override
    public void updateLeaveStatusByRequestId(String requestId, String status) {
        DataHandler.updateLeaveRequestStatusById(requestId, status);
    }

    public void updateLeaveStatusByRequestId(String requestId, String status, String reviewedBy) {
        DataHandler.updateLeaveRequestStatusById(requestId, status, reviewedBy);
    }

    @Override
    public List<LeaveRequest> findAllLeaveRequests() {
        return DataHandler.readLeaveRequests();
    }

    @Override
    public List<LeaveRequest> findLeaveRequestsByEmployee(String employeeNumber) {
        return DataHandler.getLeaveRequestsByEmployee(employeeNumber);
    }

    public void approveLeaveRequestById(String requestId) {
        updateLeaveStatusByRequestId(requestId, "Approved");
    }

    public void approveLeaveRequestById(String requestId, String reviewedBy) {
        updateLeaveStatusByRequestId(requestId, "Approved", reviewedBy);
    }

    public void rejectLeaveRequestById(String requestId) {
        updateLeaveStatusByRequestId(requestId, "Rejected");
    }

    public void rejectLeaveRequestById(String requestId, String reviewedBy) {
        updateLeaveStatusByRequestId(requestId, "Rejected", reviewedBy);
    }

    public EmployeeDetails getEmployeeDetails(String employeeNumber) {
        return EmployeeService.getEmployeeById(employeeNumber);
    }
}
