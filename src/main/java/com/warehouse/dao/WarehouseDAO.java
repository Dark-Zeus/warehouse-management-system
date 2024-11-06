package com.warehouse.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.warehouse.model.Warehouse;
import com.warehouse.util.SQLConnector;

public class WarehouseDAO {
    private Connection conn;

    public WarehouseDAO() {
        conn = SQLConnector.getConnection("warehouse");
    }

    // Method to add a new warehouse
    public boolean addWarehouse(Warehouse warehouse) {
        String query = "INSERT INTO `warehouse` (name, location, capacity) VALUES (?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, warehouse.getWarehouse_name());
            ps.setString(2, warehouse.getLocation());
            ps.setInt(3, warehouse.getCapacity());

            return ps.executeUpdate() > 0;  // Returns true if insert was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to get a warehouse by ID
    public Warehouse getWarehouseById(int id) {
        String query = "SELECT * FROM `warehouse` WHERE id = ?";
        Warehouse warehouse = null;

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                warehouse = new Warehouse(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("location"),
                    rs.getInt("capacity")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return warehouse;
    }

    // Method to update an existing warehouse
    public boolean updateWarehouse(Warehouse warehouse) {
        String query = "UPDATE `warehouse` SET name = ?, location = ?, capacity = ? WHERE id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, warehouse.getWarehouse_name());
            ps.setString(2, warehouse.getLocation());
            ps.setInt(3, warehouse.getCapacity());
            ps.setInt(4, warehouse.getWarehouse_id());

            return ps.executeUpdate() > 0;  // Returns true if update was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to delete a warehouse by ID
    public boolean deleteWarehouse(int id) {
        String query = "DELETE FROM `warehouse` WHERE id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);

            return ps.executeUpdate() > 0;  // Returns true if deletion was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
