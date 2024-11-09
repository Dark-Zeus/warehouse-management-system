package com.warehouse.controller;

import java.io.IOException;

import com.warehouse.model.User;
import com.warehouse.util.CFXMLLoader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    private Button userManagementBtn;

    @FXML
    private Button warehouseManagementBtn;


    private User user;

    @FXML
    private void initialize() {

    }

    @FXML
    void openInventoryManagementForm(ActionEvent event) {

    }

    @FXML
    void openInvoiceManagementForm(ActionEvent event) {

    }

    @FXML
    void openTransportMangementForm(ActionEvent event) throws IOException {
        Scene transportManagement = new Scene(CFXMLLoader.loadFXML("transport_management"));
        Stage stage = new Stage();
        stage.setScene(transportManagement);
        stage.setTitle("Transport Management");
        stage.show();
    }

    @FXML
    void openUserManagementForm(ActionEvent event) throws IOException {
        if(!user.isAdmin()) {
            return;
        }
        Scene userManagement = new Scene(CFXMLLoader.loadFXML("user_management"));
        Stage stage = new Stage();
        stage.setScene(userManagement);
        stage.setTitle("User Management");
        stage.setMinWidth(860);
        stage.setMinHeight(550);
        stage.show();
    }

    @FXML
    void openUserForm(ActionEvent event) throws IOException {
        FXMLLoader loader = CFXMLLoader.getFXMLLoader("profile");
        Scene scene = new Scene(loader.load());
        ProfileController pc = loader.getController();
        pc.setUser(user);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("User Profile");
        stage.setResizable(false);
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

    public void setUser(User user) {
        this.user = user;

        userManagementBtn.setDisable(true);
        if(user.isAdmin()) {
            userManagementBtn.setDisable(false);
        }
    }

}
