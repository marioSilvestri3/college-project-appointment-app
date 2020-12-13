package view_controller;

import dao.DBCustomer;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;
import model.Appointment;
import model.Customer;
import utils.I18N;
import utils.Log;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * The controller for the primary UI screen of the application. Handles appointments and customers with buttons to add, update, and delete. Displays a filter (utilizing lambda functions) for upcoming appointments by week or month, and a button that opens up the reports screen.
 * Author: Mario Silvestri III
 */
public class HomeController {
    /**
     * The home controller is a singleton class so that the home screen can be updated when changes are made to appointments and customers from their respective controllers.
     */
    private static HomeController instance;

    /**
     * The table displaying appointments.
     */
    @FXML
    private TableView<Appointment> appt_table;
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
    private TableColumn<Appointment, String> appt_contact;
    @FXML
    private TableColumn<Appointment, LocalDateTime> appt_start;
    @FXML
    private TableColumn<Appointment, LocalDateTime> appt_end;
    @FXML
    private TableColumn<Appointment, Integer> appt_customer_id;
    @FXML
    private TableColumn<Appointment, String> appt_customer_name;

    /**
     * The table displaying customers.
     */
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
    private TableColumn<Customer, Integer> cust_division_id;
    @FXML
    private TableColumn<Customer, String> cust_division_name;
    @FXML
    private TableColumn<Customer, Integer> cust_country_id;
    @FXML
    private TableColumn<Customer, String> cust_country_name;

    /**
     * Exits the application
     */
    @FXML
    private Button exit;

    public HomeController() {
        instance = this;
    }

    /**
     * Gets this controller singleton instance.
     * @return This controller singleton instance.
     */
    public static HomeController getInstance() {
        return instance;
    }

    /**
     * Initializes the home screen with appointments and customers from the database and displays information from each in their respective tables. This function is also called from the Customer and Appointment controllers to update the UI when changes are made to appointments and/or customers.
     */
    public void initialize() {

        appt_table.setItems(Appointment.getAppointments());
        appt_id.setCellValueFactory(new PropertyValueFactory<>("ID"));
        appt_title.setCellValueFactory(new PropertyValueFactory<>("Title"));
        appt_description.setCellValueFactory(new PropertyValueFactory<>("Description"));
        appt_location.setCellValueFactory(new PropertyValueFactory<>("Location"));
        appt_type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        appt_contact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        appt_start.setCellValueFactory(new PropertyValueFactory<>("formatStart"));
        appt_end.setCellValueFactory(new PropertyValueFactory<>("formatEnd"));
        appt_customer_id.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        appt_customer_name.setCellValueFactory(new PropertyValueFactory<>("customerName"));

        cust_table.setItems(DBCustomer.getCustomers());
        cust_id.setCellValueFactory(new PropertyValueFactory<>("ID"));
        cust_name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        cust_address.setCellValueFactory(new PropertyValueFactory<>("Address"));
        cust_postal.setCellValueFactory(new PropertyValueFactory<>("Postal"));
        cust_phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        cust_division_id.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
        cust_division_name.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        cust_country_id.setCellValueFactory(new PropertyValueFactory<>("countryID"));
        cust_country_name.setCellValueFactory(new PropertyValueFactory<>("countryName"));
    }

