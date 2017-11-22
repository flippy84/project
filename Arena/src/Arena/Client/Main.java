package Arena.Client;

import Arena.Client.Login.Login;
import Arena.Client.MainWindow.MainWindow;
import Arena.Shared.Game;
import Arena.Shared.User;
import Arena.Shared.UserType;
import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Optional;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Login login = new Login();
        Optional<User> user = login.login();
        if (!user.isPresent())
            return;

        user.ifPresent(u -> {
            System.out.println(String.format("Logging in as %s", u.username));
        });

        new MainWindow(stage, user.get());
    }
}
