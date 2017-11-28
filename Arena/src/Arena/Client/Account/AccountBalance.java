package Arena.Client.Account;


import Arena.Client.Client;
import Arena.Client.Session;
import Arena.Server.Server;
import Arena.Shared.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.swing.text.html.Option;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AccountBalance implements Initializable {

    @FXML
    private Button addFunds;

    @FXML
    private TextField input;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        addFunds.setOnMouseClicked(event -> {
            addFunds();
        });
    }

    public void addFunds() {
        try {
            double result = Double.parseDouble(input.getText());
            User user = Session.getInstance().getUser();
            Client.getInstance().depositFunds(user, result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private double getInput() {
        double result = Double.parseDouble(input.getText());
        return result;
    }
}

