package appka;

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
import java.sql.SQLException;

public class LoginPage {
    @FXML private Button loginButton;
    @FXML private TextField userTextField;

    public void login(ActionEvent event) throws IOException, SQLException {
        String input = userTextField.getText();
        String dbResult;

        DBconnection getAccount = new DBconnection();
        dbResult = getAccount.getAccount(input);


        if (input.equals(dbResult)){
            System.out.println("login succesful");
            SceneSwitcher ss = new SceneSwitcher();
            ss.switchScene(event, "UserDashboard");
        }
    }


}
