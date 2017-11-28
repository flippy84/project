package Arena.Client;

import Arena.Shared.User;
import Arena.Shared.UserType;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Optional;

public class Client {
    private static Client clientInstance;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public static Client getInstance() {
        if (clientInstance == null)
            clientInstance = new Client();
        return clientInstance;
    }

    private Client() {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress("localhost", 12345));
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (Exception exception) {
            clientInstance = null;
        }
    }

    public Optional<String> downloadGame(int id) {
        String game = null;

        try {
            out.writeObject("DOWNLOAD_GAME");
            out.writeObject(id);
            game = (String) in.readObject();

            if (game.equals(""))
                return Optional.empty();

            return Optional.of(game);
        } catch (Exception exception) {
            return Optional.empty();
        }
    }

    public Optional<User> getUser(String username) {
        try {
            out.writeObject("GET_USER");
            out.writeObject(username);
            User user = (User) in.readObject();
            return Optional.ofNullable(user);
        } catch (Exception exception) {
            return Optional.empty();
        }
    }

    public boolean addUser(User user) {
        try {
            out.writeObject("ADD_USER");
            out.writeObject(user);
            return (boolean) in.readObject();
        } catch (Exception exception) {
            return false;
        }
    }
}
