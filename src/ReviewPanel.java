import javax.swing.*;
import java.awt.*;

/**
 * Shows the completed order for a final look, then submits it.
 * Submitting "transmits" the order by writing it to a JSON file,
 * which simulates sending it to the business office.
 */
public class ReviewPanel extends JPanel {
    private AppFrame app;
    private JTextArea summary = new JTextArea();

    public ReviewPanel(AppFrame app) {
        this.app = app;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JLabel title = new JLabel("Review Order");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        add(title, BorderLayout.NORTH);

        summary.setEditable(false);
        summary.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        add(new JScrollPane(summary), BorderLayout.CENTER);

        JButton submit = new JButton("Submit Order");
        JButton back = new JButton("Back to Order");
        JPanel btns = new JPanel();
        btns.add(back);
        btns.add(submit);
        add(btns, BorderLayout.SOUTH);

        back.addActionListener(e -> app.show("order"));
        submit.addActionListener(e -> {
            Order o = app.getCurrentOrder();
            if (o == null) return;
            String file = app.getStore().submitOrder(o);
            if (file != null) {
                JOptionPane.showMessageDialog(this, "Order transmitted.\nSaved as: " + file);
                app.setCurrentOrder(null);
                app.show("home");
            } else {
                JOptionPane.showMessageDialog(this, "Something went wrong writing the order file.");
            }
        });
    }

    /**
     * Rebuilds the order summary text every time the screen opens.
     */
    public void refresh() {
        Order o = app.getCurrentOrder();
        if (o == null) {
            summary.setText("");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Account ID:      ").append(o.getAccountID()).append("\n");
        sb.append("Delivery date:   ").append(o.getDeliDate()).append("\n");
        sb.append("Sales rep:       ").append(o.getSalesRepId()).append("\n");
        sb.append("Delivery rep:    ").append(o.getDeliRepId()).append("\n\n");
        sb.append("ID          Item                                          Qty\n");
        sb.append("-----------------------------------------------------------\n");
        for (Item it : o.getItemsList()) {
            sb.append(String.format("%-10s  %-44s  %3d%n", it.getId(), it.getItemName(), it.getQuantity()));
        }
        summary.setText(sb.toString());
    }
}
