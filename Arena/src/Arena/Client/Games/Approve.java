package Arena.Client.Games;

import Arena.Server.Server;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Approve implements Initializable {
    @FXML
    private Button approve;
    @FXML
    private Button revoke;
    @FXML
    private TableView<GameDescription> table;
    @FXML
    private TableColumn name;
    @FXML
    private TableColumn description;
    @FXML
    private TableColumn approved;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        approved.setCellValueFactory(new PropertyValueFactory<>("approved"));

        try {
            Server server = Server.getInstance();
            List<GameDescription> gameList = server.getGameList();

            for (GameDescription game : gameList) {
                table.getItems().add(game);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        approve.setOnMouseClicked(event -> {
            GameDescription gameDescription = table.getSelectionModel().getSelectedItem();

            int index = table.getSelectionModel().getSelectedIndex();
            gameDescription.approved = true;
            table.getItems().set(index, gameDescription);

            approveGame(gameDescription.id);
        });

        revoke.setOnMouseClicked(event -> {
            GameDescription gameDescription = table.getSelectionModel().getSelectedItem();

            int index = table.getSelectionModel().getSelectedIndex();
            gameDescription.approved = false;
            table.getItems().set(index, gameDescription);

            revokeGame(gameDescription.id);
        });
    }

    private void approveGame(int id) {
        try {
            Server.getInstance().approveGame(id);
        } catch (Exception exception) {
            return;
        }
    }

    private void revokeGame(int id) {
        try {
            Server.getInstance().revokeGame(id);
        } catch (Exception exception) {
            return;
        }
    }
}
