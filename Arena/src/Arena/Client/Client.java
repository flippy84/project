package Arena.Client;

import Arena.Shared.User;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

    private void writeObjects(Object... objects) throws Exception {
        for (Object object : objects)
            out.writeObject(object);
    }

    public Optional<String> downloadGame(int id) {
        try {
            writeObjects("DOWNLOAD_GAME", id);
            String game = (String) in.readObject();

            if (game.equals(""))
                return Optional.empty();

            return Optional.of(game);
        } catch (Exception exception) {
            return Optional.empty();
        }
    }

    public Optional<User> getUser(String username) {
        try {
            writeObjects("GET_USER", username);
            User user = (User) in.readObject();
            return Optional.ofNullable(user);
        } catch (Exception exception) {
            return Optional.empty();
        }
    }

    public boolean addUser(User user) {
        try {
            writeObjects("ADD_USER", user);
            return (boolean) in.readObject();
        } catch (Exception exception) {
            return false;
        }
    }

    public boolean depositFunds(User user, Double deposit) {
        try {
            writeObjects("DEPOSIT_FUNDS", user.username, deposit);
            return (boolean) in.readObject();
        } catch (Exception exception) {
            return false;
        }
    }

    public boolean withdrawFunds(User user, Double withdrawal) {
        try {
            writeObjects("WITHDRAW_FUNDS", user.username, withdrawal);
            return (boolean) in.readObject();
        } catch (Exception exception) {
            return false;
        }
    }

    public double getBalance(User user) {
        try {
            writeObjects("GET_BALANCE", user.username);
            return (double) in.readObject();
        } catch (Exception exception) {
            return 0;
        }
    }
}
