package Chatserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


/* implements the client-side of the chat application using Java Swing.
// It connects to the server via sockets, handles message input/output, and provides a GUI.*/

/*=============================================== Client Class =================================================================*/
public class Client {

    // GUI Components
    static JFrame chatWindow = new JFrame("Chat Application");
    static JTextArea chatArea = new JTextArea(22, 40);
    static JTextField textField = new JTextField(40);
    static JLabel blankLabel = new JLabel("          ");
    static JButton sendButton = new JButton("Send");
    static BufferedReader in;
    static PrintWriter out;
    String username;


    /**
     * Constructor sets up the GUI layout and initializes action listeners.
     */

    Client() {
        chatWindow.setLayout(new FlowLayout());

        chatWindow.add(new JScrollPane(chatArea));
        chatWindow.add(blankLabel);
        chatWindow.add(textField);
        chatWindow.add(sendButton);

        chatWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chatWindow.setSize(500, 500);
        chatWindow.setVisible(true);

        textField.setEditable(false);
        chatArea.setEditable(false);

        // Add action listeners for send button and Enter key
        sendButton.addActionListener(e -> {
            out.println(textField.getText());
            textField.setText("");
        });

        textField.addActionListener(e -> {
            out.println(textField.getText());
            textField.setText("");
        });



    }

    // Socket connection and handles chat interaction.
    void startChat() throws Exception {

        // Step 1: Ask for server IP
        String ipAddress = JOptionPane.showInputDialog(chatWindow,
                "Enter Server IP Address:",
                "IP Address Required",
                JOptionPane.PLAIN_MESSAGE
        );

        // Step 2: If user cancels (null), exit the app
        if (ipAddress == null) {
            JOptionPane.showMessageDialog(chatWindow,
                    "Connection cancelled. Exiting application.",
                    "Cancelled",
                    JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }

        // Step 3: If left blank, use localhost
        if (ipAddress.trim().isEmpty()) {
            ipAddress = "localhost";
        }


        // Step 4: Try connecting
        Socket soc = new Socket(ipAddress, 9806);
        in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
        out = new PrintWriter(soc.getOutputStream(), true);



        // Step 5: Chat interaction
        while (true) {
            String str = in.readLine();
            if (str.equals("Name Required")) {
                String name = JOptionPane.showInputDialog(
                        chatWindow,
                        "Enter a unique name:",
                        "Name Required!!",
                        JOptionPane.PLAIN_MESSAGE
                );
                out.println(name);  // send name to server
                username = name; // shows the username
            } else if (str.equals("Name Already Exists")) {
                String name = JOptionPane.showInputDialog(
                        chatWindow,
                        "Enter another name:",
                        "Name Already Exists",
                        JOptionPane.WARNING_MESSAGE
                );
                out.println(name);  // send new name to server
                username = name; // shows the username
            } else if (str.equals("Name Accepted")) {
                textField.setEditable(true);
                 //update title
                chatWindow.setTitle("Chat Application - " + username);
                chatArea.append("Welcome, " + username + "!\n\n");
            } else {
                chatArea.append(str + "\n");  // display incoming messages
            }
        }
    }

    /*=============================================== Client Class =====================================================*/






    //================================================ Main ============================================================/
    public static void main(String[] args) throws Exception {

        // Password before opening the client server.

        String correctPassword = "1234";  // Simple password , it can be set differently.

        String enteredPassword = JOptionPane.showInputDialog(
                null,
                "Enter password to access Client server:",
                "Client Access",
                JOptionPane.PLAIN_MESSAGE
        );

        if (enteredPassword == null || !enteredPassword.equals(correctPassword)) {
            JOptionPane.showMessageDialog(
                    null,
                    " Incorrect password. Access denied.",
                    "Access Denied",
                    JOptionPane.ERROR_MESSAGE
            );
            System.exit(0);
        }

        // If password is correct, launch the chat client
        Client cl = new Client();
        cl.startChat();
    }
}
