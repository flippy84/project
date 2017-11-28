package Arena.Client.Account;


import Arena.Client.Client;
import Arena.Client.Session;
import Arena.Server.Server;
import Arena.Shared.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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

    @FXML
    private Text text;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        updateBalance(Session.getInstance().getUser().accountBalance);

        addFunds.setOnMouseClicked(event -> {
            addFunds();
        });
    }

    private void addFunds() {
        try {
            double result = Double.parseDouble(input.getText());
            User user = Session.getInstance().getUser();
            Client.getInstance().depositFunds(user, result);
            double balance = Client.getInstance().getBalance(user);
            updateBalance(balance);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void updateBalance(double balance) {
        text.setText("Current Balance: " + balance);
    }

    private double getInput() {
        double result = Double.parseDouble(input.getText());
        return result;
    }
}

