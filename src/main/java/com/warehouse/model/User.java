package com.warehouse.model;

import com.warehouse.util.autosql.Model;
import com.warehouse.util.autosql.annotation.ColumnConstraints;
import com.warehouse.util.autosql.annotation.PrimaryKey;
import com.warehouse.util.autosql.annotation.SQLType;
import com.warehouse.util.autosql.annotation.Table;

@Table("user")
public class User implements Model{
    @SQLType("INT")
    @PrimaryKey
    @ColumnConstraints(autoIncrement = true)
    private int user_id;

    @SQLType("VARCHAR(50)")
    @ColumnConstraints(notNull = true, unique = true)
    private String username;

    @SQLType("VARCHAR(255)")
    @ColumnConstraints(notNull = true)
    private String password;

    @SQLType("VARCHAR(20)")
    @ColumnConstraints(notNull = true)
    private String role = "user";

    public User() {
    }


    public User(int user_id, String username, String password, String role) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean isAdmin() {
        return role.equals("admin");
    }
}
