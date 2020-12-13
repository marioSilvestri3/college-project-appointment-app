package view_controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Main;
import model.Appointment;
import model.Contact;
import model.Customer;
import utils.I18N;
import utils.Log;


import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * Controls, via UI, the adding and updating of appointment objects (records) into the database. The controller has two modes, add or update.
 * Author: Mario Silvestri III
 */
public class AppointmentController {
    /**
     * Used to determine the current mode of the appointment screen: add or update.
     */
    private boolean update = false;
    /**
     * Display whether the appointment screen is in 'add' or 'update' mode.
     */
    @FXML
    private Label lblAddUpdate;
    @FXML
    private TextField appt_id;
    @FXML
    private TextField appt_title;
    @FXML
    private TextField appt_description;
    @FXML
    private TextField appt_location;
    @FXML
    private TextField appt_type;
    @FXML
    private DatePicker appt_start;
    @FXML
    private DatePicker appt_end;
    @FXML
    private DatePicker appt_create_date;
    @FXML
    private TextField appt_created_by;
    @FXML
    private DatePicker appt_last_update;
    @FXML
    private TextField appt_last_update_by;
    @FXML
    private ComboBox<Integer> appt_customer_id;
    @FXML
    private ComboBox<Integer> appt_user_id;
    @FXML
    private ComboBox<Contact> appt_contact_names;
    /**
     * UI button saves appointment into the database.
     */
    @FXML
    private Button btnAddUpdate;
    /**
     * UI button exits appointment screen without saving.
     */
    @FXML
    private Button cancel;
    @FXML
    private ComboBox<String> start_hh;
    @FXML
    private ComboBox<String> start_mm;
    @FXML
    private ComboBox<String> start_ampm;
    @FXML
    private ComboBox<String> update_hh;
    @FXML
    private ComboBox<String> update_mm;
    @FXML
    private ComboBox<String> update_ampm;
    @FXML
    private ComboBox<String> create_hh;
    @FXML
    private ComboBox<String> create_mm;
    @FXML
    private ComboBox<String> create_ampm;
    @FXML
    private ComboBox<String> end_hh;
    @FXML
    private ComboBox<String> end_mm;
    @FXML
    private ComboBox<String> end_ampm;
    /**
     * Outputs data validation errors.
     */
    @FXML
    private TextArea error_text;

    /**
     * Initialize the appointment screen with the appropriate value ranges for UI components and also with appointment data.
     */
    public void initialize() {

        start_hh.setItems(I18N.hh);
        end_hh.setItems(I18N.hh);
        create_hh.setItems(I18N.hh);
        update_hh.setItems(I18N.hh);

        appt_contact_names.setItems(Contact.getContacts());

        start_mm.setItems(I18N.mm);
        end_mm.setItems(I18N.mm);
        create_mm.setItems(I18N.mm);
        update_mm.setItems(I18N.mm);

        start_ampm.setItems(I18N.ampm);
        create_ampm.setItems(I18N.ampm);
        update_ampm.setItems(I18N.ampm);
        end_ampm.setItems(I18N.ampm);

        appt_created_by.setText(Main.username);
        appt_last_update_by.setText(Main.username);
        appt_id.setText(Appointment.getNextAutoID());
        appt_start.setValue(LocalDate.now());
        appt_end.setValue(LocalDate.now());
        appt_create_date.setValue(LocalDate.now());
        appt_last_update.setValue(LocalDate.now());

        appt_last_update.setValue(LocalDate.now());
        appt_last_update_by.setText(Main.username);

        appt_create_date.setValue(LocalDate.now());
        appt_created_by.setText(Main.username);

        start_hh.setValue("12");
        start_mm.setValue("00");
        start_ampm.setValue("PM");

        end_hh.setValue("01");
        end_mm.setValue("00");
        end_ampm.setValue("PM");

        create_hh.setValue(LocalTime.now().format(DateTimeFormatter.ofPattern("hh")));
        create_mm.setValue(LocalTime.now().format(DateTimeFormatter.ofPattern("mm")));
        create_ampm.setValue(LocalTime.now().format(DateTimeFormatter.ofPattern("a")));

        update_hh.setValue(LocalTime.now().format(DateTimeFormatter.ofPattern("hh")));
        update_mm.setValue(LocalTime.now().format(DateTimeFormatter.ofPattern("mm")));
        update_ampm.setValue(LocalTime.now().format(DateTimeFormatter.ofPattern("a")));

        ObservableList<Integer> custIDs = FXCollections.observableArrayList();
        ObservableList<Integer> userIDs = FXCollections.observableArrayList();

        for (Appointment a : Appointment.getAppointments()) {
            if (!userIDs.contains(a.getUserID())) {
                userIDs.add(a.getUserID());
            }
        }

        for (Customer c : Customer.getCustomers()) {
            custIDs.add(c.getID());
        }

        appt_customer_id.setItems(custIDs.sorted());
        appt_user_id.setItems(userIDs.sorted());
    }

