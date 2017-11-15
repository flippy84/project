package Client.Login;

import Server.*;
import Shared.User;
import Shared.UserType;
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

    private Server server;

    public RegisterDialogPane() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterDialogPane.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
        server = Server.getInstance();


        ObservableList<UserType> userTypeList = FXCollections.observableArrayList();
        for(UserType t : UserType.values()) {
            userTypeList.add(t);
        }
        userType.setItems(userTypeList);
        userType.getSelectionModel().selectFirst();

        ButtonType registerType = new ButtonType("Register", ButtonBar.ButtonData.OK_DONE);

        this.getButtonTypes().add(registerType);
        this.getButtonTypes().add(new ButtonType("Cancel", ButtonBar.ButtonData.OTHER));

        Button registerButton = (Button) this.lookupButton(registerType);
        registerButton.addEventFilter(ActionEvent.ACTION, event -> {
            if (validForm()) {
                registerUser(new User(username.getText(), password1.getText(), 0, userType.getValue()));
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
        server.addUser(user);
    }

    private boolean userExists(String username) {
        Optional<User> user = server.getUser(username);
        if (user.isPresent())
            return true;
        return false;
    }
}
