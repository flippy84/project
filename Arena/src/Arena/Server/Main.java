package Arena.Server;

import Arena.Shared.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    public static void main(String args[]) throws Exception {
        Server server = null;
        try {
            server = Server.getInstance();
        } catch (Exception exception) {
            System.out.println("Error starting server.");
        }

        Map<String, RequestHandler> handlerMap = new ConcurrentHashMap<>();
        handlerMap.put("REGISTER_USER", new RequestHandler<>(server::registerUser, User.class));

        ServerSocket serverSocket = new ServerSocket(12345);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            ClientHandler clientHandler = new ClientHandler(clientSocket, handlerMap);
            Thread thread = new Thread(clientHandler);
            thread.start();
        }

        /*RequestHandler h = handlerHashtable.get("REGISTER_USER");

        InputStream in = serialize(new User("ante", "test", UserType.Player));

        Object hej = h.handle(in);*/
    }

    public static InputStream serialize(User user) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(user);
        byte[] buf = out.toByteArray();
        ByteArrayInputStream in = new ByteArrayInputStream(buf);
        return in;

        /*ObjectInputStream ois = new ObjectInputStream(in);
        ois.reset();
        return ois;*/
    }
}