    /**
     * Opens the appointment screen in add mode.
     * @throws IOException For FXML filename exceptions
     */
    @FXML
    void addAppointment() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("appointment.fxml"), I18N.resourceBundle);
        Parent root = loader.load();
        Stage s = new Stage();
        s.setScene(new Scene(root));
        s.setTitle(I18N.getString("Add") + I18N.getString("Appointment"));
        s.show();
    }

    /**
     * Opens the customer screen in add mode.
     * @throws IOException For FXML filename exceptions
     */
    @FXML
    void addCustomer() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("customer.fxml"), I18N.resourceBundle);
        Parent root = loader.load();
        Stage s = new Stage();
        s.setScene(new Scene(root));
        s.setTitle(I18N.getString("Add") + I18N.getString("Customer"));
        s.initModality(Modality.APPLICATION_MODAL);
        s.show();
    }

    /**
     * Deletes the selected appointment from the database.
     */
    @FXML
    void deleteAppointment() {
        Appointment a = appt_table.getSelectionModel().getSelectedItem();
        Log.printSessionLn(I18N.getString("Appointment") + " " + a.getID() + I18N.getString("deleted by") + Main.username + " " + I18N.getString("at") + " " + LocalDateTime.now().format(I18N.formatter) +", " + I18N.SYSTEM_ZONE + " " + I18N.zoneOffset);
        a.delete();
        initialize();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(I18N.getString("Appointment") + " " + I18N.getString("Deleted"));
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setHeaderText(I18N.getString("Appointment ID") + " " + a.getID() + " " +I18N.getString("canceled") +  ".");
        alert.setContentText(I18N.getString("Type") + ": " + a.getType());
        alert.show();
    }

    /**
     * Deletes the selected customer from the database as long as its not associated with any appointments.
     */
    @FXML
    void deleteCustomer() {
        Customer c = cust_table.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(I18N.getString("Deleting") + " " +  I18N.getString("Customer") + " " + I18N.getString("ID") + ": " + c.getID());
        alert.initModality(Modality.APPLICATION_MODAL);
        if (c.getAppointments().size() == 0) {
            Log.printSessionLn(I18N.getString("Customer") + " " + c.getID() + I18N.getString("deleted by") + Main.username + " " + I18N.getString("at") + " " + LocalDateTime.now().format(I18N.formatter) +", " + I18N.SYSTEM_ZONE + " " + I18N.zoneOffset);
            c.delete();
            alert.setHeaderText(I18N.getString("Customer") + " " + I18N.getString("Deleted"));
            alert.setContentText(I18N.getString("Customer_ID") + ": " + c.getID() + " " + I18N.getString("successfully deleted."));
        } else {
            alert.setHeaderText(I18N.getString("Customer has appointments that must be deleted."));
            alert.setContentText(I18N.getString("Please delete the following appointments: ") + "\n");
            String appointments = "";
            for (Appointment a : c.getAppointments()) {
                appointments = appointments.concat(I18N.getString("Appointment ID") + ": " + a.getID() + "\n");
            }
            alert.setContentText(appointments);
        }
        alert.show();
        initialize();
    }

    /**
     * Opens the appointment screen in update mode.
     * @throws IOException For FXML filename exceptions
     */
    @FXML
    void updateAppointment() throws IOException {
        if (appt_table.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(I18N.getString("Appointment not selected."));
            alert.setContentText(I18N.getString("Please select an appointment from the table."));
            alert.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("appointment.fxml"), I18N.resourceBundle);
            Parent root = loader.load();
            AppointmentController ac = loader.getController();
            ac.initUpdateAppointment(appt_table.getSelectionModel().getSelectedItem());
            Stage s = new Stage();
            s.setScene(new Scene(root));
            s.setTitle(I18N.getString("Update_Appointment"));
            s.initModality(Modality.APPLICATION_MODAL);
            s.show();
        }
    }

    /**
     * Opens the customer screen in update mode.
     * @throws IOException For FXML filename exceptions
     */
    @FXML
    void updateCustomer() throws IOException {
        if (cust_table.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(I18N.getString("Error"));
            alert.setHeaderText(I18N.getString("Client non sélectionné."));
            alert.setContentText(I18N.getString("Please select a customer from the table."));
            alert.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("customer.fxml"), I18N.resourceBundle);
            Parent root = loader.load();
            CustomerController cc = loader.getController();
            cc.initUpdateCustomer(cust_table.getSelectionModel().getSelectedItem());
            Stage s = new Stage();
            s.setScene(new Scene(root));
            s.setTitle(I18N.getString("UpdateCustomer"));
            s.initModality(Modality.APPLICATION_MODAL);
            s.show();
        }
    }

    /**
     * Filter appointments occurring in the next week. This function utilizes a filtered list and a lambda expression to filter the list of all appointments down to those only starting within the next week.
     */
    @FXML
    void weeklyFilter() {
        appt_table.setItems(Appointment.getAppointments().filtered(a -> a.getStart().isAfter(LocalDateTime.now()) && a.getStart().isBefore(LocalDateTime.now().plusDays(7))));
    }

    /**
     * Filter appointments occurring in the next month. This function utilizes a filtered list and a lambda expression to filter the list of all appointments down to those only starting within the next month.
     */
    @FXML
    void monthlyFilter() {
        appt_table.setItems(Appointment.getAppointments().filtered(a -> a.getStart().isAfter(LocalDateTime.now()) && a.getStart().isBefore(LocalDateTime.now().plusMonths(1))));
    }

    /**
     * Removes the appointment filter and displays all appointments.
     */
    @FXML
    void noFilter() {
        appt_table.setItems(Appointment.getAppointments());
    }

    /**
     * Exits the application.
     */
    @FXML
    void exitApplication() {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }

    /**
     * Shows appointments coming up within the next 15 minutes.
     */
    void showUpcomingAppointments() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(I18N.getString("Upcoming_Appointments"));
        LocalDateTime now = LocalDateTime.now();
        FilteredList<Appointment> upcoming = Appointment.getAppointments().filtered(a -> a.getStart().isAfter(now) && a.getStart().isBefore(now.plusMinutes(15)));

        if (upcoming.size() == 0) {
            alert.setHeaderText(I18N.getString("No_Upcoming_Appointments"));
            alert.setContentText(I18N.getString("You_have_no_appointments_in_the_next_15_minutes."));
        } else {
            String text = "";
            alert.setHeaderText(I18N.getString("Upcoming_Appointments"));
            for (Appointment a : upcoming) {
                text = text.concat(I18N.getString("Upcoming_appointment:") + a.getID() + "\n" + I18N.getString("Appointment_Start:") + a.getFormatStart() + "\n");
            }
            alert.setContentText("\n" + text);
        }
        alert.showAndWait();
    }

    /**
     * Opens the reports screen.
     * @throws IOException For FXML filename exceptions
     */
    @FXML
    void openReports() throws IOException {
        Log.printSessionLn(I18N.getString("Reports opened by") + " " + Main.username + " " +  I18N.getString("at") + " " + LocalDateTime.now().format(I18N.formatter) +", " + I18N.SYSTEM_ZONE + " " + I18N.zoneOffset);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("report.fxml"), I18N.resourceBundle);
        Parent root = loader.load();
        Stage s = new Stage();
        s.setScene(new Scene(root));
        s.setTitle(I18N.getString("Reports"));
        s.initModality(Modality.APPLICATION_MODAL);
        s.show();
        System.out.println("openReports");
    }
}
