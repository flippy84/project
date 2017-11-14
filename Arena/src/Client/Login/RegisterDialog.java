package Client.Login;

import javafx.scene.control.Dialog;

import java.io.IOException;

public class RegisterDialog extends Dialog {
    public RegisterDialog() throws IOException {
        RegisterDialogPane register = new RegisterDialogPane();
        this.setDialogPane(register);
        this.setTitle("Register");
    }
}
