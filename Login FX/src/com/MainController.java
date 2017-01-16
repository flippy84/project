/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;


import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.Login.LoginController;
import database.Database;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.Initializable;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.application.Platform;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author Okan
 */
public class MainController implements Initializable {
    
    @FXML
    public Pane pane;

    @FXML
    HBox topMenu;

    @FXML
    private Button account;

    @FXML
    private Button library;

    @FXML
    private Button leagues;


    public MainController(){
    }
    @FXML
    private void btnAccount(ActionEvent event) throws IOException, SQLException {

        ((Node) (event.getSource())).requestFocus();
        Parent node = FXMLLoader.load(getClass().getResource("/com/FXML/Account.fxml"));
        pane.getChildren().clear();
        pane.getChildren().add(node);
    }
    @FXML
    private void btnLibrary(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).requestFocus();
        Parent node = FXMLLoader.load(getClass().getResource("/com/FXML/Library.fxml"));
        pane.getChildren().clear();
        pane.getChildren().add(node);
    }
    @FXML
    private void btnLeague(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).requestFocus();
        Parent node = FXMLLoader.load(getClass().getResource("/com/FXML/League.fxml"));
        pane.getChildren().clear();
        pane.getChildren().add(node);
    }
    @FXML
    private void btnView(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).requestFocus();
        Parent node = FXMLLoader.load(getClass().getResource("/com/FXML/View.fxml"));
        pane.getChildren().clear();
        pane.getChildren().add(node);
    }
    @FXML
    public void CloseApp(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("/com/FXML/Login.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Login");
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
        try {
            Database db = new Database();
            if(LoginController.currentUser.equals("GUEST_213886")){
                topMenu.getChildren().removeAll(account,library,leagues);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
