package Arena.Server;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class ClientHandler implements Runnable {
    private Socket socket;
    private Map<String, RequestHandler> handlerMap;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ClientHandler(Socket socket, Map<String, RequestHandler> handlerMap) throws Exception {
        this.socket = socket;
        this.handlerMap = handlerMap;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        while (true) {
            try {
                Object object = in.readObject();

                if (!String.class.isInstance(object))
                    continue;

                String request = (String) object;
                RequestHandler requestHandler = handlerMap.get(request);

                if (requestHandler == null)
                    continue;

                out.writeObject(requestHandler.handle(in));
            } catch (IOException exception) {
                return;
            } catch (Exception exception) {
                continue;
            }
        }
    }
}
