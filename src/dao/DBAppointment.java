package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import utils.DBQuery;

import java.lang.reflect.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * The DAO object for interacting with Appointment data in the MySQL database.
 * Author: Mario Silvestri III
 */
public class DBAppointment {

    /**
     * Get all appointment data from the MySQL database.
     * @return an FXCollections ObservableList of Appointment objects.
     */
    public static ObservableList<Appointment> getAppointments() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM appointments";
            DBQuery.setPreparedStatement(sql);
            ResultSet rs = DBQuery.getPreparedStatement().executeQuery(sql);
            while(rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                LocalDateTime created = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_by");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                appointments.add(new Appointment(appointmentID, title, description, location, type, start, end,created, createdBy, lastUpdate, lastUpdatedBy, customerID, userID, contactID));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointments;
    }

    /**
     * Add an appointment to the MySQL database.
     * @param a The appointment object to add.
     */
    public static void addAppointment(Appointment a) {
        try {
            String sql = "INSERT INTO appointments(Title, `Description`, Location, `Type`, Start, End, Create_Date," +
                    " Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            DBQuery.setPreparedStatement(sql);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setString(1, a.getTitle());
            ps.setString(2, a.getDescription());
            ps.setString(3, a.getLocation());
            ps.setString(4, a.getType());
            ps.setTimestamp(5, Timestamp.valueOf(a.getStart()));
            ps.setTimestamp(6, Timestamp.valueOf(a.getEnd()));
            ps.setTimestamp(7, Timestamp.valueOf(a.getCreateDate()));
            ps.setString(8, a.getCreatedBy());
            ps.setTimestamp(9, Timestamp.valueOf(a.getLastUpdate()));
            ps.setString(10, a.getLastUpdateBy());
            ps.setInt(11, a.getCustomerID());
            ps.setInt(12, a.getUserID());
            ps.setInt(13, a.getContactID());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Update an appointment in the MySQL database.
     * @param a The appointment object to update.
     */
    public static void updateAppointment(Appointment a) {
        try {
            String sql = "UPDATE appointments SET Title = ?, `Description` = ?, Location = ?, `Type` = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
            DBQuery.setPreparedStatement(sql);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setString(1, a.getTitle());
            ps.setString(2, a.getDescription());
            ps.setString(3, a.getLocation());
            ps.setString(4, a.getType());
            ps.setTimestamp(5, Timestamp.valueOf(a.getStart()));
            ps.setTimestamp(6, Timestamp.valueOf(a.getEnd()));
            ps.setTimestamp(7, Timestamp.valueOf(a.getLastUpdate()));
            ps.setString(8, a.getLastUpdateBy());
            ps.setInt(9, a.getCustomerID());
            ps.setInt(10, a.getUserID());
            ps.setInt(11, a.getContactID());
            ps.setInt(12, a.getID());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete an appointment from the MySQL database.
     * @param a The appointment object to delete.
     */
    public static void deleteAppointment(Appointment a) {
        try {
            String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
            DBQuery.setPreparedStatement(sql);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setInt(1, a.getID());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the next available unique Appointment ID from the MySQL database.
     * @return The next Appointment ID as a String.
     */
    public static String getNextAutoID() {
        try {
            String sql = "SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES " +
                    "WHERE TABLE_SCHEMA = 'WJ05qGs' AND TABLE_NAME = 'appointments'";
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();
            ResultSet resultSet = preparedStatement.executeQuery(sql);
            resultSet.next();
            return resultSet.getString("AUTO_INCREMENT");
        } catch (Exception e) {
            e.printStackTrace();
        } return "-1";
    }

    /**
     * Get the total number of appointments of each type from the MySQL database..
     * @return A HashMap of Month keys and Sum values.
     */
    public static HashMap<String, Integer> getAppointmentsByType() {
        HashMap<String, Integer> type = new HashMap<>();
        try {
            String sql = "Select Count(Customer_ID), `Type` FROM appointments GROUP BY `Type`";
            DBQuery.setPreparedStatement(sql);
            ResultSet rs = DBQuery.getPreparedStatement().executeQuery(sql);
            while (rs.next()) {
                type.put(rs.getString(2), rs.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return type;
    }

    /**
     * Get the total number of appointments in each month from the MySQL database.
     * @return A HashMap of Month keys and Sum values.
     */
    public static HashMap<String, Integer> getAppointmentsByMonth() {
        HashMap<String, Integer> type = new HashMap<>();
        try {
            String sql = "Select Count(Customer_ID), MONTHNAME(`Start`) FROM appointments GROUP BY MONTHNAME(`Start`)";
            DBQuery.setPreparedStatement(sql);
            ResultSet rs = DBQuery.getPreparedStatement().executeQuery(sql);
            while (rs.next()) {
                type.put(rs.getString(2), rs.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return type;
    }
}
