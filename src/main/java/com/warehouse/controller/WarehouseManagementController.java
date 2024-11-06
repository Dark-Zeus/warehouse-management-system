package com.warehouse.controller;

import com.warehouse.dao.WarehouseDAO;
import com.warehouse.model.Warehouse;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class WarehouseManagementController {

    private WarehouseDAO warehouseDAO = new WarehouseDAO();
    private ObservableList<Warehouse> warehouseList = FXCollections.observableArrayList();

    @FXML
    private Button WMAddRecordBtn;

    @FXML
    private Button WMdeleterecordBtn;

    @FXML
    private Button WMeditrecordBtn;

    @FXML
    private Label WarehouseManagment_label;

    @FXML
    private TableColumn<Warehouse, Integer> columnwarehouse_id;

    @FXML
    private TableColumn<Warehouse, String> columnwarehouse_location;

    @FXML
    private TableColumn<Warehouse, String> columnwarehouse_name;

    @FXML
    private TableColumn<Warehouse, Integer> columnwarehouse_storage;

    @FXML
    private TableView<Warehouse> warehouseTable;

    // Initializes the controller class and sets up table columns
    @FXML
    private void initialize() {
        // Set up table columns
        columnwarehouse_id.setCellValueFactory(new PropertyValueFactory<>("warehouse_id"));
        columnwarehouse_name.setCellValueFactory(new PropertyValueFactory<>("warehouse_name"));
        columnwarehouse_location.setCellValueFactory(new PropertyValueFactory<>("location"));
        columnwarehouse_storage.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        // Load data from the database initially
        loadWarehouseData();
    }

    // Method to load warehouse data into the table
    private void loadWarehouseData() {
        warehouseList.clear();
        warehouseList.addAll(warehouseDAO.getAllWarehouses());
        warehouseTable.setItems(warehouseList);
    }

    // Call this method whenever the form is opened to ensure data is current
    public void refreshTable() {
        loadWarehouseData();
    }

    // Add new warehouse record
    @FXML
    void openWarehouseForm(ActionEvent event) {
        // Prompt user to enter warehouse details (dummy data for testing)
        Warehouse warehouse = new Warehouse(0, "New Warehouse", "Location XYZ", 1000);

        if (warehouseDAO.addWarehouse(warehouse)) {
            warehouseList.add(warehouse);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Warehouse added successfully.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add warehouse.");
        }
    }

    // Delete selected warehouse record
    @FXML
    void deleteWarehouseRecord(ActionEvent event) {
        Warehouse selectedWarehouse = warehouseTable.getSelectionModel().getSelectedItem();
        if (selectedWarehouse != null) {
            if (warehouseDAO.deleteWarehouse(selectedWarehouse.getWarehouse_id())) {
                warehouseList.remove(selectedWarehouse);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Warehouse deleted successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete warehouse.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select a warehouse to delete.");
        }
    }

    // Edit selected warehouse record
    @FXML
    void openWarehouseEditForm(ActionEvent event) {
        Warehouse selectedWarehouse = warehouseTable.getSelectionModel().getSelectedItem();
        if (selectedWarehouse != null) {
            // Modify the details of the selected warehouse (hardcoded for testing)
            selectedWarehouse.setWarehouse_name("Updated Name");
            selectedWarehouse.setLocation("Updated Location");
            selectedWarehouse.setCapacity(2000);

            if (warehouseDAO.updateWarehouse(selectedWarehouse)) {
                warehouseTable.refresh();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Warehouse updated successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update warehouse.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select a warehouse to edit.");
        }
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
