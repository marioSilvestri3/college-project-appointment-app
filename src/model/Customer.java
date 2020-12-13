package model;

import dao.DBCustomer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.I18N;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * The class for storing customer data (database records).
 * Author: Mario Silvestri III
 */
public class Customer {
    /**
     * The unique customer ID.
     */
    private final int ID;
    /**
     * The date and time the customer was created.
     */
    private final LocalDateTime createDate;
    /**
     * The user who created the appointment.
     */
    private final String createBy;
    /**
     * The name of the customer.
     */
    private String name;
    /**
     * The address of the customer.
     */
    private String address;
    /**
     * The postal mailing code of the customer.
     */
    private String postal;
    /**
     * The phone number of the customer.
     */
    private String phone;
    /**
     * The date and time of the last update to this customer.
     */
    private LocalDateTime lastUpdate;
    /**
     * The user who last updated this customer.
     */
    private String lastUpdateBy;
    /**
     * The first level division associated with this customer.
     */
    private Division division;

    /**
     * Customer constructor.
     * @param ID Customer ID (Unique)
     * @param name Name
     * @param address Address
     * @param postal Postal code
     * @param phone Phone number
     * @param createDate Date and time created
     * @param createBy User who created
     * @param lastUpdate Date and time last updated
     * @param lastUpdateBy User who last updated
     * @param divisionID Division ID of customer's locale
     */
    public Customer(int ID, String name, String address, String postal, String phone, LocalDateTime createDate, String createBy, LocalDateTime lastUpdate, String lastUpdateBy, int divisionID) {
        this.ID = ID;
        this.name = name;
        this.address = address;
        this.postal = postal;
        this.phone = phone;
        this.createDate = createDate;
        this.createBy = createBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
        this.division = Division.getDivision(divisionID);
    }

    /**
     * Get the customer associated with the provided customer id.
     * @param customerID The integer value of the customer's id.
     * @return A customer object of the matching customer record.
     */
    public static Customer getCustomer(int customerID) {
        for (Customer c : getCustomers()) {
            if (c.ID == customerID) {
                return c;
            }
        }
        return null;
    }

    /**
     * Get the list of customers.
     * @return An FXCollections ObservableList of customer objects.
     */
    public static ObservableList<Customer> getCustomers() {
        return DBCustomer.getCustomers();
    }

    /**
     * Get the next available unique customer id from the database.
     * @return String value of the next available customer id.
     */
    public static String getNextAutoID() {
        return DBCustomer.getNextAutoID();
    }

    /**
     * Get this customer id.
     * @return Int value of this customer id.
     */
    public int getID() {
        return ID;
    }

    /**
     * Get this customer name.
     * @return String value of this customer name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set this customer name.
     * @param name String value to set this customer name to.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get this customer address.
     * @return String value to set this address to.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set this customer address.
     * @param address String value to set this customer address to.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Get this customer postal address code.
     * @return String value of this customer postal code.
     */
    public String getPostal() {
        return postal;
    }

    /**
     * Set this customer postal address code.
     * @param postal String value to set this customer postal code to.
     */
    public void setPostal(String postal) {
        this.postal = postal;
    }

    /**
     * Get this customer phone number.
     * @return String value of this customer phone number.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Set this customer phone number.
     * @param phone String value to set this customer phone number to.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Get this customer create date and time.
     * @return LocalDateTime value of this customer create date and time.
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Get the user who created this customer.
     * @return String value of user who created this customer.
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * Get this customer last update date and time.
     * @return LocalDateTime value of date and time.
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Set this customer last update date and time.
     * @param lastUpdate LocalDateTime value of date and time.
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Get user that last updated this customer.
     * @return String value of user.
     */
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    /**
     * Set user that last updated this customer.
     * @param lastUpdateBy String value of user.
     */
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     * Get division id this customer is associated with.
     * @return Integer value of the division.
     */
    public int getDivisionID() {
        return division.getDivisionID();
    }

    /**
     * Get division this customer is associated with.
     * @return The division object this customer is associated with.
     */
    public Division getDivision() {
        return this.division;
    }

    /**
     * Set division this customer is associated with.
     * @param d The division object to associate with this customer.
     */
    public void setDivision(Division d) {
        this.division = d;
    }

    /**
     * Get country name this customer is associated with.
     * @return String value of country name.
     */
    public String getCountryName() {
        return division.getAssociatedCountry().getCountryName();
    }

    /**
     * Delete this customer record from the database.
     */
    public void delete() {
        DBCustomer.deleteCustomer(this);
    }

    /**
     * Get name of division this customer is associated with.
     * @return String value of division name.
     */
    public String getDivisionName() {
        return division.getDivisionName();
    }

    /**
     * Get country id this customer is associated with.
     * @return Integer value of country id.
     */
    public int getCountryID() {
        return division.getCountryID();
    }

    /**
     * Get a more readable version of customer created date and time.
     * @return String value of customer created date and time.
     */
    public String getFormatCreate() {
        return this.getCreateDate().format(I18N.formatter);
    }

    /**
     * Get a more readable version of customer last updated date and time.
     * @return String value of customer last updated date and time.
     */
    public String getFormatUpdate() {
        return this.getLastUpdate().format(I18N.formatter);
    }

    /**
     * Get the appointments associated with this customer.
     * @return An FXCollections ObservableList of appointment objects.
     */
    public ObservableList<Appointment> getAppointments() {
        return Appointment.getAppointments().filtered(a -> a.getCustomerID() == ID);
    }


}
