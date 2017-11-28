package Arena.Client.Account;


import Arena.Server.Server;
import Arena.Shared.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AccountBalance implements Initializable {

    @FXML
    private Button addFunds;

    @FXML
    private TextField input;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            Server server = Server.getInstance();

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        addFunds.setOnMouseClicked(event -> {
            double result = getInput();
            System.out.print(result);
        });
    }

    public void addFunds() {
        //Ta in currentBalance och lägg till önskad mängd funds
    }

    private double getInput() {
        double result = Double.parseDouble(input.getText());
        return result;
    }
}

