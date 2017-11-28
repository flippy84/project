package Arena.Server;

import Arena.Client.Games.GameDescription;
import Arena.Server.Database.Database;
import Arena.Shared.GameState;
import Arena.Shared.User;
import Arena.Shared.UserType;

import java.io.ObjectInputStream;
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

    public boolean addUser(ObjectInputStream in) {
        try {
            User user = (User) in.readObject();
            if (database.getUser(user.username) != null)
                return false;
            return database.addUser(user);
        } catch (Exception exception) {
            return false;
        }
    }

    public User getUser(ObjectInputStream in) {
        try {
            String username = (String) in.readObject();
            return database.getUser(username);
        } catch (Exception exception) {
            return null;
        }
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

    public String downloadGame(ObjectInputStream in) {
        try {
            int id = (int) in.readObject();
            return database.downloadGame(id);
        } catch (Exception exception) {
            return "";
        }
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

    public boolean addBalance(ObjectInputStream in) {
        try {
            String username = (String) in.readObject();
            Double addedBalance = (double) in.readObject();
            return database.addBalance(username, addedBalance);
        } catch (Exception exception) {
            return false;
        }
    }

    /*public int getBalance(ObjectInputStream in) {
        try {

        } catch (Exception exception) {
            return 0;
        }
    }*/
}
