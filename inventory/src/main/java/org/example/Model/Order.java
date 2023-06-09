package org.example.Model;

public class Order {

    private int productID;
    private String date;
    private int quantity;

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Order{" +
                "productID=" + productID +
                ", date='" + date + '\'' +
                ", quantity=" + quantity +
                '}';
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
