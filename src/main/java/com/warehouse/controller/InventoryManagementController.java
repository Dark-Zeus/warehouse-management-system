package com.warehouse.controller;

import com.warehouse.model.Inventory;
import com.warehouse.model.Warehouse;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
//import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class InventoryManagementController {
    @FXML
    private TableView<Inventory> inventoryTable;
    @FXML
    private TableColumn<Inventory, String> inventory_id;
    @FXML
    private TableColumn<Inventory, String> warehouse_id;
    @FXML
    private TableColumn<Inventory, String> productname;
    @FXML
    private TableColumn<Inventory, Integer> quantity;
    @FXML
    private TableColumn<Inventory, Double> unit_price;
    @FXML
    private ChoiceBox<String> warehouseCmb;
    @FXML
    private TextField productNameTxt;
    @FXML
    private TextField quantityTxt;
    @FXML
    private TextField unitPriceTxt;
    @FXML
    private Button addBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Button deleteBtn;

    private ObservableList<Inventory> inventoryList = FXCollections.observableArrayList();

    public void initialize() {
        // Initialize Table Columns
        inventory_id.setCellValueFactory(cellData -> cellData.getValue().inventoryIdProperty());
        warehouse_id.setCellValueFactory(cellData -> cellData.getValue().warehouseIdProperty());
        productname.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        quantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        unit_price.setCellValueFactory(cellData -> cellData.getValue().unitPriceProperty().asObject());

        // Add data to the table
        inventoryTable.setItems(inventoryList);

        // Populate warehouse ChoiceBox (sample data)
        warehouseCmb.setItems(FXCollections.observableArrayList("Warehouse A", "Warehouse B", "Warehouse C"));

        // Add listener for selecting an item in the table
        inventoryTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showInventoryDetails(newValue));
    }

    @FXML
    private void addInventory() {
        try {
            String warehouse = warehouseCmb.getValue();
            String productName = productNameTxt.getText();
            int quantity = Integer.parseInt(quantityTxt.getText());
            double unitPrice = Double.parseDouble(unitPriceTxt.getText());

            Inventory newItem = new InventoryItem(generateInventoryId(), warehouse, productName, quantity, unitPrice);
            inventoryList.add(newItem);

            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Invalid input", "Please enter valid data for Quantity and Unit Price.");
        }
    }

    @FXML
    private void updateInventory() {
        Inventory selectedItem = Inventory.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                selectedItem.setWarehouse_id(warehouseCmb.getValue());
                selectedItem.setProduct_name(productNameTxt.getText());
                selectedItem.setQuantity(Integer.parseInt(quantityTxt.getText()));
                selectedItem.setUnit_price(Double.parseDouble(unitPriceTxt.getText()));
                //Inventory.refresh();
            } catch (NumberFormatException e) {
                showAlert("Invalid input", "Please enter valid data for Quantity and Unit Price.");
            }
        } else {
            showAlert("No Selection", "Please select an item to update.");
        }
    }

    @FXML
    private void deleteInventory() {
        Inventory selectedItem = inventoryTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            inventoryList.remove(selectedItem);
        } else {
            showAlert("No Selection", "Please select an item to delete.");
        }
    }

    private void showInventoryDetails(Inventory item) {
        if (item != null) {
            warehouseCmb.setValue(item.getWarehouseId());
            productNameTxt.setText(item.getProductName());
            quantityTxt.setText(Integer.toString(item.getQuantity()));
            unitPriceTxt.setText(Double.toString(item.getUnitPrice()));
        } else {
            clearFields();
        }
    }

    private void clearFields() {
        warehouseCmb.setValue(null);
        productNameTxt.clear();
        quantityTxt.clear();
        unitPriceTxt.clear();
    }

    private String generateInventoryId() {
        return "INV" + (inventoryList.size() + 1);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
