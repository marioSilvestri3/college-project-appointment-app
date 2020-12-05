package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import utils.DBQuery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;


public class DBAppointment {

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
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
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

    public static void addAppointment(Appointment appointment) {
        try {
            String sql = "INSERT INTO appointments(" +
                    "Title, Description, Location, Type, Start, End, Create_Date," +
                    " Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) values( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            DBQuery.setPreparedStatement(sql);
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();
            preparedStatement.setString(1, appointment.getTitle());
            preparedStatement.setString(2, appointment.getDescription());
            preparedStatement.setString(3, appointment.getLocation());
            preparedStatement.setString(4, appointment.getType());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(appointment.getStart()));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(appointment.getEnd()));
            preparedStatement.setTimestamp(7, Timestamp.valueOf(appointment.getCreateDate()));
            preparedStatement.setString(8, appointment.getCreatedBy());
            preparedStatement.setTimestamp(9, Timestamp.valueOf(appointment.getLastUpdate()));
            preparedStatement.setString(10, appointment.getLastUpdateBy());
            preparedStatement.setInt(11, appointment.getCustomerID());
            preparedStatement.setInt(12, appointment.getUserID());
            preparedStatement.setInt(13, appointment.getContactID());
            preparedStatement.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateAppointment(Appointment appointment) {
        try {
            String sql = "UPDATE appointments SET Title = ?, `Description` = ?, Location = ?, `Type` = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
            DBQuery.setPreparedStatement(sql);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setString(1, appointment.getTitle());
            ps.setString(2, appointment.getDescription());
            ps.setString(3, appointment.getLocation());
            ps.setString(4, appointment.getType());
            ps.setTimestamp(5, Timestamp.valueOf(appointment.getStart()));
            ps.setTimestamp(6, Timestamp.valueOf(appointment.getEnd()));
            ps.setTimestamp(7, Timestamp.valueOf(appointment.getLastUpdate()));
            ps.setString(8, appointment.getLastUpdateBy());
            ps.setInt(9, appointment.getCustomerID());
            ps.setInt(10, appointment.getUserID());
            ps.setInt(11, appointment.getContactID());
            ps.setInt(12, appointment.getAppointmentID());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteAppointment(Appointment appointment) {}

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
}
