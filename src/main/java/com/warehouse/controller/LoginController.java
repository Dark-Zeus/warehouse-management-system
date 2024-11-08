package com.warehouse.controller;

import java.io.IOException;

import com.warehouse.dao.UserDAO;
import com.warehouse.model.User;
import com.warehouse.util.CFXMLLoader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
        User dbUser = userDAO.login(user);

        if (dbUser != null) {
            FXMLLoader loader = CFXMLLoader.getFXMLLoader("dashboard");
            Scene dashboad = new Scene(loader.load());

            DashboardController dc = loader.getController();
            dc.setUser(dbUser);
            
            Stage stage = (Stage) loginBtn.getScene().getWindow();
            stage.setResizable(true);
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
