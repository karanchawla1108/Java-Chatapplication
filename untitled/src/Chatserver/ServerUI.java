package Chatserver;

import javax.swing.*;
import java.awt.*;


// GUI for server. It display a lice log of server events and a list of connected users.
public class ServerUI {

    private JFrame frame;
    private JTextArea logArea;
    private DefaultListModel<String> userListModel;
    private JList<String> userList;

    public ServerUI() {
        frame = new JFrame("Chat Server Console");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Log area
        logArea = new JTextArea();
        logArea.setEditable(false);
        frame.add(new JScrollPane(logArea), BorderLayout.CENTER);

        // User list panel
        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        frame.add(new JScrollPane(userList), BorderLayout.EAST);

        frame.setVisible(true);
    }

    public void log(String message) {
        logArea.append(message + "\n");
    }

    public void addUser(String username) {
        if (!userListModel.contains(username)) {
            userListModel.addElement(username);
        }
    }

    public void removeUser(String username) {
        userListModel.removeElement(username);
    }
}
