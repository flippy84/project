package Client.Login;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;

import java.io.IOException;

public class LoginDialog extends Dialog {
    private LoginDialogPane loginDialogPane;

    public LoginDialog() throws IOException {
        loginDialogPane = new LoginDialogPane();
        this.setDialogPane(loginDialogPane);
        this.setTitle("Login");
    }

    public String getUsername() {
        return loginDialogPane.getUsername();
    }

    public String getPassword() {
        return loginDialogPane.getPassword();
    }
}