package model;

import dao.DBAppointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.I18N;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;

/**
 * The class for storing appointment data (records).
 * Author: Mario Silvestri III
 */
public class Appointment {
    /**
     * The unique appointment ID.
     */
    private final int ID;
    /**
     * The date and time the appointment was created.
     */
    private final LocalDateTime createDate;
    /**
     * The user who created the appointment.
     */
    private final String createdBy;
    /**
     * The ID of the customer associated with this appointment.
     */
    private final int customerID;
    /**
     * The ID of the user associated with this appointment.
     */
    private final int userID;
    /**
     * The contact ID of the contact (employee) who the appointment is associated with. A contact can not have overlapping appointment times.
     */
    private final int contactID;
    /**
     * The appointment title.
     */
    private String title;
    /**
     * The appointment description.
     */
    private String description;
    /**
     * The appointment location.
     */
    private String location;
    /**
     * The appointment type.
     */
    private String type;
    /**
     * The start date and time of the appointment.
     */
    private LocalDateTime start;
    /**
     * The end date and time of the appointment.
     */
    private LocalDateTime end;
    /**
     * The last date and time the appointment was updated.
     */
    private LocalDateTime lastUpdate;
    /**
     * The user who last updated the appointment.
     */
    private String lastUpdateBy;

    /**
     * Appointment constructor.
     * @param ID Appointment ID (Unique)
     * @param title  Title
     * @param description Description
     * @param location Location
     * @param type Type
     * @param start Start Date and Time
     * @param end End Date and Time
     * @param createDate Date and Time Created
     * @param createdBy Username of creator
     * @param lastUpdate Date and Time Last Updated
     * @param lastUpdateBy Username whom last updated this appointment
     * @param customerID ID of the customer associated with this appointment
     * @param userID ID of the user associated with this appointment
     * @param contactID ID of the contact (employee) associated with this appointment
     */
    public Appointment(int ID, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdateBy, int customerID, int userID, int contactID) {
        this.ID = ID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**
     * Get the list of appointments.
     * @return An FXCollections ObservableList of Appointment objects.
     */
    public static ObservableList<Appointment> getAppointments() {
        return DBAppointment.getAppointments();
    }

    /**
     * Get the list of appointments associated with the provided customer ID.
     * @param customerID The integer value of the customer's ID.
     * @return An FXCollections ObservableList of Appointment objects that are associated with the customer ID.
     */
    public static ObservableList<Appointment> getAppointments(int customerID) {
        ObservableList<Appointment> as = FXCollections.observableArrayList();
        for (Appointment a : getAppointments()) {
            if (a.getCustomerID() == customerID) {
                as.add(a);
            }
        }
        return as;
    }

    /**
     * Get total number of appointments in each month.
     * @return A HashMap of 'Month' string keys and 'Sum' integer values.
     */
    public static HashMap<String, Integer> getAppointmentsByMonth() {
        return DBAppointment.getAppointmentsByMonth();
    }

    /**
     * Get the next available unique Appointment ID from the database.
     * @return The next Appointment ID as a String.
     */
    public static String getNextAutoID() {
        return DBAppointment.getNextAutoID();
    }

    /**
     * Get the number of appointments of each type.
     * @return A HashMap of 'Appointment Type' string keys and 'Sum' integer values.
     */
    public static HashMap<String, Integer> getAppointmentsByType() {
        return DBAppointment.getAppointmentsByType();
    }

    /**
     * Get this appointment ID.
     * @return Int value of this appointment ID.
     */
    public int getID() {
        return ID;
    }

    /**
     * Get this appointment title.
     * @return String value of title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set this appointment title.
     * @param title String value to set title to.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get this appointment description.
     * @return String value of description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set this appointment description.
     * @param description String value to set description to.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get this appointment location.
     * @return String value of location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Set this appointment location.
     * @param location String value to set location to.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Get this appointment type.
     * @return String value of type.
     */
    public String getType() {
        return type;
    }

    /**
     * Set this appointment type.
     * @param type String value to set type to.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get this appointment start date and time.
     * @return LocalDateTime value of start date and time.
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Set this appointment start date and time.
     * @param start LocalDateTime value to set appointment start date and time to.
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * Get this appointment end date and time.
     * @return LocalDateTime value of this end date and time.
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Set this appointment end date and time.
     * @param end LocalDateTime value to set this end date and time to.
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     * Get this appointment created date and time.
     * @return LocalDateTime value of this created date and time.
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Get username that created this appointment.
     * @return String value of username that created.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Get this appointment last update date and time.
     * @return LocalDateTime value of last update date and time.
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Set this appointment last update date and time.
     * @param lastUpdate LocalDateTime value to set last update date and time to.
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Get username that last updated this appointment.
     * @return String value of username that last updated.
     */
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    /**
     * Set username that last updated this appointment.
     * @param lastUpdateBy String value to set this appointment last updated by to.
     */
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     * Get this appointment customer ID.
     * @return String value of customer ID.
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Get this appointment contact (employee) name.
     * @return String value of contact name.
     */
    public String getContactName() {
        return Objects.requireNonNull(Contact.getContact(contactID)).getName();
    }

    /**
     * Get this appointment customer name.
     * @return String value of customer name.
     */
    public String getCustomerName() {
        return Objects.requireNonNull(Customer.getCustomer(customerID)).getName();
    }

    /**
     * Get this appointment associated user ID.
     * @return Int value of user ID.
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Get this appointment contact (employee) ID.
     * @return Int value of contact ID.
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Get formatted start date and time for readability.
     * @return String value of formatted start date and time.
     */
    public String getFormatStart() {
        return this.getStart().format(I18N.formatter);
    }

    /**
     * Get formatted end date and time for readability.
     * @return String value of formatted end date and time.
     */
    public String getFormatEnd() {
        return this.getEnd().format(I18N.formatter);
    }

    /**
     * Updates this appointment into the database.
     */
    public void update() {
        DBAppointment.updateAppointment(this);
    }

    /**
     * Adds this appointment into the database.
     */
    public void add() {
        DBAppointment.addAppointment(this);
    }

    /**
     * Deletes this appointment from the database.
     */
    public void delete() {
        DBAppointment.deleteAppointment(this);
    }
}
