package Arena.Client.Login;

import Arena.Client.Register.RegisterDialog;
import Arena.Server.Server;
import Arena.Shared.User;
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

        // Add buttons to the dialog.
        ButtonType registerType = new ButtonType("Register...", ButtonData.OTHER);
        ButtonType loginType = new ButtonType("Login", ButtonData.OK_DONE);

        this.getButtonTypes().add(loginType);
        this.getButtonTypes().add(registerType);
        this.getButtonTypes().add(new ButtonType("Guest login", ButtonData.OTHER));
        this.getButtonTypes().add(new ButtonType("Quit", ButtonData.CANCEL_CLOSE));

        // Add an event filter to prevent the dialog to close.
        Button registerButton = (Button) this.lookupButton(registerType);
        registerButton.addEventFilter(ActionEvent.ACTION, event -> {
            showRegisterDialog();
            event.consume();
        });

        // Don't close the dialog if the user isn't valid.
        Button loginButton = (Button) this.lookupButton(loginType);
        loginButton.addEventFilter(ActionEvent.ACTION, event -> {
            if (!isLoginValid())
                event.consume();
        });
    }

    /**
     * Show a user registration dialog and if the user successfully
     * registered a new user fill in username and password in the
     * login dialog for convenience.
     */
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

    /**
     * Checks if the user entered username and password
     * matches a user in the database.
     * @return Return true if the login is valid.
     */
    private boolean isLoginValid() {
        Server server = null;
        Optional<User> userOptional;

        try {
            server = Server.getInstance();
        } catch (Exception exception) {
            return false;
        }

        userOptional = server.getUser(username.getText());
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
