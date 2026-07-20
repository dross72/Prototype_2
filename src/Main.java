import javax.swing.*;

/**
 * Entry point for the ACME Distributing sales rep prototype.
 * Sprint 2 - SWE 3313.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AppFrame().setVisible(true));
    }
}
