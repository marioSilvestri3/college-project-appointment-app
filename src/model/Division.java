package model;

import dao.DBDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The class for storing division data (database records).
 * Author: Mario Silvestri III
 */
public class Division {

    private static final ObservableList<Division> divisions = DBDivision.getDivisions();
    private final int divisionID;
    private final String divisionName;
    private final int countryID;

    public Division(int divisionID, String divisionName, int countryID) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryID = countryID;
    }

    public static ObservableList<Division> getDivisions() {
        return divisions;
    }

    public static Division getDivision(int divisionID) {
        for (Division division : divisions) {
            if (division.divisionID == divisionID) {
                return division;
            }
        }
        return null;
    }

    public static int getDivisionID(String divisionName) {
        for (Division division : DBDivision.getDivisions()) {
            if (division.getDivisionName().equals(divisionName)) {
                return division.getDivisionID();
            }
        } return 0;
    }

    public int getDivisionID() {
        return divisionID;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public int getCountryID() {
        return countryID;
    }

    public Country getAssociatedCountry() {
        return Country.getCountry(countryID);
    }

    @Override
    public String toString() {
        return divisionName;
    }
}

