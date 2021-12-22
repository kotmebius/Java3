package sample.server;

import java.sql.*;

public class AuthService {
    private static Connection connection;
    private static Statement stmt;

    public static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:main.db");
            stmt = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addUser(String login, String pass, String nick) {
        try {
            String query = "INSERT INTO users (login, password, nickname) VALUES (?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, login);
            ps.setInt(2, pass.hashCode());
            ps.setString(3, nick);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getNickByLoginAndPass(String login, String pass) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT nickname, password FROM users WHERE login = '" + login + "'");
            int myHash = pass.hashCode();
            // 106438208
            if (rs.next()) {
                String nick = rs.getString(1);
                int dbHash = rs.getInt(2);
                if (myHash == dbHash) {
                    return nick;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean addToBlacList(String nick, String nickToBL){
        try {
            String query = "INSERT INTO blacklist VALUES (?, ?);";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, nick);
            ps.setString(2, nickToBL);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkInBlacList(String nick, String nickToBL){
        try {
            String query = "SELECT * FROM blacklist WHERE username=\'"+nick+"\' AND user_in_bl=\'"+nickToBL+"\';";
//            String query = "SELECT * FROM blacklist WHERE username=? AND user_in_bl=?;";
            PreparedStatement ps = connection.prepareStatement(query);
//            ps.setString(1, "\""+nick+"\"");
//            ps.setString(2, "\""+nickToBL+"\"");
            ResultSet result = ps.executeQuery();
            return result.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void changeNick(String oldNick, String newNick) { //Смена ника в базе
        try {
            String query = "UPDATE users SET nickname=? WHERE nickname=?;";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, newNick);
            ps.setString(2, oldNick);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
