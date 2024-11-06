package com.warehouse.controller;

import java.io.IOException;

import com.warehouse.dao.UserDAO;
import com.warehouse.model.User;
import com.warehouse.util.CFXMLLoader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class LoginController {

    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private TextField usernameTxt;

    @FXML
    void login(ActionEvent event) throws IOException {

        User user = new User();
        user.setUsername(usernameTxt.getText());
        user.setPassword(passwordTxt.getText());

        UserDAO userDAO = new UserDAO();
        boolean isValid = userDAO.login(user);

        if (isValid) {
            Scene dashboad = new Scene(CFXMLLoader.loadFXML("dashboard"));
            Stage stage = (Stage) loginBtn.getScene().getWindow();
            stage.setResizable(true);
            stage.setMaximized(true);
            stage.setTitle("Warehouse Management System");
            stage.setScene(dashboad);
            return;
        }else {
            //show "JAVAFX ALERT" with message "Invalid login"
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid login");
            alert.showAndWait();
        }
    }
}