    /**
     * Saves the input appointment data. This function branches based on whether the appointment is being added or updated.
     */
    @FXML
    private void save() {
        int id = Integer.parseInt(appt_id.getText());
        String title = appt_title.getText();
        String desc = appt_description.getText();
        String location = appt_location.getText();
        String type = appt_type.getText();
        String createdBy = appt_created_by.getText();
        String updateBy = Main.username;
        int customerID = appt_customer_id.getValue();
        int userID = appt_user_id.getValue();
        int contactID = appt_contact_names.getValue().getID();
        LocalDateTime startDate = convertUISelectionToLocalDateTime(appt_start, start_hh, start_mm, start_ampm);
        LocalDateTime endDate = convertUISelectionToLocalDateTime(appt_end, end_hh, end_mm, end_ampm);
        LocalDateTime createdDate = convertUISelectionToLocalDateTime(appt_create_date, create_hh, create_mm, create_ampm);
        LocalDateTime lastUpdate = LocalDateTime.now();
        Appointment a = new Appointment(id, title, desc, location, type, startDate, endDate, createdDate, createdBy, lastUpdate, updateBy, customerID, userID, contactID);
        if (!update) {
            Log.printSessionLn(I18N.getString("Appointment") + " " + a.getID() + " " + I18N.getString("added by") + " " + Main.username + I18N.getString("at")+ LocalDateTime.now().format(I18N.formatter) +", " + I18N.SYSTEM_ZONE + " " + I18N.zoneOffset);
            a.add();
        } else {
            Log.printSessionLn(I18N.getString("Appointment") + " " + a.getID() + I18N.getString("updated by") + Main.username + I18N.getString("at") + LocalDateTime.now().format(I18N.formatter) +", " + I18N.SYSTEM_ZONE + " " + I18N.zoneOffset);
            a.update();
        }
        Stage stage = (Stage) btnAddUpdate.getScene().getWindow();
        stage.close();
        HomeController.getInstance().initialize();
    }

    /**
     * Converts UI values to a LocalDateTime.
     * @param dp The datepicker box for selecting day/month/year
     * @param hh The hours combobox
     * @param mm The minutes combobox
     * @param ampm The am/pm combobox
     * @return  LocalDateTime value of the UI selection
     */
    private LocalDateTime convertUISelectionToLocalDateTime(DatePicker dp, ComboBox<String> hh, ComboBox<String> mm, ComboBox<String> ampm) {
        LocalTime lt = LocalTime.parse(hh.getValue() +":"+mm.getValue());
        LocalDateTime ldt = LocalDateTime.of(dp.getValue(), lt);
        if (ampm.getValue().equals("PM")) {
            ldt = ldt.plusHours(12);
        }
        if (hh.getValue().equals("12")) ldt = ldt.minusHours(12);
        return ldt;
    }

