package sample.client;

import javafx.scene.control.TextArea;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Log {
    private String myNick;
    private FileWriter logFile;

    //Конструктор открытия файла лога
    public Log(String myNick) {
        this.myNick = myNick;
        try {
            logFile = new FileWriter("History_" + myNick + ".txt", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addStringToHistory(String newStr) {
        try {
            logFile.write(newStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void flushBuf() {
        try {
            logFile.flush();
            logFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadHistory(TextArea mainTextArea, String myNick) {
        ArrayList<String> history = new ArrayList<>();
        try {
            BufferedReader logReader = new BufferedReader(new FileReader("History_" + myNick + ".txt"));
            String line;
            while ((line = logReader.readLine()) != null) {
                history.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (history.size() <= 1000) {
            for (int i = 0; i < history.size(); i++) {
                mainTextArea.appendText(history.get(i) + "\n");
            }
        } else {
            for (int i = history.size() - 1000; i < history.size(); i++) {
                mainTextArea.appendText(history.get(i) + "\n");
            }
        }
    }

}
