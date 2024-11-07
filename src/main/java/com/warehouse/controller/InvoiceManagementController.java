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
    private TableColumn<Invoice, Integer> invoiceIdColumn;

    @FXML
    private TableColumn<Invoice, Integer> userIdColumn;

    @FXML
    private TableColumn<Invoice, String> totalAmountColumn;

    @FXML
    private TableColumn<Invoice, Integer> dateColumn;

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
        invoiceIdColumn.setCellValueFactory(new PropertyValueFactory<>("invoiceId"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        totalAmountColumn.setCellValueFactory(new PropertyValueFactory<>("totalamount"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        //warehouseCmb.getItems().addAll("1", "2", "3");  // Example warehouse IDs
        updateTable();
    }

    @FXML
    void addInvoice(ActionEvent event) {
        InvoiceDAO invoiceDAO = new InvoiceDAO();
        Invoice newInvoice = new Invoice();

        newInvoice.setUser_id(Integer.parseInt(userIdColumn.getText()));
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

        if (selectedInvoice != null && invoiceDAO.deleteInvoice(selectedInvoice.getInvoice_id())) {
            updateTable();
        } else {
            showErrorAlert("Invoice not deleted");
        }
    }

    @FXML
    void updateInvoice(ActionEvent event) {
        Invoice selectedInvoice = invoiceTable.getSelectionModel().getSelectedItem();

        if (selectedInvoice != null) {
            selectedInvoiceId = selectedInvoice.getInvoice_id();
            userIdColumn.setText(String.valueOf(selectedInvoice.getUser_id()));
            totalAmountColumn.setText(String.valueOf(selectedInvoice.getTotal_amount()));
            dateColumn.setText(String.valueOf(selectedInvoice.getDate()));

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

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
