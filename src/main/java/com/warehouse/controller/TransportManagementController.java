package com.warehouse.controller;

import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import com.warehouse.dao.TransportDAO;
import com.warehouse.model.Transport;

public class TransportManagementController {

    @FXML
    private Button addBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Button deleteBtn;

    @FXML
    private TextField startlocationTxt;
    @FXML
    private TextField destinationTxt;
    @FXML
    private ChoiceBox<String> vehicletypeCmb;
    @FXML
    private TextField vehicleNumberTxt;
    @FXML
    private TextField contactNumberTxt;
    @FXML
    private ChoiceBox<String> statusCmb;

    @FXML
    private TableView<Transport> transportTable;
    @FXML
    private TableColumn<Transport, Integer> transport_id;
    @FXML
    private TableColumn<Transport, String> start_location;
    @FXML
    private TableColumn<Transport, String> destination;
    @FXML
    private TableColumn<Transport, String> vehicle_type;
    @FXML
    private TableColumn<Transport, String> vehicle_number;
    @FXML
    private TableColumn<Transport, String> contact_number;
    @FXML
    private TableColumn<Transport, String> status;

    private String addMode = "add";
    private int selectedTransportId = -1;

    @FXML
    private void initialize() {
        vehicletypeCmb.getItems().addAll("Truck", "Van", "Motorbike");
        statusCmb.getItems().addAll("On Queue", "On Route", "Completed");

        transport_id.setCellValueFactory(new PropertyValueFactory<>("transport_id"));
        start_location.setCellValueFactory(new PropertyValueFactory<>("start_location"));
        destination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        vehicle_type.setCellValueFactory(new PropertyValueFactory<>("vehicle_type"));
        vehicle_number.setCellValueFactory(new PropertyValueFactory<>("vehicle_number"));
        contact_number.setCellValueFactory(new PropertyValueFactory<>("contact_number"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        updateTable();
    }

    @FXML
    void addTransport(ActionEvent event) {
        TransportDAO transportDAO = new TransportDAO();
        Transport transport = new Transport();

        transport.setStart_location(startlocationTxt.getText());
        transport.setDestination(destinationTxt.getText());
        transport.setVehicle_type(vehicletypeCmb.getValue());
        transport.setVehicle_number(vehicleNumberTxt.getText());
        transport.setContact_number(contactNumberTxt.getText());
        transport.setStatus(statusCmb.getValue());

        if ("update".equals(addMode)) {
            transport.setTransport_id(selectedTransportId);
            if (transportDAO.updateTransport(transport)) {
                updateTable();
                resetForm();
            } else {
                showAlert("Error", "Transport not updated");
            }
        } else {
            if (transportDAO.addTransport(transport)) {
                updateTable();
                resetForm();
            } else {
                showAlert("Error", "Transport not added");
            }
        }
    }

    @FXML
    void deleteTransport(ActionEvent event) {
        TransportDAO transportDAO = new TransportDAO();
        Transport transport = transportTable.getSelectionModel().getSelectedItem();

        if (transport != null && transportDAO.deleteTransport(transport.getTransport_id())) {
            updateTable();
        } else {
            showAlert("Error", "Transport not deleted");
        }
    }

    @FXML
    void updateTransport(ActionEvent event) {
        Transport transport = transportTable.getSelectionModel().getSelectedItem();
        if (transport != null) {
            selectedTransportId = transport.getTransport_id();

            startlocationTxt.setText(transport.getStart_location());
            destinationTxt.setText(transport.getDestination());
            vehicletypeCmb.setValue(transport.getVehicle_type());
            vehicleNumberTxt.setText(transport.getVehicle_number());
            contactNumberTxt.setText(transport.getContact_number());
            statusCmb.setValue(transport.getStatus());

            addBtn.setText("Update Transport");
            updateBtn.setDisable(true);
            transportTable.setDisable(true);
            addMode = "update";
        }
    }

    private void updateTable() {
        TransportDAO transportDAO = new TransportDAO();
        List<Transport> transports = transportDAO.getAllTransports();
        transportTable.getItems().setAll(transports);
    }

    private void resetForm() {
        startlocationTxt.clear();
        destinationTxt.clear();
        vehicletypeCmb.setValue(null);
        vehicleNumberTxt.clear();
        contactNumberTxt.clear();
        statusCmb.setValue(null);

        addBtn.setText("Add Transport");
        updateBtn.setDisable(false);
        transportTable.setDisable(false);
        addMode = "add";
        selectedTransportId = -1;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
