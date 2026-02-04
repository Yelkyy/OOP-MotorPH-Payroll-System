package motorph.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import motorph.contracts.LeaveCrudOperations;
import motorph.model.LeaveRequest;
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
    public void updateLeaveStatus(String employeeNumber, Date requestDate, String status) {
        DataHandler.updateLeaveRequestStatus(employeeNumber, requestDate, status);
    }

    @Override
    public List<LeaveRequest> findAllLeaveRequests() {
        return DataHandler.readLeaveRequests();
    }

    @Override
    public List<LeaveRequest> findLeaveRequestsByEmployee(String employeeNumber) {
        return DataHandler.getLeaveRequestsByEmployee(employeeNumber);
    }

    @Override
    public List<LeaveRequest> findPendingLeaveRequests() {
        return DataHandler.readLeaveRequests().stream()
                .filter(request -> "Pending".equalsIgnoreCase(request.getStatus()))
                .collect(Collectors.toList());
    }

    public void approveLeaveRequest(String employeeNumber, Date requestDate) {
        updateLeaveStatus(employeeNumber, requestDate, "Approved");
    }

    public void rejectLeaveRequest(String employeeNumber, Date requestDate) {
        updateLeaveStatus(employeeNumber, requestDate, "Rejected");
    }
}
