package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;


public class DepositScreen {

    @FXML private Button depositButton;
    @FXML private TextField AmountTextField;

    public void deposit(ActionEvent event) throws SQLException, IOException {
        double amount = Double.parseDouble(AmountTextField.getText());
        String[] userInput = LoginPage.getUserName().split("\\s+");
        int userAcc;

        DBConnection getUser = new DBConnection();
        userAcc = getUser.getAccountId(userInput[0],userInput[1]);
        getUser.close();

        // Parse info

        DBConnection transaction = new DBConnection();
        transaction.newTransaction(amount,999,userAcc);
        transaction.close();


        DBConnection newConnection = new DBConnection();
        newConnection.updateBalance(userInput[0],userInput[1],amount);
        newConnection.close();

        goBack(event);
    }

    public void goBack(ActionEvent event) throws IOException {
        SceneSwitcher ss = new SceneSwitcher();
        ss.switchScene(event,"UserDashboard");
    }

}
