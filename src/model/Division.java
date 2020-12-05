package model;

import dao.DBDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Division {

    private final int divisionID;
    private final String divisionName;
    private final int countryID;
    private static final ObservableList<Division> divisions = DBDivision.getDivisions();

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

    public int getDivisionID() {
        return divisionID;
    }

    public static int getDivisionID(String divisionName) {
        for (Division division : DBDivision.getDivisions()) {
            if (division.getDivisionName().equals(divisionName)) {
                return division.getDivisionID();
            }
        } return 0;
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

