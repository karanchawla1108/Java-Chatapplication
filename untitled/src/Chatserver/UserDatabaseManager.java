package Chatserver;

import java.sql.*;

public class UserDatabaseManager {

    public static void insertUser(String username) {
        // connection for database.
        String url = "jdbc:mysql://localhost:3307/karanchawla_chatapp";
        String user = "root";
        String pass = "";

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("\uD83D\uDD17 Connecting to MySQL...");
            Connection conn = DriverManager.getConnection(url, user, pass);

            // sql query to insert name .
            String sql = "INSERT IGNORE INTO users (username) VALUES (?)";

//            String sql = "INSERT INTO users (username) VALUES (?)";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);

            stmt.executeUpdate();
            stmt.close();
            conn.close();

            System.out.println("\u2705  Inserted user into MySQL: " + username);
        } catch (Exception e) {
            System.out.println("\u274C MySQL Insert Error:");
            e.printStackTrace();  //shows full JDBC error
        }
    }



    // Storing the messages in Databse.
    public static void insertMessage(String username, String message) {
        String url = "jdbc:mysql://localhost:3307/karanchawla_chatapp";
        String user = "root";
        String pass = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, pass);

            // sql query to insert name .
            String sql = "INSERT INTO messages (username, message) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, message);
            stmt.executeUpdate();

            stmt.close();
            conn.close();

            System.out.println("ॐ Saved message from " + username + " to DB.");
        } catch (Exception e) {
            System.out.println("\uD83D\uDEA9 MySQL Message Insert Error:");
            e.printStackTrace();
        }
    }





}
