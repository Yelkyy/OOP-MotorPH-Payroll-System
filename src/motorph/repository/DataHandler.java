package motorph.repository;

import com.opencsv.*;
import com.opencsv.exceptions.CsvException;
import motorph.model.EmployeeDetails;
import motorph.model.EmployeeTimeLogs;
import motorph.model.LeaveRequest;

import java.io.*;
import java.nio.file.*;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Repository class responsible for CSV file operations including reading and
 * writing employee data.
 * Handles employee details, time logs, and user authentication data from CSV
 * files.
 */
public class DataHandler {

    private static final Path CSV_FILE = Paths.get("resources", "Copy of MotorPH Employee Data.csv");
    private static final Path TIME_LOG_CSV = Paths.get("resources", "Copy of MotorPH Employee Data Time Logs.csv");
    private static final Path LOGIN_CSV = Paths.get("resources", "MotorPH Users.csv");
    private static final Path LEAVE_CSV = Paths.get("resources", "MotorPH_LeaveRequest.csv");

    private static final String[] EMPLOYEE_CSV_HEADER = {
            "Employee #", "Last Name", "First Name", "Birthday", "Address", "Phone Number",
            "SSS #", "PhilHealth #", "TIN #", "Pag-IBIG #", "Status", "Position",
            "Immediate Supervisor", "Basic Salary", "Rice Subsidy", "Phone Allowance",
            "Clothing Allowance", " Gross Semi-Monthly Rate", "Hourly Rate"
    };

    /**
     * Reads employee details from the CSV file.
     * 
     * @return List of EmployeeDetails objects.
     */
    public static List<EmployeeDetails> readEmployeeDetails() {
        List<EmployeeDetails> employees = new ArrayList<>();

        try (Reader reader = Files.newBufferedReader(CSV_FILE);
                CSVReader csvReader = new CSVReaderBuilder(reader)
                        .withCSVParser(new CSVParserBuilder()
                                .withSeparator(',')
                                .withIgnoreQuotations(false)
                                .build())
                        .build()) {

            List<String[]> records = csvReader.readAll();

            if (records.isEmpty())
                return employees;

            records.remove(0); // Remove header row

            for (String[] record : records) {
                if (record.length < EMPLOYEE_CSV_HEADER.length) {
                    System.out.println("Skipping malformed row: " + Arrays.toString(record));
                    continue;
                }

                EmployeeDetails employee = new EmployeeDetails(
                        record[0], record[1], record[2], record[3], record[4], record[5], record[6],
                        record[7].trim(), record[8].trim(), record[9].trim(), record[10], record[11],
                        record[12], parseDoubleSafe(record[13]), parseDoubleSafe(record[14]),
                        parseDoubleSafe(record[15]), parseDoubleSafe(record[16]),
                        parseDoubleSafe(record[17]), parseDoubleSafe(record[18]));

                employees.add(employee);
            }
        } catch (IOException | CsvException e) {
            System.err.println("Error reading employee details: " + e.getMessage());
        }

        return employees;
    }

    /**
     * Reads employee time logs from the CSV file.
     * 
     * @return List of EmployeeTimeLogs objects.
     */
    public static List<EmployeeTimeLogs> readEmployeeTimeLogs() {
        List<EmployeeTimeLogs> timeLogs = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(TIME_LOG_CSV)) {
            String line;
            boolean isFirstLine = true;
            int lineNum = 0;

            while ((line = br.readLine()) != null) {
                lineNum++;
                line = line.trim();
                if (line.isEmpty())
                    continue;

                String[] parts = line.split(",", -1);

                // Skip header line
                if (isFirstLine && line.toLowerCase().contains("employee")) {
                    isFirstLine = false;
                    continue;
                }

                // Malformed row check
                if (parts.length != 6) {
                    System.out.println(
                            "Skipping malformed time log at line " + lineNum + ": " + Arrays.toString(parts));
                    continue;
                }

                EmployeeTimeLogs log = new EmployeeTimeLogs(
                        parts[0].trim(),
                        parts[1].trim(),
                        parts[2].trim(),
                        parts[3].trim(),
                        parts[4].trim(),
                        parts[5].trim());

                timeLogs.add(log);
            }

        } catch (IOException e) {
            System.err.println("Error reading time logs: " + e.getMessage());
        }

