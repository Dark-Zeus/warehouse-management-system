package com.warehouse.controller;

import java.util.ArrayList;
import java.util.List;

import com.warehouse.dao.InvoiceDAO;
import com.warehouse.dao.UserDAO;
import com.warehouse.model.Invoice;
import com.warehouse.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class InvoiceManagementController {

    @FXML
    private TableView<Invoice> invoiceTable;

    @FXML
    private TableColumn<Invoice, Integer> invoice_id;

    @FXML
    private TableColumn<Invoice, Integer> user_id;

    @FXML
    private TableColumn<Invoice, String> total_amount;

    @FXML
    private TableColumn<Invoice, Integer> date;

    //@FXML
    //private TableColumn<Invoice, Double> unitPriceColumn;

    //@FXML
    //private ChoiceBox<String> userCmb;

    @FXML
    private ChoiceBox<Integer> userCmb;

    @FXML
    private TextField totalamountTxt;

    @FXML
    private TextField dateTxt;

    @FXML
    private Button addBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private Button deleteBtn;

    private String addMode = "add";
    private int selectedInvoiceId;

    @FXML
    private void initialize() {
        //Set the columns in the table
        invoice_id.setCellValueFactory(new PropertyValueFactory<>("invoice_id"));
        user_id.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        total_amount.setCellValueFactory(new PropertyValueFactory<>("total_amount"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        UserDAO userDAO = new UserDAO();
        List<User> users = userDAO.getAllUsers();
        List<Integer> userIds = new ArrayList<>();
        for (User user : users) {
            userIds.add(user.getUser_id());
        }
        userCmb.getItems().setAll(userIds);

        updateTable();
    }

    @FXML
    void addInvoice(ActionEvent event) {
        InvoiceDAO invoiceDAO = new InvoiceDAO();
        Invoice newInvoice = new Invoice();

        newInvoice.setUser_id(userCmb.getValue());
        newInvoice.setTotal_amount(Double.parseDouble(totalamountTxt.getText()));
        newInvoice.setDate(dateTxt.getText());

        if (addMode.equals("update")) {
            newInvoice.setInvoice_id(selectedInvoiceId);
            if (invoiceDAO.updateInvoice(newInvoice)) {
                resetForm();
                updateTable();

                showSuccessAlert("Invoice updated successfully");

            } else {
                showErrorAlert("Invoice not updated");
            }
        } else {
            if (invoiceDAO.addInvoice(newInvoice)) {
                resetForm();
                updateTable();

                showSuccessAlert("Invoice added successfully");
            } else {
                showErrorAlert("Invoice not added");
            }
        }
    }

    @FXML
    void deleteInvoice(ActionEvent event) {
        InvoiceDAO invoiceDAO = new InvoiceDAO();
        Invoice selectedInvoice = invoiceTable.getSelectionModel().getSelectedItem();

        // Check if an invoice is selected
        if(selectedInvoice == null) {
            showErrorAlert("Please select an invoice to delete");
            return;
        }

        if (selectedInvoice != null && invoiceDAO.deleteInvoice(selectedInvoice.getInvoice_id())) {
            updateTable();
            showSuccessAlert("Invoice deleted successfully");
        } else {
            showErrorAlert("Invoice not deleted");
        }
    }

    @FXML
    void updateInvoice(ActionEvent event) {
        Invoice selectedInvoice = invoiceTable.getSelectionModel().getSelectedItem();

        // Check if an invoice is selected
        if(selectedInvoice == null) {
            showErrorAlert("Please select an invoice to update");
            return;
        }

        if (selectedInvoice != null) {
            selectedInvoiceId = selectedInvoice.getInvoice_id();
            userCmb.setValue(selectedInvoice.getUser_id());
            totalamountTxt.setText(String.valueOf(selectedInvoice.getTotal_amount()));
            dateTxt.setText(String.valueOf(selectedInvoice.getDate()));

            addBtn.setText("Update Invoice");
            updateBtn.setDisable(true);
            invoiceTable.setDisable(true);
            addMode = "update";
        }
    }

    private void updateTable() {
        InvoiceDAO invoiceDAO = new InvoiceDAO();
        List<Invoice> invoices = invoiceDAO.getAllInvoices();
        invoiceTable.getItems().setAll(invoices);
    }

    // Reset the form to add mode
    private void resetForm() {
        //userCmb.setValue(null);
        userCmb.getSelectionModel().clearSelection();
        totalamountTxt.clear();
        dateTxt.clear();
        addBtn.setText("Add Invoice");
        updateBtn.setDisable(false);
        invoiceTable.setDisable(false);
        addMode = "add";
        selectedInvoiceId = -1;
    }

    // Show an error alert
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}