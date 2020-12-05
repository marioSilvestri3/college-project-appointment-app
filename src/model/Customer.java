package model;

import dao.DBCustomer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

public class Customer {

    private final int customerID;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private final LocalDateTime createDate;
    private final String createBy;
    private LocalDateTime lastUpdate;
    private String lastUpdateBy;
    private int divisionID;


    public Customer(int customerID, String name, String address, String postalCode, String phone, LocalDateTime createDate, String createBy, LocalDateTime lastUpdate, String lastUpdateBy, int divisionID) {
        this.customerID = customerID;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = createDate;
        this.createBy = createBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
        this.divisionID = divisionID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public int getDivisionID() {
        return this.divisionID;
    }

    public Division getAssociatedDivision() {
        return Division.getDivision(divisionID);
    }

    public void setDivision(Division division) {
        for (Division innerDivision : Division.getDivisions()) {
            if (division == innerDivision) {
                this.divisionID = innerDivision.getDivisionID();
            }
        }
    }

    public ObservableList<Customer> getCustomers() {
        return DBCustomer.getCustomers();
    }

    public static String getNextAutoID() {
        return DBCustomer.getNextAutoID();
    }
    public String getCountryName() {
        return getAssociatedDivision().getAssociatedCountry().getCountryName();
    }

}
