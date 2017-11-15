package Client.Login;

import Shared.UserType;
import javafx.scene.control.Dialog;

public class RegisterDialog extends Dialog {
    private RegisterDialogPane registerDialogPane;

    public RegisterDialog() throws Exception {
        registerDialogPane = new RegisterDialogPane();
        this.setDialogPane(registerDialogPane);
        this.setTitle("Register");
    }

    public String getUsername() {
        return registerDialogPane.getUsername();
    }

    public String getPassword() {
        return registerDialogPane.getPassword();
    }

    public UserType getUserType() {
        return registerDialogPane.getUserType();
    }
}
