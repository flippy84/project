package Server;

import Server.Database.Database;
import Shared.User;

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

    public void addUser(String username, String password) {

    }

    public Optional<User> getUser(String username) {
        return database.getUser(username);
    }
}
