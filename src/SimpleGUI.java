import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class SimpleGUI {



/**
 * A simple Java Swing GUI example.
 * Demonstrates JFrame, JLabel, JTextField, JButton, and event handling.
 */

    public static void main(String[] args) {
        // Always start Swing apps on the Event Dispatch Thread
        SwingUtilities.invokeLater(SimpleGUI::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        // Create the main window (JFrame)
        JFrame frame = new JFrame("Java Swing Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null); // Center on screen

        // Create components
        JLabel label = new JLabel("Enter your name:");
        JTextField textField = new JTextField(15);
        JButton button = new JButton("Greet");
        JLabel outputLabel = new JLabel("");

        // Set layout
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        // Add components to panel
        panel.add(label);
        panel.add(textField);
        panel.add(button);
        panel.add(outputLabel);

        // Add event listener to button
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = textField.getText().trim();
                if (name.isEmpty()) {
                    outputLabel.setText("Please enter a valid name.");
                    outputLabel.setForeground(Color.RED);
                } else {
                    outputLabel.setText("Hello, " + name + "!");
                    outputLabel.setForeground(Color.BLUE);
                }
            }
        });

        // Add panel to frame
        frame.add(panel);

        // Make the window visible
        frame.setVisible(true);
    }
}



