import java.io.*;
import java.util.ArrayList;

/**
 * Reads the master product list file and hands back the products
 * a rep is allowed to see. The list ships as a tab separated text
 * file so the app runs with no external libraries.
 */
public class MasterProductList {
    private String filePath;

    //Made a constructor in case the file ever changes
    public MasterProductList(String filePath) {
        this.filePath = filePath;
    }

    //All the fields Reps can see
    //    productId column 1;
    //    description column 2;
    //    brandName column 4;
    //    containerName column 7;
    //The field that decides which product is restricted
    //    productStatus column 0;

    /**
     * Loads the products reps are allowed to view. Skips the header,
     * Restricted rows, and rows with missing fields since the client
     * warned us the data is messy.
     */
    public ArrayList<Product> loadProducts() {
        ArrayList<Product> products = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // header row
            while ((line = br.readLine()) != null) {
                String[] cells = line.split("\t");
                if (cells.length < 8) continue;
                String status = cells[0].trim();
                String id = cells[1].trim();
                String desc = cells[2].trim();
                String brand = cells[4].trim();
                String container = cells[7].trim();
                //skip restricted products, reps should never see them
                if (!status.equalsIgnoreCase("Active")) continue;
                if (id.isEmpty() || desc.isEmpty() || brand.isEmpty() || container.isEmpty()) continue;
                products.add(new Product(id, desc, brand, container, status));
            }
        } catch (IOException e) {
            System.err.println("Error reading product list: " + e.getMessage());
        }
        return products;
    }

}
