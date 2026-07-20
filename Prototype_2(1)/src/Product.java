/**
 * One product entry from the master product list.
 * Reps only ever see the id, description, brand and container,
 * so those are the only fields kept from the file.
 */
public class Product {
    private String productId;
    private String description;
    private String brandName;
    private String containerName;
    private String productStatus;

    public Product(String productId, String description, String brandName, String containerName, String productStatus) {
        this.productId = productId;
        this.description = description;
        this.brandName = brandName;
        this.containerName = containerName;
        this.productStatus = productStatus;
    }

    public String getProductId() {
        return productId;
    }

    public String getDescription() {
        return description;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getContainerName() {
        return containerName;
    }

    public String getProductStatus() {
        return productStatus;
    }

    /**
     * Checks if this product matches a search typed by the rep.
     * Matches against id, description and brand, case insensitive.
     */
    public boolean matches(String query) {
        String q = query.toLowerCase();
        return productId.toLowerCase().contains(q)
                || description.toLowerCase().contains(q)
                || brandName.toLowerCase().contains(q);
    }
}
