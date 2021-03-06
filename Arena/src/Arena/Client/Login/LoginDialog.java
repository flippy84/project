package Arena.Client.Login;

import Arena.Shared.User;
import javafx.scene.control.Dialog;

import java.io.IOException;

public class LoginDialog extends Dialog {
    private LoginDialogPane loginDialogPane;

    public LoginDialog() throws IOException {
        loginDialogPane = new LoginDialogPane();
        this.setDialogPane(loginDialogPane);
        this.setTitle("Login");
    }

    public User getUser() {
        return loginDialogPane.getUser();
    }
}