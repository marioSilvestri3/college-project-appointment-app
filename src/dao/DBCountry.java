package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import utils.DBQuery;

import java.sql.ResultSet;

/**
 * The DAO object for interacting with Country data in the MySQL database.
 * Author: Mario Silvestri III
 */
public class DBCountry {

    /**
     * Get all country data from the MySQL database.
     * @return an FXCollections ObservableList of Country objects.
     */
    public static ObservableList<Country> getCountries() {
        ObservableList<Country> countries = FXCollections.observableArrayList();
        try {
            String sql = "Select * FROM countries";
            DBQuery.setPreparedStatement(sql);
            ResultSet rs = DBQuery.getPreparedStatement().executeQuery();
            while (rs.next()) {
                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                countries.add(new Country(countryID, countryName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } return countries;
    }
}
