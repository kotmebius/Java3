package sample.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class Controller {
    final String IP_ADRESS = "localhost";
    final int PORT = 8189;

    @FXML
    TextArea mainTextArea;
    @FXML
    TextField inputText;
    @FXML
    Button sendMsgButton;
    @FXML
    VBox body = new VBox();
    @FXML
    MenuBar menu = new MenuBar();
    @FXML
    Menu fontMenu = new Menu();
    @FXML
    MenuItem fontMinus2 = new MenuItem();
    @FXML
    MenuItem fontMinus1 = new MenuItem();
    @FXML
    MenuItem fontNormal = new MenuItem();
    @FXML
    MenuItem fontPlus1 = new MenuItem();
    @FXML
    MenuItem fontPlus2 = new MenuItem();
    @FXML
    ToggleGroup $themeToggle = new ToggleGroup();
    @FXML
    HBox bottomPanel;
    @FXML
    HBox upperPanel;
    @FXML
    TextField loginField;
    @FXML
    PasswordField passwordField;
    @FXML
    ListView<String> clientsList;

    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    private boolean isAuthorized;

    public void setAuthorized(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
        if (!isAuthorized) {
            upperPanel.setVisible(true);
            upperPanel.setManaged(true);
            bottomPanel.setVisible(false);
            bottomPanel.setManaged(false);
            clientsList.setVisible(false);
            clientsList.setManaged(false);
        } else {
            upperPanel.setVisible(false);
            upperPanel.setManaged(false);
            bottomPanel.setVisible(true);
            bottomPanel.setManaged(true);
            clientsList.setVisible(true);
            clientsList.setManaged(true);
        }
    }

    public void connect() {
        try {
            socket = new Socket(IP_ADRESS, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            setAuthorized(false);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String str = in.readUTF();
                            if (str.startsWith("/authok")) {
                                setAuthorized(true);
                                break;
                            } else {
                                mainTextArea.appendText(str + "\n");
                            }
                        }

                        while (true) {
                            String str = in.readUTF();
                            if (str.equals("/serverclosed")) {
                                //out.writeUTF("/end");
                                break;
                            }
                            if (str.startsWith("/clientslist ")) {
                                String[] tokens = str.split(" ");
                                Platform.runLater(() -> {
                                    clientsList.getItems().clear();
                                    for (int i = 1; i < tokens.length; i++) {
                                        clientsList.getItems().add(tokens[i]);
                                    }
                                });
                            } else if (str.equals("/changenickok")) {
                                mainTextArea.appendText("Нмк успешно изменён" + "\n");
                            }
                            else  {
                                mainTextArea.appendText(str + "\n");
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            in.close();
                            out.close();
                            socket.close();
                            System.out.println("Успешно отключились");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        setAuthorized(false);
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(ActionEvent actionEvent) {
        try {
            out.writeUTF(inputText.getText());
            inputText.clear();
            inputText.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tryToAuth() {
        if (socket == null || socket.isClosed()) {
            connect();
        }
        try {
            out.writeUTF("/auth " + loginField.getText() + " " + passwordField.getText());
            loginField.clear();
            passwordField.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setFontMinus2(ActionEvent actionEvent) {
        body.setStyle("-fx-font-size: 12px;");
        inputText.requestFocus();
    }

    public void setFontMinus1(ActionEvent actionEvent) {
        body.setStyle("-fx-font-size: 14px;");
        inputText.requestFocus();
    }

    public void setFontNormal(ActionEvent actionEvent) {
        body.setStyle("-fx-font-size: 16px;");
        inputText.requestFocus();
    }

    public void setFontPlus1(ActionEvent actionEvent) {
        body.setStyle("-fx-font-size: 18px;");
        inputText.requestFocus();
    }

    public void setFontPlus2(ActionEvent actionEvent) {
        body.setStyle("-fx-font-size: 20px;");
        inputText.requestFocus();
    }

    public void setLightTheme(ActionEvent actionEvent) {
        mainTextArea.getStyleClass().set(2, "lightTextArea");
        inputText.getStyleClass().set(2, "lightInputText");
        sendMsgButton.getStyleClass().set(1, "lightButton");
        menu.getStyleClass().set(1, "lightMenu");
        body.getStyleClass().set(1, "lightBody");
        inputText.requestFocus();
    }

    public void setDarkTheme(ActionEvent actionEvent) {
        mainTextArea.getStyleClass().set(2, "darkTextArea");
        inputText.getStyleClass().set(2, "darkInputText");
        sendMsgButton.getStyleClass().set(1, "darkButton");
        menu.getStyleClass().set(1, "darkMenu");
        body.getStyleClass().set(1, "darkBody");
        inputText.requestFocus();
    }

    public void setBrightTheme(ActionEvent actionEvent) {
        mainTextArea.getStyleClass().set(2, "brightTextArea");
        inputText.getStyleClass().set(2, "brightInputText");
        sendMsgButton.getStyleClass().set(1, "brightButton");
        menu.getStyleClass().set(1, "brightMenu");
        body.getStyleClass().set(1, "brightBody");
        inputText.requestFocus();
    }

}

