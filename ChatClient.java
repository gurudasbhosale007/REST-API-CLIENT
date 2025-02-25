import java.io.*;
import java.net.*;

public class ChatClient {
public static void main(String[] args) {
String serverAddress = "localhost"; 
int port = 5000;

try (Socket socket = new Socket(serverAddress, port);
BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {
System.out.println("Connected to chat server. Type messages to send:");
Thread receiveThread = new Thread(() -> {
    try {
String serverMessage;
    while ((serverMessage = in.readLine()) != null) 
{ System.out.println("Server: " + serverMessage);
}
} 
catch (IOException e) 
{
e.printStackTrace();
 }
});
receiveThread.start();
String message;
while ((message = userInput.readLine()) != null) 
{
 out.println(message);
 }
}
catch (IOException e)
{
  e.printStackTrace();
}
}
}
