package Arena.Client.Register;

import Arena.Client.Client;
import Arena.Server.Server;
import Arena.Shared.User;
import Arena.Shared.UserType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.util.Optional;

public class RegisterDialogPane extends DialogPane {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password1;
    @FXML
    private PasswordField password2;
    @FXML
    private ChoiceBox<UserType> userType;

    private Client client;

    public RegisterDialogPane() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterDialogPane.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
        // Get the server instance for adding an user.
        client = Client.getInstance();

        // Add items to the userType ChoiceBox.
        ObservableList<UserType> userTypeList = FXCollections.observableArrayList();
        for(UserType t : UserType.values()) {
            userTypeList.add(t);
        }
        userType.setItems(userTypeList);
        userType.getSelectionModel().selectFirst();

        // Add buttons to the dialog.
        ButtonType registerType = new ButtonType("Register", ButtonBar.ButtonData.OK_DONE);

        this.getButtonTypes().add(registerType);
        this.getButtonTypes().add(new ButtonType("Cancel", ButtonBar.ButtonData.OTHER));

        // Get the register button and add an event filter for form validation.
        Button registerButton = (Button) this.lookupButton(registerType);
        registerButton.addEventFilter(ActionEvent.ACTION, event -> {
            if (validForm()) {
                registerUser(new User(username.getText(), password1.getText(), userType.getValue()));
            } else {
                event.consume();
            }
        });
    }

    public String getUsername() {
        return username.getText();
    }

    public String getPassword() {
        return password1.getText();
    }

    public UserType getUserType() {
        return userType.getSelectionModel().getSelectedItem();
    }

    /**
     * Validate the form and check that the user doesn't already exists
     * and check that both passwords are equal.
     * @return Returns true if the form is valid.
     */
    private boolean validForm() {
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

    private void registerUser(User user) {
        client.addUser(user);
    }

    /**
     * Check that the user doesn't exists.
     * @param username The username of the user to check.
     * @return Returns true if the user exists.
     */
    private boolean userExists(String username) {
        Optional<User> user = client.getUser(username);
        if (user.isPresent())
            return true;
        return false;
    }
}
