package Client.Login;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.control.ButtonBar.ButtonData;
import java.io.IOException;

public class LoginDialogPane extends DialogPane {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    public LoginDialogPane() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginDialogPane.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();

        ButtonType registerType = new ButtonType("Register...", ButtonData.OTHER);

        this.getButtonTypes().add(new ButtonType("Login", ButtonData.OK_DONE));
        this.getButtonTypes().add(registerType);
        this.getButtonTypes().add(new ButtonType("Guest login", ButtonData.OTHER));

        Button registerButton = (Button) this.lookupButton(registerType);
        registerButton.addEventFilter(ActionEvent.ACTION, event -> {
            registerUser();
            event.consume();
        });
    }

    private boolean registerUser() {
        try {
            RegisterDialog registerDialog = new RegisterDialog();
            registerDialog.showAndWait();
        } catch (Exception exception) {
            return false;
        }
        return true;
    }
}
