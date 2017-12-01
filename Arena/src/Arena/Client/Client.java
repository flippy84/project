package Arena.Client;

import Arena.Shared.Advertisement;
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
            socket.connect(new InetSocketAddress("localhost", 4444));
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

    public boolean addAdvertisement(Advertisement advertisement) {
        try {
            out.writeObject("ADD_ADVERTISEMENT");
            out.writeObject(advertisement);
            return (boolean) in.readObject();
        } catch (Exception exception) {
            return false;
        }
    }

    public Optional<Advertisement> getAdvertisement(int id) {
        try {
            out.writeObject("GET_ADVERTISEMENT");
            out.writeObject(id);
            Advertisement advertisement = (Advertisement) in.readObject();
            return Optional.ofNullable(advertisement);
        } catch (Exception exception) {
            return Optional.empty();
        }
    }
}
