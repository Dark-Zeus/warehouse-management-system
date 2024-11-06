package com.warehouse.controller;

import com.warehouse.dao.WarehouseDAO;
import com.warehouse.model.Warehouse;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddWarehouseController {

    private WarehouseDAO warehouseDAO = new WarehouseDAO();

    @FXML
    private Label label_InsertNewRecord;

    @FXML
    private Label warehouse_id_LBL;

    @FXML
    private Label warehouse_name_LBL;

    @FXML
    private Label warehouse_location_LBL;

    @FXML
    private Label warehouse_capacity_LBL;

    @FXML
    private TextField warehouse_id_txt;

    @FXML
    private TextField warehouse_name_txt;

    @FXML
    private TextField warehouse_location_txt;

    @FXML
    private TextField warehouse_capacity_txt;

    @FXML
    private Button insertRecord_Btn;

    @FXML
    private Button resetRecord_Btn;

    WarehouseManagementController warehouseManagementController;

    public void setWMController(WarehouseManagementController warehouseManagementController) {
        this.warehouseManagementController = warehouseManagementController;
    }

    // Method to handle the insert action
    @FXML
    private void insertWMRecord(ActionEvent event) {
        try {
            String warehouseName = warehouse_name_txt.getText();
            String location = warehouse_location_txt.getText();
            int capacity = Integer.parseInt(warehouse_capacity_txt.getText());

            // Assuming warehouse_id is generated automatically or managed in the DAO
            Warehouse warehouse = new Warehouse(0, warehouseName, location, capacity);

            if (warehouseDAO.addWarehouse(warehouse)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Warehouse added successfully.");
                warehouseManagementController.loadWarehouseData();
                closeWindow();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add warehouse.");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a valid capacity.");
        }
    }

    // Method to handle the reset action
    @FXML
    private void resetWMRecord(ActionEvent event) {
        warehouse_id_txt.clear();
        warehouse_name_txt.clear();
        warehouse_location_txt.clear();
        warehouse_capacity_txt.clear();
    }

    // Closes the current window after saving
    private void closeWindow() {
        Stage stage = (Stage) insertRecord_Btn.getScene().getWindow();
        stage.close();
    }

    // Utility method to show alert messages with customizable alert types
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
