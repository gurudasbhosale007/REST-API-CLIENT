import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
private static Set<PrintWriter> clientWriters = new HashSet<>();

public static void main(String[] args) {
int port = 5000;

try (ServerSocket serverSocket = new ServerSocket(port)) {
System.out.println("Chat Server started on port " + port);
while (true) {
Socket clientSocket = serverSocket.accept();
System.out.println("New client connected!");
ClientHandler handler = new ClientHandler(clientSocket);
new Thread(handler).start();
}
}
catch (IOException e) {
e.printStackTrace();
}
}
static class ClientHandler implements Runnable {
private Socket socket;
private PrintWriter out;
private BufferedReader in;
public ClientHandler(Socket socket) {
this.socket = socket;
}
@Override
public void run() {
try {
in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
out = new PrintWriter(socket.getOutputStream(), true);
synchronized (clientWriters) {
clientWriters.add(out);
}
String message;
while ((message = in.readLine()) != null)
{
System.out.println("Received: " + message);
broadcastMessage(message);
}
}
catch (IOException e) 
{
e.printStackTrace();
} finally {
 try {
socket.close();
} catch (IOException e) 
{
e.printStackTrace();
}
synchronized (clientWriters) {
clientWriters.remove(out);
}
}
}
private void broadcastMessage(String message) {
synchronized (clientWriters) {
for (PrintWriter writer : clientWriters) {
writer.println(message);
}
}
}
}
}
