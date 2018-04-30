package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/appka/LoginPage.fxml"));
        Scene scene = new Scene(root);

        stage.setResizable(false);

        stage.setTitle("Login Page");
        stage.setScene(scene);
        stage.show();
    }
}
