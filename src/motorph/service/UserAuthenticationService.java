package motorph.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import motorph.model.Role;
import motorph.model.core.Employee;

public class UserAuthenticationService {

    public static Employee authenticate(String username, String password) {
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
                String usrRole = parts[4].trim();
                String employeeId = parts[5].trim();

                if (csvUsername.equalsIgnoreCase(username.trim())
                        && csvPassword.equals(password.trim())) {

                    // employee record must exist (for ALL accounts)
                    if (!employeeExists(employeeId)) {
                        System.out.println("Login blocked: Employee record not found for employee #" + employeeId);
                        return null;
                    }

                    if (!isValidRole(usrRole)) {
                        System.out.println("Invalid role value in CSV for user " + csvUsername);
                        return null;
                    }

                    Role role = Role.valueOf(usrRole.toUpperCase());
                    return EmployeeService.loadEmployeeForRole(employeeId, role);
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
        Matcher m = p.matcher(username.trim());

        if (m.matches()) {
            String empNo = m.group(1);

            if (password.trim().equals(empNo) && employeeExists(empNo)) {
                return EmployeeService.loadEmployeeForRole(empNo, Role.EMPLOYEE);
            }
        }

        return null;

    }

    private static boolean isValidRole(String roleValue) {
        try {
            Role.valueOf(roleValue.toUpperCase());
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
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

}
