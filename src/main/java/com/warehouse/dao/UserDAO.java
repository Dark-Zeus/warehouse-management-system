package com.warehouse.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

import com.warehouse.model.User;
import com.warehouse.util.SQLConnector;

/**
 * User Data Access Object class for handling database operations related to User
 */
public class UserDAO {
    Connection conn = null;

    /**
     * Constructor
     */    
    public UserDAO() {
        conn = SQLConnector.getConnection("warehouse");
    }

    /**
     * Login function
     * @param user - {@code User} - {@link User}
     * @return User - {@code User} - {@link User}
     */
    public User login(User user) {
        String query = "SELECT * FROM `user` WHERE username = ? AND password = ?";

        User dbUser = new User();

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());

            ResultSet rs = ps.executeQuery();

            // If user exists, set the user details
            if(rs.next()) {
                dbUser.setUser_id(rs.getInt("user_id"));
                dbUser.setUsername(rs.getString("username"));
                dbUser.setRole(rs.getString("role"));
                return dbUser;
            }

            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get user by ID
     * @param user
     * @return
     */
    public User getUserById(User user) {
        String query = "SELECT * FROM `user` WHERE user_id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, user.getUser_id());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user.setUser_id(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    /**
     * Get all users
     * @return List<User>
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM `user`";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setUser_id(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setRole(rs.getString("role"));

                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    /**
     * Register a new user
     * @param user
     * @return boolean - {@code boolean} - {@link boolean}
     */
    public boolean register(User user) {
        String query = "INSERT INTO `user` (username, password, role) VALUES (?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Update user
     * @param user
     * @return boolean
     */
    public boolean updateUser(User user) {
        String query = "UPDATE `user` SET username = ?, password = ?, role = ? WHERE user_id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());
            ps.setInt(4, user.getUser_id());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Delete user
     * @param user
     * @return boolean
     */
    public boolean deleteUser(User user) {
        String query = "DELETE FROM `user` WHERE user_id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, user.getUser_id());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
