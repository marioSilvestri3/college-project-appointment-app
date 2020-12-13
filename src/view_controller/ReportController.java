package view_controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import utils.I18N;
import utils.Log;

import java.util.Comparator;
import java.util.HashMap;

/**
 * Controls the reports screen.
 */
public class ReportController {

    @FXML
    private TextArea appointment_type;
    @FXML
    private TextArea appointment_month;
    @FXML
    private TextArea contacts;
    /**
     * This text box is for implementing the third required report. My report tracks user activity during the session. Adding, updating, and deleting customers and appointments will be tracked in this report, as well as logins and accessing of the reports menu.
     */
    @FXML
    private TextArea activity;
    @FXML
    private Button exit;

    /**
     * Initializes each text box with its associated report. The appointments by type, appointments by month, and contacts reports utilize each utilize a lambda expressions to succinctly describe complex functions.
     *
     * The appointment month and type lambda expressions take in their respective hashmaps and format the values into a string that displays cleanly on the UI.
     *
     * The contacts report utilizes a lambda to filter a list of appointments into a smaller list matching a specific associated customer id.
     */
    @FXML
    private void initialize() {
        HashMap<String, Integer> byType = Appointment.getAppointmentsByType();
        HashMap<String, Integer> byMonth = Appointment.getAppointmentsByMonth();


        // Appointments by type
        appointment_type.appendText(String.format("%-5s", "#").concat(String.format("%-35s", I18N.getString("Type"))).concat("\n"));
        byType.forEach((k, v) -> {
            appointment_type.appendText(String.format("%-5d", v).concat(String.format("%-35s", k).concat("\n")));
        });

        //Appointments by month
        appointment_month.appendText(String.format("%-5s", "#").concat(String.format("%-35s", I18N.getString("Month"))).concat("\n"));
        byMonth.forEach((k, v) -> {
            appointment_month.appendText(String.format("%-5d", v).concat(String.format("%-35s", k).concat("\n")));
        });

        // Schedule by contact
        for (Contact c : Contact.getContacts()) {
            ObservableList<Appointment> a = Appointment.getAppointments().filtered(a1 -> a1.getContactID() == c.getID()).sorted(Comparator.comparing(Appointment::getStart));
            contacts.appendText(c.getName() + "\n");
            for (Appointment a2 : a) {
                contacts.appendText(I18N.getString("ID") + ": " + a2.getID() + " " + I18N.getString("Title") + ": " + a2.getTitle() + " " + I18N.getString("Description") +  ": " + a2.getDescription() + "\n" + I18N.getString("Start") + ": " + a2.getFormatStart() + " " + I18N.getString("End") + ": " + a2.getFormatEnd() + " " + I18N.getString("Customer_ID") + ": " + a2.getCustomerID() + "\n");
            }
            contacts.appendText("\n");
        }

        // Activity log
        activity.setText(Log.getSessionLog());
    }

    /**
     * Exit the report screen.
     */
    @FXML
    private void exit() {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }
}
