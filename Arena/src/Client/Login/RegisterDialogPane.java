package Client.Login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;

public class RegisterDialogPane extends DialogPane {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password1;
    @FXML
    private PasswordField password2;
    @FXML
    private ChoiceBox<String> accountType;

    public RegisterDialogPane() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterDialogPane.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();

        ButtonType registerType = new ButtonType("Register", ButtonBar.ButtonData.OK_DONE);

        this.getButtonTypes().add(registerType);
        this.getButtonTypes().add(new ButtonType("Cancel", ButtonBar.ButtonData.OTHER));

        Button registerButton = (Button) this.lookupButton(registerType);
        registerButton.addEventFilter(ActionEvent.ACTION, event -> {
            if (!validateForm())
                event.consume();
        });
    }

    private boolean validateForm() {
        if (username.getText().length() == 0)
            return false;

        if (password1.getText().length() == 0)
            return false;

        if (!password1.getText().equals(password2.getText()))
            return false;

        if (userExists(username.getText()))
            return false;

        return true;
    }

    private boolean userExists(String username) {
        return false;
    }
}
