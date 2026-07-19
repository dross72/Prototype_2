// class created to contain and display the information of individual items
public class Item {
    private String id;
    private String itemName; //Pack Type is in the item name
    private Integer quantity;
    public Item(String id, String itemName, Integer quantity){
        this.id = id;
        this.itemName = itemName;
        this.quantity = quantity;
    }

    // prints the item's id, name, and quantity according to the sample's format
    public String info(){
        return id+"\t\t"+itemName+"\t\t\t\t\t"+quantity;
    }
}
