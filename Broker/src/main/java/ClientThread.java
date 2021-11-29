import java.io.*;
import java.net.Socket;

public class ClientThread implements Runnable {

    private Socket clientSocket;
    private OutputStreamWriter outputStreamWriter;
    private BufferedWriter bufferedWriter;

    public ClientThread(Socket socket) throws IOException {
        clientSocket = socket;
        outputStreamWriter = new OutputStreamWriter(clientSocket.getOutputStream());
        bufferedWriter = new BufferedWriter(outputStreamWriter);
    }

    @Override
    public void run() {
        try {
            System.out.println("There's a new request from " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());

            InputStreamReader inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String content = bufferedReader.readLine();
            NotificationService.processContent(content, this);
        } catch (IOException ioe) {
            System.out.println("Could not receive data: " + ioe.getMessage());
        }
    }

    public BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }
}
