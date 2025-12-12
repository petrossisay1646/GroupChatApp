
# Socket-Based Group Chat Application (Java)

This project is a simple Java **socket-based group chat app** that allows multiple users to chat in real time. It includes a **server program** and a **client GUI** built using **Java Swing**.

## Features
- Real-time group messaging  
- TCP socket communication  
- Username support  
- Simple Swing-based client interface  
- Multithreaded server and client  
- Server broadcasts messages to all connected clients  

## How to Run

### 1. Compile
```bash
javac GroupChatServer.java
javac ChatClientGUI.java

2. Start the Server

java GroupChatServer

3. Start Client(s)

java ChatClientGUI

How It Works

The server listens on port 5000 and accepts client connections.

Each client enters a username when joining.

Messages sent by any client are broadcast to all connected clients.

The client uses one thread for sending and another thread for receiving messages, keeping the GUI responsive.


Technologies

Java Sockets (TCP)

Java Swing

Multithreading
