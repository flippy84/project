package com.League;

import com.Login.LoginController;
import database.Database;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by tratt on 2016-12-29.
 */

public class TournamentLobbyController implements Initializable{

    private Database db;
    @FXML Pane bottomPane;
    @FXML AnchorPane mainPane;
    private final String title;
    private ArrayList<String> players = new ArrayList<>();
    private ArrayList<Label> labels = new ArrayList<>();
    private int originalSize = 0;



    public TournamentLobbyController(String title) {
        this.title = title;
        try {
            this.db = new Database();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadBracket(){
        ImageView bracket = new ImageView(new Image("/com/Images/bracket1.png"));
        bracket.relocate(0, 0);
        bracket.setOpacity(0.25);
        mainPane.getChildren().add(bracket);
        try {
            players = db.getTournamnetPlayers(title);
            originalSize = players.size();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        loadLabels();
    }
    private void reloadPlayers(Label currLabel){
        System.out.println(originalSize + " " + players.size());
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).equals(currLabel.getText())){
                System.out.println("Player " + players.get(i) + " lost.");
                players.remove(i);
            }
        }
        loadLabels();
    }
    private void loadLabels(){
        if(originalSize != 8)
            return;
        EventHandler<MouseEvent> clicked = null;
        EventHandler<MouseEvent> finalClicked = clicked;
        clicked = a -> {
            Label label = (Label) a.getSource();
            if(a.getButton() == MouseButton.PRIMARY){
                reloadPlayers(label);
            }
        };
        if(players.size() == originalSize){
            for(int i = 1; i <= 520; i+= 65){
                Label label = new Label(players.get((i/65)));
                label.setLayoutY(i + (i/65*2));
                label.setLayoutX(25);
                label.setOnMousePressed(clicked);
                label.setStyle("-fx-border-color: lightgray; -fx-background-color: white");
                mainPane.getChildren().add(label);
                labels.add(label);
            }
        }
        if(players.size() == originalSize/2){
            int pixelOffset = 65;
            for(int i = 0; i < players.size(); i++){
                Label label = new Label(players.get((i)));
                label.setLayoutY(30 + pixelOffset*i + (pixelOffset*i + i*6));
                label.setLayoutX(200);
                label.setOnMousePressed(clicked);
                label.setStyle("-fx-border-color: lightgray; -fx-background-color: white");
                mainPane.getChildren().add(label);
            }
        }
        if(players.size() == originalSize/4){

            Label newLabel1 = new Label(players.get((0)));
            newLabel1.setLayoutY(95);
            newLabel1.setLayoutX(340);
            newLabel1.setOnMousePressed(clicked);
            newLabel1.setStyle("-fx-border-color: lightgray; -fx-background-color: white");

            Label newLabel2 = new Label(players.get((1)));
            newLabel2.setLayoutY(365);
            newLabel2.setLayoutX(340);
            newLabel2.setOnMousePressed(clicked);
            newLabel2.setStyle("-fx-border-color: lightgray; -fx-background-color: white");

            mainPane.getChildren().addAll(newLabel1, newLabel2);
        }
        if(players.size() == 1){
            System.out.println(players);
            Label label = new Label(players.get((0)));
            label.setLayoutY(235);
            label.setLayoutX(525);
            label.setStyle("-fx-border-color: gold; -fx-background-color: white");

            mainPane.getChildren().addAll(label);
        }
    }
    @FXML
    private void btnApply(ActionEvent event){
        try {
            db.addPlayer(LoginController.currentUser, title);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadBracket();
    }
}
