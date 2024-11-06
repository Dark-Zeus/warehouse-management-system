package com.warehouse.model;

import com.warehouse.util.autosql.Model;
import com.warehouse.util.autosql.annotation.ColumnConstraints;
import com.warehouse.util.autosql.annotation.PrimaryKey;
import com.warehouse.util.autosql.annotation.SQLType;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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

    // JavaFX Properties for Data Binding
    private IntegerProperty warehouseIdProperty;
    private StringProperty warehouseNameProperty;
    private StringProperty locationProperty;
    private IntegerProperty capacityProperty;

    public Warehouse() {
        // Initialize the JavaFX properties
        this.warehouseIdProperty = new SimpleIntegerProperty();
        this.warehouseNameProperty = new SimpleStringProperty();
        this.locationProperty = new SimpleStringProperty();
        this.capacityProperty = new SimpleIntegerProperty();
    }

    public Warehouse(int warehouse_id, String warehouse_name, String location, int capacity) {
        this();
        this.warehouse_id = warehouse_id;
        this.warehouse_name = warehouse_name;
        this.location = location;
        this.capacity = capacity;
        // Initialize JavaFX properties with data
        this.warehouseIdProperty.set(warehouse_id);
        this.warehouseNameProperty.set(warehouse_name);
        this.locationProperty.set(location);
        this.capacityProperty.set(capacity);
    }

    public int getWarehouse_id() {
        return warehouseIdProperty.get();
    }

    public void setWarehouse_id(int warehouse_id) {
        this.warehouseIdProperty.set(warehouse_id);
    }

    public String getWarehouse_name() {
        return warehouseNameProperty.get();
    }

    public void setWarehouse_name(String warehouse_name) {
        this.warehouseNameProperty.set(warehouse_name);
    }

    public String getLocation() {
        return locationProperty.get();
    }

    public void setLocation(String location) {
        this.locationProperty.set(location);
    }

    public int getCapacity() {
        return capacityProperty.get();
    }

    public void setCapacity(int capacity) {
        this.capacityProperty.set(capacity);
    }

    // JavaFX Properties getters for data binding
    public IntegerProperty warehouseIdProperty() {
        return warehouseIdProperty;
    }

    public StringProperty warehouseNameProperty() {
        return warehouseNameProperty;
    }

    public StringProperty locationProperty() {
        return locationProperty;
    }

    public IntegerProperty capacityProperty() {
        return capacityProperty;
    }
}
