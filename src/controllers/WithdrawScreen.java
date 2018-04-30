package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class WithdrawScreen {
    @FXML
    private Button WithdrawButton;
    @FXML private TextField AmountTextField;

    public void withdraw(ActionEvent event) throws SQLException, IOException {
        double amount = Double.parseDouble(AmountTextField.getText());
        int userAcc;
        if (amount>getBalance()){
            return;
        }
        String[] userInput = LoginPage.getUserName().split("\\s+");
        // Parse info

        DBConnection getUser = new DBConnection();
        userAcc = getUser.getAccountId(userInput[0],userInput[1]);
        getUser.close();

        // Parse info

        DBConnection transaction = new DBConnection();
        transaction.newTransaction(amount,userAcc,999);
        transaction.close();

        amount = amount * (-1);
        DBConnection newConnection = new DBConnection();
        newConnection.updateBalance(userInput[0],userInput[1],amount);
        newConnection.close();
        //Update Database

        goBack(event);

    }

    public double getBalance() throws SQLException {
        String[] userInput = LoginPage.getUserName().split("\\s+");
        DBConnection balance = new DBConnection();
        return balance.getBalance(userInput[0],userInput[1]);
    }

    public void goBack(ActionEvent event) throws IOException {
        Parent userDashboard = FXMLLoader.load(getClass().getResource("/fxml/UserDashboard.fxml"));
        Scene scene = new Scene(userDashboard);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        // cast scenes window to stage
        window.setScene(scene);
        window.show();
    }
}
