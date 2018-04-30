package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class NewTransaction {

    @FXML private TextField recipientTextField;
    @FXML private TextField amountTextField;

    public void sendTransaction(ActionEvent event) throws SQLException, IOException {
        int inputAcc, outputAcc;

        String[] inputUser = LoginPage.getUserName().split("\\s+");
        String[] outputUser = recipientTextField.getText().split("\\s+");
        double amount = Double.parseDouble(amountTextField.getText());
        if (amount>getBalance()){
            return;
        }

        DBConnection getIds = new DBConnection();
        inputAcc = getIds.getAccountId(inputUser[0],inputUser[1]);
        outputAcc = getIds.getAccountId(outputUser[0],outputUser[1]);
        getIds.close();

        DBConnection transaction = new DBConnection();
        transaction.newTransaction(amount,inputAcc,outputAcc);
        transaction.close();

        DBConnection balance = new DBConnection();
        balance.updateBalance(inputAcc,amount*(-1));
        balance.updateBalance(outputAcc,amount);
        balance.close();

        goUserDashboard(event);
    }

    public void goUserDashboard(ActionEvent event) throws IOException {
        SceneSwitcher ss = new SceneSwitcher();
        ss.switchScene(event,"UserDashboard");
    }

    public void goTransactionPage(ActionEvent event) throws IOException {
        SceneSwitcher ss = new SceneSwitcher();
        ss.switchScene(event,"TransactionPage");
    }

    public double getBalance() throws SQLException {
        String[] userInput = LoginPage.getUserName().split("\\s+");
        DBConnection balance = new DBConnection();
        return balance.getBalance(userInput[0],userInput[1]);
    }

}
