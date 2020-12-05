package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.DBConnection;
import utils.I18N;

import java.io.IOException;

public class Main extends Application {

    public static final String username = "test";
    public static final String password = "test";

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view_controller/login.fxml"), I18N.resourceBundle);
        Parent root = loader.load();
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        DBConnection.openConnection();
        launch(args);
        DBConnection.closeConnection();
    }
}
