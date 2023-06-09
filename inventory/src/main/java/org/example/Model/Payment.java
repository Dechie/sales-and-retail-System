package org.example.Model;

public class Payment {
    private String cost;
    private int account_no;

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public int getAccount_no() {
        return account_no;
    }

    public Payment(String cost, int account_no) {
        this.cost = cost;
        this.account_no = account_no;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "cost='" + cost + '\'' +
                ", account_no=" + account_no +
                '}';
    }
}
