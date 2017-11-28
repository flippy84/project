package Arena.Client.Login;

import Arena.Shared.User;
import Arena.Shared.UserType;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * This class is used for logging in a user, it returns the
 * logged in user as a non empty optional if the login succeeded.
 */
public class Login {
    public Optional<User> login() {
        ButtonType buttonType;
        LoginDialog dialog;
        Optional result;
        User user = null;

        try {
            dialog = new LoginDialog();
        } catch (Exception exception) {
            return Optional.empty();
        }

        result = dialog.showAndWait();
        if (!result.isPresent())
            return Optional.empty();

        buttonType = (ButtonType) result.get();
        switch (buttonType.getText()) {
            case "Guest login":
                user = new User("guest", "guest", UserType.Spectator);
                break;
            case "Login":
                user = dialog.getUser();
                break;
        }

        return Optional.ofNullable(user);
    }
}
