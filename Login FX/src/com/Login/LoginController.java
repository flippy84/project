/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import database.Database;

import java.sql.SQLException;

/**
 * FXML Controller class
 *
 * @author Okan
 */

public class LoginController implements Initializable {

    @FXML
    private Label lblMessage;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML Hyperlink guestLogin;

    private Database db;
    public static String currentUser;
    public static String currentPassword;

    public LoginController() throws SQLException{
        this.db = new Database();
    }
    
    @FXML
    private void btnLoginAction(ActionEvent event) throws IOException, SQLException{
        System.out.print(guestLogin.isVisited());

        if(guestLogin.isVisited()){
            currentUser = "GUEST_213886";

            ((Node) (event.getSource())).getScene().getWindow().hide();
            Parent parent = FXMLLoader.load(getClass().getResource("/com/FXML/Main.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.setTitle("Main Frame");
            stage.setResizable(false);
            stage.show();
        }
        else if(db.checkLogin(txtUsername.getText(),txtPassword.getText())){
            currentUser = txtUsername.getText();
            currentPassword = txtPassword.getText();
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Parent parent = FXMLLoader.load(getClass().getResource("/com/FXML/Main.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.setTitle("Main Frame");
            stage.setResizable(false);
            stage.show();
        }
        else{
            lblMessage.setText("Username or Password is invalid");
        }
    }
    @FXML
    private void btnNewUserAction(ActionEvent event) throws IOException, SQLException{
        Parent parent = FXMLLoader.load(getClass().getResource("/com/FXML/NewUser.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("New User");
        stage.show();
    }
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
