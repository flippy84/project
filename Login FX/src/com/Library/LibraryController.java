package com.Library;

import database.Database;
import games.TicTacToe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by tratt on 2016-12-20.
 */
public class LibraryController implements Initializable {

    private Database db;
    public LibraryController() throws SQLException, IOException {
        this.db = new Database();
    }

    @FXML
    private void btnTicTacToeAction(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).requestFocus();
        TicTacToe game = new TicTacToe();
        game.start(new Stage());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
