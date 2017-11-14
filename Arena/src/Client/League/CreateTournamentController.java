package Client.League;

import Server.Database.Database;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by tratt on 2016-12-29.
 */
public class CreateTournamentController implements Initializable {

    private Database db;

    @FXML
    private ComboBox<String> league;
    @FXML private TextField tourneyName;

    @FXML private Label lblMessage;


    public CreateTournamentController() throws IOException, SQLException {
        this.db = new Database();
    }

    @FXML
    private void btnCreate(ActionEvent event) throws IOException, SQLException {
        ((Node) (event.getSource())).requestFocus();


        if(tourneyName.getText().length() > 0 && league.getValue() != null){
            System.out.println(tourneyName.getText() + " " + league.getValue());
            db.createTournament(tourneyName.getText(), league.getValue());

            ((Node) (event.getSource())).getScene().getWindow().hide();
        }else{
            lblMessage.setText("Don't leave shit blank bro");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<String> leagues = new ArrayList<>();
        try {
            leagues = db.getLeagues();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(leagues);
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(leagues);
        league.setItems(items);
    }
}
