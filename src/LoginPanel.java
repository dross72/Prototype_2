import javax.swing.*;
import java.awt.*;

/**
 * Login screen. Per the client: username and password only,
 * no remember me and no forgot password link.
 */
public class LoginPanel extends JPanel {
    public LoginPanel(AppFrame app) {
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(8, 8, 8, 8);
        gc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("ACME Distributing", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 28f));
        JLabel sub = new JLabel("Sales Representative Login", SwingConstants.CENTER);
        sub.setFont(sub.getFont().deriveFont(16f));

        JTextField userField = new JTextField(18);
        JPasswordField passField = new JPasswordField(18);
        JButton loginBtn = new JButton("Log In");
        JLabel error = new JLabel(" ", SwingConstants.CENTER);
        error.setForeground(Color.RED);

        gc.gridx = 0; gc.gridy = 0; gc.gridwidth = 2;
        add(title, gc);
        gc.gridy = 1;
        add(sub, gc);
        gc.gridwidth = 1;
        gc.gridy = 2;
        add(new JLabel("Username:"), gc);
        gc.gridx = 1;
        add(userField, gc);
        gc.gridx = 0; gc.gridy = 3;
        add(new JLabel("Password:"), gc);
        gc.gridx = 1;
        add(passField, gc);
        gc.gridx = 0; gc.gridy = 4; gc.gridwidth = 2;
        add(loginBtn, gc);
        gc.gridy = 5;
        add(error, gc);

        Runnable doLogin = () -> {
            String u = userField.getText().trim();
            String p = new String(passField.getPassword());
            if (app.getStore().login(u, p)) {
                app.setCurrentRepId(u);
                error.setText(" ");
                userField.setText("");
                passField.setText("");
                app.show("home");
            } else {
                error.setText("Invalid username or password.");
            }
        };
        loginBtn.addActionListener(e -> doLogin.run());
        passField.addActionListener(e -> doLogin.run());
    }
}
