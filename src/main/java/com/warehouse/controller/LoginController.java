package com.warehouse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.IOException;

import com.warehouse.util.CFXMLLoader;

public class LoginController {

    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private TextField usernameTxt;

    @FXML
    void login(ActionEvent event) throws IOException{
        Scene dashboad = new Scene(CFXMLLoader.loadFXML("dashboard"));
        Stage stage = (Stage) loginBtn.getScene().getWindow();
        stage.setScene(dashboad);
    }
}
