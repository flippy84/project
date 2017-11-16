package Arena.Client.Ads;

import Arena.Server.Database.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Ante on 2017-01-25.
 */
public class AdsController implements Initializable {

    private Database db;

    @FXML
    private Button btnPrev;
    @FXML
    private Button btnNext;
    @FXML
    private ImageView image;
    @FXML
    private Button btnUpload;

    public AdsController() {
        try {
            db = new Database();
        }
        catch(SQLException e) {
            System.out.println("SQL error in AdsController.");
        }
    }

    @FXML
    private void prev(ActionEvent a) {
        System.out.println("Previous");
    }

    @FXML
    private void next(ActionEvent a) {
        System.out.println("Next");
    }

    @FXML
    private void upload(ActionEvent a) {
        System.out.println("Upload");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
