package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.*;


public class UserDashboard {

    @FXML private Button depositButton;
    @FXML private Button withdrawButton;
    @FXML private Button deleteButton;
    @FXML private Button blockButton;
    @FXML private Button goBackButton;
    @FXML private Button changePinButton;
    @FXML private Button exitButton;
    @FXML private Label AccountLabel;

    @FXML private Label BalanceLabel;

    @FXML
    private void initialize() throws SQLException {
        String userName = LoginPage.getUserName();
        String[] userInput = userName.split("\\s+");

        DBConnection myConnection = new DBConnection();
        double balance = myConnection.getBalance(userInput[0], userInput[1]);
        myConnection.close();
        AccountLabel.setText(userInput[0]+" "+userInput[1]);
        BalanceLabel.setText(""+balance);
    }


    public void deposit(ActionEvent event) throws IOException {
        SceneSwitcher ss = new SceneSwitcher();
        ss.switchScene(event,"DepositScreen");
    }

    public void withdraw(ActionEvent event) throws IOException {
        SceneSwitcher ss = new SceneSwitcher();
        ss.switchScene(event,"WithdrawScreen");
    }

    public void deleteAccount(ActionEvent event) throws IOException {
        SceneSwitcher ss = new SceneSwitcher();
        ss.switchScene(event,"DeleteConfirmation");
    }

    public void transactions(ActionEvent event) throws IOException {
        SceneSwitcher ss = new SceneSwitcher();
        ss.switchScene(event,"TransactionPage");
    }

    public void changePin(ActionEvent event) throws IOException {
        SceneSwitcher ss = new SceneSwitcher();
        ss.switchScene(event,"ChangePinPage");
    }

    public void exit(ActionEvent event) {
        System.exit(0);
    }

    public void goBack(ActionEvent event) throws IOException {
        SceneSwitcher ss = new SceneSwitcher();
        ss.switchScene(event,"LoginPage");
    }
}

