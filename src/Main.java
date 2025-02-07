import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        boolean testDatabase = false; // Change this to true when testing database

        if (testDatabase) {
            DatabaseManager.testDatabaseConnection();
        } else {
            javax.swing.SwingUtilities.invokeLater(GUI::new);
        }
    }
}
