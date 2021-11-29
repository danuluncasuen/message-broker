import com.utm.grpc.Broker;
import com.utm.grpc.brokerGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class GrpcClient {
    private final brokerGrpc.brokerStub asyncStub;
    private final brokerGrpc.brokerBlockingStub blockingStub;
    private final ManagedChannel channel;
    private final String clientId;

    public GrpcClient(String host, int port, String clientId) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext(true), clientId);
    }

    /**
     * Construct client for accessing RouteGuide server using the existing
     * channel.W
     */
    public GrpcClient(ManagedChannelBuilder<?> channelBuilder, String clientId) {
        this.clientId = clientId;
        this.channel = channelBuilder.build();
        this.asyncStub = brokerGrpc.newStub(channel);
        this.blockingStub = brokerGrpc.newBlockingStub(channel);
        System.out.println("init client "+clientId);
    }

    public void shutdown() throws InterruptedException {
        this.channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("shut down "+clientId);
    }

    public void registerToServer(String subject){
        Broker.SubscribeRequest subscribeRequest = Broker.SubscribeRequest.newBuilder()
                .setSubject(subject).setClientId(clientId).build();
        this.asyncStub.subscribe(subscribeRequest,
                new CustomObserver("128"));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select an ID for yourself");
        String id = scanner.nextLine();
        System.out.println("Please chose a subject you want to receive news about");
        String subject = scanner.nextLine();
        GrpcClient client = new GrpcClient("127.0.0.1", 8083, id);
        client.registerToServer(subject);
        while (true) {}
    }

}

