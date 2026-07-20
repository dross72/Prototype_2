import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

/**
 * Loads and saves all of the app's data from plain files.
 * The client's network isn't built yet, so files simulate the
 * database: reps.txt, customers.txt, the MPL product file, and
 * one output file per submitted order in the orders folder.
 */
public class DataStore {
    private ArrayList<Representative> reps = new ArrayList<>();
    private Company company = new Company();
    private ArrayList<Product> products = new ArrayList<>();

    /**
     * Loads reps, customers and products into memory.
     */
    public DataStore() {
        loadReps();
        loadCustomers();
        products = new MasterProductList(findFile("MPL").getPath()).loadProducts();
    }

    private File findFile(String name) {
        // try a couple of likely spots so it runs from the IDE or the jar
        String[] spots = {"data/" + name, name, "src/" + name, "../data/" + name};
        for (String s : spots) {
            File f = new File(s);
            if (f.exists()) return f;
        }
        return new File("data/" + name);
    }

    private void loadReps() {
        try (BufferedReader br = new BufferedReader(new FileReader(findFile("reps.txt")))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] p = line.split("\t");
                if (p.length >= 3) {
                    reps.add(new Representative(p[0].trim(), p[1].trim(), p[2].trim()));
                }
            }
        } catch (IOException e) {
            System.out.println("Could not load reps file: " + e.getMessage());
        }
    }

    private void loadCustomers() {
        try (BufferedReader br = new BufferedReader(new FileReader(findFile("customers.txt")))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] p = line.split("\t");
                if (p.length >= 13) {
                    company.addCustomer(new Customer(p[0], p[2], p[3], p[4], p[5], p[6], p[7], p[8], p[9], p[10], p[11], p[1], p[12]));
                }
            }
        } catch (IOException e) {
            System.out.println("Could not load customers file: " + e.getMessage());
        }
    }

    /**
     * Checks a username/password combo against the reps on file.
     */
    public boolean login(String username, String password) {
        for (Representative r : reps) {
            if (r.checkLogin(username, password)) return true;
        }
        return false;
    }

    public String getRepName(String username) {
        for (Representative r : reps) {
            if (r.getUsername().equals(username)) return r.getName();
        }
        return username;
    }

    public Company getCompany() {
        return company;
    }

    public ArrayList<Customer> getCustomers() {
        return company.getCustomers();
    }

    /**
     * Returns only the customers that belong to the given rep.
     * Other reps' customers need the override code to open.
     */
    public ArrayList<Customer> getCustomersForRep(String repId) {
        ArrayList<Customer> mine = new ArrayList<>();
        for (Customer c : company.getCustomers()) {
            if (c.getOwnerRepId().equals(repId)) mine.add(c);
        }
        return mine;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    /**
     * Saves a brand new customer to the company list and appends it
     * to the customers file so it survives a restart.
     */
    public boolean addCustomer(Customer c) {
        File f = findFile("customers.txt");
        if (f.getParentFile() != null) {
            f.getParentFile().mkdirs();
        }
        try (FileWriter fw = new FileWriter(f, true)) {
            fw.write(c.toFileLine() + System.lineSeparator());
            company.addCustomer(c);
            return true;
        } catch (IOException e) {
            System.out.println("Could not save customer: " + e.getMessage());
            return false;
        }
    }

    /**
     * Escapes quotes and backslashes so product names or free text
     * can't break the JSON order file.
     */
    private String esc(String s) {
        return s == null ? "" : s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    /**
     * "Transmits" a submitted order by writing it to a JSON file in
     * the orders folder, which simulates sending it to the office.
     * Returns the file name it wrote.
     */
    public String submitOrder(Order order) {
        try {
            Files.createDirectories(Paths.get("orders"));
            String fileName = "orders/order_" + order.getSalesRepId() + "_" + order.getAccountID() + "_" + System.currentTimeMillis() + ".json";
            StringBuilder sb = new StringBuilder();
            sb.append("{\n");
            sb.append("  \"accountId\": \"").append(esc(order.getAccountID())).append("\",\n");
            sb.append("  \"deliveryDate\": \"").append(esc(order.getDeliDate())).append("\",\n");
            sb.append("  \"salesRepId\": \"").append(esc(order.getSalesRepId())).append("\",\n");
            sb.append("  \"deliveryRepId\": \"").append(esc(order.getDeliRepId())).append("\",\n");
            sb.append("  \"items\": [\n");
            ArrayList<Item> items = order.getItemsList();
            for (int i = 0; i < items.size(); i++) {
                Item it = items.get(i);
                sb.append("    {\"id\": \"").append(it.getId())
                  .append("\", \"name\": \"").append(esc(it.getItemName()))
                  .append("\", \"quantity\": ").append(it.getQuantity()).append("}");
                sb.append(i < items.size() - 1 ? ",\n" : "\n");
            }
            sb.append("  ]\n}");
            Files.writeString(Paths.get(fileName), sb.toString());
            return fileName;
        } catch (IOException e) {
            System.out.println("Could not write order: " + e.getMessage());
            return null;
        }
    }

    /**
     * Lists the file names of previously submitted orders, newest first,
     * so the home screen can show the rep their recent order history.
     */
    public ArrayList<String> getRecentOrderFiles(String repId) {
        ArrayList<String> names = new ArrayList<>();
        File dir = new File("orders");
        File[] files = dir.listFiles((d, n) -> n.endsWith(".json") && n.contains(repId));
        if (files != null) {
            java.util.Arrays.sort(files, (a, b) -> Long.compare(b.lastModified(), a.lastModified()));
            for (File f : files) names.add(f.getName());
        }
        return names;
    }
}
