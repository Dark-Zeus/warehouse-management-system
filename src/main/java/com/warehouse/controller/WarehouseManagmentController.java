package com.warehouse.controller;

import com.warehouse.model.Warehouse;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class WarehouseManagmentController {

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

    @FXML
    private void initialize() {
        System.out.println("WarehouseManagmentController initialized");  
    }

    @FXML
    void deleteWarehouseRecord(ActionEvent event) {

    }

    @FXML
    void openWarehouseEditForm(ActionEvent event) {

    }

    @FXML
    void openWarehouseForm(ActionEvent event) {

    }

}
