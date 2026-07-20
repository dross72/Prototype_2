import javax.swing.*;
import java.awt.*;

/**
 * The rep's home screen. Built around what a rep actually does when
 * they walk into a store: start a new order fast, get at their own
 * customers, and see their recent orders (history is valuable since
 * reps reuse past orders to build new ones).
 */
public class HomePanel extends JPanel {
    private AppFrame app;
    private JLabel greeting = new JLabel("", SwingConstants.LEFT);
    private DefaultListModel<String> customerModel = new DefaultListModel<>();
    private DefaultListModel<String> orderModel = new DefaultListModel<>();

    public HomePanel(AppFrame app) {
        this.app = app;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        greeting.setFont(greeting.getFont().deriveFont(Font.BOLD, 20f));

        JButton newOrderBtn = new JButton("Start New Order");
        JButton newCustomerBtn = new JButton("Add New Customer");
        JButton logoutBtn = new JButton("Log Out");
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttons.add(newOrderBtn);
        buttons.add(newCustomerBtn);
        buttons.add(logoutBtn);

        JPanel top = new JPanel(new BorderLayout());
        top.add(greeting, BorderLayout.NORTH);
        top.add(buttons, BorderLayout.SOUTH);
        add(top, BorderLayout.NORTH);

        JList<String> customerList = new JList<>(customerModel);
        JList<String> orderList = new JList<>(orderModel);
        JPanel middle = new JPanel(new GridLayout(1, 2, 10, 10));
        JPanel left = new JPanel(new BorderLayout());
        left.add(new JLabel("My Customers"), BorderLayout.NORTH);
        left.add(new JScrollPane(customerList), BorderLayout.CENTER);
        JPanel right = new JPanel(new BorderLayout());
        right.add(new JLabel("Recent Orders"), BorderLayout.NORTH);
        right.add(new JScrollPane(orderList), BorderLayout.CENTER);
        middle.add(left);
        middle.add(right);
        add(middle, BorderLayout.CENTER);

        newOrderBtn.addActionListener(e -> app.show("order"));
        newCustomerBtn.addActionListener(e -> app.show("newCustomer"));
        logoutBtn.addActionListener(e -> {
            app.setCurrentRepId(null);
            app.show("login");
        });
    }

    /**
     * Reloads the greeting, customer list and recent orders each
     * time the rep lands on the home screen.
     */
    public void refresh() {
        String rep = app.getCurrentRepId();
        if (rep == null) return;
        greeting.setText("Welcome, " + app.getStore().getRepName(rep));
        customerModel.clear();
        for (Customer c : app.getStore().getCustomersForRep(rep)) {
            customerModel.addElement(c.getName() + "  (" + c.getAccountId() + ")");
        }
        orderModel.clear();
        for (String f : app.getStore().getRecentOrderFiles(rep)) {
            orderModel.addElement(f);
        }
        if (orderModel.isEmpty()) orderModel.addElement("(no orders submitted yet)");
    }
}
