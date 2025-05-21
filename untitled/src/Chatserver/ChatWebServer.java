package Chatserver;


import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


/* A chat server handles sending messages, checking to see if users are online, setting up link methods,
and making sure that data is sent reliably.
For these servers to work, the networking technology, concurrency models,
and message handling methods must be stable.
*/



public class ChatWebServer {

    private List<String> usernames;

    public ChatWebServer(List<String> usernames) {
        this.usernames = usernames;
    }

    /**
     * Starts the HTTP web server on port 8000.
     * Displays current connected users and last 10 chat messages.
     */
    public void start() throws IOException {
        HttpServer webServer = HttpServer.create(new InetSocketAddress(8000), 0);

        webServer.createContext("/", new HttpHandler() {
            public void handle(HttpExchange exchange) throws IOException, UnsupportedEncodingException {
                StringBuilder response = new StringBuilder();
                // HTML with Auto-Refresh
                response.append("<html><head><meta charset='UTF-8'><title>Chat Dashboard</title>");
                response.append("<script>setTimeout(() => { location.reload(); }, 5000);</script>");
                response.append("</head><body>");

                // Dark mode.
                response.append("<html><head><meta charset='UTF-8'><title>Chat Dashboard</title>");
                response.append("<script>setTimeout(() => { location.reload(); }, 5000);</script>");
                response.append("<style>");
                response.append("body { background-color: #121212; color: #eeeeee; font-family: sans-serif; padding: 20px; }");
                response.append("h2, h3 { color: #bb86fc; }");
                response.append("ul { padding-left: 20px; }");
                response.append("li { margin-bottom: 5px; }");
                response.append("div.chat-msg { padding: 10px; margin: 8px 0; background: #1f1f1f; border-radius: 8px; border: 1px solid #333; }");
                response.append("</style>");
                response.append("</head><body>");

                // Status Header
                response.append("<h2>Chat Server Status</h2>");
                response.append("<p>Server is running...</p>");
                response.append("<p>Connected Users: ").append(usernames.size()).append("</p>");
                response.append("<ul>");

                // List of connected usernames
                synchronized (usernames) {
                    for (String name : usernames) {
                        response.append("<li>").append(name).append("</li>");
                    }
                }

                response.append("</ul>");
                response.append("<hr>");
                response.append("<h3>Recent Chat Messages:</h3>");
                response.append(getRecentChatLogs());

                response.append("</body></html>");

                // Convert response to bytes and send it back
                byte[] bytes = response.toString().getBytes("UTF-8");
                exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                exchange.sendResponseHeaders(200, bytes.length);

                OutputStream os = exchange.getResponseBody();
                os.write(bytes);
                os.close();
            }
        });





        webServer.setExecutor(null); // Use default executor
        webServer.start();

        System.out.println("Web server started at http://localhost:8000/");
    }

    // Helper method to get last 10 lines of chat log
    private String getRecentChatLogs() {
        try {
            List<String> allLines = Files.readAllLines(Paths.get("chat_logs.txt"));
            int size = allLines.size();
            List<String> last10 = allLines.subList(Math.max(size - 10, 0), size);

            StringBuilder chatHtml = new StringBuilder();
            for (String line : last10) {
                chatHtml.append("<div class='chat-msg'>")

                        .append(line)
                        .append("</div>");
            }
            return chatHtml.toString();

        } catch (IOException e) {
            return "<p>No chat logs found.</p>";
        }
    }


}
