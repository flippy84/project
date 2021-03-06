package Arena.Client.Games;

import Arena.Server.Server;
import Arena.Shared.Utility;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class Upload implements Initializable {
    @FXML
    private Button upload;
    @FXML
    private Button select;
    @FXML
    private TextField path;
    @FXML
    private TextField name;
    @FXML
    private TextArea description;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        upload.setOnMouseClicked(event -> {
            uploadGame();
        });

        select.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(upload.getScene().getWindow());
            if (file == null)
                return;
            path.setText(file.getPath());
        });
    }

    private void uploadGame() {
        String path = this.path.getText();
        String name = this.name.getText();
        String description = this.description.getText();
        if (path.length() == 0 | name.length() == 0 || description.length() == 0)
            return;

        File file = new File(path);
        try {
            Server server = Server.getInstance();
            FileInputStream in = new FileInputStream(file);
            String gameBase64 = Utility.getBase64String(in);
            server.uploadGame(gameBase64, name, description);
        } catch (Exception exception) {
            return;
        }
    }
}