    /**
     * Exit the appointment screen without saving.
     */
    @FXML
    private void cancel() {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    /**
     * Initializes the appointment screen with the data from the selected appointment to be updated.
     * @param a The appointment to be updated.
     */
    public void initUpdateAppointment(Appointment a) {
        update = true;
        lblAddUpdate.setText(I18N.getString("Update"));
        btnAddUpdate.setText(I18N.getString("Update"));

        appt_id.setText(String.valueOf(a.getID()));
        appt_title.setText(a.getTitle());
        appt_description.setText(a.getDescription());
        appt_location.setText(a.getLocation());
        appt_type.setText(a.getType());
        appt_customer_id.setValue(a.getCustomerID());
        appt_user_id.setValue(a.getUserID());
        appt_contact_names.setValue(Contact.getContact(a.getContactID()));

        appt_start.setValue(a.getStart().toLocalDate());
        start_hh.setValue(a.getStart().format(DateTimeFormatter.ofPattern("hh")));
        start_mm.setValue(a.getStart().format(DateTimeFormatter.ofPattern("mm")));
        start_ampm.setValue(a.getStart().getHour() < 12 ? "AM" : "PM");

        appt_end.setValue(a.getEnd().toLocalDate());
        end_hh.setValue(a.getEnd().format(DateTimeFormatter.ofPattern("hh")));
        end_mm.setValue(a.getEnd().format(DateTimeFormatter.ofPattern("mm")));
        end_ampm.setValue(a.getEnd().getHour() < 12 ? "AM" : "PM");

        appt_last_update.setValue(a.getLastUpdate().toLocalDate());
        appt_last_update_by.setText(a.getLastUpdateBy());

        appt_create_date.setValue(a.getCreateDate().toLocalDate());
        appt_created_by.setText(a.getCreatedBy());

        create_hh.setValue(a.getCreateDate().format(DateTimeFormatter.ofPattern("hh")));
        create_mm.setValue(a.getCreateDate().format(DateTimeFormatter.ofPattern("mm")));
        create_ampm.setValue(a.getCreateDate().getHour() < 12 ? "AM" : "PM");

        update_hh.setValue(a.getLastUpdate().format(DateTimeFormatter.ofPattern("hh")));
        update_mm.setValue(a.getLastUpdate().format(DateTimeFormatter.ofPattern("mm")));
        update_ampm.setValue(a.getLastUpdate().getHour() < 12 ? "AM" : "PM");
    }

    /**
     * Performs validation checks on appointment data to ensure data and scheduling rules. Disables the add/update button and outputs validation errors while the current UI selections are invalid.
     */
    @FXML
    private void validate() {
        LocalDateTime startLDT;
        LocalDateTime endLDT;
        error_text.clear();
        btnAddUpdate.setDisable(false);

        try {
            appt_start.getValue();
        } catch (Exception e) {
            e.printStackTrace();
            error_text.appendText(I18N.getString("Invalid start time selected.") + "\n");
            btnAddUpdate.setDisable(true);
        }

        try {
            appt_end.getValue();
        } catch (Exception e) {
            e.printStackTrace();
            error_text.appendText(I18N.getString("Invalid end time selected." + "\n"));
            btnAddUpdate.setDisable(true);
        }


        if (start_ampm.getValue() != null
                && start_hh.getValue() != null
                && start_mm.getValue() != null
                && end_ampm.getValue() != null
                && end_hh.getValue() != null
                && end_mm.getValue() != null) {

            startLDT = convertUISelectionToLocalDateTime(appt_start, start_hh, start_mm, start_ampm);
            endLDT = convertUISelectionToLocalDateTime(appt_end, end_hh, end_mm, end_ampm);
/*            System.out.println(startLDT.toString());
            System.out.println(endLDT.toString());*/

            try {
                if (startLDT.plusMinutes(15).isAfter(endLDT)) {

                    error_text.appendText(I18N.getString("Appointment must be at least 15 minutes long.") + "\n");
                    btnAddUpdate.setDisable(true);
                }

                ZonedDateTime startZDT = ZonedDateTime.of(startLDT, I18N.SYSTEM_ZONE);
                ZonedDateTime endZDT = ZonedDateTime.of(endLDT, I18N.SYSTEM_ZONE);
/*                System.out.println(startZDT.toString());
                System.out.println(startZDT.withZoneSameInstant(I18N.NY_ZONE).toString());*/
                if (startZDT.withZoneSameInstant(I18N.NY_ZONE).toLocalTime().isBefore(I18N.NYOpen)
                 || startZDT.withZoneSameInstant(I18N.NY_ZONE).toLocalTime().isAfter(I18N.NYClose)) {
                    error_text.appendText(I18N.getString("Appointment must start between 8AM and 10PM Eastern.") + "\n");
                    btnAddUpdate.setDisable(true);
                }
                if (endZDT.withZoneSameInstant(I18N.NY_ZONE).toLocalTime().isBefore(I18N.NYOpen)
                    || endZDT.withZoneSameInstant(I18N.NY_ZONE).toLocalTime().isAfter(I18N.NYClose)) {
                    error_text.appendText(I18N.getString("Appointment must end between 8AM and 10PM Eastern.") + "\n");
                    btnAddUpdate.setDisable(true);
                }

            } catch (Exception e) {
                e.printStackTrace();
                error_text.appendText(I18N.getString("Error calculating Start / End dates.") + "\n");
            }

            if (appt_customer_id.getValue() != null) {
                try {
                    for (Appointment a : Appointment.getAppointments(appt_customer_id.getValue())) {
                        if ((startLDT.isAfter(a.getStart()) || startLDT.isEqual(a.getStart()))
                                && startLDT.isBefore(a.getEnd())
                                && a.getID() != Integer.parseInt(appt_id.getText())) {
                            error_text.appendText(I18N.getString("Start time conflicts with appointment ") + a.getID() + ".\n");
                            btnAddUpdate.setDisable(true);
                        }
                        if (endLDT.isAfter(a.getStart())
                                && (endLDT.isBefore(a.getEnd()) || endLDT.isEqual((a.getEnd())))
                                && a.getID() != Integer.parseInt(appt_id.getText())) {
                            error_text.appendText(I18N.getString("End time conflicts with appointment ") + a.getID() + ".\n");
                            btnAddUpdate.setDisable(true);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    btnAddUpdate.setDisable(true);
                    error_text.appendText(I18N.getString("Error parsing Customer ID.") + "\n");
                }
            }
        }
    }
}
