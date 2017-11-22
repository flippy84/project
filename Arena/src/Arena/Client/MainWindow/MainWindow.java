package Arena.Client.MainWindow;

import Arena.Shared.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Optional;

public class MainWindow {
    private User user;

    @FXML
    private Button upload, library, installed;
    @FXML
    private Pane view;

    public MainWindow(Stage stage, User user) throws Exception {
        this.user = user;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        loader.setController(this);
        Parent root = loader.load();

        // Add all handlers for switching views.
        upload.setOnMouseClicked(event -> {
            LoadFXML("/Arena/Client/Games/Upload.fxml").ifPresent(upload -> {
                updateView(upload);
            });
        });

        library.setOnMouseClicked(event -> {
            LoadFXML("/Arena/Client/Games/Library.fxml").ifPresent(libary -> {
                updateView(libary);
            });
        });

        installed.setOnMouseClicked(event -> {
            LoadFXML("/Arena/Client/Games/Installed.fxml").ifPresent(installed -> {
                updateView(installed);
            });
        });

        /*try {
            Server.getInstance().uploadGame();
        } catch (Exception exception) {
            System.out.println("Error uploading game");
        }*/

        /*try {
            Server.getInstance().downloadGame();
        } catch (Exception exception) {
            System.out.println("Error uploading game");
        }*/

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(String.format("Arena - Logged in as %s %s", user.userType, user.username));
        stage.show();
    }

    private Optional<Parent> LoadFXML(String fxml) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        try {
            return Optional.of(loader.load());
        } catch (Exception exception) {
            return Optional.empty();
        }
    }

    private void updateView(Parent newView) {
        view.getChildren().clear();
        view.getChildren().add(newView);
    }
}
