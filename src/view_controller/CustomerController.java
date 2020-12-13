package view_controller;

import dao.DBCustomer;
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
import utils.Log;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Handles, via UI, the adding and updating of customer objects (records) into the database. The controller has two modes, add or update.
 */
public class CustomerController {
    /**
     * Used to determine the current mode of the customer screen: add or update.
     */
    private boolean update = false;
    /**
     * UI text displaying whether the screen is in add or update mode.
     */
    @FXML
    private Label cust_addupdate;
    @FXML
    private TextField cust_name;
    @FXML
    private TextField cust_postal;
    @FXML
    private TextField cust_last_update_by;
    @FXML
    private DatePicker cust_last_update;
    /**
     * UI button saves the customer into the database.
     */
    @FXML
    private Button cust_add_update;
    /**
     * UI button exits the customer screen without saving.
     */
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

    /**
     * Initialize the customer screen with the appropriate value ranges for selection components and also with customer data.
     */
    public void initialize() {

        create_hh.setItems(I18N.hh);
        create_mm.setItems(I18N.mm);
        create_ampm.setItems(I18N.ampm);

        update_hh.setItems(I18N.hh);
        update_mm.setItems(I18N.mm);
        update_ampm.setItems(I18N.ampm);


        cust_country_names.setItems(Country.getCountries());
        cust_id.setText(Customer.getNextAutoID());
        cust_create_date.setValue(LocalDate.now());

        cust_last_update.setValue(LocalDate.now());
        cust_last_update_by.setText(Main.username);

        cust_create_date.setValue(LocalDate.now());
        cust_created_by.setText(Main.username);

        create_hh.setValue(LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh")));
        create_mm.setValue(LocalDateTime.now().format(DateTimeFormatter.ofPattern("mm")));
        create_ampm.setValue(LocalDateTime.now().getHour() < 12 ? "AM" : "PM");

        update_hh.setValue(LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh")));
        update_mm.setValue(LocalDateTime.now().format(DateTimeFormatter.ofPattern("mm")));
        update_ampm.setValue(LocalDateTime.now().getHour() < 12 ? "AM" : "PM");
    }

    /**
     * Initializes the customer screen with the data from the selected customer to be updated.
     * @param c The customer to be updated.
     */
    public void initUpdateCustomer(Customer c) {
        update = true;
        cust_addupdate.setText(I18N.getString("Update"));
        cust_add_update.setText(I18N.getString("Update"));
        cust_id.setText(String.valueOf(c.getID()));
        cust_name.setText(c.getName());
        cust_address.setText(c.getAddress());
        cust_postal.setText(c.getPostal());
        cust_phone.setText(c.getPhone());
        cust_create_date.setValue(c.getCreateDate().toLocalDate());
        create_hh.setValue(c.getCreateDate().format(DateTimeFormatter.ofPattern("hh")));
        create_mm.setValue(c.getCreateDate().format(DateTimeFormatter.ofPattern("mm")));
        create_ampm.setValue(c.getCreateDate().getHour() < 12 ? "AM" : "PM");
        cust_created_by.setText(c.getCreateBy());
        cust_last_update.setValue(c.getCreateDate().toLocalDate());
        cust_last_update_by.setText(c.getLastUpdateBy());
        update_hh.setValue(c.getCreateDate().format(DateTimeFormatter.ofPattern("hh")));
        update_mm.setValue(c.getCreateDate().format(DateTimeFormatter.ofPattern("mm")));
        update_ampm.setValue(c.getCreateDate().getHour() < 12 ? "AM" : "PM");
        cust_country_names.setValue(c.getDivision().getAssociatedCountry());
        cust_division_names.setItems(c.getDivision().getAssociatedCountry().getAssociatedDivisions());
        cust_division_names.setValue(c.getDivision());
    }

    /**
     * Exit the customer screen without saving.
     */
    @FXML
    void cancel() {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    /**
     * Saves the input customer data. This function branches based on whether the customer is being added or updated.
     */
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
            Customer c = new Customer(id, name, address, postal, phone, createDate, createdBy, lastUpdate, lastUpdatedBy, divisionID);
        if (!update) {
            Log.printSessionLn(I18N.getString("Customer") + " " + c.getID() + " " + I18N.getString("added by") + " " + Main.username +" " + I18N.getString("at") + " " + LocalDateTime.now().format(I18N.formatter) +", " + I18N.SYSTEM_ZONE + " " + I18N.zoneOffset + ".");
            DBCustomer.addCustomer(c);
        } else {
            Log.printSessionLn(I18N.getString("Customer") + " " + c.getID() + " " + I18N.getString("updated by") + " " + Main.username + " " + I18N.getString("at") + " " + LocalDateTime.now().format(I18N.formatter) +", " + I18N.SYSTEM_ZONE + " " + I18N.zoneOffset + ".");
            DBCustomer.updateCustomer(c);
        }
        Stage stage = (Stage) cust_add_update.getScene().getWindow();
        stage.close();
        HomeController.getInstance().initialize();
    }

    /**
     * Converts UI values to a LocalDateTime.
     * @param date The datepicker box for selecting day/month/year
     * @param hh The hours combobox
     * @param mm The minutes combobox
     * @param ampm The am/pm combobox
     * @return  LocalDateTime value of the UI selection
     */
    private LocalDateTime ofUISelection(DatePicker date, ComboBox<String> hh, ComboBox<String> mm, ComboBox<String> ampm) {
        LocalTime lt = LocalTime.parse(hh.getValue()+":"+mm.getValue());
        if (ampm.getSelectionModel().getSelectedItem().equals("PM")) {
            lt = lt.plusHours(12);
        }
        return LocalDateTime.of(date.getValue(), lt);
    }

    /**
     * Gets the user selected country and populates the division combobox with the country's associated divisions.
     */
    @FXML
    void populateDivisionComboBox() {
        cust_division_names.setItems(cust_country_names.getValue().getAssociatedDivisions());
    }
}
