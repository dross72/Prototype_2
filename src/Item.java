/**
 * One line item on an order. Pack type (case, keg, 4/6 etc.)
 * is baked into the item name instead of being its own field.
 */
public class Item {
    private String id;
    private String itemName;
    private Integer quantity;

    public Item(String id, String itemName, Integer quantity) {
        this.id = id;
        this.itemName = itemName;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Prints the item's id, name, and quantity according to the sample's format.
     */
    public String info() {
        return id + "\t\t" + itemName + "\t\t\t\t\t" + quantity;
    }
}
