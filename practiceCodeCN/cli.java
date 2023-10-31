import java.io.*;
import java.net.*;
public class cli {
    public static void main(String[] args) {
        String portAddress="127.0.0.1";
        int portNumber=12345;
        try (Socket clientSocket=new Socket(portAddress,portNumber);
            BufferedReader in=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out=new PrintWriter(clientSocket.getOutputStream(),true);

        ){
            System.out.println("connected to the server...");
            String inputLine;
            while((inputLine=br.readLine())!=null){
                out.println(inputLine);
                String response=in.readLine();
                System.out.println("Server recieved:"+response);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
