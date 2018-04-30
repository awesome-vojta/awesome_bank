package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;

import java.io.IOException;
import java.sql.*;

public class ChangePinPage {
    @FXML private Button ChangePinButton;
    @FXML private PasswordField NewPinField;

    public void changePin(ActionEvent event) throws IOException, SQLException {
        int amount = Integer.parseInt(NewPinField.getText());
        String userName = LoginPage.getUserName();
        String[] userInput = userName.split("\\s+");
        // Parse info

        DBConnection newConnection = new DBConnection();
        newConnection.changePin(userInput[0],userInput[1],amount);
        newConnection.close();
        // Update DB

        goBack(event);
    }

    public void goBack(ActionEvent event) throws IOException {
        SceneSwitcher ss = new SceneSwitcher();
        ss.switchScene(event,"UserDashboard");
    }
}
