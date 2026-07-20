import java.util.ArrayList;
import java.util.Scanner;

/**
 * A sales representative. Extends Company so a rep can reach the
 * shared customer list, and keeps their own list of the customers
 * they personally own.
 */
public class Representative extends Company {
    private String repId;
    private String name;
    private String username;
    private String password;
    private ArrayList<Customer> repCustomers = new ArrayList<>();

    public Representative(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.repId = username;
    }

    /**
     * Adds a customer to this rep's personal list and to the
     * company's shared list, since a representative's customer
     * is the company's customer.
     */
    public void addCustomer(Customer newCustomer) {
        repCustomers.add(newCustomer);
        super.addCustomer(newCustomer);
    }

    /**
     * Checks a login attempt against this rep's credentials.
     */
    public boolean checkLogin(String usernameAttempt, String passwordAttempt) {
        return username.equals(usernameAttempt) && password.equals(passwordAttempt);
    }

    public String getRepId() {
        return repId;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<Customer> getRepCustomers() {
        return repCustomers;
    }

    public void editCustomer(Customer customer){
        Scanner myScan = new Scanner(System.in);
        //if the customer is not in the representative's list ask for the override code
        if (!repCustomers.contains(customer)){
            System.out.println("Error: This Customer is not registered to you.");
            System.out.println("Enter override code: ");
            String overrideAttempt = myScan.nextLine();
            //If the Rep's override code is wrong
            if (!super.checkOverrideCode(overrideAttempt)){
                System.out.println("Incorrect");
                return;
            }
        }
        String change;
        while (true){
            //entering a number for the choice is the most efficient method
            System.out.println("1. Street Address\n2. City\n" +
                    "3. State\n4. Zip\n5.Beer License #\n" +
                    "6. Loading dock capabilities\n7. Delivery constraints\n" +
                    "8. POC\n9. Phone\n0. Quit");
            Integer choice = Integer.parseInt(myScan.nextLine());

            if (choice.equals(0)){
                break;
            }
            switch (choice){
                case 1:
                    System.out.println("New Street Address: ");
                    change = myScan.nextLine();
                    customer.setStreet(change);

                case 2:
                    System.out.println("New City: ");
                    change = myScan.nextLine();
                    customer.setCity(change);
                case 3:
                    System.out.println("New State: ");
                    change = myScan.nextLine();
                    customer.setState(change);
                case 4:
                    System.out.println("New Zip: ");
                    change = myScan.nextLine();
                    customer.setZip(change);
                case 5:
                    System.out.println("Beer license #: ");
                    change = myScan.nextLine();
                    customer.setBeerLicNum(change);
                case 6:
                    System.out.println("New Loading dock capabilities: ");
                    change = myScan.nextLine();
                    customer.setLoadDockCapa(change);
                case 7:
                    System.out.println("New Delivery constraints: ");
                    change = myScan.nextLine();
                    customer.setDeliConstraints(change);

                case 8:
                    System.out.println("New POC: ");
                    change = myScan.nextLine();
                    customer.setPOC(change);

                case 9:
                    System.out.println("New Phone #: ");
                    change = myScan.nextLine();
                    customer.setPhoneNum(change);
            }
        }
    }
}
