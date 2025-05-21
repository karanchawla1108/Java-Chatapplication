package Chatserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/*The Server is the main piece of code; it manages the database.
 The ConversationHandler classes use Java ports, threads, and basic filtering to build the back end of a real-time, multi-user chat system.
It emphasizes concurrent communication, live status monitoring, and logging
aligning with key learning outcomes like Java programming, network programming, OOP, and security best practices.*/



/*========================================= ConversationHandler Class ================================================*/

class ConversationHandler extends Thread {
    Socket socket;
    BufferedReader in;
    PrintWriter out;
    PrintWriter pw;
    FileWriter fw;
    BufferedWriter bw;

    public ConversationHandler(Socket socket) throws IOException {
        this.socket = socket;
        //======file path===========.

        // this commented line can save the chat in the txt file on the provided path
        // fw = new FileWriter("C:\\Users\\karan\\OneDrive\\Desktop\\Chatserver.txt", true);

        // This can load the chat in the web server.
        fw = new FileWriter("chat_logs.txt", true);


        bw = new BufferedWriter(fw);
        pw = new PrintWriter(bw,true);
    }

    public void run() {

        String name = null ;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);




            int count = 0;
//            String name;

            // Request and validate username
            while (true) {
                if (count > 0) {
                    out.println("Name Already Exists");
                } else {
                    out.println("Name Required");
                }

                name = in.readLine();

                if (name == null || name.trim().isEmpty()) {
                    out.println("Invalid name. Please try again.");
                    continue;
                }

                synchronized (Server.usernames) {
                    if (!Server.usernames.contains(name)) {
                        Server.usernames.add(name);

                        UserDatabaseManager.insertUser(name); // INSERT username into DataBase.


                        break;
                    }
                }
                count++;
            }

            out.println("Name Accepted");
            Server.printWriters.add(out);


            // Notify the users.
            String joinMessage = "\uD83D\uDECE User [" + name + "] has joined the chat!";
            synchronized (Server.printWriters) {
                for (PrintWriter writer : Server.printWriters) {
                    if (writer != out) {
                        writer.println(joinMessage);
                    }
                }
            }


            // Receive and broadcast messages
            String message;
            while ((message = in.readLine()) != null) {
//                System.out.println(name + ": " + message); // log on server side.
//                pw.println(name + ": " + message);         // log to file path.


                message = MessageFilter.filter(message); // Filter message
                String time = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
                String formattedMessage = "[" + time + "] " + name + ": " + message;

                System.out.println(formattedMessage);
                pw.println(formattedMessage);


                UserDatabaseManager.insertMessage(name, message); //Save to MySQL


                synchronized (Server.printWriters) {
                    for (PrintWriter writer : Server.printWriters) {
//                        writer.println(name + ": " + message);
                        writer.println(formattedMessage);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (name != null) {
                    // Notify all users about the user leaving
                    String leaveMessage = "\uD83D\uDD15 User [" + name + "] has left the chat!";
                    synchronized (Server.printWriters) {
                        for (PrintWriter writer : Server.printWriters) {
                            if (writer != out) {
                                writer.println(leaveMessage);
                            }
                        }
                        Server.printWriters.remove(out);
                    }

                    synchronized (Server.usernames) {
                        Server.usernames.remove(name);
                    }
                }

                //close the resources.
                if (pw != null) pw.close();
                if (bw != null) bw.close();
                if (fw != null) fw.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                System.out.println("Socket closing error: " + e.getMessage());
            }
        }
    }
}

/*=============================================== Server Main Class ========================================================*/

public class Server {

    static ArrayList<String> usernames = new ArrayList<>();
    static ArrayList<PrintWriter> printWriters = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Waiting for clients...");


        try {

            //================ Web Server ============================//

            ChatWebServer webServer = new ChatWebServer(usernames);
            new Thread(() -> {
                try {
                    webServer.start(); // Starts the web server on a separate thread
                } catch (IOException e) {
                    System.out.println("Web server failed to start: " + e.getMessage());
                }
            }).start();

            //================ Web Server ============================//

            // Start the socket server (on port 9806).
            ServerSocket serverSocket = new ServerSocket(9806);
//            System.out.println("Chat socket server running on port 9806...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection established");
                ConversationHandler handler = new ConversationHandler(clientSocket);
                handler.start();
                System.out.println("Chat socket server running on port 9806...");
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}
