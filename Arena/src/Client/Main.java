package Client;

import Client.Login.Login;
import Shared.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Optional;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        /*Parent root = FXMLLoader.load(getClass().getResource("/Client/Login/LoginDialogPane.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();*/

        Login login = new Login();
        Optional<User> user = login.login();
        user.ifPresent(u -> {
            System.out.println(String.format("Logging in as %s", u.username));
        });
    }
}
