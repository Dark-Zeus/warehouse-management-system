package com.warehouse.controller;

import java.io.IOException;

import com.warehouse.util.CFXMLLoader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DashboardController {

    @FXML
    private Button warehouseBtn;

    @FXML
    void openWarehouseUI(ActionEvent event) {
        try {
            Scene dashboad = new Scene(CFXMLLoader.loadFXML("warehousemanagement_table"));
            Stage stage = (Stage) warehouseBtn.getScene().getWindow();
            stage.setScene(dashboad);
        } catch (IOException e) {
            e.printStackTrace();
            // Optionally, you can show an alert to the user or handle the error appropriately
        }
    }

}
