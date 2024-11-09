package com.warehouse.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.warehouse.model.Inventory;
import com.warehouse.util.SQLConnector;

/**
 * Inventory Data Access Object class for handling database operations related to Inventory
 */
public class InventoryDAO {
    Connection conn = null;

    public InventoryDAO() {
        conn = SQLConnector.getConnection("warehouse");
    }

    /**
     * Get inventory by ID
     * @param inventory
     * @return
     */
    public Inventory getInventoryById(Inventory inventory){
        String sql = "SELECT * FROM `inventory` WHERE inventory_id = ?"; // Ensure 'Inventory' table is correct in your DB

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, inventory.getInventory_id()); // 'inventory_id'

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                inventory.setWarehouse_id(rs.getInt("warehouse_id")); // Matches column 'warehouse_id'
                inventory.setProduct_name(rs.getString("product_name")); // Matches column 'product_name'
                inventory.setQuantity(rs.getInt("quantity")); // Matches column 'quantity'
                inventory.setUnit_price(rs.getDouble("unit_price")); // Matches column 'unit_price'
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inventory;
    }

    /**
     * Get all inventories from the database
     * @return List<Inventory> - {@code List<Inventory>} {@link Inventory}
     */
    public List<Inventory> getAllInventories() {
        List<Inventory> inventories = new ArrayList<>();
        String sql = "SELECT * FROM `inventory`"; // Ensure 'Inventory' table is correct in your DB

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                        Inventory inventory = new Inventory();
                        inventory.setInventory_id(rs.getInt("inventory_id")); // Matches column 'inventory_id'
                        inventory.setWarehouse_id(rs.getInt("warehouse_id")); // Matches column 'warehouse_id'
                        inventory.setProduct_name(rs.getString("product_name")); // Matches column 'product_name'
                        inventory.setQuantity(rs.getInt("quantity")); // Matches column 'quantity'
                        inventory.setUnit_price(rs.getDouble("unit_price")); // Matches column 'unit_price'
                        
                        inventories.add(inventory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return inventories;
    }

    /**
     * Add a new inventory to the database
     * @param inventory
     * @return
     */
    public boolean addInventory(Inventory inventory) {
        String sql = "INSERT INTO `inventory` (warehouse_id, product_name, quantity, unit_price) VALUES (?, ?, ?, ?)"; // Adjust column
                                                                                                    // names
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, inventory.getWarehouse_id()); // 'warehouse_name'
            stmt.setString(2, inventory.getProduct_name()); // 'product_name'
            stmt.setInt(3, inventory.getQuantity()); // 'quantity'
            stmt.setDouble(4, inventory.getUnit_price()); // 'unit_price'

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Delete an inventory from the database
     * @param inventoryId
     * @return
     */
    public boolean deleteInventory(int inventoryId) {
        String sql = "DELETE FROM `inventory` WHERE inventory_id = ?"; // 'inventory_id' matches column name
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, inventoryId); // 'inventory_id'

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Update inventory details in the database
     * @param inventory
     * @return
     */
    public boolean updateInventory(Inventory inventory) {
        String sql = "UPDATE `inventory` SET warehouse_id = ?, product_name = ?, quantity = ?, unit_price = ? WHERE inventory_id = ?"; 
        // Adjust column names

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, inventory.getWarehouse_id()); // 'warehouse_name'
            stmt.setString(2, inventory.getProduct_name()); // 'product_name'
            stmt.setInt(3, inventory.getQuantity()); // 'quantity'
            stmt.setDouble(4, inventory.getUnit_price()); // 'unit_price'
            stmt.setInt(5, inventory.getInventory_id()); // 'inventory_id'

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
