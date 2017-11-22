import Arena.Shared.Game;
import Arena.Shared.GameState;
import Arena.Shared.User;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Sample extends Game {
    private User player1;
    private User player2;
    private SampleGameState state;

    public Sample(Pane pane, User player1, User player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.state = new SampleGameState();

        Button b1 = new Button("4");
        Button b2 = new Button("5");

        EventHandler<MouseEvent> handler = event -> {
            Button source = (Button) event.getSource();
            int number = Integer.decode(source.getText());
            source.setText(Integer.toString(number - 1));

            state.num1 = Integer.decode(b1.getText());
            state.num2 = Integer.decode(b2.getText());
            updateGameState(state, player1, player2);
        };

        b1.setOnMouseClicked(handler);
        b2.setOnMouseClicked(handler);

        HBox box = new HBox();
        box.getChildren().addAll(b1, b2);
        pane.getChildren().add(box);
    }

}
