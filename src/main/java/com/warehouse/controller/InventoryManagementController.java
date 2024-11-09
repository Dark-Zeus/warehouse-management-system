package com.warehouse.controller;

import java.util.List;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import com.warehouse.dao.InventoryDAO;
import com.warehouse.model.Inventory;
import com.warehouse.dao.WarehouseDAO;
import com.warehouse.model.Warehouse;

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
        // Set the cell value factories for the table columns
        inventory_id.setCellValueFactory(new PropertyValueFactory<>("inventory_id"));
        // Populate the warehouse ChoiceBox with example values (e.g., Warehouse IDs)

        WarehouseDAO warehouseDAO = new WarehouseDAO();
        List<Warehouse> warehouses = warehouseDAO.getAllWarehouses();
        List<String> warehouseIds = new ArrayList<>();
        for (Warehouse warehouse : warehouses) {
            warehouseIds.add(String.valueOf(warehouse.getWarehouse_id()));
        }
        warehouseCmb.getItems().setAll(warehouseIds);

        warehouse_id.setCellValueFactory(new PropertyValueFactory<>("warehouse_id"));
        productname.setCellValueFactory(new PropertyValueFactory<>("product_name"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        unit_price.setCellValueFactory(new PropertyValueFactory<>("unit_price"));

        updateTable();
    }

    @FXML
    void addInventory(ActionEvent event) {
        InventoryDAO inventoryDAO = new InventoryDAO();

        Inventory newInventory = new Inventory(); // Create a new Inventory object and set the values

        newInventory.setWarehouse_id(Integer.parseInt(warehouseCmb.getValue()));
        newInventory.setProduct_name(productNameTxt.getText());
        newInventory.setQuantity(Integer.parseInt(quantityTxt.getText()));
        newInventory.setUnit_price(Double.parseDouble(unitPriceTxt.getText()));

        // Check if the addMode is 'update'
        if (addMode.equals("update")) {
            newInventory.setInventory_id(selectedInventoryId);
            // Update the inventory
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

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Inventory updated successfully");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Inventory not updated");
                alert.showAndWait();
            }
            return;
        } else {
            // Add a new inventory to the database
            if (inventoryDAO.addInventory(newInventory)) {
                updateTable();

                warehouseCmb.setValue(null);
                productNameTxt.clear();
                quantityTxt.clear();
                unitPriceTxt.clear();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Inventory added successfully");
                alert.showAndWait();
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
        Inventory inventory = inventoryTable.getSelectionModel().getSelectedItem(); // Get the selected inventory

        // Check if an inventory is selected
        if(inventory == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please select an inventory to delete");
            alert.showAndWait();
            return;
        }

        if (inventoryDAO.deleteInventory(inventory.getInventory_id())) {
            updateTable();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Inventory deleted successfully");
            alert.showAndWait();
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

        // Check if an inventory row is selected in the table
        if(selectedInventory == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please select an inventory to update");
            alert.showAndWait();
            return;
        }

        Inventory dbInventory = inventoryDAO.getInventoryById(selectedInventory); // Get the inventory from the database

        // Set the values in the form
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

    /**
     * Update the inventory table with the latest data
     */
    private void updateTable() {
        InventoryDAO inventoryDAO = new InventoryDAO();
        List<Inventory> inventories = inventoryDAO.getAllInventories();

        inventoryTable.getItems().setAll(inventories);
    }
}
    