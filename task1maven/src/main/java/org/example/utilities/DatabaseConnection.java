package org.example.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Static variables for connection properties
    private static final String URL = "jdbc:mysql://localhost:3306/task1";
    private static final String USER = "kafafy";
    private static final String PASSWORD = "123!";

    // Private constructor to prevent instantiation
    private DatabaseConnection() {}

    // Method to get a connection to the database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
