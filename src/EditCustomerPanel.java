import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Edit an existing customer. A rep can pick one of their own customers
 * right away; opening another rep's customer asks for the override code
 * first, same rule as the order screen. Company name and form of payment
 * are shown but locked, since those belong to the business office.
 */
public class EditCustomerPanel extends JPanel {
    private AppFrame app;
    private JComboBox<String> pick = new JComboBox<>();
    private ArrayList<Customer> choices = new ArrayList<>();
    private Customer loaded = null;

    private JTextField name = new JTextField(20);
    private JComboBox<String> payment = new JComboBox<>(new String[]{"Cash", "EBT", "Fintech"});
    private JTextField street = new JTextField(20);
    private JTextField city = new JTextField(20);
    private JTextField state = new JTextField(20);
    private JTextField zip = new JTextField(20);
    private JTextField license = new JTextField(20);
    private JTextField dock = new JTextField(20);
    private JTextField constraints = new JTextField(20);
    private JTextField poc = new JTextField(20);
    private JTextField phone = new JTextField(20);
    private JLabel status = new JLabel(" ");

    public EditCustomerPanel(AppFrame app) {
        this.app = app;
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(4, 8, 4, 8);
        gc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Edit Customer");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));

        JButton load = new JButton("Open");
        // company name and form of payment are locked to the business office
        name.setEditable(false);
        payment.setEnabled(false);

        String[] labels = {"Customer:", "Company name (locked):", "Form of payment (locked):",
                "Street address:", "City:", "State:", "Zip:", "Beer license #:",
                "Loading dock capabilities:", "Delivery constraints:", "POC:", "Phone:"};
        Component[] fields = {null, name, payment, street, city, state, zip, license, dock, constraints, poc, phone};

        gc.gridx = 0; gc.gridy = 0; gc.gridwidth = 3;
        add(title, gc);
        gc.gridwidth = 1;

        gc.gridx = 0; gc.gridy = 1;
        add(new JLabel(labels[0]), gc);
        gc.gridx = 1;
        add(pick, gc);
        gc.gridx = 2;
        add(load, gc);

        for (int i = 1; i < labels.length; i++) {
            gc.gridx = 0; gc.gridy = i + 1;
            add(new JLabel(labels[i]), gc);
            gc.gridx = 1; gc.gridwidth = 2;
            add(fields[i], gc);
            gc.gridwidth = 1;
        }

        JButton save = new JButton("Save Changes");
        JButton back = new JButton("Back to Home");
        JPanel btns = new JPanel();
        btns.add(save);
        btns.add(back);
        gc.gridx = 0; gc.gridy = labels.length + 1; gc.gridwidth = 3;
        add(btns, gc);
        gc.gridy = labels.length + 2;
        add(status, gc);

        setEditable(false);

        load.addActionListener(e -> openSelected());
        back.addActionListener(e -> app.show("home"));
        save.addActionListener(e -> saveChanges());
    }

    /**
     * Loads the customer chosen in the dropdown. If it belongs to another
     * rep, asks for the override code before unlocking the fields.
     */
    private void openSelected() {
        int i = pick.getSelectedIndex();
        if (i < 0) return;
        Customer c = choices.get(i);
        if (!c.getOwnerRepId().equals(app.getCurrentRepId())) {
            String code = JOptionPane.showInputDialog(this, "This customer belongs to another rep.\nEnter override code:");
            if (code == null) return;
            if (!app.getStore().getCompany().checkOverrideCode(code)) {
                JOptionPane.showMessageDialog(this, "Override code incorrect.");
                return;
            }
        }
        loaded = c;
        name.setText(c.getName());
        payment.setSelectedItem(c.getFormOfPayment());
        street.setText(c.getStreet());
        city.setText(c.getCity());
        state.setText(c.getState());
        zip.setText(c.getZip());
        license.setText(c.getBeerLicNum());
        dock.setText(c.getLoadDockCapa());
        constraints.setText(c.getDeliConstraints());
        poc.setText(c.getPOC());
        phone.setText(c.getPhoneNum());
        setEditable(true);
        status.setForeground(Color.BLACK);
        status.setText("Editing " + c.getName());
    }

    private void saveChanges() {
        if (loaded == null) {
            status.setForeground(Color.RED);
            status.setText("Open a customer first.");
            return;
        }
        // company name and form of payment are never written back
        loaded.setStreet(clean(street.getText()));
        loaded.setCity(clean(city.getText()));
        loaded.setState(clean(state.getText()));
        loaded.setZip(clean(zip.getText()));
        loaded.setBeerLicNum(clean(license.getText()));
        loaded.setLoadDockCapa(clean(dock.getText()));
        loaded.setDeliConstraints(clean(constraints.getText()));
        loaded.setPOC(clean(poc.getText()));
        loaded.setPhoneNum(clean(phone.getText()));
        if (app.getStore().saveAllCustomers()) {
            status.setForeground(new Color(0, 128, 0));
            status.setText("Saved changes to " + loaded.getName());
        } else {
            status.setForeground(Color.RED);
            status.setText("Could not save. Check the data folder.");
        }
    }

    private void setEditable(boolean on) {
        for (JTextField f : new JTextField[]{street, city, state, zip, license, dock, constraints, poc, phone}) {
            f.setEditable(on);
        }
    }

    private String clean(String s) {
        return s.replace('\t', ' ').replace('\n', ' ').replace('\r', ' ').trim();
    }

    /**
     * Reloads the customer dropdown every time the screen opens. The whole
     * company list shows, non owned ones will ask for the override code.
     */
    public void refresh() {
        loaded = null;
        setEditable(false);
        status.setText(" ");
        pick.removeAllItems();
        choices.clear();
        String rep = app.getCurrentRepId();
        for (Customer c : app.getStore().getCustomers()) {
            String tag = c.getOwnerRepId().equals(rep) ? "" : "  [other rep]";
            pick.addItem(c.getName() + " (" + c.getAccountId() + ")" + tag);
            choices.add(c);
        }
    }
}
