package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.DBConnection;
import utils.I18N;

import java.io.IOException;

/**
 * Runs this JavaFX desktop application that manages appointments with customers.
 * Author: Mario Silvestri III.
 */
public class Main extends Application {

    public static final String username = "test";
    public static final String password = "test";

    /**
     * This is the start method. It is called after the Application.launch method and initiates the fxml root, stage, and scene for the login screen.
     * @param args Not used for this application.
     */
    public static void main(String[] args) {
        DBConnection.openConnection();
        launch(args);
        DBConnection.closeConnection();
    }

    /**
     * This is the start method. It is called after the Application.launch method and initiates the fxml root, stage, and scene for the login screen.
     * @param primaryStage The stage for the login screen.
     * @throws IOException For FXML filename exceptions
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view_controller/login.fxml"), I18N.resourceBundle);
        Parent root = loader.load();
        primaryStage.setTitle(I18N.getString("Login"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
