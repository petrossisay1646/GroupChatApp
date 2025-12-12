import java.io.*;
import java.net.*;
import java.util.*;

public class GroupChatServer {
    
    private static Map<Socket, String> clientUsers = new HashMap<>();
    private static Set<PrintWriter> clientWriters = new HashSet<>();

    public static void main(String[] args) throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server started on port 5000...");

            while (true) {
                Socket socket = serverSocket.accept();

                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                clientWriters.add(writer);

                new Thread(new ClientHandler(socket, writer)).start();
            }
        }
    }

   
    public static void broadcast(String message) {
        for (PrintWriter writer : clientWriters) {
            writer.println(message);
        }
    }

    
    public static void setUsername(Socket socket, String username) {
        clientUsers.put(socket, username);
    }

    public static String getUsername(Socket socket) {
        return clientUsers.get(socket);
    }
}

class ClientHandler implements Runnable {
    private Socket socket;
    public ClientHandler(Socket socket, PrintWriter writer) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            );

           
            String username = reader.readLine();
            GroupChatServer.setUsername(socket, username);

            GroupChatServer.broadcast("👉 " + username + " joined the chat");

           
            String msg;
            while ((msg = reader.readLine()) != null) {
                GroupChatServer.broadcast(username + ": " + msg);
            }

        } catch (Exception e) {
            String user = GroupChatServer.getUsername(socket);
            GroupChatServer.broadcast("❌ " + user + " left the chat");
        }
    }
}
