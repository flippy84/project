package Client.Account;
import Server.Database.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by tratt on 2016-12-20.
 */
public class AccountController implements Initializable {

    private Database db;
    public AccountController() throws SQLException, IOException {
        this.db = new Database();

    }

    @FXML
    private void btnChangeUserEvent(ActionEvent event) throws IOException, SQLException{
        Parent parent = FXMLLoader.load(getClass().getResource("/Client/FXML/ChangeUsername.fxml"));
        ((Node) (event.getSource())).requestFocus();
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Change Username");
        stage.show();

    }
    @FXML
    private void btnChangePasswordEvent(ActionEvent event) throws IOException, SQLException{
        Parent parent = FXMLLoader.load(getClass().getResource("/com./FXML/ChangePassword.fxml"));
        ((Node) (event.getSource())).requestFocus();
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Change Password");
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
