package com.warehouse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.warehouse.util.CFXMLLoader;
import com.warehouse.model.Inventory;
import com.warehouse.model.Invoice;
import com.warehouse.model.Transport;
import com.warehouse.model.User;
import com.warehouse.model.Warehouse;
import com.warehouse.util.autosql.AutoSQL;
import com.warehouse.util.SQLConnector;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(CFXMLLoader.loadFXML("login"));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Warehouse Management System");
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(CFXMLLoader.loadFXML(fxml));
    }

    public static void main(String[] args) {

        // Create tables
        String tables = AutoSQL.generateTables("warehouse",
                User.class,
                Warehouse.class,
                Inventory.class,
                Transport.class,
                Invoice.class
        );

        // Aquire connection
        Connection primaryConn = SQLConnector.getConnection();

        // Execute tables
        AutoSQL.executeInSequence(primaryConn, tables);

        // Close connection
        SQLConnector.closeSQLConnection();

        // Create new connection to the created database
        Connection con = SQLConnector.getConnection("warehouse");

        // Create admin user
        User user = new User();
        user.setUser_id(0);
        user.setUsername("admin");
        user.setPassword("admin");
        user.setRole("admin");

        /* Add the admin record to the database */
        String record = AutoSQL.generateRecord(user, true);

        try {
            con.createStatement().execute(record);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        SQLConnector.closeDBConnection();

        // Launch the application
        launch();
    }

}