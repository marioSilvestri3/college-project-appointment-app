package model;

import dao.DBContact;
import javafx.collections.ObservableList;

public class Contact {
    private final int contactID;
    private final String contactName;

    public Contact(int contactID, String contactName) {
        this.contactID = contactID;
        this.contactName = contactName;
    }

    public static ObservableList<Contact> getContacts() {
        return DBContact.getContacts();
    }

    public int getContactID() {
        return contactID;
    }

    public String getContactName() {
        return contactName;
    }

    public static Contact getContact(int contactID) {
        for (Contact contact : getContacts()) {
            if (contact.contactID == contactID) {
                return contact;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return contactName;
    }
}
