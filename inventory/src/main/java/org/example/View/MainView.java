package org.example.View;

import javax.swing.*;

public class MainView implements View{
    private MainFrame mainFrame;
    private LoginFrame loginFrame;

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public LoginFrame getLoginFrame() {
        return loginFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void setLoginFrame(LoginFrame loginFrame) {
        this.loginFrame = loginFrame;
    }

    public MainView() {
        mainFrame = new MainFrame();
        loginFrame = new LoginFrame();
        show();
    }

    @Override
    public void show(){
        showLogin();
    }

    public void showMain() {
        loginFrame.setVisible(false);
        mainFrame.setVisible(true);
    }

    private void showLogin() {
        loginFrame.setVisible(true);
        mainFrame.setVisible(false);
    }

}
