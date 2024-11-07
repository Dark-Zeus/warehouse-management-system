package com.warehouse.model;

import com.warehouse.util.autosql.Model;
import com.warehouse.util.autosql.annotation.ColumnConstraints;
import com.warehouse.util.autosql.annotation.ForiegnKey;
import com.warehouse.util.autosql.annotation.PrimaryKey;
import com.warehouse.util.autosql.annotation.SQLType;

public class Invoice implements Model {
    // CREATE TABLE Invoices (
    // invoice_id INT PRIMARY KEY AUTO_INCREMENT,
    // user_id INT,
    // total_amount DECIMAL(10, 2),
    // date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    // FOREIGN KEY (user_id) REFERENCES Users(user_id)
    // );

    @SQLType("INT")
    @PrimaryKey
    @ColumnConstraints(autoIncrement = true)
    private int invoice_id;

    @SQLType("INT")
    @ColumnConstraints(notNull = true)
    @ForiegnKey(references = User.class, referencedColumn = "user_id")
    private int user_id;

    @SQLType("DECIMAL(10, 2)")
    @ColumnConstraints(notNull = true)
    private double total_amount;

    @SQLType("TIMESTAMP")
    @ColumnConstraints(defaultValue = "CURRENT_TIMESTAMP")
    private String date;

    public Invoice() {
    }

    public Invoice(int invoice_id, int user_id, double total_amount, String date) {
        this.invoice_id = invoice_id;
        this.user_id = user_id;
        this.total_amount = total_amount;
        this.date = date;
    }

    public int getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(int invoice_id) {
        this.invoice_id = invoice_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
