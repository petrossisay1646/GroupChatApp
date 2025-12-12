import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class ChatClientGUI {

    private JFrame frame;
    private JTextArea chatArea;
    private JTextField inputField;
    private PrintWriter writer;
    private String username;

    public ChatClientGUI() {
        askUsername();
        buildGUI();
        connectToServer();
    }

    private void askUsername() {
        username = JOptionPane.showInputDialog("Enter your username:");
        if (username == null || username.trim().isEmpty()) {
            username = "User" + (int)(Math.random() * 1000);
        }
    }

    private void buildGUI() {
        frame = new JFrame("Chat - " + username);
        frame.setSize(400, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chatArea = new JTextArea();
        chatArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(chatArea);

        inputField = new JTextField();
        JButton sendBtn = new JButton("Send");

        sendBtn.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage());

        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(sendBtn, BorderLayout.EAST);

        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void connectToServer() {
        try {
            Socket socket = new Socket("localhost", 5000);
            writer = new PrintWriter(socket.getOutputStream(), true);

            // Send username first
            writer.println(username);

            // Thread to read incoming messages
            new Thread(() -> {
                try {
                    BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                    );

                    String msg;
                    while ((msg = reader.readLine()) != null) {
                        chatArea.append(msg + "\n");
                    }

                } catch (Exception ignored) {}
            }).start();

        } catch (Exception e) {
            chatArea.append("❌ Unable to connect to server.\n");
        }
    }

    private void sendMessage() {
        String msg = inputField.getText().trim();
        if (!msg.isEmpty()) {
            writer.println(msg);
            inputField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChatClientGUI::new);
    }
}
