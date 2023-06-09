package org.example.View;

import org.example.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import static org.example.Main.*;

public class LoginFrame extends JFrame implements View {
    JLabel passwordL, usernameL;
    public static JTextField username;
    public static JPasswordField password;
    public JButton submit;

    JPanel pane;
    public LoginFrame(){
        super("Inventory System Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel welcome = new JLabel("WELCOME TO ZARA STORE");
        welcome.setFont(global);
        welcome.setBounds(100, 30, 300, 30);


        usernameL = new JLabel("Username: ");
        usernameL.setFont(global);
        usernameL.setBounds(100, 80, 120, 25);

        username = new JTextField();
        username.setFont(global);
        username.setBounds(100, 110, 250, 35);

        passwordL = new JLabel("Password: ");
        passwordL.setFont(global);
        passwordL.setBounds(100, 160, 120, 25);

        password = new JPasswordField();
        password.setFont(global);
        password.setBounds(100, 190, 250, 35);

        submit = new JButton("Login");
        submit.setFont(global);
        submit.setBounds(100, 290, 250, 40);
        submit.setForeground(Color.WHITE);
        submit.setBackground(new Color(80, 120, 200));

        JLabel errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);
        errorLabel.setBounds(100, 230, 270, 30);


        submit.addActionListener(e -> {
            String name = username.getText();
            String pass = new String(password.getPassword());

            if (name.equals("") || pass.equals("")) {
                errorLabel.setText("Input the fields properly");
            } else {
                if (controller.authenticate(name, pass)) {
                    view.showMain();
                } else {
                    errorLabel.setText("Incorrect Username or Password");
                }
            }

        });

        pane = new JPanel();
        pane.setLayout(null);

        pane.add(welcome);
        pane.add(usernameL);
        pane.add(username);
        pane.add(passwordL);
        pane.add(password);
        pane.add(errorLabel);
        pane.add(submit);
        add(pane);

        setLocation(600, 300);
        setSize(480, 480);
        setLookAndFeel();

    }

    private void setLookAndFeel() {
        try {
            // Set System L&F
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatIntelliJLaf");

        }
        catch (UnsupportedLookAndFeelException | ClassNotFoundException | IllegalAccessException |
               InstantiationException e) {
            // handle exception
        }
    }
}
