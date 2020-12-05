package view_controller;

import dao.DBAppointment;
import dao.DBCustomer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import utils.I18N;
import java.io.IOException;
import java.time.LocalDateTime;

public class HomeController {

    private static HomeController instance;
    public HomeController() {
        instance = this;
    }
    public static HomeController getInstance() {
        return instance;
    }

    public void initialize() {
        appt_table.setItems(Appointment.getAppointments());
        appt_id.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        appt_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        appt_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        appt_location.setCellValueFactory(new PropertyValueFactory<>("location"));
        appt_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        appt_start.setCellValueFactory(new PropertyValueFactory<>("formatStart"));
        appt_end.setCellValueFactory(new PropertyValueFactory<>("formatEnd"));
        appt_created.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        appt_created_by.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        appt_last_update.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        appt_last_update_by.setCellValueFactory(new PropertyValueFactory<>("lastUpdateBy"));
        appt_customer_id.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        appt_user_id.setCellValueFactory(new PropertyValueFactory<>("userID"));
        appt_contact_id.setCellValueFactory(new PropertyValueFactory<>("contactID"));

        cust_table.setItems(DBCustomer.getCustomers());
        cust_id.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        cust_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        cust_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        cust_postal.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        cust_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        cust_created.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        cust_created_by.setCellValueFactory(new PropertyValueFactory<>("createBy"));
        cust_last_update.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        cust_last_update_by.setCellValueFactory(new PropertyValueFactory<>("lastUpdateBy"));
        cust_division_id.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
    }

    @FXML
    TableView<Appointment> appt_table;

    @FXML
    private TableColumn<Appointment, Integer> appt_id;

    @FXML
    private TableColumn<Appointment, String> appt_title;

    @FXML
    private TableColumn<Appointment, String> appt_description;

    @FXML
    private TableColumn<Appointment, String> appt_location;

    @FXML
    private TableColumn<Appointment, String> appt_type;

    @FXML
    private TableColumn<Appointment, LocalDateTime> appt_start;

    @FXML
    private TableColumn<Appointment, LocalDateTime> appt_end;

    @FXML
    private TableColumn<Appointment, LocalDateTime> appt_created;

    @FXML
    private TableColumn<Appointment, String> appt_created_by;

    @FXML
    private TableColumn<Appointment, LocalDateTime> appt_last_update;

    @FXML
    private TableColumn<Appointment, String> appt_last_update_by;

    @FXML
    private TableColumn<Appointment, Integer> appt_customer_id;

    @FXML
    private TableColumn<Appointment, Integer> appt_user_id;

    @FXML
    private TableColumn<Appointment, Integer> appt_contact_id;

    @FXML
    private TableView<Customer> cust_table;

    @FXML
    private TableColumn<Customer, Integer> cust_id;

    @FXML
    private TableColumn<Customer, String> cust_name;

    @FXML
    private TableColumn<Customer, String> cust_address;

    @FXML
    private TableColumn<Customer, String> cust_postal;

    @FXML
    private TableColumn<Customer, String> cust_phone;

    @FXML
    private TableColumn<Customer, LocalDateTime> cust_created;

    @FXML
    private TableColumn<Customer, String> cust_created_by;

    @FXML
    private TableColumn<Customer, LocalDateTime> cust_last_update;

    @FXML
    private TableColumn<Customer, String> cust_last_update_by;

    @FXML
    private TableColumn<Customer, Integer> cust_division_id;

    @FXML
    private Button exit;

    @FXML
    void addAppointment() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("appointment.fxml"), I18N.resourceBundle);
        Parent root = loader.load();
        Stage appointmentStage = new Stage();
        appointmentStage.setScene(new Scene(root));
        appointmentStage.setTitle(I18N.getString("Add") + I18N.getString("Appointment"));
        appointmentStage.show();
    }

    @FXML
    void addCustomer() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("customer.fxml"), I18N.resourceBundle);
        Parent root = loader.load();
        Stage appointmentStage = new Stage();
        appointmentStage.setScene(new Scene(root));
        appointmentStage.setTitle(I18N.getString("Add") + I18N.getString("Customer"));
        appointmentStage.initModality(Modality.APPLICATION_MODAL);
        appointmentStage.show();
    }

    @FXML
    void deleteAppointment() {
    }

    @FXML
    void deleteCustomer() {
    }

    @FXML
    void updateAppointment() throws IOException {
        if (appt_table.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Appointment not selected");
            alert.setContentText("Select an appointment from the table");
            alert.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("appointment.fxml"), I18N.resourceBundle);
            Parent root = loader.load();
            AppointmentController appointmentController = loader.getController();
            appointmentController.initUpdateAppointment(appt_table.getSelectionModel().getSelectedItem());
            Stage appointmentStage = new Stage();
            appointmentStage.setScene(new Scene(root));
            appointmentStage.setTitle(I18N.getString("UpdateAppointment"));
            appointmentStage.initModality(Modality.APPLICATION_MODAL);
            appointmentStage.show();
        }
    }

    @FXML
    void updateCustomer() throws IOException {
        if (cust_table.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Customer not selected");
            alert.setContentText("Select a customer from the table");
            alert.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("customer.fxml"), I18N.resourceBundle);
            Parent root = loader.load();
            CustomerController customerController = loader.getController();
            customerController.initUpdateCustomer(cust_table.getSelectionModel().getSelectedItem());
            Stage customerStage = new Stage();
            customerStage.setScene(new Scene(root));
            customerStage.setTitle(I18N.getString("UpdateCustomer"));
            customerStage.initModality(Modality.APPLICATION_MODAL);
            customerStage.show();
        }
    }


    @FXML
    void exitApplication() {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }

}
