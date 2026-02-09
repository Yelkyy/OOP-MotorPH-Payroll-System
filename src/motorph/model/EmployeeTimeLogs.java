package motorph.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Data model class representing a single employee attendance record.
 * Tracks login time, logout time, and calculates total work hours for a
 * specific date.
 * Supports multiple date/time formats for flexible data import from CSV files.
 */
public class EmployeeTimeLogs {

    private final String employeeNumber;
    private final String lastName;
    private final String firstName;
    private final LocalDate date;
    private final String logIn;
    private final String logOut;

    // Accepts multiple date formats from CSV
    private static final List<DateTimeFormatter> DATE_FORMATS = Arrays.asList(
            DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.US),
            DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US),
            DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.US));

    public EmployeeTimeLogs(String employeeNumber, String lastName, String firstName, String date, String logIn,
            String logOut) {
        this.employeeNumber = clean(employeeNumber);
        this.lastName = clean(lastName);
        this.firstName = clean(firstName);
        this.date = parseDate(clean(date));
        this.logIn = clean(logIn);
        this.logOut = clean(logOut);
    }

    private String clean(String input) {
        return input == null ? "" : input.replace("\"", "").trim();
    }

    private LocalDate parseDate(String dateStr) {
        if (dateStr == null)
            return null;
        dateStr = dateStr.replace("\"", "").trim();

        for (DateTimeFormatter formatter : DATE_FORMATS) {
            try {
                return LocalDate.parse(dateStr, formatter);
            } catch (DateTimeParseException ignored) {
            }
        }
        System.err.println("❌ Invalid date format: \"" + dateStr + "\"");
        return null;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    // 👇 Used for display and sorting based on CSV format
    public String getDate() {
        return (date != null) ? date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) : "Invalid Date";
    }

    // 👇 Optional ISO format getter
    public String getDateISO() {
        return (date != null) ? date.format(DateTimeFormatter.ISO_LOCAL_DATE) : "Invalid Date";
    }

    public String getLogIn() {
        return logIn;
    }

    public String getLogOut() {
        return logOut;
    }

    public double getHoursWorked() {
        if (logIn == null || logOut == null || logIn.isEmpty() || logOut.isEmpty()) {
            return 0.0;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
            LocalTime inTime = LocalTime.parse(logIn, formatter);
            LocalTime outTime = LocalTime.parse(logOut, formatter);

            long minutesWorked = Duration.between(inTime, outTime).toMinutes();
            return minutesWorked / 60.0;
        } catch (Exception e) {
            System.err.println("Error processing time log for Employee #" + employeeNumber + ": " + e.getMessage());
            return 0.0;
        }
    }

    public LocalDate getDateValue() {
        return date;
    }

    @Override
    public String toString() {
        return "Employee #" + employeeNumber + " | Name: " + firstName + " " + lastName +
                " | Date: " + getDate() + " | Log In: " + logIn + " | Log Out: " + logOut +
                " | Hours Worked: " + getHoursWorked();
    }

    public String[] toCSVArray() {
        return new String[] {
                clean(employeeNumber),
                clean(lastName),
                clean(firstName),
                getDate(), // Use consistent MM/dd/yyyy format
                clean(logIn),
                clean(logOut)
        };
    }
}
