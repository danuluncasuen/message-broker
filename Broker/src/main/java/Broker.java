import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Broker {

    private ServerSocket socket;
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(8);

    public void accept() throws IOException {
        while (true) {
            Socket clientConnection = socket.accept();
            ClientThread clientThread = new ClientThread(clientConnection);
            threadPool.execute(clientThread);
        }
    }

    public Broker(int port) throws IOException {
        socket = new ServerSocket(port);
    }
}
