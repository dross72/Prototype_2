import java.util.ArrayList;

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
}
