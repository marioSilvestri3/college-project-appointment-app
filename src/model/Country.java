package model;

import dao.DBCountry;
import dao.DBDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The class for storing country records.
 * Author: Mario Silvestri III
 */
public class Country {
    private final int countryID;
    private final String countryName;

    public Country(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }

    public static ObservableList<Country> getCountries() {
        return DBCountry.getCountries();
    }

    public static Country getCountry(int countryID) {
        for (Country country : Country.getCountries()) {
            if (country.getCountryID() == countryID) {
                return country;
            }
        }
        return null;
    }

    public String getCountryName() {
        return countryName;
    }

    public int getCountryID() {
        return countryID;
    }

    public ObservableList<Division> getAssociatedDivisions() {
        ObservableList<Division> associatedDivisions = FXCollections.observableArrayList();
        for (Division division : Division.getDivisions()) {
            if (countryID == division.getCountryID()) {
                associatedDivisions.add(division);
            }
        } return associatedDivisions;
    }

    @Override
    public String toString() {
        return countryName;
    }

}
