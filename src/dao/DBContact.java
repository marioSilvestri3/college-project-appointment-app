package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import utils.DBQuery;

import java.sql.ResultSet;

/**
 * The DAO object for interacting with Contact data in the MySQL database.
 * Author: Mario Silvestri III
 */
public class DBContact {

    /**
     * Get all contact data from the MySQL database.
     * @return an FXCollections ObservableList of Contact objects.
     */
    public static ObservableList<Contact> getContacts() {
        ObservableList<Contact> contacts = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Contact_ID, Contact_Name FROM contacts";
            DBQuery.setPreparedStatement(sql);
            ResultSet rs = DBQuery.getPreparedStatement().executeQuery(sql);
            while (rs.next()) {
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                contacts.add(new Contact(contactID, contactName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } return contacts;
    }

}
