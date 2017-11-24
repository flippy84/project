package Arena.Server;

import Arena.Client.Games.GameDescription;
import Arena.Server.Database.Database;
import Arena.Shared.GameState;
import Arena.Shared.User;
import Arena.Shared.UserType;

import java.util.List;
import java.util.Optional;

public class Server {
    private static Server serverInstance;
    private Database database;

    private Server() throws Exception {
        database = new Database();
    }

    public static Server getInstance() throws Exception {
        if (serverInstance == null)
            serverInstance = new Server();
        return serverInstance;
    }

    public boolean addUser(User user) {
        return database.addUser(user);
    }

    public void addUser(String username, String password, int rating, UserType userType) {
        database.addUser(username, password, rating, userType);
    }

    public Optional<User> getUser(String username) {
        return database.getUser(username);
    }

    public void updateGameState(GameState gameState, User player1, User player2) {
        database.saveGame(gameState, player1, player2);
    }

    public Optional<GameState> getGameState(User player1, User player2) {
        return database.loadGame(player1, player2);
    }

    public List<GameDescription> getApprovedGameList() {
        return database.getApprovedGameList();
    }

    public List<GameDescription> getGameList() {
        return database.getGameList();
    }

    public boolean uploadGame(String gameBase64, String name, String description) {
        return database.uploadGame(gameBase64, name, description);
    }

    public String downloadGame(int id) throws Exception {
        return database.downloadGame(id);
    }

    public void approveGame(int id) {
        database.approveGame(id);
    }

    public void revokeGame(int id) {
        database.revokeGame(id);
    }

    public void removeGame(int id) {
        database.removeGame(id);
    }
}
