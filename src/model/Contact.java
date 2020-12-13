package model;

import dao.DBContact;
import javafx.collections.ObservableList;

/**
 * The class for storing contact records.
 * Author: Mario Silvestri III
 */
public class Contact {
    private final int ID;
    private final String Name;

    public Contact(int ID, String Name) {
        this.ID = ID;
        this.Name = Name;
    }

    public static ObservableList<Contact> getContacts() {
        return DBContact.getContacts();
    }

    public static Contact getContact(int contactID) {
        for (Contact contact : getContacts()) {
            if (contact.ID == contactID) {
                return contact;
            }
        }
        return null;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    @Override
    public String toString() {
        return Name;
    }
}
