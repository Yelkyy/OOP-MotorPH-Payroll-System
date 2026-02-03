package motorph.service;

import motorph.model.Role;
import motorph.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserAuthenticationService {

    public static User authenticate(String email, String password) {
        Path filePath = Paths.get("resources", "MotorPH Users.csv");

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length < 6) {
                    continue;
                }

                String csvUsername = parts[0].trim();
                String csvPassword = parts[1].replaceAll("[\\r\\n]+", "").trim();
                String firstName = parts[2].trim();
                String lastName = parts[3].trim();
                String usrRole = parts[4].trim();
                String employeeId = parts[5].trim();

                if (csvUsername.equalsIgnoreCase(email.trim())
                        && csvPassword.equals(password.trim())) {

                    // employee record must exist
                    if (!employeeExists(employeeId)) {
                        System.out.println(
                                "Login blocked: Employee record not found for employee #" + employeeId);
                        return null;
                    }

                    Role role;
                    try {
                        role = Role.valueOf(usrRole.toUpperCase());
                    } catch (IllegalArgumentException badRole) {
                        System.out.println("Invalid role value in CSV for user " + csvUsername);
                        return null;
                    }

                    return new User(csvUsername, firstName, lastName, role, employeeId);
                }

            }

        } catch (IOException e) {
            System.err.println("Error reading users.csv: " + e.getMessage());
        }

        /**
         * Auto employee login: emp(employee number)
         * password: employee number
         **/
        Pattern p = Pattern.compile("^emp(\\d+)$", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(email.trim());

        if (m.matches()) {
            String empNo = m.group(1);

            if (password.trim().equals(empNo) && employeeExists(empNo)) {
                String[] name = getEmployeeName(empNo);
                return new User(email.trim(), name[0], name[1], Role.EMPLOYEE, empNo);
            }
        }

        return null;

    }

    private static boolean employeeExists(String employeeNumber) {
        Path empFilePath = Paths.get("resources", "Copy of MotorPH Employee Data.csv");

        try (BufferedReader reader = Files.newBufferedReader(empFilePath)) {
            String line;
            reader.readLine(); // skip header

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\",\"");

                if (parts.length > 0) {
                    String empNo = parts[0].replace("\"", "").trim(); // first column should be "Employee #"
                    if (employeeNumber.equals(empNo)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading employee data CSV: " + e.getMessage());
        }

        return false;
    }

    private static String[] getEmployeeName(String employeeNumber) {
        Path empFilePath = Paths.get("resources", "Copy of MotorPH Employee Data.csv");

        try (BufferedReader reader = Files.newBufferedReader(empFilePath)) {
            String line;
            reader.readLine(); // skip header

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\",\"");

                if (parts.length > 2) {
                    String empNo = parts[0].replace("\"", "").trim();
                    if (employeeNumber.equals(empNo)) {
                        String lastName = parts[1].replace("\"", "").trim();
                        String firstName = parts[2].replace("\"", "").trim();
                        return new String[] { firstName, lastName };
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading employee data CSV: " + e.getMessage());
        }

        return new String[] { "Employee", employeeNumber };
    }

}
