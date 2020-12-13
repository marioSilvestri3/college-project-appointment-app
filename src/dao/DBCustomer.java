package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import utils.DBQuery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * The DAO object for interacting with Customer data in the MySQL database.
 * Author: Mario Silvestri III
 */
public class DBCustomer {

    /**
     * Get all customer data from the MySQL database.
     * @return an FXCollections ObservableList of Customer objects.
     */
    public static ObservableList<Customer> getCustomers() {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM customers";
            DBQuery.setPreparedStatement(sql);
            ResultSet rs = DBQuery.getPreparedStatement().executeQuery();
            while(rs.next()) {
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createBy = rs.getString("Created_by");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_update").toLocalDateTime();
                String lastUpdateBy = rs.getString("Last_Updated_By");
                int divisionID = rs.getInt("Division_ID");

                customers.add(new Customer(customerID, customerName, address, postalCode, phone, createDate, createBy, lastUpdate, lastUpdateBy, divisionID));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customers;
    }

    /**
     * Adds a customer into the MySQL customer table.
     * @param c The customer to add.
     */
    public static void addCustomer(Customer c) {
        try {
            String sql = "INSERT INTO customers(" +
                    "Customer_Name, Address, Postal_Code, Phone, Create_Date," +
                    " Created_By, Last_Update, Last_Updated_By, Division_ID) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            DBQuery.setPreparedStatement(sql);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setString(1, c.getName());
            ps.setString(2, c.getAddress());
            ps.setString(3, c.getPostal());
            ps.setString(4, c.getPhone());
            ps.setTimestamp(5, Timestamp.valueOf(c.getCreateDate()));
            ps.setString(6, c.getCreateBy());
            ps.setTimestamp(7, Timestamp.valueOf(c.getLastUpdate()));
            ps.setString(8, c.getLastUpdateBy());
            ps.setInt(9, c.getDivisionID());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates a customer in the MySQL customer table.
     * @param c The customer to update.
     */
    public static void updateCustomer(Customer c) {
        try {
            String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
            DBQuery.setPreparedStatement(sql);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setString(1, c.getName());
            ps.setString(2, c.getAddress());
            ps.setString(3, c.getPostal());
            ps.setString(4, c.getPhone());
            ps.setTimestamp(5, Timestamp.valueOf(c.getLastUpdate()));
            ps.setString(6, c.getLastUpdateBy());
            ps.setInt(7, c.getDivisionID());
            ps.setInt(8, c.getID());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a customer from the MySQL database.
     * @param c The customer to delete.
     */
    public static void deleteCustomer(Customer c) {
        try {
            String sql = "DELETE FROM customers WHERE Customer_ID = ?";
            DBQuery.setPreparedStatement(sql);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setInt(1, c.getID());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the next available unique customer ID from the MySQL database.
     * @return String value of the next customer ID.
     */
    public static String getNextAutoID() {
        try {
            String sql = "SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES " +
                    "WHERE TABLE_SCHEMA = 'WJ05qGs' AND TABLE_NAME = 'customers'";
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();
            ResultSet resultSet = preparedStatement.executeQuery(sql);
            resultSet.next();
            return resultSet.getString("AUTO_INCREMENT");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "-1";
    }
}
