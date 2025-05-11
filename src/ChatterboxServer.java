import java.io.*;
import java.net.*;

public class ChatterboxServer {
    public static void main(String[] args) throws IOException {
        int port = 8000;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server listening on port " + port);
        Socket socket = serverSocket.accept();
        System.out.println("Client connected.");
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
        Thread receiveThread = new Thread(() -> {
            try {
                String msg;
                while ((msg = in.readLine()) != null) {
                    System.out.println("Client: " + msg);
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
