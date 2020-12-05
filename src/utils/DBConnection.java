package utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    //JDBC URL
    private static final String jdbcURL = "jdbc:mysql://wgudb.ucertify.com/WJ05qGs";
    private static final String mysqlJDBC = "com.mysql.cj.jdbc.Driver";

    // JDBC Login Credentials
    private static final String username = "U05qGs";
    private static final String password = "53688579503";

    // JDBC Connection Pointer
    private static Connection connection;

    // Open Connection
    public static void openConnection() {
        try { Class.forName(mysqlJDBC);
            connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection successful.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    // End Connection
    public static void closeConnection() {
        try { connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Connection closed.");
    }
}
