package view_controller;

import dao.DBCustomer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import model.Country;
import model.Customer;
import model.Division;
import utils.I18N;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CustomerController {

    private final ObservableList<String> hh = FXCollections.observableArrayList();
    private final ObservableList<String> mm = FXCollections.observableArrayList();
    private final ObservableList<String> ampm = FXCollections.observableArrayList("am","pm");

    private boolean update = false;

    public void initialize() {
        for (int i=1; i <=12; ++i) {
            hh.add(String.valueOf(i));
        }
        create_hh.setItems(hh);
        update_hh.setItems(hh);
        for (int i=0; i<60; ++i) {
            if (i % 5 == 0) {
                mm.add(String.valueOf(i));
            }
        }
        mm.set(0, "00");
        mm.set(1, "05");
        cust_country_names.setItems(Country.getCountries());
        cust_id.setText(Customer.getNextAutoID());
    }

    @FXML
    private Label cust_addupdate;

    @FXML
    private Label cust_customer;

    @FXML
    private TextField cust_name;

    @FXML
    private TextField cust_postal;

    @FXML
    private TextField cust_last_update_by;

    @FXML
    private DatePicker cust_last_update;

    @FXML
    private Button cust_add_update;

    @FXML
    private Button cancel;

    @FXML
    private TextField cust_id;

    @FXML
    private TextField cust_address;

    @FXML
    private ComboBox<Country> cust_country_names;

    @FXML
    private TextField cust_phone;

    @FXML
    private ComboBox<Division> cust_division_names;

    @FXML
    private DatePicker cust_create_date;

    @FXML
    private TextField cust_created_by;

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

    public void initUpdateCustomer(Customer c) {
        update = true;
        cust_addupdate.setText(I18N.getString("update"));
        cust_add_update.setText(I18N.getString("update"));
        cust_id.setText(String.valueOf(c.getCustomerID()));
        cust_name.setText(c.getName());
        cust_address.setText(c.getAddress());
        cust_postal.setText(c.getPostalCode());
        cust_phone.setText(c.getPhone());
        cust_create_date.setValue(c.getCreateDate().toLocalDate());
        create_hh.setValue(String.valueOf((c.getCreateDate().getHour()) % 12));
        create_mm.setValue(c.getCreateDate().format(DateTimeFormatter.ofPattern("mm")));
        create_ampm.setValue(c.getCreateDate().getHour() < 12 ? "am" : "pm");
        cust_created_by.setText(c.getCreateBy());
        cust_last_update.setValue(c.getCreateDate().toLocalDate());
        cust_last_update_by.setText(c.getLastUpdateBy());
        update_hh.setValue(String.valueOf((c.getCreateDate().getHour()) % 12));
        update_mm.setValue(c.getCreateDate().format(DateTimeFormatter.ofPattern("mm")));
        update_ampm.setValue(c.getCreateDate().getHour() < 12 ? "am" : "pm");
        cust_country_names.setValue(c.getAssociatedDivision().getAssociatedCountry());
        cust_division_names.setItems(c.getAssociatedDivision().getAssociatedCountry().getAssociatedDivisions());
        cust_division_names.setValue(c.getAssociatedDivision());
    }

    @FXML
    void cancel() {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void save() {
            int id = Integer.parseInt(cust_id.getText());
            String name = cust_name.getText();
            String address = cust_address.getText();
            String postal = cust_postal.getText();
            String phone = cust_phone.getText();
            LocalDateTime createDate = ofUISelection(cust_create_date, create_hh, create_mm, create_ampm);
            String createdBy = cust_created_by.getText();
            LocalDateTime lastUpdate = LocalDateTime.now();
            String lastUpdatedBy = Main.username;
            int divisionID = cust_division_names.getValue().getDivisionID();
            Customer customer = new Customer(id, name, address, postal, phone, createDate, createdBy, lastUpdate, lastUpdatedBy, divisionID);
        if (!update) {
            DBCustomer.addCustomer(customer);
        } else {
            DBCustomer.updateCustomer(customer);
        }
        Stage stage = (Stage) cust_add_update.getScene().getWindow();
        stage.close();
        HomeController.getInstance().initialize();
    }

    private LocalDateTime ofUISelection(DatePicker date, ComboBox<String> hh, ComboBox<String> mm, ComboBox<String> ampm) {
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
    void populateDivisionComboBox() {
        cust_division_names.setItems(cust_country_names.getValue().getAssociatedDivisions());
    }
}
