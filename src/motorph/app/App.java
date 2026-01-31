package motorph.app;

// Main class to launch the MotorPH application.
// This initializes and displays the login screen when the application starts.
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import com.formdev.flatlaf.FlatLightLaf;
import motorph.ui.LoginScreen;

public class App {
    /**
     * The entry point of the MotorPH application.
     * It launches the login screen and centers it on the screen.
     * 
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // Create an instance of the login screen
        LoginScreen LoginF = new LoginScreen();

        // Make the login screen visible
        LoginF.setVisible(true);

        // Resize the frame to fit its components
        LoginF.pack();

        // Center the login screen on the screen
        LoginF.setLocationRelativeTo(null);
    }

}
