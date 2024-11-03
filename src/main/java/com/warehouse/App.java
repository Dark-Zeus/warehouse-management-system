package com.warehouse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.warehouse.model.User;
import com.warehouse.util.autosql.SQLGen;
import com.warehouse.util.SQLConnector;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        String tables = SQLGen.generateTables("warehouse", User.class);

        Connection primaryConn = SQLConnector.getConnection();

        SQLGen.executeInSequence(primaryConn, tables);

        SQLConnector.closeSQLConnection();

        Connection con = SQLConnector.getConnection("warehouse");

        User user = new User();
        user.setId(1);
        user.setUsername("admin");
        user.setPassword("admin");

        String record = SQLGen.generateRecord(user, true);

        try{
            con.createStatement().execute(record);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        SQLConnector.closeDBConnection();

        launch();
    }

}