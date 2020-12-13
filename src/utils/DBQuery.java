package utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Sets and gets prepared statements to use with a MySQL JDBC connection.
 * Author: Mario Silvestri III
 */
public class DBQuery {

    // Statement reference.
    private static PreparedStatement preparedStatement;

    // Return Statement object.
    public static PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    // Create Statement object.
    public static void setPreparedStatement(String SQLStatement) throws SQLException {
        preparedStatement = DBConnection.getConnection().prepareStatement(SQLStatement);
    }
}
