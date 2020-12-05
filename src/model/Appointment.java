package model;

import dao.DBAppointment;
import javafx.collections.ObservableList;
import utils.I18N;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Appointment {
    private final int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private final LocalDateTime createDate;
    private final String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdateBy;
    private final int customerID;
    private final int userID;
    private final int contactID;

    public Appointment(int appointmentID, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdateBy, int customerID, int userID, int contactID) {
        this.appointmentID = appointmentID;
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

    public static ObservableList<Appointment> getAppointments() {
        return DBAppointment.getAppointments();
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public int getCustomerID() {
        return customerID;
    }

    public int getUserID() {
        return userID;
    }

    public int getContactID() {
        return contactID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public String getFormatStart() {
        return this.getStart().format(I18N.formatter);
    }

    public String getFormatEnd() {
        return this.getEnd().format(I18N.formatter);
    }

    /**
     * Returns the appointment associated with the passed in unique appointment ID.
     * @param appointmentID the appointment ID, an int.
     * @return the appointment associated with the appointment ID.
     */
    public Appointment getAppointment(int appointmentID) {
        for (Appointment appointment : DBAppointment.getAppointments()) {
            if (appointment.getAppointmentID() == appointmentID) {
                return appointment;
            }
        } return null;
    }

    /**
     * Prints appointment details for debugging purposes.
     */
    void printAppointment() {
        System.out.println(this.getAppointmentID());
        System.out.println(this.getTitle());
        System.out.println(this.getDescription());
        System.out.println(this.getLocation());
        System.out.println(this.getType());
        System.out.println(Timestamp.valueOf(this.getStart()).toString());
        System.out.println(Timestamp.valueOf(this.getEnd()).toString());
        System.out.println(Timestamp.valueOf(this.getCreateDate()).toString());
        System.out.println(this.getCreatedBy());
        System.out.println(Timestamp.valueOf(this.getLastUpdate()).toString());
        System.out.println(this.getLastUpdateBy());
        System.out.println(this.getCustomerID());
        System.out.println(this.getUserID());
        System.out.println(this.getContactID());
    }

    public static String getNextAutoID() {
        return DBAppointment.getNextAutoID();
    }

    public static void updateAppointment(Appointment appointment) {
        DBAppointment.updateAppointment(appointment);
    }

    public static void addAppointment(Appointment appointment) {
        DBAppointment.addAppointment(appointment);
    }
}
