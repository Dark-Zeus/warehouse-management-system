package com.warehouse.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.warehouse.model.Warehouse;
import com.warehouse.util.SQLConnector;

public class WarehouseDAO {

    Connection conn = null;

    public WarehouseDAO() {
        conn = SQLConnector.getConnection("warehouse");
    }

    // Get all warehouses from the database
    public List<Warehouse> getAllWarehouses() {
        List<Warehouse> warehouses = new ArrayList<>();
        String sql = "SELECT * FROM warehouse"; // Ensure 'warehouse' table is correct in your DB

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Warehouse warehouse = new Warehouse(
                        rs.getInt("warehouse_id"), // Matches column 'warehouse_id'
                        rs.getString("warehouse_name"), // Matches column 'warehouse_name'
                        rs.getString("location"), // Matches column 'location'
                        rs.getInt("capacity") // Matches column 'capacity'
                );
                warehouses.add(warehouse);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return warehouses;
    }

    // Add a new warehouse to the database
    public boolean addWarehouse(Warehouse warehouse) {
        String sql = "INSERT INTO warehouse (warehouse_name, location, capacity) VALUES (?, ?, ?)"; // Adjust column
                                                                                                    // names
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, warehouse.getWarehouse_name()); // 'warehouse_name'
            stmt.setString(2, warehouse.getLocation()); // 'location'
            stmt.setInt(3, warehouse.getCapacity()); // 'capacity'

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a warehouse from the database
    public boolean deleteWarehouse(int warehouseId) {
        String sql = "DELETE FROM warehouse WHERE warehouse_id = ?"; // 'warehouse_id' matches column name
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, warehouseId); // 'warehouse_id'

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update warehouse details in the database
    public boolean updateWarehouse(Warehouse warehouse) {
        String sql = "UPDATE warehouse SET warehouse_name = ?, location = ?, capacity = ? WHERE warehouse_id = ?"; // Updated
                                                                                                                   // column
                                                                                                                   // names
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, warehouse.getWarehouse_name()); // 'warehouse_name'
            stmt.setString(2, warehouse.getLocation()); // 'location'
            stmt.setInt(3, warehouse.getCapacity()); // 'capacity'
            stmt.setInt(4, warehouse.getWarehouse_id()); // 'warehouse_id'

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
