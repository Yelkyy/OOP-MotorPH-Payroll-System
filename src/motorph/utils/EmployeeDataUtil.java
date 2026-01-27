package motorph.utils;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.Locale;

import motorph.model.EmployeeDetails;
import motorph.model.EmployeeTimeLogs;
import motorph.repository.DataHandler;

public class EmployeeDataUtil {

    // Get all employees from CSV
    public static List<EmployeeDetails> getAllEmployees() {
        return DataHandler.readEmployeeDetails();
    }

    // Get all time logs from CSV
    public static List<EmployeeTimeLogs> getAllTimeLogs() {
        return DataHandler.readEmployeeTimeLogs();
    }

    // Get a specific employee by ID
    public static EmployeeDetails getEmployeeById(String empId) {
        return getAllEmployees()
            .stream()
            .filter(emp -> emp.getEmployeeNumber().equals(empId))
            .findFirst()
            .orElse(null);
    }

    // Get time logs for a specific employee (ALL logs)
    public static List<EmployeeTimeLogs> getTimeLogsForEmployee(String empId) {
        return getAllTimeLogs()
            .stream()
            .filter(log -> log.getEmployeeNumber().equals(empId))
            .toList();
    }

    // Get time logs for a specific employee within a cutoff pay period
    public static List<EmployeeTimeLogs> getTimeLogsForEmployee(String empId, LocalDate cutoffDate) {
        LocalDate startDate = (cutoffDate.getDayOfMonth() <= 15)
                ? cutoffDate.withDayOfMonth(1)
                : cutoffDate.withDayOfMonth(16);

        return getAllTimeLogs()
            .stream()
            .filter(log -> log.getEmployeeNumber().equals(empId))
            .filter(log -> {
                LocalDate d = log.getDateValue();
                return d != null && !d.isBefore(startDate) && !d.isAfter(cutoffDate);
            })
            .toList();
    }


    // Optional helper (if you ever need the cutoff from a date)
    public static LocalDate getCutoffForDate(LocalDate date) {
        return (date.getDayOfMonth() <= 15)
                ? LocalDate.of(date.getYear(), date.getMonth(), 15)
                : YearMonth.of(date.getYear(), date.getMonth()).atEndOfMonth();
    }

    public static class DeductionBreakdown {
        public double sss;
        public double philhealth;
        public double pagibig;
        public double tax;
        public double lateUndertime;
        public double totalDeductions;
        public double netPay;
    }
}
