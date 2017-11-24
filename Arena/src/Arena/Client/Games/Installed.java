package Arena.Client.Games;

import Arena.Shared.Game;
import Arena.Shared.User;
import Arena.Shared.UserType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class Installed implements Initializable {
    @FXML
    private Button play;
    @FXML
    private AnchorPane pane;
    @FXML
    private ListView<GameDescription> list;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Path dir = FileSystems.getDefault().getPath("C:\\Games\\");
            Stream<Path> jarFiles = Files.find(dir, 1, (path, attributes) -> {
                return path.getFileName().toString().endsWith(".jar");
            });

            jarFiles.forEach(path -> {
                String name = path.getFileName().toString();
                name = name.replaceAll(".jar", "");
                list.getItems().add(new GameDescription(name, path));
            });

        } catch (Exception exception) {
            return;
        }

        play.setOnMouseClicked(event -> {
            GameDescription gameDescription = list.getSelectionModel().getSelectedItem();
            loadGame(gameDescription);
        });
    }

    private void loadGame(GameDescription gameDescription) {
        try {
            URL url = new URL("file:" + gameDescription.location.toString());
            URL[] urls = {url};

            URLClassLoader classLoader = new URLClassLoader(urls);

            Optional<String> classNameOptional = getClassName(classLoader);
            if (!classNameOptional.isPresent())
                return;

            pane.getChildren().clear();
            String className = classNameOptional.get();

            Class<? extends Game> gameClass = classLoader.loadClass(className).asSubclass(Game.class);
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
