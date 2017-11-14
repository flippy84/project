package Client.Login;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;

import java.io.IOException;

public class LoginDialog extends Dialog {
    public LoginDialog() throws IOException {
        LoginDialogPane login = new LoginDialogPane();
        this.setDialogPane(login);
        this.setTitle("Login");
    }
}