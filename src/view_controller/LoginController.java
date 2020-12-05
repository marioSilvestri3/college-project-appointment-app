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

import java.io.IOException;

public class LoginController {

    @FXML
    public void initialize() {
        locale_text.setText(I18N.resourceBundle.getString("locale") + " " + I18N.zoneId.toString() + " " + I18N.zoneOffset.toString());
    }

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
    void login() throws IOException {
        if (username.getText().equals(Main.username) && password.getText().equals(Main.password)) {
            Stage stage = (Stage) login.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"), I18N.resourceBundle);
            Parent root = loader.load();
            Stage homeStage = new Stage();
            homeStage.setScene(new Scene(root));
            homeStage.setTitle("Home");
            homeStage.show();
        } else {
            login_error.setText(I18N.resourceBundle.getString("login_error"));
        }
    }
}
