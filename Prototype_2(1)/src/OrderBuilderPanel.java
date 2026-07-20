import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * The order building screen. The rep picks a customer, searches the
 * product list, adds line items with a quantity, and can pull a single
 * item back off the order without cancelling the whole thing.
 */
public class OrderBuilderPanel extends JPanel {
    private AppFrame app;
    private JComboBox<String> customerBox = new JComboBox<>();
    private ArrayList<Customer> customerChoices = new ArrayList<>();
    private JTextField deliveryDate = new JTextField(10);
    private JTextField deliveryRep = new JTextField(10);
    private JTextField search = new JTextField(16);
    private DefaultTableModel resultsModel = new DefaultTableModel(new String[]{"Product ID", "Description", "Brand", "Container"}, 0) {
        public boolean isCellEditable(int r, int c) { return false; }
    };
    private JTable results = new JTable(resultsModel);
    private JSpinner qty = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
    private DefaultListModel<String> lineModel = new DefaultListModel<>();
    private JList<String> lines = new JList<>(lineModel);
    private ArrayList<Product> shown = new ArrayList<>();
    private Order building = null;
    private JLabel resultCount = new JLabel(" ");

    public OrderBuilderPanel(AppFrame app) {
        this.app = app;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        // top strip: customer on the first row, order header fields on
        // the second so nothing gets pushed off the edge of the window
        JPanel customerRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        customerRow.add(new JLabel("Customer:"));
        customerRow.add(customerBox);
        JPanel headerRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerRow.add(new JLabel("Delivery date:"));
        headerRow.add(deliveryDate);
        headerRow.add(new JLabel("Delivery rep ID:"));
        headerRow.add(deliveryRep);
        JPanel top = new JPanel(new GridLayout(2, 1));
        top.add(customerRow);
        top.add(headerRow);
        add(top, BorderLayout.NORTH);

        // middle: product search on the left, current order lines on the right
        JPanel searchBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton searchBtn = new JButton("Search");
        searchBar.add(new JLabel("Find product:"));
        searchBar.add(search);
        searchBar.add(searchBtn);
        searchBar.add(new JLabel("Qty:"));
        searchBar.add(qty);

        JPanel searchRows = new JPanel(new GridLayout(2, 1));
        searchRows.add(searchBar);
        JPanel countRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        countRow.add(resultCount);
        searchRows.add(countRow);

        JPanel left = new JPanel(new BorderLayout(5, 5));
        left.add(searchRows, BorderLayout.NORTH);
        left.add(new JScrollPane(results), BorderLayout.CENTER);
        JButton addBtn = new JButton("Add Selected to Order");
        left.add(addBtn, BorderLayout.SOUTH);

        JPanel right = new JPanel(new BorderLayout(5, 5));
        right.add(new JLabel("Order lines"), BorderLayout.NORTH);
        right.add(new JScrollPane(lines), BorderLayout.CENTER);
        JButton removeBtn = new JButton("Remove Selected Item");
        right.add(removeBtn, BorderLayout.SOUTH);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
        split.setResizeWeight(0.6);
        add(split, BorderLayout.CENTER);

        JButton reviewBtn = new JButton("Review Order");
        JButton backBtn = new JButton("Back to Home");
        JPanel bottom = new JPanel();
        bottom.add(backBtn);
        bottom.add(reviewBtn);
        add(bottom, BorderLayout.SOUTH);

        searchBtn.addActionListener(e -> runSearch());
        search.addActionListener(e -> runSearch());

        addBtn.addActionListener(e -> {
            int row = results.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Select a product from the search results first.");
                return;
            }
            ensureOrder();
            if (building == null) return;
            Product p = shown.get(row);
            Item item = new Item(p.getProductId(), p.getDescription(), (Integer) qty.getValue());
            building.addItem(item);
            lineModel.addElement(p.getProductId() + "  " + p.getDescription() + "  x" + qty.getValue());
            // lock the customer once the order has items so it can't
            // silently switch mid order
            customerBox.setEnabled(false);
        });

        removeBtn.addActionListener(e -> {
            int idx = lines.getSelectedIndex();
            if (idx < 0 || building == null) return;
            building.removeItem(idx);
            lineModel.remove(idx);
            if (building.getItemsList().isEmpty()) {
                customerBox.setEnabled(true);
            }
        });

        backBtn.addActionListener(e -> app.show("home"));
        reviewBtn.addActionListener(e -> {
            if (building == null || building.getItemsList().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Add at least one item before reviewing.");
                return;
            }
            // re-read the header fields so edits made after the first
            // item was added still make it onto the order
            building.setDeliDate(deliveryDate.getText().isBlank() ? "TBD" : deliveryDate.getText().trim());
            building.setDeliRepId(deliveryRep.getText().isBlank() ? "TBD" : deliveryRep.getText().trim());
            app.setCurrentOrder(building);
            app.show("review");
        });
    }

    /**
     * Creates the Order object the first time an item is added, using
     * the header fields at the top of the screen. Opening another rep's
     * customer asks for the manager override code first.
     */
    private void ensureOrder() {
        if (building != null) return;
        int i = customerBox.getSelectedIndex();
        if (i < 0) {
            JOptionPane.showMessageDialog(this, "Pick a customer first.");
            return;
        }
        Customer c = customerChoices.get(i);
        if (!c.getOwnerRepId().equals(app.getCurrentRepId())) {
            String code = JOptionPane.showInputDialog(this, "This customer belongs to another rep.\nEnter override code:");
            if (code == null) {
                return; // rep cancelled the dialog
            }
            if (!app.getStore().getCompany().checkOverrideCode(code)) {
                JOptionPane.showMessageDialog(this, "Override code incorrect.");
                return;
            }
        }
        String date = deliveryDate.getText().isBlank() ? "TBD" : deliveryDate.getText().trim();
        String dRep = deliveryRep.getText().isBlank() ? "TBD" : deliveryRep.getText().trim();
        building = new Order(c.getAccountId(), date, app.getCurrentRepId(), dRep);
    }

    private void runSearch() {
        resultsModel.setRowCount(0);
        shown.clear();
        String q = search.getText().trim();
        int count = 0;
        for (Product p : app.getStore().getProducts()) {
            if (q.isEmpty() || p.matches(q)) {
                resultsModel.addRow(new Object[]{p.getProductId(), p.getDescription(), p.getBrandName(), p.getContainerName()});
                shown.add(p);
                count++;
                if (count >= 200) break; // keep the table snappy
            }
        }
        int total = 0;
        for (Product p : app.getStore().getProducts()) {
            if (q.isEmpty() || p.matches(q)) total++;
        }
        resultCount.setText("Showing " + count + " of " + total);
    }

    /**
     * Resets the screen for a fresh order and reloads the customer
     * dropdown. Every customer in the company shows up here, but
     * non owned ones will ask for the override code.
     */
    public void refresh() {
        building = null;
        lineModel.clear();
        customerBox.setEnabled(true);
        customerBox.removeAllItems();
        customerChoices.clear();
        String rep = app.getCurrentRepId();
        for (Customer c : app.getStore().getCustomers()) {
            String tag = c.getOwnerRepId().equals(rep) ? "" : "  [other rep]";
            customerBox.addItem(c.getName() + " (" + c.getAccountId() + ")" + tag);
            customerChoices.add(c);
        }
        runSearch();
    }
}
