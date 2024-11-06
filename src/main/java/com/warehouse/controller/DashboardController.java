package com.warehouse.controller;

import java.io.IOException;

import com.warehouse.util.CFXMLLoader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class DashboardController {

    @FXML
    private Button inventoryManagementBtn;

    @FXML
    private Button invoiceMangementBtn;

    @FXML
    private Button transportManagementBtn;

    @FXML
    private Button userBtn;

    @FXML
    private Button warehouseManagementBtn;

    @FXML
    private void initialize() {
        System.out.println("DashboardController initialized");
    }

    @FXML
    void openInventoryManagementForm(ActionEvent event) {

    }

    @FXML
    void openInvoiceManagementForm(ActionEvent event) {

    }

    @FXML
    void openTransportMangementForm(ActionEvent event) {

    }

    @FXML
    void openUserForm(ActionEvent event) throws IOException {
        Scene userManagement = new Scene(CFXMLLoader.loadFXML("user_management"));
        Stage stage = new Stage();
        stage.setScene(userManagement);
        stage.setTitle("User Management");
        stage.show();
    }

    @FXML
    void openWarehouseManagementForm(ActionEvent event) throws IOException {
        Scene warehouseManagement = new Scene(CFXMLLoader.loadFXML("warehousemanagement_table"));
        Stage stage = new Stage();
        stage.setScene(warehouseManagement);
        stage.setTitle("Warehouse Management");
        stage.show();
    }

}
