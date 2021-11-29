import com.utm.grpc.Broker;
import com.utm.grpc.brokerGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;

public class GrpcPublisher {

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost",8083).usePlaintext().build();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Please input the subject you want to publish:");
            String subject = scanner.nextLine();
            System.out.println("Please input the news about " + subject);
            String news = scanner.nextLine();
            brokerGrpc.brokerBlockingStub brokerStub = brokerGrpc.newBlockingStub(channel);
            Broker.Subject subscribeRequest = Broker.Subject.newBuilder().setTitle(subject).setContent(news).build();
            Broker.ResponseMessage responseMessage = brokerStub.publish(subscribeRequest);
            System.out.println(responseMessage.getResponse());
        }
    }

}
