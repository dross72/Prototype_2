import java.util.ArrayList;

/**
 * The company itself. Holds the shared customer list that every
 * rep works from, plus the manager override code. The list is
 * static so all reps see the same customers, since a
 * representative's customer is the company's customer.
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
