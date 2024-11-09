package com.warehouse.controller;

import java.util.List;

import com.warehouse.dao.InvoiceDAO;
import com.warehouse.model.Invoice;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
    //private ChoiceBox<String> warehouseCmb;

    @FXML
    private TextField useridTxt;

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

        //warehouseCmb.getItems().addAll("1", "2", "3");  // Example warehouse IDs
        updateTable();
    }

    @FXML
    void addInvoice(ActionEvent event) {
        InvoiceDAO invoiceDAO = new InvoiceDAO();
        Invoice newInvoice = new Invoice();

        newInvoice.setUser_id(Integer.parseInt(user_id.getText()));
        newInvoice.setTotal_amount(Double.parseDouble(totalamountTxt.getText()));
        newInvoice.setDate(dateTxt.getText());

        if (addMode.equals("update")) {
            newInvoice.setInvoice_id(selectedInvoiceId);
            if (invoiceDAO.updateInvoice(newInvoice)) {
                resetForm();
                updateTable();
            } else {
                showErrorAlert("Invoice not updated");
            }
        } else {
            if (invoiceDAO.addInvoice(newInvoice)) {
                updateTable();
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
            user_id.setText(String.valueOf(selectedInvoice.getUser_id()));
            total_amount.setText(String.valueOf(selectedInvoice.getTotal_amount()));
            date.setText(String.valueOf(selectedInvoice.getDate()));

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
        //warehouseCmb.setValue(null);
        useridTxt.clear();
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
}
