package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import utils.DBQuery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;


public class DBCustomer {

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
                String createBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdateBy = rs.getString("Last_Updated_By");
                int divisionID = rs.getInt("Division_ID");

                customers.add(new Customer(customerID, customerName, address, postalCode, phone, createDate, createBy, lastUpdate, lastUpdateBy, divisionID));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customers;
    }

    public static void addCustomer(Customer customer) {
        try {
            String sql = "INSERT INTO customers(" +
                    "Customer_Name, Address, Postal_Code, Phone, Create_Date," +
                    " Created_By, Last_Update, Last_Updated_By, Division_ID) values( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            DBQuery.setPreparedStatement(sql);
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();
            preparedStatement.setString(1, customer.getCountryName());
            preparedStatement.setString(2, customer.getAddress());
            preparedStatement.setString(3, customer.getPostalCode());
            preparedStatement.setString(4, customer.getPhone());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(customer.getCreateDate()));
            preparedStatement.setString(6, customer.getCreateBy());
            preparedStatement.setTimestamp(7, Timestamp.valueOf(customer.getLastUpdate()));
            preparedStatement.setString(8, customer.getLastUpdateBy());
            preparedStatement.setInt(9, customer.getDivisionID());
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateCustomer(Customer customer) {
        try {
            String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
            DBQuery.setPreparedStatement(sql);
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getAddress());
            preparedStatement.setString(3, customer.getPostalCode());
            preparedStatement.setString(4, customer.getPhone());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(customer.getLastUpdate()));
            preparedStatement.setString(6, customer.getLastUpdateBy());
            preparedStatement.setInt(7, customer.getDivisionID());
            preparedStatement.setInt(8, customer.getCustomerID());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteCustomer(Customer customer) {}

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
