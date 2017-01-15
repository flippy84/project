package com.Account;


import com.Login.LoginController;
import database.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by tratt on 2016-12-20.
 */
public class dbChange{

    private Database db;

    @FXML
    private TextField changeUsername;
    @FXML
    private TextField changePassword;

    public dbChange() throws SQLException {
        this.db = new Database();
    }

    @FXML
    private void btnConfirmUsername(ActionEvent event) throws IOException, SQLException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        System.out.print(LoginController.currentUser);
        db.changeUsername(changeUsername.getText(), LoginController.currentUser);
        LoginController.currentUser = changeUsername.getText();
    }
    @FXML
    private void btnConfirmPassword(ActionEvent event) throws IOException, SQLException{
        ((Node) (event.getSource())).getScene().getWindow().hide();
        System.out.print(LoginController.currentPassword);
        db.changePassword(changePassword.getText(), LoginController.currentUser);
        LoginController.currentPassword = changePassword.getText();
    }
}
