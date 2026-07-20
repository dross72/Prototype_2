import java.util.ArrayList;
public class Order {
    private String accountID;
    private String deliDate;
    private String salesRepId;
    private String deliRepId;
    private ArrayList<Item> itemsList;

    public Order(String accountID,String deliDate, String salesRepId, String deliRepId){
        this.accountID = accountID;
        this.deliDate = deliDate;
        this.salesRepId = salesRepId;
        this.deliRepId = deliRepId;
    }
    public void displayItems(){
        for (Item x : itemsList){
            System.out.println(x.info());
        }
    }


}
