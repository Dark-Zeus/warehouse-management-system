package com.warehouse.model;

import com.warehouse.util.autosql.Model;
import com.warehouse.util.autosql.annotation.ColumnConstraints;
import com.warehouse.util.autosql.annotation.ForiegnKey;
import com.warehouse.util.autosql.annotation.PrimaryKey;
import com.warehouse.util.autosql.annotation.SQLType;
import com.warehouse.util.autosql.annotation.Table;

// CREATE TABLE Inventory (
//   inventory_id INT PRIMARY KEY AUTO_INCREMENT,
//   warehouse_id INT,
//   product_name VARCHAR(50),
//   quantity INT,
//   unit_price DECIMAL(10, 2),
//   FOREIGN KEY (warehouse_id) REFERENCES Warehouses(warehouse_id)
// );

@Table("inventory")
public class Inventory implements Model {
    @SQLType("INT")
    @PrimaryKey
    @ColumnConstraints(autoIncrement = true)
    private int inventory_id;

    @SQLType("INT")
    @ForiegnKey(references = Warehouse.class, referencedColumn = "warehouse_id")
    private int warehouse_id;

    @SQLType("VARCHAR(50)")
    @ColumnConstraints(notNull = true)
    private String product_name;

    @SQLType("INT")
    @ColumnConstraints(notNull = true)
    private int quantity;

    @SQLType("DECIMAL(10, 2)")
    @ColumnConstraints(notNull = true)
    private double unit_price;

    public Inventory() {
    }

    public Inventory(int inventory_id, int warehouse_id, String product_name, int quantity, double unit_price) {
        this.inventory_id = inventory_id;
        this.warehouse_id = warehouse_id;
        this.product_name = product_name;
        this.quantity = quantity;
        this.unit_price = unit_price;
    }

    public int getInventory_id() {
        return inventory_id;
    }

    public void setInventory_id(int inventory_id) {
        this.inventory_id = inventory_id;
    }

    public int getWarehouse_id() {
        return warehouse_id;
    }

    public void setWarehouse_id(int warehouse_id) {
        this.warehouse_id = warehouse_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
    }
}
