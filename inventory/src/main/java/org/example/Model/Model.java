package org.example.Model;

import java.awt.*;
import java.util.ArrayList;
import java.sql.*;
import org.example.Main;

// access to database in this class.
// every function in here is database related, therefore
// we create a database connection that persists.
// we will bring the database connection from the controller page.
public class Model {

    ArrayList<Product> products = new ArrayList<>();
    Connection conn;
    String query;
    ResultSet rs;
    PreparedStatement pst;
    public Model(Connection conn) {
        this.conn = conn;
    }

    public void createConnection() {
        try {
            this.conn = DriverManager.getConnection(
                    "jdbc:mariadb://localhost:3306/zara_store",
                    "myuser",
                    "dechasa1234");

            System.out.println("Database connection established");
        } catch (Exception e) {
            e.getMessage();
        }

    }

    public Model() {

    }

    public boolean addProduct(Product p) {
        try {
            final String query = "INSERT INTO products(id, name, category, price, image) VALUES(?, ?, ?, ?, ?)";

            pst = this.conn.prepareStatement(query);

            if (this.conn == null) {
                createConnection();
            }
            pst.setInt(1, p.getId());
            pst.setString(2, p.getName());
            pst.setString(3, p.getCategory());
            pst.setString(4, p.getPrice());
            pst.setString(5, p.getImage());

            pst.executeUpdate();

            return true;
        } catch (Exception e){
            e.printStackTrace();
            System.out.print(e.getMessage());
        }

        return false;
    }
    public boolean fetchUser(String username, String password) {
        User auth;

        ArrayList<User> usrList = new ArrayList<>();

        try {
            final String query = "SELECT * FROM users";


            pst = this.conn.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, password);


            rs = pst.executeQuery();

            while (rs.next())
            {
                int id;
                String name, pass;
                id = rs.getInt("id");
                name = rs.getString("username");
                pass = rs.getString("password");
                User user = new User(id, name, pass);

                System.out.println(user);
                usrList.add(user);

            }

            System.out.println(usrList);

        } catch (Exception e){
            e.printStackTrace();
            System.out.print(e.getMessage());
        }


        for (User u : usrList) {

            String nm = u.getUsername();
            String ps = u.getPassword();
            System.out.println(nm + "\t" + ps);
            if (u.getUsername().equals(username) && u.getPassword().equals(password))
                return true;
        }

        return false;
    }


    public void addNewTransaction(String cost, String account, String dataTable) throws SQLException {

        final String qry = "INSERT INTO " + dataTable + "(cost, account_no) VALUES (?, ?);";

        try {
            pst = this.conn.prepareStatement(qry);
            pst.setString(1, cost);
            pst.setInt(2, Integer.parseInt(account));
            pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //public ArrayList<Product> fetchProducts() {
    public ArrayList<Product> fetchProducts() {
        final String qry = "SELECT * FROM products";
        String name, category, price;
        int id;
        Product prod;

        if (this.conn == null) {
            this.conn = Main.conn;
        }
        try {
            pst = this.conn.prepareStatement(qry);
            rs = pst.executeQuery();

            while (rs.next()) {
               id = rs.getInt("id");
               name = rs.getString("name");
               category = rs.getString("category");
               price = rs.getString("price");
               prod = new Product();
               prod.setId(id);
               prod.setName(name);
               prod.setCategory(category);
               prod.setPrice(price);
               prod = new Product(id, name, category, price);

               products.add(prod);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    public boolean addNewOrder(ArrayList<Product> products, String date) {
        try {
            for (Product p : products) {

                final String qry = "INSERT INTO Orders (ProductID, OrderDate, quantity) VALUES (?, ?, ?)";

                pst = conn.prepareStatement(qry);

                pst.setInt(1, p.getId());
                pst.setString(2, date);
                pst.setInt(3, 1);

                pst.executeUpdate();

            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public ArrayList<Order> fetchOrders() {

        final String qry = "SELECT * FROM orders";
        ArrayList<Order> orders = new ArrayList<>();
        String date;
        int id, quantity;
        Order order;

        if (this.conn == null) {
            this.conn = Main.conn;
        }
        try {
            System.out.println("model.fetchOrders()");
            pst = this.conn.prepareStatement(qry);
            rs = pst.executeQuery();

            while (rs.next()) {
                id = rs.getInt("productId");
                date = rs.getString("orderDate");
                quantity= rs.getInt("quantity");

                order = new Order();
                order.setProductID(id);
                order.setDate(date);
                order.setQuantity(quantity);
                System.out.println("order: " + order.toString());

                orders.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }

    public ArrayList<Payment> fetchPayments(String dataTable) {

        final String qry = "SELECT * FROM " + dataTable;
        ArrayList<Payment> payments = new ArrayList<>();
        int account;
        String cost;

        if (this.conn == null) {
            this.conn = Main.conn;
        }
        try {
            System.out.println("model.fetchPayments()");
            pst = this.conn.prepareStatement(qry);
            rs = pst.executeQuery();

            while (rs.next()) {
                account = rs.getInt("account_no");
                cost = rs.getString("cost");

                payments.add(new Payment(cost, account));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return payments;
    }
}
