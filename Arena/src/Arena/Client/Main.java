package Arena.Client;

import Arena.Client.Login.Login;
import Arena.Shared.User;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Optional;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        /*Parent root = FXMLLoader.load(getClass().getResource("/Arena.Client/Login/LoginDialogPane.fxml"));
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
