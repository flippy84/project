package Arena.Server;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class ClientHandler implements Runnable {
    private Socket socket;
    private Map<String, RequestHandler> handlerMap;

    public ClientHandler(Socket socket, Map<String, RequestHandler> handlerMap) {
        this.socket = socket;
        this.handlerMap = handlerMap;
    }

    @Override
    public void run() {
        while (true) {
            try {
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                Object object = in.readObject();

                if (!String.class.isInstance(object))
                    continue;

                String request = (String) object;
                RequestHandler requestHandler = handlerMap.get(request);

                if (requestHandler == null)
                    continue;

                requestHandler.handle(in);
            } catch (IOException exception) {
                return;
            } catch (Exception exception) {
                continue;
            }
        }
    }
}
