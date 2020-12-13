package view_controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import utils.I18N;
import utils.Log;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoginController {

    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Button login;
    @FXML
    private Label locale_text;
    @FXML
    private Label login_error;

    @FXML
    public void initialize() {
        locale_text.setText(I18N.resourceBundle.getString("Your_time_zone_is:") + " " + I18N.SYSTEM_ZONE.toString() + " " + I18N.zoneOffset.toString());
    }

    @FXML
    void login() throws IOException {
        boolean success = false;
        if (username.getText().equals(Main.username) && password.getText().equals(Main.password)) {
            success = true;
            Stage stage = (Stage) login.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"), I18N.resourceBundle);
            Parent root = loader.load();
            Stage homeStage = new Stage();
            homeStage.setScene(new Scene(root));
            homeStage.setTitle(I18N.getString("Home"));
            homeStage.show();
            HomeController.getInstance().showUpcomingAppointments();
        } else {
            login_error.setText(I18N.resourceBundle.getString("login_error"));
        }

        Log.printLogin((success ? "Successful" : "Failed") + " login at" + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));

        if (success) {
            Log.printSessionLn(I18N.getString("Session started by") + Main.username + " " + I18N.getString("at") + " " + LocalDateTime.now().format(I18N.formatter) +", " + I18N.SYSTEM_ZONE + " " + I18N.zoneOffset);
        }
    }
}
