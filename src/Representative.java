import java.util.ArrayList;
import java.util.Scanner;

public class Representative extends Company{
    private String repId;
    private String username;
    private String password;
    private ArrayList<Customer> repCustomers;


    //allow the representative to add customers
    public void addCustomer(){
        Scanner myScan = new Scanner(System.in);
        String name = myScan.nextLine();
        System.out.println("Customer name: ");
        String street = myScan.nextLine();
        System.out.println("Street Address: ");
        String city = myScan.nextLine();
        System.out.println("City: ");
        String state = myScan.nextLine();
        System.out.println("State: ");
        String zip = myScan.nextLine();
        System.out.println("Zip ");
        String beerLicNum = myScan.nextLine();
        System.out.println("Beer license #: ");
        String formOfPayment = myScan.nextLine();
        System.out.println("Form of Payment(cash/EBT/Fintech) ");
        String loadCap = myScan.nextLine();
        System.out.println("Loading dock Capabilities: ");
        String deliCon = myScan.nextLine();
        System.out.println("Delivery constraints: ");
        String POC = myScan.nextLine();
        System.out.println("POC ");
        String phone = myScan.nextLine();
        System.out.println("Phone ");
        //create the customer and add them to the Company customers list
        // and rep's personal list
        Customer newCustomer = new Customer(name,street,city,state,zip,beerLicNum,formOfPayment,loadCap,deliCon,POC,phone);
        repCustomers.add(newCustomer);
        //adds the new customer to the Company's customer list
        //a representative's customer is the company's customer
        super.addCustomer(newCustomer);
    }


}
