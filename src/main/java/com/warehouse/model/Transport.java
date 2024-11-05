package com.warehouse.model;

import com.warehouse.util.autosql.Model;
import com.warehouse.util.autosql.annotation.ColumnConstraints;
import com.warehouse.util.autosql.annotation.PrimaryKey;
import com.warehouse.util.autosql.annotation.SQLType;

public class Transport implements Model{
    @SQLType("INT")
    @PrimaryKey
    @ColumnConstraints(autoIncrement = true)
    private int transport_id;
   
    @SQLType("VARCHAR(50)") 
    @ColumnConstraints(notNull = true)
    private String vehicle_type;
  
    @SQLType("VARCHAR(20)")
    @ColumnConstraints(notNull = true, unique = true)
    private String vehicle_number;
  
    @SQLType("VARCHAR(50)")
    @ColumnConstraints(notNull = true)
    private String driver_name;

    @SQLType("VARCHAR(15)")
    @ColumnConstraints(notNull = true)
    private String contact_number;

    public Transport() {
    }

    public Transport(int transport_id, String vehicle_type, String vehicle_number, String driver_name, String contact_number) {
        this.transport_id = transport_id;
        this.vehicle_type = vehicle_type;
        this.vehicle_number = vehicle_number;
        this.driver_name = driver_name;
        this.contact_number = contact_number;
    }

    public int getTransport_id() {
        return transport_id;
    }

    public void setTransport_id(int transport_id) {
        this.transport_id = transport_id;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public String getVehicle_number() {
        return vehicle_number;
    }

    public void setVehicle_number(String vehicle_number) {
        this.vehicle_number = vehicle_number;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }
}
