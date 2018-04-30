package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionPage {

    @FXML private Label date1;
    @FXML private Label amount1;
    @FXML private Label type1;
    @FXML private Label recipient1;


    @FXML private Label date2;
    @FXML private Label amount2;
    @FXML private Label type2;
    @FXML private Label recipient2;


    @FXML private Label date3;
    @FXML private Label amount3;
    @FXML private Label type3;
    @FXML private Label recipient3;


    @FXML private Label date4;
    @FXML private Label amount4;
    @FXML private Label type4;
    @FXML private Label recipient4;

    @FXML private Label date5;
    @FXML private Label amount5;
    @FXML private Label type5;
    @FXML private Label recipient5;


    @FXML
    public void initialize() throws SQLException {

        Label[] dates = new Label[] {date1,date2,date3,date4,date5};
        Label[] amounts = new Label[] {amount1,amount2,amount3,amount4,amount5};
        Label[] types = new Label[] {type1,type2,type3,type4,type5};
        Label[] recipients = new Label[] {recipient1,recipient2,recipient3,recipient4,recipient5};

        String outputUserName;
        String userName = LoginPage.getUserName();
        String[] userInput = userName.split("\\s+");
        int userAcc, inputAcc, outputAcc;
        double amount;
        String date, type;
        type = "";

        DBConnection getUser = new DBConnection();
        userAcc = getUser.getAccountId(userInput[0],userInput[1]);
        getUser.close();


        DBConnection newConnection = new DBConnection();
        ResultSet myResult = newConnection.getTransaction(userInput[0],userInput[1]);

        int i = 0;
        while (myResult.next()) {
            date = myResult.getString("date").substring(0,10);
            inputAcc = myResult.getInt("input_acc");
            outputAcc = myResult.getInt("output_acc");
            amount = myResult.getDouble("amount");

            outputUserName = newConnection.getAccountName(outputAcc);

            if (inputAcc==999 || outputAcc==999) {
                if (outputAcc==userAcc){
                    type="Deposit";
                } else {
                    type="Withdrawal";
                }
            } else if (inputAcc==userAcc || outputAcc==userAcc){
                if (outputAcc==userAcc) {
                    type="Receive";
                } else {
                    type="Sent";
                }
            }

            dates[i].setText(date);
            amounts[i].setText(""+amount);
            types[i].setText(type);
            recipients[i].setText(""+outputUserName);
            i++;
        }
        newConnection.close();
    }

    public void goNewTransaction(ActionEvent event) throws IOException {
        SceneSwitcher ss = new SceneSwitcher();
        ss.switchScene(event,"NewTransaction");
    }

    public void goUserDashBoard(ActionEvent event) throws IOException {
        SceneSwitcher ss = new SceneSwitcher();
        ss.switchScene(event,"UserDashboard");
    }



}
