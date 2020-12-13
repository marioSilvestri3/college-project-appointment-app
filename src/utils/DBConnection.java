package utils;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Opens up a connection to the MySQL database.
 * Author: Mario Silvestri III
 */
public class DBConnection {
    /**
     * URL to MySQL Database
     */
    private static final String jdbcURL = "jdbc:mysql://wgudb.ucertify.com/WJ05qGs";
    /**
     * Name of MySQL JDBC driver
     */
    private static final String mysqlJDBC = "com.mysql.cj.jdbc.Driver";

    /**
     * MySQL database username
     */
    private static final String username = "U05qGs";
    /**
     * MySQL database password
     */
    private static final String password = "53688579503";

    /**
     * MySQL JDBC Connection object
     */
    private static Connection connection;

    /**
     * Opens a new MySQL JDBC connection to the database
     */
    public static void openConnection() {
        try { Class.forName(mysqlJDBC);
            connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection successful.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the open MySQL JDBC database connection
     * @return Connection object of the open MySQL JDBC database connection
     */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * Closes the MySQL JDBC database connection
     */
    public static void closeConnection() {
        try { connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Connection closed.");
    }
}
