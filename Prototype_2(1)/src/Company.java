import java.util.ArrayList;

/**
 * The company itself. Holds the customer list that every rep
 * works from, plus the manager override code. The list is static
 * so every rep is looking at the same customers.
 */
public class Company {
    private static ArrayList<Customer> customers = new ArrayList<>();
    private static String overrideCode = "ACME2026";

    /**
     * Adds a new customer to the company's shared customer list.
     */
    public void addCustomer(Customer newCustomer) {
        customers.add(newCustomer);
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    /**
     * Checks whether a rep's override code attempt is correct.
     */
    public Boolean checkOverrideCode(String overrideAttempt) {
        if (overrideAttempt.equals(overrideCode)) {
            return true;
        } else {
            return false;
        }
    }
}
