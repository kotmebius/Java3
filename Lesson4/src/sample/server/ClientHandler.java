package sample.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class ClientHandler {
    private MainServ serv;
    private Socket socket;
    private String nick;
    private boolean isAuth = false;
    DataInputStream in;
    DataOutputStream out;
    List<String> blackList;
    ExecutorService threadService;

    public ClientHandler(MainServ serv, Socket socket) {
        try {
            this.serv = serv;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.blackList = new ArrayList<>();
            this.threadService=serv.threadService;


            threadService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String str = in.readUTF();
                            if (str.startsWith("/auth")) {
                                String[] tokens = str.split(" ");
                                String currentNick = AuthService.getNickByLoginAndPass(tokens[1], tokens[2]);
                                if (currentNick != null) {
                                    if (!serv.isNickBusy(currentNick)) {
                                        sendMsg("/authok "+currentNick);
                                        nick = currentNick;
                                        serv.subscribe(ClientHandler.this);
                                        break;
                                    } else {
                                        sendMsg("Учетная запись уже используется");
                                    }
                                } else {
                                    sendMsg("Неверный логин/пароль");
                                }
                            }
                        }
                        while (true) {
                            String str = in.readUTF();
                            if (str.startsWith("/")) {
                                if (str.equals("/end")) {
                                    out.writeUTF("/serverclosed");
                                    break;
                                }
                                if (str.startsWith("/w ")) { // /w nick3 lsdfhldf sdkfjhsdf wkerhwr
                                    String[] tokens = str.split(" ", 3);
                                    // String m = str.substring(tokens[1].length() + 4);
                                    serv.sendPersonalMsg(ClientHandler.this, tokens[1], tokens[2]);
                                }
                                if (str.startsWith("/changenick ")) { //Смена ника
                                    String[] tokens = str.split(" ", 2);
                                    if (!serv.isNickBusy(tokens[1])) {
                                        serv.unsubscribe(ClientHandler.this);
                                        AuthService.changeNick(getNick(),tokens[1]);
                                        nick=tokens[1];
                                        sendMsg("/changenickok");
                                        serv.subscribe(ClientHandler.this);
                                        //break;
                                    } else {
                                        sendMsg("Учетная запись уже используется");
                                    }
                                }






                                if (str.startsWith("/blacklist ")) { // /blacklist nick3
                                    String[] tokens = str.split(" ");
                                    if ((!tokens[1].equals(getNick())) && (serv.isNickBusy(tokens[1]))) {
                                        if (AuthService.addToBlacList(getNick(), tokens[1])) {
                                            sendMsg("Вы добавили пользователя " + tokens[1] + " в черный список");
                                        } else {
                                            sendMsg("Пользователь " + tokens[1] + " уже в Вашем чёрном списке");
                                        }

                                    } else if (tokens[1].equals(getNick())) {
                                        sendMsg("Вы не можете добавить самого себя в черный список");
                                    } else {
                                        sendMsg("Пользователя " + tokens[1] + " не существует");
                                    }

                                }
                            } else {
                                serv.broadcastMsg(ClientHandler.this, nick + ": " + str);
                            }
                            System.out.println("Client: " + str);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        serv.unsubscribe(ClientHandler.this);
                    }

                }
            });

        } catch (
                IOException e) {
            e.printStackTrace();
        }

    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNick() {
        return nick;
    }
}
