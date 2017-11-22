package Arena.Shared;

import Arena.Server.Server;
import javafx.scene.layout.Pane;

import java.util.Optional;

/*
TODO: Every game needs to inherit from this class.
TODO: A callback to save the game state after each move (if something crashes, max one move can be lost).
TODO: A stage, pane or something similar needs to be provided for the game to draw the user interface.
TODO: The game needs to support spectators (maybe use the game state saving callback).
TODO: A game chat would be neat to have where players and spectators can chat.
 */
public class Game {
    public Game() {}
    public Game(Pane pane) {}

    public void updateGameState(GameState gameState, User player1, User player2) {
        try {
            Server server = Server.getInstance();
            server.updateGameState(gameState, player1, player2);
        } catch (Exception exception) {
            return;
        }
    }

    public Optional<GameState> getGameState(User player1, User player2) {
        try {
            Server server = Server.getInstance();
            return server.getGameState(player1, player2);
        } catch (Exception exception) {
            return Optional.empty();
        }
    }
}
