package utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBQuery {

    // Statement reference.
    private static PreparedStatement preparedStatement;

    // Create Statement object.
    public static void setPreparedStatement(String SQLStatement) throws SQLException {
        preparedStatement = DBConnection.getConnection().prepareStatement(SQLStatement);
    }

    // Return Statement object.
    public static PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }
}
