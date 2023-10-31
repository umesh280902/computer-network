import java.io.*;
import java.net.*;
public class serv {
    public static void main(String[] args) {
        int portNumber=12345;
        try (ServerSocket serverSocket=new ServerSocket(portNumber)){
            System.out.println("Waiting for client connection....");
            while (true) {
                try (Socket clientSocket=serverSocket.accept();
                    BufferedReader br=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out=new PrintWriter(clientSocket.getOutputStream(),true);
                ){

                    InetAddress clientAddress=clientSocket.getInetAddress();
                    System.out.println("Client connected:"+clientAddress);
                    String inputLine;
                    while((inputLine=br.readLine())!=null){
                        System.out.println("message received from client:"+inputLine);
                        out.println("Server recieved: "+inputLine);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
