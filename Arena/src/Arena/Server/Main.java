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
        handlerMap.put("ADD_USER", new RequestHandler(server::addUser));
        handlerMap.put("GET_USER", new RequestHandler(server::getUser));
        handlerMap.put("DOWNLOAD_GAME", new RequestHandler(server::downloadGame));
        handlerMap.put("DEPOSIT_FUNDS", new RequestHandler(server::depositFunds));
        handlerMap.put("GET_BALANCE", new RequestHandler(server::getBalance));
        handlerMap.put("WITHDRAW_FUNDS", new RequestHandler(server::withdrawFunds));

        ServerSocket serverSocket = new ServerSocket(12345);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            ClientHandler clientHandler = new ClientHandler(clientSocket, handlerMap);
            Thread thread = new Thread(clientHandler);
            thread.start();
        }
    }
}
