package com.League;
import com.Login.LoginController;
import database.Database;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.awt.*;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by tratt on 2016-12-27.
 */
public class LeagueController implements Initializable {

    private Database db;

    @FXML
    Button createLeague;

    @FXML
    Button createTournament;

    @FXML
    Pane leaguePane;

    private ListView<String> leagueList = new ListView<>();
    private ListView<String> tourneyList = new ListView<>();

    public LeagueController() throws SQLException, IOException {
        this.db = new Database();
    }


    private void gameList() throws SQLException{

        ListView<String> list = new ListView<>();

        ObservableList<String> items = FXCollections.observableArrayList ();
        list.setItems(items);
        list.setLayoutX(60);
        list.setLayoutY(0);
        list.setMaxWidth(100);
        items.add("TicTacToe");
        list.setPrefHeight(items.size() * 24 + 1);

        Label gameLabel = new Label("Games");
        gameLabel.setLayoutX(75);
        gameLabel.setLayoutY(-25);
        leaguePane.getChildren().addAll(list,gameLabel);

        leagueList(list);
    }

    private void leagueList(ListView<String> list){

        list.setOnMouseClicked(e -> {

            ObservableList<String> leagueItems = FXCollections.observableArrayList ();
            leagueList.setItems(leagueItems);
            leagueList.setLayoutX(180);
            leagueList.setLayoutY(0);
            leagueList.setMaxWidth(100);

            try {
                leaguePane.getChildren().remove(leagueList);
                for(int i = 0; i < db.getLeagues().size();i++){
                    leagueItems.add(db.getLeagues().get(i));
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }


            Label leagueLabel = new Label("Leagues");
            leagueLabel.setLayoutX(190);
            leagueLabel.setLayoutY(-25);

            leagueList.setPrefHeight(leagueItems.size() * 24 + 1);
            leaguePane.getChildren().addAll(leagueList, leagueLabel);

            tournamentList();
        });
    }

    private void tournamentList(){

        leagueList.setOnMousePressed(e2 -> {
            String selectedLeague = leagueList.selectionModelProperty().getValue().getSelectedItem();
            ObservableList<String> tourneyItems = FXCollections.observableArrayList ();

            tourneyList.setItems(tourneyItems);
            tourneyList.setLayoutX(295);
            tourneyList.setLayoutY(0);
            tourneyList.setMaxWidth(150);
            try {
                leaguePane.getChildren().remove(tourneyList);
                for(int i = 0; i < db.getTournament(selectedLeague).size();i++){
                    tourneyItems.add(db.getTournament(selectedLeague).get(i));
                }
            } catch (SQLException e1) { e1.printStackTrace(); }

            Label tourneyLabel = new Label("Tournaments");
            tourneyLabel.setLayoutX(305);
            tourneyLabel.setLayoutY(-25);

            tourneyList.setPrefHeight(tourneyItems.size() * 24 + 1);
            leaguePane.getChildren().addAll(tourneyList, tourneyLabel);
            btnTournamentLobby();
        });


    }

    private void btnTournamentLobby() {

        tourneyList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if(mouseEvent.getClickCount() == 2){
                        try {
                            String title = tourneyList.selectionModelProperty().getValue().getSelectedItem();
                            Stage stage = new Stage();
                            stage.setTitle(title);

                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/FXML/TournamentLobby.fxml"));
                            loader.setControllerFactory(c -> new TournamentLobbyController(title));
                            Pane pane = loader.load();

                            Scene scene = new Scene(pane);
                            stage.setScene(scene);
                            stage.setResizable(false);
                            stage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }
    @FXML
    private void btnCreateLeague(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).requestFocus();
        Parent parent = FXMLLoader.load(getClass().getResource("/com/FXML/CreateLeague.fxml"));
        Stage stage = new Stage();

        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Create League");
        stage.show();
    }
    @FXML
    private void btnCreateTournament(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).requestFocus();
        Parent parent = FXMLLoader.load(getClass().getResource("/com/FXML/CreateTournament.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Create Tournament");
        stage.show();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources)  {
        assert leaguePane != null : "fx:id=\"pane\" was not injected: check your FXML file 'League.fxml'.";
        try {
            gameList();
            if(!db.getUserType(LoginController.currentUser).equals("LeagueOwner")){
                leaguePane.getChildren().removeAll(createLeague,createTournament);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
