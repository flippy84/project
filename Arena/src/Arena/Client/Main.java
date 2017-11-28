package Arena.Client;

import Arena.Client.Login.Login;
import Arena.Client.MainWindow.MainWindow;
import Arena.Shared.User;
import Arena.Shared.UserType;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
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

        Session.getInstance().setUser(user.get());
        new MainWindow(stage, user.get());
    }
}
