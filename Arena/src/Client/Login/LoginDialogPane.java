package Client.Login;

import Shared.User;
import Server.*;
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
import java.util.Optional;

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
        ButtonType loginType = new ButtonType("Login", ButtonData.OK_DONE);

        this.getButtonTypes().add(loginType);
        this.getButtonTypes().add(registerType);
        this.getButtonTypes().add(new ButtonType("Guest login", ButtonData.OTHER));
        this.getButtonTypes().add(new ButtonType("Quit", ButtonData.CANCEL_CLOSE));

        Button registerButton = (Button) this.lookupButton(registerType);
        registerButton.addEventFilter(ActionEvent.ACTION, event -> {
            registerUser();
            event.consume();
        });

        Button loginButton = (Button) this.lookupButton(loginType);
        loginButton.addEventFilter(ActionEvent.ACTION, event -> {
            if (!isUserValid())
                event.consume();
        });
    }

    public String getUsername() {
        return username.getText();
    }

    public String getPassword() {
        return password.getText();
    }

    private Optional<User> registerUser() {
        User user = null;

        try {
            RegisterDialog registerDialog = new RegisterDialog();
            registerDialog.showAndWait();
        } catch (Exception exception) {
            return Optional.empty();
        }

        return Optional.ofNullable(user);
    }

    private boolean isUserValid() {
        Server server = null;
        Optional<User> userOptional;
        User user;

        try {
            server = Server.getInstance();
        } catch (Exception exception) {
            return false;
        }

        userOptional  = server.getUser(username.getText());
        if (!userOptional.isPresent())
            return false;

        user = userOptional.get();
        if (!user.password.equals(password.getText()))
            return false;

        return true;
    }
}
