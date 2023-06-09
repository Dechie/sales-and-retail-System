package org.example.View;

import org.example.Main;
import org.example.Model.Model;
import org.example.Model.Order;
import org.example.Model.Payment;
import org.example.Model.Product;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.function.Function;

import static org.example.Main.controller;
import static org.example.Main.global;

public class MainFrame extends JFrame implements View {

    LocalDate date_today = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    JTabbedPane tabbedPane;
    JPanel products, addNewProduct, report, sales;

    JComboBox paymentMethods = new JComboBox();

    public ArrayList<Product> productArrayList;

    public int requiredState = 0;

    Font tableFont = new Font("Arial", Font.PLAIN, 20);

    ArrayList<Product> products1;

    Model mo = new Model(Main.conn);
    public MainFrame() {
        setTitle("Zara inventory management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane();

        sales = setSalesForm();
        products = setTableToPane();
        addNewProduct = setProductForm();
        report = setReportPane();


        tabbedPane.setFont(global);
        tabbedPane.addTab("Sale", sales);
        tabbedPane.addTab("Product", products);
        tabbedPane.addTab("Add New", addNewProduct);
        tabbedPane.addTab("Report", report);

        add(tabbedPane);
        UIManager.put( "TabbedPane.showTabSeparators", true );
        setLocation(500, 100);

        setLookFeel();

        setSize(1200, 800);
    }

    private void setLookFeel() {
        try {
            // Set System L&F
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatIntelliJLaf");

        }
        catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
               IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private JPanel setSalesForm() {

        double[] total2 = {0};

        JPanel pane = new JPanel();
        pane.setLayout(null);

        JLabel nameL, paymentL, summaryL;
        JTextField nameF, priceF;
        JTextField accountF;

        JComboBox paymentMethods = new JComboBox();
        JButton submit = new JButton("Submit");


        paymentL = new JLabel("Payment Method: ");
        summaryL = new JLabel("Account Number: ");


        priceF = new JTextField(20);
        accountF = new JTextField(20);

        Model mo = new Model(Main.conn);
        products1 = mo.fetchProducts();

        ArrayList<String> nameList = productList(Product::getName);
        ArrayList<Double> priceList = toDouble(productList(Product::getPrice));

        DefaultListModel<String> listModel = new DefaultListModel<>();

        for (String s : nameList) {
            listModel.addElement(s);
        }

        JLabel totalCost = new JLabel();
        JLabel selectItem = new JLabel("Select Items: ");
        JList<String> prodList = new JList<>(listModel);

        selectItem.setBounds(650, 50, 200, 30);
        prodList.setBounds(650, 100, 200, 300);
        totalCost.setBounds(900, 200, 200, 30);
        selectItem.setFont(global);
        prodList.setFont(global);
        totalCost.setFont(global);

        ArrayList<String> selected = new ArrayList<>();
        ArrayList<Double> prices = new ArrayList<>();
        ArrayList<Product> selectedProducts = new ArrayList<>();

        prodList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        prodList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int[] selectedIndices = prodList.getSelectedIndices();
                    System.out.println("selected indices: " + selectedIndices);
                    selected.clear();
                    prices.clear();
                    for (int i : selectedIndices) {

                        String value = listModel.getElementAt(i);
                        int index = nameList.indexOf(value);
                        prices.add(priceList.get(index));
                        selected.add(value);

                    }

                    double totalPrice = prices.stream()
                            .mapToDouble(Double::doubleValue)
                            .sum();

                    totalCost.setText("Total Price:" + totalPrice);
                    total2 [0]= totalPrice;

                }
            }
        });

        for (String str : selected){
            for (Product p : products1) {
                if (p.getName().equals(str)) {
                    selectedProducts.add(p);
                    break;
                }
            }
        }

        String date = date_today.format(formatter);

        pane.add(selectItem);
        pane.add(prodList);
        pane.add(totalCost);

        paymentL.setBounds(150, 140, 250, 30);
        paymentL.setFont(global);

        paymentMethods.addItem("CASH");
        paymentMethods.addItem("AMOLE");
        paymentMethods.addItem("CBE");
        paymentMethods.addItem("TELEBIRR");
        paymentMethods.setBounds(320, 140, 300, 30);
        pane.add(paymentL);
        pane.add(paymentMethods);

        summaryL.setBounds(150, 210, 250, 30);
        summaryL.setFont(global);
        accountF.setBounds(320, 210, 300, 30);
        pane.add(summaryL);
        pane.add(accountF);

        submit.setForeground(Color.WHITE);
        submit.setBackground(new Color(80, 120, 200));
        submit.setBounds(400, 370, 150, 35);

        submit.addActionListener(e -> {

            double tt = total2[0];
            String total3 = Double.toString(tt);
            String dataTable = paymentMethods.getSelectedItem().toString();
            String account = accountF.getText();

            if (controller.addTransaction(total3, account, dataTable) ) {
                JOptionPane.showMessageDialog(pane,
                        "Your transaction has been successfully conducted.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(pane,
                        "invalid inputs or connection failure",
                        "Sales registry failed",
                        JOptionPane.ERROR_MESSAGE);
            }

            priceF.setText(null);
            accountF.setText(null);
        });
        pane.add(submit);

        return pane;
    }

    private JPanel setTableToPane() {
        requiredState = 1;

        JPanel products = new JPanel();
        products.setLayout(null);
        JTable table;

        DefaultListModel<Product> model = new DefaultListModel<>();

        ArrayList<Product> productList = mo.fetchProducts();

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("id");
        tableModel.addColumn("name");
        tableModel.addColumn("category");
        tableModel.addColumn("price");

        for (Product p : productList) {
            model.addElement(p);
            Object[] row = {p.getId(), p.getName(), p.getCategory(), p.getPrice()};
            tableModel.addRow(row);
        }

        table = new JTable(tableModel);

        table.setFont(tableFont);
        table.getTableHeader().setFont(tableFont);
        JScrollPane holdTable = new JScrollPane(table);
        holdTable.setBounds(30, 20, 550, 450);
        table.setFillsViewportHeight(true);
        products.add(holdTable);

        requiredState = 0;

        return products;
    }

    private JPanel setProductForm() {
        JPanel pane = new JPanel();
        pane.setLayout(null);

        JButton addP = new JButton("Add Product");
        JLabel idL, nameL, categoryL, priceL, imageL;
        idL = new JLabel("Product ID:");
        nameL = new JLabel("Product:");

        categoryL = new JLabel("Category:");
        priceL = new JLabel("Total Price:");
        imageL = new JLabel("Image File:");

        JTextField idF, nameF, categoryF, priceF, imageF;
        idF = new JTextField(20);
        nameF = new JTextField(20);
        categoryF = new JTextField(20);
        priceF = new JTextField(20);
        imageF = new JTextField();

        idL.setBounds(150, 50, 200, 30);
        idF.setBounds(350, 50, 300, 30);

        nameL.setBounds(150, 110, 200, 30);
        nameF.setBounds(350, 110, 300, 30);

        categoryL.setBounds(150, 170, 200, 30);
        categoryF.setBounds(350, 170, 300, 30);

        priceL.setBounds(150, 230, 200, 30);
        priceF.setBounds(350, 230, 300, 30);

        imageL.setBounds(150, 290, 200, 30);
        imageF.setBounds(350, 290, 300, 30);
        addP.setBounds(400, 400, 150, 35);

        addP.setForeground(Color.WHITE);
        addP.setBackground(new Color(80, 120, 200));

        JLabel errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);
        errorLabel.setBounds(430, 350, 250, 30);
        pane.add(errorLabel);



        addP.addActionListener(e -> {
            String[] attributes = new String[5];
            boolean emptyFields = true;
            boolean numberFields = true;

            attributes[0] = idF.getText();
            attributes[1] = nameF.getText();
            attributes[2] = categoryF.getText();
            attributes[3] = priceF.getText();
            attributes[4] = imageF.getText();


            for (String str : attributes) {
                if (str.equals("")) {
                    emptyFields = false;
                    break;
                }
            }

            try {
                Integer.parseInt(attributes[0]);
                Double.parseDouble(attributes[3]);
            } catch (NumberFormatException n) {
                n.printStackTrace();
                numberFields = false;
            }

            if (!emptyFields) {
                errorLabel.setText("Input Fields properly.");
            } else {
                if (!numberFields) {
                    errorLabel.setText("Invalid Number Format");
                } else {
                    Product prod = new Product(attributes);
                    if (mo.addProduct(prod) )
                    {
                        JOptionPane.showMessageDialog(pane,
                                "Product successfully added to database.",
                                "Inane custom dialog",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(pane,
                                "Error occured",
                                "Inane custom dialog",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        pane.add(idL);
        pane.add(idF);

        pane.add(nameL);
        pane.add(nameF);

        pane.add(categoryL);
        pane.add(categoryF);

        pane.add(priceL);
        pane.add(priceF);

        pane.add(imageL);
        pane.add(imageF);
        pane.add(addP);

        return pane;
    }
    private JPanel setReportPane() {
        JPanel pane = new JPanel();
        pane.setLayout(null);

        JLabel title1 = new JLabel("Orders");
        title1.setFont(global);
        title1.setBounds(50, 50, 250, 30);
        pane.add(title1);

        JLabel title2 = new JLabel("PAYMENT TRANSACTIONS");
        title2.setFont( new Font("Arial", Font.BOLD, 15));
        title2.setBounds(450, 200, 250, 30);
        pane.add(title2);

        JLabel title3 = new JLabel("SELECT PAYMENT METHOD");
        title3.setFont( new Font("Arial", Font.BOLD, 15));
        title3.setBounds(450, 230, 250, 30);
        pane.add(title3);

        ButtonGroup payMethods = new ButtonGroup();
        JRadioButton payment1 = new JRadioButton("CBE", false);
        payment1.setBounds(450, 260, 100, 20);
        payMethods.add(payment1);
        JRadioButton payment2 = new JRadioButton("CASH", false);
        payment2.setBounds(450, 290, 100, 20);
        payMethods.add(payment2);
        JRadioButton payment3 = new JRadioButton("AMOLE", false);
        payment3.setBounds(450, 320, 100, 20);
        payMethods.add(payment3);
        JRadioButton payment4 = new JRadioButton("TELEBIRR", false);
        payment4.setBounds(450, 350, 100, 20);
        payMethods.add(payment4);

        pane.add(payment1);
        pane.add(payment2);
        pane.add(payment3);
        pane.add(payment4);

        ArrayList<Payment> cbePay = mo.fetchPayments("CBE");
        ArrayList<Payment> cashPay = mo.fetchPayments("CASH");
        ArrayList<Payment> amolePay = mo.fetchPayments("AMOLE");
        ArrayList<Payment> tele = mo.fetchPayments("TELEBIRR");


        DefaultTableModel cashModel = new DefaultTableModel();
        DefaultTableModel cbeModel = new DefaultTableModel();
        DefaultTableModel amoleModel = new DefaultTableModel();
        DefaultTableModel teleModel = new DefaultTableModel();
        DefaultListModel<Payment> cbeL = new DefaultListModel<>();
        DefaultListModel<Payment> cL = new DefaultListModel<>();
        DefaultListModel<Payment> aL = new DefaultListModel<>();
        DefaultListModel<Payment> tbL = new DefaultListModel<>();
        JTable pTable = new JTable();

        JButton refresh = new JButton("Refresh");
        refresh.setBounds(1050, 120, 50, 30);
        refresh.addActionListener(e -> {
            refreshElements(cbePay, "CBE", cbeModel, cbeL);
            refreshElements(cashPay, "CASH", cashModel, cL);
            refreshElements(amolePay, "AMOLE", amoleModel, aL);
            refreshElements(tele, "TELEBIRR", teleModel, tbL);
        });
        pane.add(refresh);


        cbeModel.addColumn("Price");
        cbeModel.addColumn("Account Number");

        cashModel.addColumn("Price");
        cashModel.addColumn("Account Number");

        amoleModel.addColumn("Price");
        amoleModel.addColumn("Account Number");

        teleModel.addColumn("Price");
        teleModel.addColumn("Account Number");

        for (Payment p : cbePay) {
            cbeL.addElement(p);
            Object[] row = {p.getCost(), p.getAccount_no()};
            cbeModel.addRow(row);
        }

        for (Payment p : cashPay) {
            cL.addElement(p);
            Object[] row = {p.getCost(), p.getAccount_no()};
            cashModel.addRow(row);
        }

        for (Payment p : amolePay) {
            aL.addElement(p);
            Object[] row = {p.getCost(), p.getAccount_no()};
            amoleModel.addRow(row);
        }

        for (Payment p : tele) {
            tbL.addElement(p);
            Object[] row = {p.getCost(), p.getAccount_no()};
            teleModel.addRow(row);
        }

        payment1.addActionListener(e -> {
            pTable.setModel(cbeModel);
        });

        payment2.addActionListener(e -> {
            pTable.setModel(cashModel);
        });

        payment3.addActionListener(e -> {
            pTable.setModel(amoleModel);
        });

        payment4.addActionListener(e -> {
            pTable.setModel(teleModel);
        });

        pTable.setFont(tableFont);
        pTable.getTableHeader().setFont(tableFont);
        JScrollPane holdPTable = new JScrollPane(pTable);
        holdPTable.setBounds(680, 100, 350, 450);
        pTable.setFillsViewportHeight(true);
        pane.add(holdPTable);


        ArrayList<Order> orderList = mo.fetchOrders();

        DefaultTableModel tableModel = new DefaultTableModel();
        DefaultListModel<Order> model = new DefaultListModel<>();
        JTable table;

        tableModel.addColumn("Product Id");
        tableModel.addColumn("date");
        tableModel.addColumn("quantity");

        for (Order o : orderList) {
            model.addElement(o);
            Object[] row = {o.getProductID(), o.getDate(), o.getQuantity()};
            tableModel.addRow(row);
        }

        table = new JTable(tableModel);

        table.setFont(tableFont);
        table.getTableHeader().setFont(tableFont);
        JScrollPane holdtable = new JScrollPane(table);
        holdtable.setBounds(50, 100, 350, 450);
        table.setFillsViewportHeight(true);
        pane.add(holdtable);


        return pane;
    }

    private ArrayList<Double> toDouble(ArrayList<String> strings) {

        ArrayList<Double> doubles = new ArrayList<>();

        for (String str : strings)
            doubles.add(Double.parseDouble(str));

        return doubles;
    }

    public ArrayList<String> productList(Function<Product, String> function) {
        ArrayList<String> result = new ArrayList<>();

        for (Product p : products1) {
            String value = function.apply(p);

            if (value != null && !value.isEmpty())
                result.add(value);
        }

        return result;
    }

    public void refreshElements(ArrayList<Payment> paymentList, String paymentType, DefaultTableModel tableModel, DefaultListModel<Payment> listModel) {
        paymentList.clear();
        paymentList = mo.fetchPayments(paymentType);
        System.out.println(paymentList);
        listModel = null;
        listModel = new DefaultListModel<>();
        tableModel = null;
        tableModel = new DefaultTableModel();

        for (Payment p : paymentList) {
            listModel.addElement(p);
            Object[] row = {p.getCost(), p.getAccount_no()};
            tableModel.addRow(row);
        }
    }
}