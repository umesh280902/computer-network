import java.io.*;
import java.net.*;

public class Client {
    static final String serverAddress = "127.0.0.1";
    static final int serverPort = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(serverAddress, serverPort)) {
            System.out.println("Connected to the server");
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            String userInputLine;

            while ((userInputLine = br.readLine()) != null) {
                out.println(userInputLine);
                String response = in.readLine();
                System.out.println("Server response: " + response);
            }
        } catch (IOException e) {
            System.out.println("Error connecting to the server: " + e.getMessage());
        }
    }
}
