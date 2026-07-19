import javax.swing.*;
import java.awt.*;

/**
 * New customer form. Field list matches the client's sample form:
 * name, address, beer license, form of payment, loading dock,
 * delivery constraints, POC and phone.
 */
public class CustomerFormPanel extends JPanel {
    public CustomerFormPanel(AppFrame app) {
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(4, 8, 4, 8);
        gc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("New Customer");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));

        JTextField name = new JTextField(20);
        JTextField street = new JTextField(20);
        JTextField city = new JTextField(20);
        JTextField state = new JTextField(20);
        JTextField zip = new JTextField(20);
        JTextField license = new JTextField(20);
        JComboBox<String> payment = new JComboBox<>(new String[]{"Cash", "EBT", "Fintech"});
        JTextField dock = new JTextField(20);
        JTextField constraints = new JTextField(20);
        JTextField poc = new JTextField(20);
        JTextField phone = new JTextField(20);
        JLabel status = new JLabel(" ");
        status.setForeground(new Color(0, 128, 0));

        String[] labels = {"Customer name:", "Street address:", "City:", "State:", "Zip:", "Beer license #:", "Form of payment:", "Loading dock capabilities:", "Delivery constraints:", "POC:", "Phone:"};
        Component[] fields = {name, street, city, state, zip, license, payment, dock, constraints, poc, phone};

        gc.gridx = 0; gc.gridy = 0; gc.gridwidth = 2;
        add(title, gc);
        gc.gridwidth = 1;
        for (int i = 0; i < labels.length; i++) {
            gc.gridx = 0; gc.gridy = i + 1;
            add(new JLabel(labels[i]), gc);
            gc.gridx = 1;
            add(fields[i], gc);
        }

        JButton save = new JButton("Save Customer");
        JButton back = new JButton("Back to Home");
        JPanel btns = new JPanel();
        btns.add(save);
        btns.add(back);
        gc.gridx = 0; gc.gridy = labels.length + 1; gc.gridwidth = 2;
        add(btns, gc);
        gc.gridy = labels.length + 2;
        add(status, gc);

        back.addActionListener(e -> app.show("home"));
        save.addActionListener(e -> {
            if (name.getText().isBlank() || street.getText().isBlank() || license.getText().isBlank()) {
                status.setForeground(Color.RED);
                status.setText("Name, street and beer license are required.");
                return;
            }
            // build a 15 character account id from the name plus a number,
            // same style as the sample stores (e.g. ABCPS_123432109)
            String prefix = name.getText().replaceAll("[^A-Za-z]", "").toUpperCase();
            if (prefix.length() > 5) prefix = prefix.substring(0, 5);
            while (prefix.length() < 5) prefix = prefix + "X";
            String digits = String.valueOf(System.currentTimeMillis());
            String acct = prefix + "_" + digits.substring(digits.length() - 9);

            Customer c = new Customer(name.getText().trim(), street.getText().trim(), city.getText().trim(),
                    state.getText().trim(), zip.getText().trim(), license.getText().trim(),
                    (String) payment.getSelectedItem(), dock.getText().trim(), constraints.getText().trim(),
                    poc.getText().trim(), phone.getText().trim(), acct, app.getCurrentRepId());
            app.getStore().addCustomer(c);
            status.setForeground(new Color(0, 128, 0));
            status.setText("Saved. Account ID: " + acct);
            for (Component f : fields) {
                if (f instanceof JTextField) ((JTextField) f).setText("");
            }
        });
    }
}
