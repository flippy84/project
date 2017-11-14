package Client.Login;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

import java.io.IOException;
import java.util.Optional;

public class Login {
    public Login() throws IOException {
        LoginDialog dialog = new LoginDialog();
        Optional<ButtonType>  result = dialog.showAndWait();
        ButtonType buttonType;

        if (result.isPresent()) {
            buttonType = result.get();
            switch (buttonType.getText()) {
                case "Register...":
                    System.out.println("Show register dialog");
                    RegisterDialog registerDialog = new RegisterDialog();
                    registerDialog.showAndWait();
                    break;
                case "Guest login":
                    System.out.println("Logging in as guest");
                    break;
                case "Login":
                    System.out.println("Logging in as user {cp}");
                    break;
            }
        }
    }
}
