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

public class UpdateWarehouseController {

    private WarehouseDAO warehouseDAO = new WarehouseDAO();

    @FXML
    private Label label_UpdateRecord;

    @FXML
    private Label warehouse_id_LBL;

    @FXML
    private Label warehouse_name_LBL;

    @FXML
    private Label warehouse_location_LBL;

    @FXML
    private Label warehouse_capacity_LBL;

    @FXML
    private TextField updatewarehouse_id_txt;

    @FXML
    private TextField updatewarehouse_name_txt;

    @FXML
    private TextField updatewarehouse_location_txt;

    @FXML
    private TextField updatewarehouse_capacity_txt;

    @FXML
    private Button UpdateRecord_Btn;

    private WarehouseManagementController warehouseManagementController;
    private Warehouse warehouseToEdit;

    // Method to set the controller of WarehouseManagementController
    public void setWMController(WarehouseManagementController warehouseManagementController) {
        this.warehouseManagementController = warehouseManagementController;
    }

    // Method to set the warehouse to be edited
    public void setWarehouseForEdit(Warehouse warehouse) {
        this.warehouseToEdit = warehouse;

        // Populate fields with the selected warehouse's data
        updatewarehouse_id_txt.setDisable(true);
        updatewarehouse_id_txt.setText(String.valueOf(warehouse.getWarehouse_id()));
        updatewarehouse_name_txt.setText(warehouse.getWarehouse_name());
        updatewarehouse_location_txt.setText(warehouse.getLocation());
        updatewarehouse_capacity_txt.setText(String.valueOf(warehouse.getCapacity()));
    }

    // Method to handle the update action
    @FXML
    private void updateWMRecord(ActionEvent event) {
        try {
            String warehouseName = updatewarehouse_name_txt.getText();
            String location = updatewarehouse_location_txt.getText();
            int capacity = Integer.parseInt(updatewarehouse_capacity_txt.getText());

            // Update the warehouse object
            warehouseToEdit.setWarehouse_name(warehouseName);
            warehouseToEdit.setLocation(location);
            warehouseToEdit.setCapacity(capacity);

            // Update the warehouse in the database
            if (warehouseDAO.updateWarehouse(warehouseToEdit)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Warehouse updated successfully.");
                warehouseManagementController.loadWarehouseData();
                closeWindow();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update warehouse.");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a valid capacity.");
        }
    }

    // Method to handle the reset action
    @FXML
    private void resetWMRecord(ActionEvent event) {
        // Reset fields to the warehouse data
        if (warehouseToEdit != null) {
            updatewarehouse_name_txt.setText(warehouseToEdit.getWarehouse_name());
            updatewarehouse_location_txt.setText(warehouseToEdit.getLocation());
            updatewarehouse_capacity_txt.setText(String.valueOf(warehouseToEdit.getCapacity()));
        }
    }

    // Closes the current window after updating
    private void closeWindow() {
        Stage stage = (Stage) UpdateRecord_Btn.getScene().getWindow();
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
