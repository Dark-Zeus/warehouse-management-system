package com.warehouse.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.warehouse.model.Invoice;
import com.warehouse.util.SQLConnector;

/**
 * Invoice Data Access Object class for handling database operations related to Invoice
 */
public class InvoiceDAO {
    private Connection conn;

    public InvoiceDAO() {
        conn = SQLConnector.getConnection("warehouse");
    }

    /**
     * Get invoice by ID
     * @param invoiceId - {@code int}
     * @return Invoice - {@code Invoice} {@link Invoice}
     */
    public Invoice getInvoiceById(int invoiceId) {
        String sql = "SELECT * FROM `invoice` WHERE invoice_id = ?";
        Invoice invoice = null;

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, invoiceId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                invoice = new Invoice();
                invoice.setInvoice_id(rs.getInt("invoice_id"));
                invoice.setUser_id(rs.getInt("user_id"));
                invoice.setTotal_amount(rs.getDouble("total_amount"));
                invoice.setDate(rs.getString("date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invoice;
    }

    /**
     * Get all invoices from the database
     * @return List<Invoice> - {@code List<Invoice>} {@link Invoice}
     */
    public List<Invoice> getAllInvoices() {
        List<Invoice> invoices = new ArrayList<>();
        String sql = "SELECT * FROM `invoice`";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setInvoice_id(rs.getInt("invoice_id"));
                invoice.setUser_id(rs.getInt("user_id"));
                invoice.setTotal_amount(rs.getDouble("total_amount"));
                invoice.setDate(rs.getString("date"));
                invoices.add(invoice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return invoices;
    }

    /**
     * Add a new invoice to the database
     * @param invoice - {@code Invoice} {@link Invoice}
     * @return boolean - {@code boolean} {@link boolean}
     */
    public boolean addInvoice(Invoice invoice) {
        String sql = "INSERT INTO `invoice` (invoice_id, user_id, total_amount, date) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, invoice.getInvoice_id());
            stmt.setInt(2, invoice.getUser_id());
            stmt.setDouble(3, invoice.getTotal_amount());
            stmt.setString(4, invoice.getDate());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Delete an invoice from the database
     * @param invoiceId - {@code int}
     * @return boolean - {@code boolean} {@link boolean}
     */
    public boolean deleteInvoice(int invoiceId) {
        String sql = "DELETE FROM `invoice` WHERE invoice_id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, invoiceId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Update invoice details in the database
     * @param invoice - {@code Invoice} {@link Invoice}
     * @return boolean - {@code boolean} {@link boolean}
     */
    public boolean updateInvoice(Invoice invoice) {
        String sql = "UPDATE `invoice` SET user_id = ?, total_amount = ?, date = ? WHERE invoice_id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, invoice.getUser_id());
            stmt.setDouble(2, invoice.getTotal_amount());
            stmt.setString(3, invoice.getDate());
            stmt.setInt(4, invoice.getInvoice_id());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}