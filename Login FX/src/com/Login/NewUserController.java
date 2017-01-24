/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Login;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import database.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Okan
 */
public class NewUserController implements Initializable {

    @FXML
    private Label lblMessage;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private ComboBox<String> userType;

    private Database db;

    public NewUserController() throws SQLException{
        this.db = new Database();
    }
    @FXML
    private void btnSubmitOnAction(ActionEvent event) throws IOException, SQLException {
        if(txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty()){
            lblMessage.setText("No fields can be blank.");
        }
        else {
            db.addUser(txtUsername.getText(), txtPassword.getText(), userType.getSelectionModel().getSelectedItem());
            ((Node) (event.getSource())).getScene().getWindow().hide();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userType.getItems().addAll("Player", "Advertiser");
        userType.getSelectionModel().select(0);
    }    
    
}
