package Client.Login;

import Shared.User;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Login {
    public Optional<User> login() {
        ButtonType buttonType;
        LoginDialog dialog;
        Optional<ButtonType> result;
        User user = null;

        try {
            dialog = new LoginDialog();
        } catch (Exception exception) {
            return Optional.empty();
        }

        result = dialog.showAndWait();
        if (!result.isPresent())
            return Optional.empty();

        buttonType = result.get();
        switch (buttonType.getText()) {
            case "Register...":
                System.out.println("Show register dialog");
                //RegisterDialog registerDialog = new RegisterDialog();
                //registerDialog.showAndWait();
                break;
            case "Guest login":
                user = new User("guest", "guest");
                break;
            case "Login":
                user = new User(dialog.getUsername(), dialog.getPassword());
                break;
        }

        return Optional.ofNullable(user);
    }
}
