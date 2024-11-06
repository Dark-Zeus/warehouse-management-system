package com.warehouse.controller;

import java.io.IOException;

import com.warehouse.util.CFXMLLoader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class PleaseProvideControllerClassName {

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
    void openInventoryManagementForm(ActionEvent event) {

    }

    @FXML
    void openInvoiceManagementForm(ActionEvent event) {

    }

    @FXML
    void openTransportMangementForm(ActionEvent event) {

    }

    @FXML
    void openUserForm(ActionEvent event) {

    }

    @FXML
    void openWarehouseManagementForm(ActionEvent event) throws IOException {
        Scene warehouse = new Scene(CFXMLLoader.loadFXML("warehousemanagement_table"));
        Stage stage = (Stage) warehouseManagementBtn.getScene().getWindow();
        stage.setScene(warehouse);
    }

}
