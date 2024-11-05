package com.warehouse.model;

import com.warehouse.util.autosql.Model;
import com.warehouse.util.autosql.annotation.ColumnConstraints;
import com.warehouse.util.autosql.annotation.PrimaryKey;
import com.warehouse.util.autosql.annotation.SQLType;

public class Warehouse implements Model {
    @SQLType("INT")
    @PrimaryKey
    @ColumnConstraints(autoIncrement = true)
    private int warehouse_id;

    @SQLType("VARCHAR(50)")
    @ColumnConstraints(notNull = true)
    private String warehouse_name;
 
    @SQLType("VARCHAR(100)")
    @ColumnConstraints(notNull = true)
    private String location;

    @SQLType("INT")
    @ColumnConstraints(notNull = true)
    private int capacity;

    public Warehouse() {
    }

    public Warehouse(int warehouse_id, String warehouse_name, String location, int capacity) {
        this.warehouse_id = warehouse_id;
        this.warehouse_name = warehouse_name;
        this.location = location;
        this.capacity = capacity;
    }

    public int getWarehouse_id() {
        return warehouse_id;
    }

    public void setWarehouse_id(int warehouse_id) {
        this.warehouse_id = warehouse_id;
    }

    public String getWarehouse_name() {
        return warehouse_name;
    }

    public void setWarehouse_name(String warehouse_name) {
        this.warehouse_name = warehouse_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
