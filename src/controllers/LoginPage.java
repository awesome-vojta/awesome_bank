package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;


public class LoginPage {

    @FXML private Button loginButton;
    @FXML private TextField userTextField;
    @FXML private PasswordField pinTextField;

    private static String userName;
    private static int password;

    public void createNewAccount(ActionEvent event) throws SQLException, IOException {
        userName = userTextField.getText();
        password = Integer.parseInt(pinTextField.getText());
        String[] userInput = userName.split("\\s+");

        DBConnection newConnection = new DBConnection();
        newConnection.createNewAccount(userInput[0],userInput[1],password);
        newConnection.close();

        goUserDashboard(event);
    }


    public void login(ActionEvent event) throws IOException, SQLException {
        userName = userTextField.getText();
        password = Integer.parseInt(pinTextField.getText());

        String[] userInput = userName.split("\\s+");

        DBConnection newConnection = new DBConnection();
        int dbPassword = newConnection.getPassword(userInput[0],userInput[1]);

        if (password==dbPassword) {
            System.out.println("Login succes");
            goUserDashboard(event);


        }else{
            System.out.println("login not success");
        }
        newConnection.close();
    }

    public void goUserDashboard(ActionEvent event) throws IOException, SQLException {
        SceneSwitcher ss = new SceneSwitcher();
        ss.switchScene(event,"UserDashboard");
    }


    public static int getPassword() {
        return password;
    }

    public static String getUserName() {
        return userName;
    }


}
