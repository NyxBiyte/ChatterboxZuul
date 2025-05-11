import java.io.*;
import java.net.*;

public class ChatterboxServerWithGame {
    public static void run() throws IOException {
        int port = 8000;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server listening on port " + port);
        Socket socket = serverSocket.accept();
        System.out.println("Client connected.");

        BufferedReader clientIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter clientOut = new PrintWriter(socket.getOutputStream(), true);


        ProcessBuilder pb = new ProcessBuilder("cd src", "java", "zuul.Start");
        pb.redirectErrorStream(true); // Combine stderr with stdout
        Process gameProcess = pb.start();

        BufferedReader gameOut = new BufferedReader(new InputStreamReader(gameProcess.getInputStream()));
        PrintWriter gameIn = new PrintWriter(gameProcess.getOutputStream(), true);

        // Relay game output to client
        new Thread(() -> {
            try {
                String line;
                while ((line = gameOut.readLine()) != null) {
                    clientOut.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // Relay client input to game
        String input;
        while ((input = clientIn.readLine()) != null) {
            gameIn.println(input);
        }

        socket.close();
        serverSocket.close();
        gameProcess.destroy();
        System.out.println("Game session ended.");
    }
}
