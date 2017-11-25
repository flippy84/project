package Arena.Server;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class ClientHandler implements Runnable {
    private Socket socket;
    private Map<String, Handler> handlerMap;

    public ClientHandler(Socket socket, Map<String, Handler> handlerMap) {
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
                Handler handler = handlerMap.get(request);

                if (handler == null)
                    continue;

                handler.handle(in);
            } catch (IOException exception) {
                return;
            } catch (Exception exception) {
                continue;
            }
        }
    }
}
