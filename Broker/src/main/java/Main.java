import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("The broker has started");
        Broker brokerSocket = new Broker(8088);
        brokerSocket.accept();
    }

}
