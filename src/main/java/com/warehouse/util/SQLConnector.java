package com.warehouse.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Map;

public class SQLConnector {
    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private static Connection sqlConnection = null;
    private static Connection dbConnection = null;

    private SQLConnector() {
    }

    /**
     * Get a connection to the SQL server
     * @return Connection - {@code Connection} {@link Connection}
     */
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

    /**
     * Get a connection to the database
     * @param database - {@code String}
     * @return Connection - {@code Connection} {@link Connection}
     */
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

    /**
     * Close the database connection
     * @return Connection - {@code Connection} {@link Connection}
     */
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

    /**
     * Close the SQL connection
     * @return Connection - {@code Connection} {@link Connection}
     */
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
