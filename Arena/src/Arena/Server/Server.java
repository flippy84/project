package Arena.Server;

import Arena.Server.Database.Database;
import Arena.Shared.User;
import Arena.Shared.UserType;

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
}
