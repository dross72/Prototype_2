import java.util.ArrayList;

public class Company {
    private ArrayList<Customer> customers;
    private String overrideCode;

    public void printCustomerList(){
        for (Customer x : customers){
            System.out.println(x.getName());
        }
    }

    //adds new customers to the company's customer list
    public void addCustomer(Customer newCustomer){
        customers.add(newCustomer);
    }

    //Check to see if a Rep's override code is correct
    public Boolean checkOverrideCode(String overrideAttempt){
        if (overrideAttempt.equals(overrideCode)){
            return true;
        }else{
            return false;
        }
    }
}
