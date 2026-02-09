package motorph.service;

import java.util.List;
import motorph.model.LeaveRequest;
import motorph.model.core.Employee;
import motorph.dao.DataHandler;

public class LeaveService {

    // ==========================================
    // CRUD Operations
    // ==========================================

    public void add(LeaveRequest request) {
        if (request.getFrom().after(request.getTo())) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        DataHandler.addLeaveRequest(request);
    }

    public void update(LeaveRequest request) {
        // Not directly supported by DataHandler; use updateLeaveStatusByRequestId
        // instead
        throw new UnsupportedOperationException("Use updateLeaveStatusByRequestId() instead");
    }

    public void delete(String requestId) {
        // Not directly supported by DataHandler
        throw new UnsupportedOperationException("Delete operation not supported for leave requests");
    }

    public LeaveRequest findById(String requestId) {
        // Not directly supported by DataHandler; would need custom method
        throw new UnsupportedOperationException("Use findAllLeaveRequests() or findLeaveRequestsByEmployee() instead");
    }

    public List<LeaveRequest> findAll() {
        return DataHandler.readLeaveRequests();
    }

    // ==========================================
    // Backward compatibility delegates
    // ==========================================

    public void submitLeaveRequest(LeaveRequest request) {
        add(request);
    }

    public void updateLeaveStatusByRequestId(String requestId, String status) {
        DataHandler.updateLeaveRequestStatusById(requestId, status);
    }

    public void updateLeaveStatusByRequestId(String requestId, String status, String reviewedBy) {
        DataHandler.updateLeaveRequestStatusById(requestId, status, reviewedBy);
    }

    public List<LeaveRequest> findAllLeaveRequests() {
        return findAll();
    }

    public List<LeaveRequest> findLeaveRequestsByEmployee(String employeeNumber) {
        return DataHandler.getLeaveRequestsByEmployee(employeeNumber);
    }

    // ==========================================
    // Helper methods
    // ==========================================

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

    public Employee getEmployee(String employeeNumber) {
        return EmployeeService.getEmployeeById(employeeNumber);
    }
}
