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
import javafx.scene.control.TableView;
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
        
    }

    @FXML
    private void addInventory() {
        
    }

    @FXML
    private void updateInventory() {
        
    }

    @FXML
    private void deleteInventory() {
        
    }


    private void clearFields() {
        warehouseCmb.setValue(null);
        productNameTxt.clear();
        quantityTxt.clear();
        unitPriceTxt.clear();
    }

    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
