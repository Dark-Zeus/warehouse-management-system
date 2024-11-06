package com.warehouse.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.warehouse.model.User;
import com.warehouse.util.SQLConnector;

public class UserDAO {
    Connection conn = null;

    public UserDAO() {
        conn = SQLConnector.getConnection("warehouse");
    }

    public boolean login(User user) {
        String query = "SELECT * FROM `user` WHERE username = ? AND password = ?";

        try{
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());

            return ps.executeQuery().next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean register(String username, String password) {
        return true;
    }
}
