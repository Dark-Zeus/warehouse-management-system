package com.warehouse.controller;

import java.util.List;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import com.warehouse.dao.InventoryDAO;
import com.warehouse.model.Inventory;

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

    private String addMode = "add";
    private int selectedInventoryId;

    @FXML
    private void initialize() {

        inventory_id.setCellValueFactory(new PropertyValueFactory<>("inventory_id"));
        // Populate the warehouse ChoiceBox with example values (e.g., Warehouse IDs)
        warehouseCmb.getItems().addAll("1", "2", "3");
        productname.setCellValueFactory(new PropertyValueFactory<>("productname"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        unit_price.setCellValueFactory(new PropertyValueFactory<>("unit_price"));

        updateTable();
    }

    @FXML
    void addInventory(ActionEvent event) {
        InventoryDAO inventoryDAO = new InventoryDAO();

        Inventory newInventory = new Inventory();

        newInventory.setWarehouse_id(Integer.parseInt(warehouseCmb.getValue()));
        newInventory.setProduct_name(productNameTxt.getText());
        newInventory.setQuantity(Integer.parseInt(quantityTxt.getText()));
        newInventory.setUnit_price(Double.parseDouble(unitPriceTxt.getText()));

        if (addMode.equals("update")) {
            newInventory.setInventory_id(selectedInventoryId);
            if (inventoryDAO.updateInventory(newInventory)) {
                updateTable();
                addBtn.setText("Add Inventory");
                updateBtn.setDisable(false);
                inventoryTable.setDisable(false);
                addMode = "add";
                selectedInventoryId = -1;

                warehouseCmb.setValue(null);
                productNameTxt.clear();
                quantityTxt.clear();
                unitPriceTxt.clear();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Inventory not updated");
                alert.showAndWait();
            }
            return;
        } else {
            if (inventoryDAO.addInventory(newInventory)) {
                updateTable();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Inventory not added");
                alert.showAndWait();
            }
        }
    }

    @FXML
    void deleteInventory(ActionEvent event) {
        InventoryDAO inventoryDAO = new InventoryDAO();
        Inventory inventory = inventoryTable.getSelectionModel().getSelectedItem();

        if (inventoryDAO.deleteInventory(inventory.getInventory_id())) {
            updateTable();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Inventory not deleted");
            alert.showAndWait();
            
        }
    }

    @FXML
    void updateInventory(ActionEvent event) {
        InventoryDAO inventoryDAO = new InventoryDAO();
        Inventory selectedInventory = inventoryTable.getSelectionModel().getSelectedItem();

        Inventory dbInventory = inventoryDAO.getInventoryById(selectedInventory);

        selectedInventoryId = dbInventory.getInventory_id();
        warehouseCmb.setValue(String.valueOf(dbInventory.getWarehouse_id()));
        productNameTxt.setText(dbInventory.getProduct_name());
        quantityTxt.setText(String.valueOf(dbInventory.getQuantity()));
        unitPriceTxt.setText(String.valueOf(dbInventory.getUnit_price()));

        addBtn.setText("Update Inventory");
        updateBtn.setDisable(true);
        inventoryTable.setDisable(true);
        addMode = "update";
    }

    private void updateTable() {
        InventoryDAO inventoryDAO = new InventoryDAO();
        List<Inventory> inventories = inventoryDAO.getAllInventories();

        inventoryTable.getItems().setAll(inventories);
    }
}
    