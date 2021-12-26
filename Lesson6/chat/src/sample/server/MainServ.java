package sample.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MainServ {
    private Vector<sample.server.ClientHandler> clients;
    ExecutorService threadService = Executors.newCachedThreadPool();
    private static final Logger LOGGER = LogManager.getLogger(MainServ.class);

    public MainServ() {
        clients = new Vector<>();
        ServerSocket server = null;
        Socket socket = null;

        try {
            AuthService.connect();
//            AuthService.addUser("login1", "pass1", "nick1");
//            AuthService.addUser("login2", "pass2", "nick2");
//            AuthService.addUser("login3", "pass3", "nick3");

            server = new ServerSocket(8189);
            LOGGER.info("Сервер запущен!");
            while (true) {
                socket = server.accept();
                LOGGER.info("Клиент подключился!");
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        } finally {
            try {
                socket.close();
                LOGGER.info("Клиент отключился");
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
            try {
                server.close();
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
            AuthService.disconnect();
            threadService.shutdownNow();
        }
    }

    public void subscribe(ClientHandler client) {
        clients.add(client);
        broadcastClientsList();
    }

    public void unsubscribe(ClientHandler client) {
        clients.remove(client);
        broadcastClientsList();
    }

    public void broadcastMsg(ClientHandler from, String msg) {
        for (ClientHandler o : clients) {
            if (!AuthService.checkInBlacList(o.getNick(),from.getNick())) {
                o.sendMsg(msg);
            }
        }
    }

    public boolean isNickBusy(String nick) {
        for (ClientHandler o : clients) {
            if (o.getNick().equals(nick)) {
                return true;
            }
        }
        return false;
    }

    public void sendPersonalMsg(ClientHandler from, String nickTo, String msg) {
        for (ClientHandler o : clients) {
            if (o.getNick().equals(nickTo)) {
                o.sendMsg("from " + from.getNick() + ": " + msg);
                from.sendMsg("to " + nickTo + ": " + msg);
                return;
            }
        }
        from.sendMsg("Клиент с ником " + nickTo + " не найден в чате");
    }

    public void broadcastClientsList() {
        StringBuilder sb = new StringBuilder();
        sb.append("/clientslist ");
        for (ClientHandler o : clients) {
            sb.append(o.getNick() + " ");
        }
        String out = sb.toString();
        for (ClientHandler o : clients) {
            o.sendMsg(out);
        }
    }
}
