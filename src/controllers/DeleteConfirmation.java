package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class DeleteConfirmation {
    @FXML private Button DeleteButton;

    public void deleteAccount(ActionEvent event) throws IOException, SQLException {
        String userName = LoginPage.getUserName();
        String[] userInput = userName.split("\\s+");

        DBConnection newConnection = new DBConnection();
        newConnection.deleteAccount(userInput[0],userInput[1]);
        newConnection.close();

        goLoginPage(event);
    }

    public void goLoginPage(ActionEvent event) throws IOException {
        Parent userDashboard = FXMLLoader.load(getClass().getResource("/fxml/LoginPage.fxml"));
        Scene scene = new Scene(userDashboard);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    public void goBack(ActionEvent event) throws IOException {
        Parent userDashboard = FXMLLoader.load(getClass().getResource("/fxml/UserDashboard.fxml"));
        Scene scene = new Scene(userDashboard);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
