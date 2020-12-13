package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Division;
import utils.DBQuery;

import java.sql.ResultSet;

/**
 * The DAO object for interacting with Division data in the MySQL database.
 * Author: Mario Silvestri III
 */
public class DBDivision {
    /**
     * Get all division data from the MySQL database.
     * @return an FXCollections ObservableList of Division objects.
     */
    public static ObservableList<Division> getDivisions() {
        ObservableList<Division> divisions = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM first_level_divisions";
            DBQuery.setPreparedStatement(sql);
            ResultSet rs = DBQuery.getPreparedStatement().executeQuery();
            while (rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryID = rs.getInt("Country_ID");
                divisions.add(new Division(divisionID, divisionName, countryID));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return divisions;
    }

}
