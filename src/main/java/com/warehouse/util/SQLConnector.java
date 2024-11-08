package com.warehouse.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Map;

public class SQLConnector {
    private static final String HOST = "localhost";
    private static final String PORT = "3307";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private static Connection sqlConnection = null;
    private static Connection dbConnection = null;

    private SQLConnector() {
    }

    public static Connection getConnection() {
        if (sqlConnection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                sqlConnection = DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT, USERNAME, PASSWORD);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return sqlConnection;
    }

    public static Connection getConnection(String database) {
        if (dbConnection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                dbConnection = DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/" + database, USERNAME,
                        PASSWORD);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dbConnection;
    }

    public static Connection closeDBConnection() {
        if (dbConnection != null) {
            try {
                dbConnection.close();
                dbConnection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dbConnection;
    }

    public static Connection closeSQLConnection() {
        if (sqlConnection != null) {
            try {
                sqlConnection.close();
                sqlConnection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return sqlConnection;
    }

}
