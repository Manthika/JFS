package Database;

public class Product {

    private int productId;
    private String productName;
    private String productSize;
    private String productCode;
    private String quantity;
    private String date;

    public Product(int productId, String productName, String productSize, String productCode, String quantity, String date){
        this.setProductId(productId);
        this.setProductName(productName);
        this.setProductSize(productSize);
        this.setProductCode(productCode);
        this.setQuantity(quantity);
        this.setDate(date);
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
