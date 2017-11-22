package Arena.Client.Games;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class Library implements Initializable {
    @FXML
    private Button install;
    @FXML
    private ListView list;

    public Library() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list.getItems().add("test");
        install.setOnMouseClicked(event -> {

        });
    }
}
