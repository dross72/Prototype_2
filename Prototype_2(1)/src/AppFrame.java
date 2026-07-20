import javax.swing.*;
import java.awt.*;

/**
 * The main window for the rep app. Uses a CardLayout so each
 * screen (login, home, new customer, order builder, review) is a
 * panel we can flip between without opening new windows.
 */
public class AppFrame extends JFrame {

    private CardLayout cards = new CardLayout();
    private JPanel root = new JPanel(cards);
    private DataStore store = new DataStore();

    // session state
    private String currentRepId = null;
    private Order currentOrder = null;
    private Customer currentCustomer = null;

    private HomePanel homePanel;
    private OrderBuilderPanel orderPanel;
    private ReviewPanel reviewPanel;

    public AppFrame() {
        super("ACME Distributing - Sales Rep");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 620);
        setLocationRelativeTo(null);

        homePanel = new HomePanel(this);
        orderPanel = new OrderBuilderPanel(this);
        reviewPanel = new ReviewPanel(this);

        root.add(new LoginPanel(this), "login");
        root.add(homePanel, "home");
        root.add(new CustomerFormPanel(this), "newCustomer");
        root.add(orderPanel, "order");
        root.add(reviewPanel, "review");
        add(root);
        show("login");
    }

    /**
     * Flips to the named screen and lets it refresh itself first.
     */
    public void show(String name) {
        if (name.equals("home")) homePanel.refresh();
        if (name.equals("order")) orderPanel.refresh();
        if (name.equals("review")) reviewPanel.refresh();
        cards.show(root, name);
    }

    /**
     * Returns to the order screen WITHOUT resetting it, so a rep
     * can come back from the review screen and keep their items.
     */
    public void backToOrder() {
        cards.show(root, "order");
    }

    public DataStore getStore() {
        return store;
    }

    public String getCurrentRepId() {
        return currentRepId;
    }

    public void setCurrentRepId(String repId) {
        this.currentRepId = repId;
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(Order order) {
        this.currentOrder = order;
    }

    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public void setCurrentCustomer(Customer c) {
        this.currentCustomer = c;
    }
}
