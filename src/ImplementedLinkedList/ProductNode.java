package ImplementedLinkedList;

public class ProductNode {

    private int number;
    private String description;
    private String code;
    private String size;
    private int cost;
    private int quantity;
    private int amount;
    private ProductNode nextProductNode;

    public ProductNode(int number,String description,String code,String size,int cost,int quantity,int amount){
        this.setNumber(number);
        this.setDescription(description);
        this.setCode(code);
        this.setSize(size);
        this.setCost(cost);
        this.setQuantity(quantity);
        this.setAmount(amount);
    }


    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getCost() {
        return this.cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public ProductNode getNextProductNode() {
        return nextProductNode;
    }

    public void setNextProductNode(ProductNode nextProductNode) {
        this.nextProductNode = nextProductNode;
    }


    @Override
    public String toString(){
        return "Data : "+this.number+"  "+this.description+"  "+this.code+"  "+this.size+"  "+this.cost+"  "+this.quantity+"  "+this.amount;
    }

}
