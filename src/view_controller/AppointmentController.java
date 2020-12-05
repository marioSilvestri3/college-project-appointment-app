package view_controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Main;
import model.Appointment;
import model.Contact;
import utils.I18N;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AppointmentController {

    private final ObservableList<String> hh = FXCollections.observableArrayList();
    private final ObservableList<String> mm = FXCollections.observableArrayList();
    private final ObservableList<String> ampm = FXCollections.observableArrayList("am","pm");
    private boolean update = false;

    public void initialize() {

        for (int i=1; i <=12; ++i) {
            hh.add(String.valueOf(i));
        }
        start_hh.setItems(hh);
        end_hh.setItems(hh);
        create_hh.setItems(hh);
        update_hh.setItems(hh);

        for (int i=0; i<60; ++i) {
            if (i % 5 == 0) {
            mm.add(String.valueOf(i));
            }
        }
        mm.set(0, "00");
        mm.set(1, "05");

        appt_contact_names.setItems(Contact.getContacts());

        start_mm.setItems(mm);
        end_mm.setItems(mm);
        create_mm.setItems(mm);
        update_mm.setItems(mm);

        start_ampm.setItems(ampm);
        create_ampm.setItems(ampm);
        update_ampm.setItems(ampm);
        end_ampm.setItems(ampm);

        appt_created_by.setText(Main.username);
        appt_last_update_by.setText(Main.username);
        appt_id.setText(Appointment.getNextAutoID());
        appt_start.setValue(LocalDate.now());
        appt_end.setValue(LocalDate.now());
        appt_create_date.setValue(LocalDate.now());
        appt_last_update.setValue(LocalDate.now());
    }

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
    private TextField appt_customer_id;

    @FXML
    private TextField appt_user_id;

    @FXML
    private ComboBox<Contact> appt_contact_names;

    @FXML
    private Button btnAddUpdate;

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

    @FXML
    private void save() {
        int id = Integer.parseInt(appt_id.getText());
        String title = appt_title.getText();
        String desc = appt_description.getText();
        String location = appt_location.getText();
        String type = appt_type.getText();
        String createdBy = appt_created_by.getText();
        String updateBy = Main.username;
        int customerID = Integer.parseInt(appt_customer_id.getText());
        int userID = Integer.parseInt(appt_user_id.getText());
        int contactID = appt_contact_names.getValue().getContactID();
        LocalDateTime startDate = convertUISelectionToLocalDateTime(appt_start, start_hh, start_mm, start_ampm);
        LocalDateTime endDate = convertUISelectionToLocalDateTime(appt_end, end_hh, end_mm, end_ampm);
        LocalDateTime createdDate = convertUISelectionToLocalDateTime(appt_create_date, create_hh, create_mm, create_ampm);
        LocalDateTime lastUpdate = LocalDateTime.now();
        Appointment a = new Appointment(id, title, desc, location, type, startDate, endDate, createdDate, createdBy, lastUpdate, updateBy, customerID, userID, contactID);
        if (!update) {
            Appointment.addAppointment(a);
        } else {

            Appointment.updateAppointment(a);
        }
        Stage stage = (Stage) btnAddUpdate.getScene().getWindow();
        stage.close();
        HomeController.getInstance().initialize();
    }

    private LocalDateTime convertUISelectionToLocalDateTime(DatePicker date, ComboBox<String> hh, ComboBox<String> mm, ComboBox<String> ampm) {
        if (hh.getValue().length() == 1) {
            hh.setValue("0" + hh.getValue());
        }
        LocalTime lt = LocalTime.parse(hh.getValue()+":"+mm.getValue());
        if (ampm.getSelectionModel().getSelectedItem().equals("pm")) {
            lt = lt.plusHours(12);
        }
        return LocalDateTime.of(date.getValue(), lt);
    }

    @FXML
    private void cancel() {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    public void initUpdateAppointment(Appointment appointment) {
        update = true;
        lblAddUpdate.setText(I18N.getString("update"));
        btnAddUpdate.setText(I18N.getString("update"));

        appt_id.setText(String.valueOf(appointment.getAppointmentID()));
        appt_title.setText(appointment.getTitle());
        appt_description.setText(appointment.getDescription());
        appt_location.setText(appointment.getLocation());
        appt_type.setText(appointment.getType());
        appt_customer_id.setText(String.valueOf(appointment.getCustomerID()));
        appt_user_id.setText(String.valueOf(appointment.getUserID()));
        appt_contact_names.setValue(Contact.getContact(appointment.getContactID()));

        appt_start.setValue(appointment.getStart().toLocalDate());
        start_hh.setValue(String.valueOf((appointment.getStart().getHour()) % 12));
        start_mm.setValue(appointment.getStart().format(DateTimeFormatter.ofPattern("mm")));
        start_ampm.setValue(appointment.getStart().getHour() < 12 ? "am" : "pm");

        appt_end.setValue(appointment.getEnd().toLocalDate());
        end_hh.setValue(String.valueOf((appointment.getEnd().getHour()) % 12));
        end_mm.setValue(appointment.getEnd().format(DateTimeFormatter.ofPattern("mm")));
        end_ampm.setValue(appointment.getEnd().getHour() < 12 ? "am" : "pm");

        appt_last_update.setValue(appointment.getLastUpdate().toLocalDate());
        appt_last_update_by.setText(appointment.getLastUpdateBy());

        appt_create_date.setValue(appointment.getCreateDate().toLocalDate());
        appt_created_by.setText(appointment.getCreatedBy());

        create_hh.setValue(String.valueOf((appointment.getCreateDate().getHour()) % 12));
        create_mm.setValue(appointment.getCreateDate().format(DateTimeFormatter.ofPattern("mm")));
        create_ampm.setValue(appointment.getCreateDate().getHour() < 12 ? "am" : "pm");

        update_hh.setValue(String.valueOf((appointment.getLastUpdate().getHour()) % 12));
        update_mm.setValue(String.valueOf(appointment.getLastUpdate().getMinute()));
        update_ampm.setValue(appointment.getLastUpdate().getHour() < 12 ? "am" : "pm");
    }

    private void validateAppointment(Appointment a) {

    };
}
