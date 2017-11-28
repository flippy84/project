package Arena.Client.Games;

import Arena.Server.Server;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;

public class Library implements Initializable {
    @FXML
    private Button install;
    @FXML
    private TableView<GameDescription> table;
    @FXML
    private TableColumn name;
    @FXML
    private TableColumn description;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));

        try {
            Server server = Server.getInstance();
            List<GameDescription> gameList = server.getApprovedGameList();

            for (GameDescription game : gameList) {
                table.getItems().add(game);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        install.setOnMouseClicked(event -> {
            GameDescription gameDescription = table.getSelectionModel().getSelectedItem();
            installGame(gameDescription);
        });
    }

    private void installGame(GameDescription game) {
        try {
            Server server = Server.getInstance();
            String gameBase64 = server.downloadGame(game.id);

            byte[] gameBytes = Base64.getDecoder().decode(gameBase64);
            Path path = FileSystems.getDefault().getPath(new File(".").getCanonicalPath() + "/Games/" + game.name + ".jar");
            Files.createDirectories(path.getParent());
            ByteArrayInputStream in = new ByteArrayInputStream(gameBytes);
            Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception exception) {
            return;
        }
    }
}
