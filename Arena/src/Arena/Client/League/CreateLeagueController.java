package Arena.Client.League;

import Arena.Server.Database.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by tratt on 2016-12-29.
 */
public class CreateLeagueController implements Initializable {

    private Database db;

    @FXML private ComboBox<String> game;
    @FXML private TextField leagueName;

    @FXML private Label lblMessage;


    public CreateLeagueController() throws IOException, SQLException {
        this.db = new Database();
    }

    @FXML
    private void btnCreate(ActionEvent event) throws IOException, SQLException {

        if(leagueName.getText().length() > 0 && game.getValue() != null){
            System.out.println(leagueName.getText() + " " + game.getValue());
            db.createLeague(leagueName.getText(), game.getValue());

            ((Node) (event.getSource())).getScene().getWindow().hide();
        }else{
            lblMessage.setText("Don't leave shit blank bro");
        }
    }
    private ObservableList<String> getGames(){
        return FXCollections.observableArrayList("TicTacToe");
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> entryOptions = FXCollections.observableArrayList("8","16","32","64","128");
        game.setItems(getGames());
    }
}
