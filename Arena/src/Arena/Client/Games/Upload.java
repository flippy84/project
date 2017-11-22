package Arena.Client.Games;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class Upload implements Initializable {
    @FXML
    private Button upload;

    public Upload() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        upload.setOnMouseClicked(event -> {
            System.out.println("upload file");
        });
    }
}
