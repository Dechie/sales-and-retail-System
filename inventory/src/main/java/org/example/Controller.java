package org.example;

import org.example.Model.Model;
import org.example.Model.Product;
import org.example.Model.User;
import org.example.View.MainView;

import java.util.ArrayList;

public class Controller {
    private Model model;
    private MainView view;

    public Controller() {

    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public MainView getView() {
        return view;
    }

    public void setView(MainView view) {
        this.view = view;
    }

    public Controller(Model model, MainView view) {
        this.model = model;
        this.view = view;
    }

    public boolean authenticate(String username, String password) {
        return model.fetchUser(username, password);
    }

    public void init() {
        view.show();
    }

    public boolean addTransaction(String username, String cost, String dataTable) {
        try {
            this.model.addNewTransaction(username, cost, dataTable);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
       return false;
    }

    public ArrayList<Product> fetchProducts() {

        return model.fetchProducts();
    }


    public boolean insertData(ArrayList<Product> selectedProducts, String date) {
        try {
            this.model.addNewOrder(selectedProducts, date);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }
}
