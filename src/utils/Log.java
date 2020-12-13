package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Utility class for logging events.
 * Author: Mario Silvestri III
 */
public class Log {
    /**
     * Holds in memory the session activity log to be displayed on the reports table.
     */
    private static String sessionActivity = "";

    /**
     * Logs successful / unsuccessful login attempts to login_activity.txt
     * @param string The string value to log
     * @throws IOException In case of errors creating or writing to text file
     */
    public static void printLogin(String string) throws IOException {
        FileWriter login = new FileWriter("login_activity", true);
        PrintWriter loginPrint = new PrintWriter(login);
        loginPrint.println(string);
        loginPrint.close();
    }

    /**
     * Logs an activity to the session log.
     * @param string The string value to log
     */
    public static void printSessionLn(String string) {
        sessionActivity = sessionActivity.concat(string + "\n");
    }

    /**
     * Gets the current session activity log.
     * @return String value of session activity log
     */
    public static String getSessionLog() {
        return sessionActivity;
    }

}
