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

    /**
     * Open the inventory management form
     * @param event
     * @throws IOException
     */
    @FXML
    void openInventoryManagementForm(ActionEvent event) throws IOException {
        Scene inventoryManagement = new Scene(CFXMLLoader.loadFXML("inventory_management"));
        Stage stage = new Stage();
        stage.setScene(inventoryManagement);
        stage.setTitle("Inventory Management");
        stage.setMinWidth(830);
        stage.setMinHeight(540);
        stage.show();
    }

    /**
     * Open the invoice management form
     * @param event
     * @throws IOException
     */
    @FXML
    void openInvoiceManagementForm(ActionEvent event) throws IOException {
        Scene invoiceManagement = new Scene(CFXMLLoader.loadFXML("invoice_management"));
        Stage stage = new Stage();
        stage.setScene(invoiceManagement);
        stage.setTitle("Invoice Management");
        stage.setMinWidth(840);
        stage.setMinHeight(520);
        stage.show();

    }

    /**
     * Open the transport management form
     * @param event
     * @throws IOException
     */
    @FXML
    void openTransportMangementForm(ActionEvent event) throws IOException {
        Scene transportManagement = new Scene(CFXMLLoader.loadFXML("transport_management"));
        Stage stage = new Stage();
        stage.setScene(transportManagement);
        stage.setTitle("Transport Management");

        stage.setMinWidth(1100);
        stage.setMinHeight(620);
        stage.show();
    }

    /**
     * Open the user management form
     * @param event
     * @throws IOException
     */
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

    /**
     * Open the user form (profile)
     * @param event
     * @throws IOException
     */
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

    /**
     * Set the user
     * @param user
     */
    public void setUser(User user) {
        this.user = user;

        userManagementBtn.setDisable(true); // Disable the user management button by default (Admin only)
        // Check if the user is an admin
        if(user.isAdmin()) {
            userManagementBtn.setDisable(false); // Enable the user management button
        }
    }

}
