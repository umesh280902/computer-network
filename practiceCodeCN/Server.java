import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        int portNumber = 12345;
        PrintStream ps = System.out;

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            ps.println("Waiting for the client connection...");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    InetAddress clientAddress = clientSocket.getInetAddress();
                    ps.println("Client connected: " + clientAddress);

                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        ps.println("Message received from client: " + inputLine);
                        out.println("Server received: " + inputLine);
                    }
                } catch (IOException e) {
                    ps.println("Error connecting to the client: " + e);
                }
            }
        } catch (IOException e) {
            ps.println("Error starting the server: " + e);
        }
    }
}
