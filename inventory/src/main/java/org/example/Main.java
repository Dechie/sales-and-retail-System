package org.example;

import com.formdev.flatlaf.FlatLightLaf;
import org.example.Connection.DbConn;
import org.example.Model.Model;
import org.example.View.LoginFrame;
import org.example.View.MainFrame;
import org.example.View.MainView;


import java.awt.*;
import java.sql.*;

public class Main {
    public static Font global = new Font("Arial", Font.PLAIN, 20);
    public static Connection conn;
    static Model model;
    public static MainView view;
    public static Controller controller;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        FlatLightLaf.setup();
        conn = DbConn.getConnection();

        model = new Model(conn);
        view = new MainView();
        controller = new Controller(model, view);
        controller.init();

    }
}
