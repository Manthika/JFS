package Database;

public class Transaction {

    private int transactionId;
    private String customerName;
    private String customerEmail;
    private String amount;
    private String bought;
    private String date;

    public Transaction(int transactionId, String customerName, String customerEmail, String amount, String bought, String date){
        this.setTransactionId(transactionId);
        this.setCustomerName(customerName);
        this.setCustomerEmail(customerEmail);
        this.setAmount(amount);
        this.setBought(bought);
        this.setDate(date);
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBought() {
        return bought;
    }

    public void setBought(String bought) {
        this.bought = bought;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
