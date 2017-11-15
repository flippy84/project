package Client.Login;

import Server.Server;
import Shared.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;

import java.io.IOException;
import java.util.Optional;

public class LoginDialogPane extends DialogPane {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    private User user;

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
            showRegisterDialog();
            event.consume();
        });

        Button loginButton = (Button) this.lookupButton(loginType);
        loginButton.addEventFilter(ActionEvent.ACTION, event -> {
            if (!isUserValid())
                event.consume();
        });
    }

    private void showRegisterDialog() {
        Optional<ButtonType> result;
        RegisterDialog registerDialog;

        try {
            registerDialog = new RegisterDialog();
            result = registerDialog.showAndWait();
        } catch (Exception exception) {
            return;
        }

        if (!result.isPresent())
            return;

        if (!result.get().getText().equals("Register"))
            return;

        username.setText(registerDialog.getUsername());
        password.setText(registerDialog.getPassword());
    }

    private boolean isUserValid() {
        Server server = null;
        Optional<User> userOptional;

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

    public User getUser() {
        return user;
    }
}
