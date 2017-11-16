package Arena.Client.MainWindow;

import Arena.Shared.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainWindow {
    private User user;

    public MainWindow(Stage stage, User user) throws Exception {
        this.user = user;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        loader.setController(this);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(String.format("Arena - Logged in as %s %s", user.userType, user.username));
        stage.show();
    }
}
