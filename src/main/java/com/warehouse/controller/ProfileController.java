package com.warehouse.controller;

import com.warehouse.dao.UserDAO;
import com.warehouse.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ProfileController {

    @FXML
    private TextField passwordTxt;

    @FXML
    private Button updateBtn;

    @FXML
    private Label userFormLbl;

    @FXML
    private TextField usernameTxt;

    private User user;

    @FXML
    void updateUser(ActionEvent event) {
        UserDAO userDAO = new UserDAO();
        User newUser = new User(); 
        newUser.setUser_id(user.getUser_id());
        newUser.setUsername(usernameTxt.getText());
        newUser.setPassword(passwordTxt.getText());

        if(userDAO.updateUser(newUser)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("User updated successfully");
            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("User update failed");
            alert.showAndWait();
        }
    }

    public void setUser(User user) {
        this.user = user;
        usernameTxt.setText(user.getUsername());
        passwordTxt.setText(user.getPassword());
    }

}
