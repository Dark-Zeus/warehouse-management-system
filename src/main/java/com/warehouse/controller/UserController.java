package com.warehouse.controller;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import com.warehouse.dao.UserDAO;
import com.warehouse.model.User;

public class UserController {

    @FXML
    private Button addBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private TextField passwordTxt;

    @FXML
    private ChoiceBox<String> roleCmb;

    @FXML
    private Button updateBtn;

    @FXML
    private Label userFormLbl;

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, Integer> user_id;

    @FXML
    private TableColumn<User, String> username;

    @FXML
    private TableColumn<User, String> role;

    @FXML
    private TextField usernameTxt;

    private String addMode = "add";
    private int selectedUserId;

    @FXML
    private void initialize() {
        roleCmb.getItems().addAll("Admin", "User"); // Add the roles to the choice box

        // Set the cell value factory for the table columns
        user_id.setCellValueFactory(new PropertyValueFactory<User, Integer>("user_id"));
        username.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        role.setCellValueFactory(new PropertyValueFactory<User, String>("role"));

        updateTable(); // Update the table
    }

    @FXML
    void addUser(ActionEvent event) {
        UserDAO userDAO = new UserDAO(); // Create a new UserDAO object

        User newUser = new User(); // Create a new User object and set the values

        newUser.setUsername(usernameTxt.getText());
        newUser.setPassword(passwordTxt.getText());
        newUser.setRole(roleCmb.getValue().toLowerCase());

        // Check if the fields are empty
        if(newUser.getUsername().isEmpty() || newUser.getPassword().isEmpty() || newUser.getRole() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please fill all fields");
            alert.showAndWait();
            return;
        }

        // Check if the add mode is update
        if(addMode.equals("update")){
            newUser.setUser_id(selectedUserId); // Set the user id

            // Check if the user was updated
            if(userDAO.updateUser(newUser)){
                updateTable(); // Update the table

                // Set the fields to empty and reset the UI
                addBtn.setText("Add User");
                updateBtn.setDisable(false);
                userTable.setDisable(false);
                addMode = "add";
                selectedUserId = -1;

                usernameTxt.clear();
                passwordTxt.clear();
                roleCmb.setValue(null);

                // Show a success alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("User updated successfully");
                alert.showAndWait();
            } else{
                // Show an error alert
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("User not updated");
                alert.showAndWait();
            }
            return;
        }else{
            // Check if the user was added if the add mode is add
            if(userDAO.register(newUser)){
                updateTable(); // Update the table

                // Show a success alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("User updated successfully");
                alert.showAndWait();
            } else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("User not added");
                alert.showAndWait();
            }
        }

    }

    @FXML
    void deleteUser(ActionEvent event) {
        UserDAO userDAO = new UserDAO(); // Create a new UserDAO object
        User user = userTable.getSelectionModel().getSelectedItem();  // Get the selected user

        // Check if the user is null (not selected any rows from the table)
        if(user == null){
            // Show an error alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please select a user to delete");
            alert.showAndWait();
            return;
        }

        // Check if the user was deleted
        if(userDAO.deleteUser(user)){
            updateTable();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("User deleted successfully");
            alert.showAndWait();
        } else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("User not deleted");
            alert.showAndWait();
        }
    }

    @FXML
    void updateUser(ActionEvent event) {
        UserDAO userDAO = new UserDAO(); // Create a new UserDAO object
        User selectedUser = userTable.getSelectionModel().getSelectedItem(); // Get the selected user

        // Check if the user is null (not selected any rows from the table)
        if(selectedUser == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please select a user to update");
            alert.showAndWait();
            return;
        }

        // Get the user from the database
        User dbUser = userDAO.getUserById(selectedUser);

        // Set the fields to the selected user values
        selectedUserId = dbUser.getUser_id();
        usernameTxt.setText(dbUser.getUsername());
        passwordTxt.setText(dbUser.getPassword());
        roleCmb.setValue(dbUser.getRole());

        // Set the UI to update mode
        addBtn.setText("Update User");
        updateBtn.setDisable(true);
        userTable.setDisable(true);
        addMode = "update";       
    }

    /**
     * Update the table with the users retrieved from the database
     */
    private void updateTable() {
        UserDAO userDAO = new UserDAO();
        List<User> users = userDAO.getAllUsers();

        userTable.getItems().setAll(users);
    }
}
