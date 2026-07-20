import java.util.ArrayList;

/**
 * An order being built by a sales rep for one customer.
 * Holds the order header info plus the list of line items.
 */
public class Order {
    private String accountID;
    private String deliDate;
    private String salesRepId;
    private String deliRepId;
    private ArrayList<Item> itemsList;

    public Order(String accountID, String deliDate, String salesRepId, String deliRepId) {
        this.accountID = accountID;
        this.deliDate = deliDate;
        this.salesRepId = salesRepId;
        this.deliRepId = deliRepId;
        this.itemsList = new ArrayList<>();
    }

    /**
     * Adds one line item to the order.
     */
    public void addItem(Item item) {
        itemsList.add(item);
    }

    /**
     * Removes a single line item without cancelling the whole order.
     */
    public void removeItem(int index) {
        if (index >= 0 && index < itemsList.size()) {
            itemsList.remove(index);
        }
    }

    public ArrayList<Item> getItemsList() {
        return itemsList;
    }

    public String getAccountID() {
        return accountID;
    }

    public String getDeliDate() {
        return deliDate;
    }

    public String getSalesRepId() {
        return salesRepId;
    }

    public String getDeliRepId() {
        return deliRepId;
    }

    public void setDeliDate(String deliDate) {
        this.deliDate = deliDate;
    }

    public void setDeliRepId(String deliRepId) {
        this.deliRepId = deliRepId;
    }

}
