package motorph.utils;

import motorph.model.Role;
import motorph.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UserAuthentication {

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
                String lastName  = parts[3].trim();
                String usrRole   = parts[4].trim();
                String employeeId = parts[5].trim();

                if (csvUsername.equalsIgnoreCase(email.trim())
                        && csvPassword.equals(password.trim())) {

                    Role role;
                    try {
                        role = Role.valueOf(usrRole.toUpperCase());
                    } catch (IllegalArgumentException badRole) {
                        System.out.println("Invalid role value in CSV for user " + csvUsername + ": " + usrRole);
                        return null;
                    }

                    return new User(csvUsername, firstName, lastName, role, employeeId);
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading users.csv: " + e.getMessage());
        }

        return null;
    }
}
