import java.io.*;
import java.net.*;

public class ChatterboxClient {
    public static void main(String[] args) throws IOException {
        String host = "localhost";
        int port = 8000;
        Socket socket = new Socket(host, port);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
        Thread receiveThread = new Thread(() -> {
            try {
                String msg;
                while ((msg = in.readLine()) != null) {
                    System.out.println("Server: " + msg);
                }
            } catch (IOException e) {
                System.out.println("Connection closed.");
            }
        });
        Thread sendThread = new Thread(() -> {
            try {
                String msg;
                while ((msg = consoleIn.readLine()) != null) {
                    out.println(msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        receiveThread.start();
        sendThread.start();
    }
}