        return timeLogs;
    }

    /**
     * Appends a new employee time log entry to the CSV file.
     */
    public static void logEmployeeTime(String empId, String lastName, String firstName, String date,
            String logIn, String logOut) {
        File file = TIME_LOG_CSV.toFile();
        boolean fileExists = file.exists();

        try (Writer writer = new FileWriter(file, true);
                CSVWriter csvWriter = new CSVWriter(writer,
                        CSVWriter.DEFAULT_SEPARATOR,
                        CSVWriter.DEFAULT_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.RFC4180_LINE_END)) {

            if (!fileExists) {
                csvWriter.writeNext(new String[] {
                        "Employee #", "Last Name", "First Name", "Date", "Log In", "Log Out"
                });
            }

            csvWriter.writeNext(new String[] { empId, lastName, firstName, date, logIn, logOut });
            System.out.println("Time log recorded for Employee #" + empId);

        } catch (IOException e) {
            System.err.println("Error writing time log: " + e.getMessage());
        }
    }

    /**
     * Writes the full list of employees back to the CSV file, overwriting existing
     * content.
     */
    public static void writeEmployeeData(List<EmployeeDetails> employees) {
        try (Writer writer = new FileWriter(CSV_FILE.toFile());
                CSVWriter csvWriter = new CSVWriter(writer,
                        CSVWriter.DEFAULT_SEPARATOR,
                        CSVWriter.DEFAULT_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.RFC4180_LINE_END)) {

            csvWriter.writeNext(EMPLOYEE_CSV_HEADER);

            for (EmployeeDetails emp : employees) {
                csvWriter.writeNext(emp.toCSVArray());
            }

            System.out.println("Employee data written to CSV.");

        } catch (IOException e) {
            System.err.println("Error writing employee data: " + e.getMessage());
        }
    }

    /**
     * Adds a new employee record to the CSV file.
     */
    public static void addEmployeeToCSV(EmployeeDetails emp) {
        try (Writer writer = new FileWriter(CSV_FILE.toFile(), true);
                CSVWriter csvWriter = new CSVWriter(writer,
                        CSVWriter.DEFAULT_SEPARATOR,
                        CSVWriter.DEFAULT_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.RFC4180_LINE_END)) {

            csvWriter.writeNext(emp.toCSVArray());
            System.out.println("Employee added to CSV: " + emp.getEmployeeNumber());

        } catch (IOException e) {
            System.err.println("Error adding employee: " + e.getMessage());
        }
    }

    /**
     * Generates a new unique employee ID by incrementing the highest existing ID.
     * 
     * @return New employee ID as a String.
     */
    public static String generateNewEmpId() {
        List<EmployeeDetails> employees = readEmployeeDetails();
        int maxId = 10000;

        for (EmployeeDetails emp : employees) {
            try {
                int id = Integer.parseInt(emp.getEmployeeNumber());
                if (id > maxId)
                    maxId = id;
            } catch (NumberFormatException e) {
                // Ignore malformed IDs
            }
        }

        return String.valueOf(maxId + 1);
    }

    /**
     * Removes an employee by employee number from the CSV file.
     */
    public static void removeEmployeeFromCSV(String employeeNumber) {
        List<EmployeeDetails> employees = readEmployeeDetails();

        boolean removed = employees.removeIf(emp -> emp.getEmployeeNumber().equals(employeeNumber));

        if (removed) {
            writeEmployeeData(employees);
            System.out.println("Employee #" + employeeNumber + " removed.");
        } else {
            System.out.println("Employee #" + employeeNumber + " not found.");
        }
    }

    /**
     * Updates an existing employee's details in the CSV file.
     */
    public static void updateEmployeeInCSV(EmployeeDetails updatedEmployee) {
        List<EmployeeDetails> employees = readEmployeeDetails();
        boolean updated = false;

        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getEmployeeNumber().equals(updatedEmployee.getEmployeeNumber())) {
                employees.set(i, updatedEmployee);
                updated = true;
                break;
            }
        }

        if (updated) {
            writeEmployeeData(employees);
            System.out.println("Employee #" + updatedEmployee.getEmployeeNumber() + " updated.");
        } else {
            System.out.println("Employee #" + updatedEmployee.getEmployeeNumber() + " not found.");
        }
    }

    /**
     * Parses a String to double safely, returning 0.0 for invalid or missing
     * values.
     */
    private static double parseDoubleSafe(String value) {
        if (value == null || value.trim().isEmpty() || value.equalsIgnoreCase("N/A")) {
            return 0.0;
        }
        try {
            return Double.parseDouble(value.replace(",", "").trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    /**
     * Removes all time logs related to a specific employee.
     * 
     * @param employeeNumber Employee number to filter logs by.
     */
    public static void deleteTimeLogsByEmployeeNumber(String employeeNumber) {
        List<EmployeeTimeLogs> logs = readEmployeeTimeLogs();

        boolean removed = logs.removeIf(log -> log.getEmployeeNumber().equals(employeeNumber));

        if (removed) {
            writeEmployeeTimeLogs(logs);
            System.out.println("🗑️ Time logs for Employee #" + employeeNumber + " removed.");
        } else {
            System.out.println("⚠️ No time logs found for Employee #" + employeeNumber);
        }
    }

    /**
     * Writes the list of employee time logs back to the CSV.
     */
    public static void writeEmployeeTimeLogs(List<EmployeeTimeLogs> logs) {
        try (Writer writer = Files.newBufferedWriter(TIME_LOG_CSV);
                CSVWriter csvWriter = new CSVWriter(writer,
                        CSVWriter.DEFAULT_SEPARATOR,
                        CSVWriter.DEFAULT_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.RFC4180_LINE_END)) {

            // Write header
            csvWriter.writeNext(new String[] {
                    "Employee #", "Last Name", "First Name", "Date", "Log In", "Log Out"
            });

            for (EmployeeTimeLogs log : logs) {
                csvWriter.writeNext(log.toCSVArray());
            }

            System.out.println("✅ Time log data written to CSV.");
        } catch (IOException e) {
            System.err.println("Error writing time logs: " + e.getMessage());
        }
    }

    /**
     * Loads user credentials from the login CSV file.
     * 
     * @return Map of username-password pairs.
     */
    public static Map<String, String> loadUserCredentials() {
        Map<String, String> credentials = new HashMap<>();

        try (BufferedReader br = Files.newBufferedReader(LOGIN_CSV)) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false; // skip header
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String username = parts[0].trim();
                    String password = parts[1].trim();
                    credentials.put(username, password);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading user credentials: " + e.getMessage());
        }

        return credentials;
    }

    public static String getFirstNameByEmail(String email) {
        try (BufferedReader br = Files.newBufferedReader(LOGIN_CSV)) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // skip header
                }

                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[0].trim().equalsIgnoreCase(email.trim())) {
                    return parts[2].trim();
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading first name: " + e.getMessage());
        }

        return "User";
    }

    public static String generateLeaveRequestId() {
        List<LeaveRequest> requests = readLeaveRequests();
        int maxId = 0;

        for (LeaveRequest req : requests) {
            try {
                int id = Integer.parseInt(req.getRequestId());
                if (id > maxId)
                    maxId = id;
            } catch (NumberFormatException e) {
                // Ignore malformed IDs
            }
        }

        return String.valueOf(maxId + 1);
    }

    public static List<LeaveRequest> readLeaveRequests() {
        List<LeaveRequest> requests = new ArrayList<>();

        try (Reader reader = Files.newBufferedReader(LEAVE_CSV);
                CSVReader csvReader = new CSVReaderBuilder(reader)
                        .withCSVParser(new CSVParserBuilder()
                                .withSeparator(',')
                                .withIgnoreQuotations(false)
                                .build())
                        .build()) {

            List<String[]> records = csvReader.readAll();

            for (int i = 1; i < records.size(); i++) {
                String[] record = records.get(i);
                if (record.length >= 8) {
                    String requestId = record[0];
                    String employeeNumber = record[1];
                    Date requestDate = new java.text.SimpleDateFormat("MM-dd-yyyy").parse(record[2]);
                    String leaveType = record[3];
                    Date from = new java.text.SimpleDateFormat("MM-dd-yyyy").parse(record[4]);
                    Date to = new java.text.SimpleDateFormat("MM-dd-yyyy").parse(record[5]);
                    String reason = record[6];
                    String status = record[7];
                    String reviewedBy = record.length > 8 && !record[8].trim().isEmpty() ? record[8] : null;
                    Date dateReviewed = null;
                    if (record.length > 9 && !record[9].trim().isEmpty()) {
                        try {
                            dateReviewed = new java.text.SimpleDateFormat("MM-dd-yyyy").parse(record[9]);
                        } catch (Exception ex) {
                            // ignore parse error
                        }
                    }

                    requests.add(new LeaveRequest(requestId, employeeNumber, requestDate, from, to, reason, leaveType,
                            status, reviewedBy, dateReviewed));
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading leave requests: " + e.getMessage());
        }

        return requests;
    }

    public static void addLeaveRequest(LeaveRequest request) {
        try (Writer writer = Files.newBufferedWriter(LEAVE_CSV, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                CSVWriter csvWriter = new CSVWriter(writer,
                        CSVWriter.DEFAULT_SEPARATOR,
                        CSVWriter.NO_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.RFC4180_LINE_END)) {

            csvWriter.writeNext(request.toCSVArray());

        } catch (IOException e) {
            System.err.println("Error adding leave request: " + e.getMessage());
        }
    }

    public static void updateLeaveRequestStatusById(String requestId, String newStatus) {
        updateLeaveRequestStatusById(requestId, newStatus, null);
    }

    public static void updateLeaveRequestStatusById(String requestId, String newStatus, String reviewedBy) {
        List<LeaveRequest> allRequests = readLeaveRequests();

        for (LeaveRequest request : allRequests) {
            if (request.getRequestId().equals(requestId)) {
                request.setStatus(newStatus);
                if (reviewedBy != null) {
                    request.setReviewedBy(reviewedBy);
                    request.setDateReviewed(new Date());
                }
                break;
            }
        }

        writeLeaveRequest(allRequests);
    }

    public static List<LeaveRequest> getLeaveRequestsByEmployee(String employeeNumber) {
        List<LeaveRequest> allRequests = readLeaveRequests();
        List<LeaveRequest> employeeRequests = new ArrayList<>();

        for (LeaveRequest request : allRequests) {
            if (request.getEmployeeNumber().equals(employeeNumber)) {
                employeeRequests.add(request);
            }
        }

        return employeeRequests;
    }

    public static void writeLeaveRequest(List<LeaveRequest> logs) {
        try (Writer writer = Files.newBufferedWriter(LEAVE_CSV, StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
                CSVWriter csvWriter = new CSVWriter(writer,
                        CSVWriter.DEFAULT_SEPARATOR,
                        CSVWriter.NO_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.RFC4180_LINE_END)) {

            String[] header = { "Request #", "Employee #", "Request Date", "Leave Type", "Date From", "Date To",
                    "Reason", "Status", "Reviewed By", "Date Reviewed" };
            csvWriter.writeNext(header);

            for (LeaveRequest request : logs) {
                csvWriter.writeNext(request.toCSVArray());
            }

        } catch (IOException e) {
            System.err.println("Error writing leave requests: " + e.getMessage());
        }
    }
}
