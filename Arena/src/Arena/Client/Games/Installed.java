package Arena.Client.Games;

import Arena.Shared.Game;
import Arena.Shared.User;
import Arena.Shared.UserType;
import com.sun.javafx.scene.traversal.SubSceneTraversalEngine;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Optional;
import java.util.ResourceBundle;

public class Installed implements Initializable {
    @FXML
    private Button play;
    @FXML
    private AnchorPane pane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        play.setOnMouseClicked(event -> {
            loadGame();
        });
    }

    private void loadGame() {
        System.out.println("play");

        try {
            URL url = new URL("file:C:\\Games\\sample.jar");
            URL[] urls = {url};

            URLClassLoader classLoader = new URLClassLoader(urls);

            Optional<String> classNameOptional = getClassName(classLoader);
            if (!classNameOptional.isPresent())
                return;

            pane.getChildren().clear();

            Class<? extends Game> gameClass = classLoader.loadClass("Sample").asSubclass(Game.class);
            Constructor<? extends Game> gameConstructor = gameClass.getConstructor(Pane.class, User.class, User.class);
            Game game = gameConstructor.newInstance(pane, new User("player1", "test", UserType.Player), new User("player2", "test", UserType.Player));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private Optional<String> getClassName(URLClassLoader classLoader) {
        try {
            Class export = classLoader.loadClass("Export");
            Method getClassName = export.getMethod("getClassName");
            return Optional.of((String) getClassName.invoke(export.newInstance()));
        } catch (Exception exception) {
            return Optional.empty();
        }
    }
}
