import java.io.*;

public class Main {
    public static void main(String[] args) {
        // Start the server in a new thread
        new Thread(() -> {
            try {
                ChatterboxServerWithGame.run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // Wait a bit to ensure server starts before client connects
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}

        // Start the client in another thread
        new Thread(() -> {
            try {
                ChatterboxClient.main(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
