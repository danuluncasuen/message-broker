import com.utm.grpc.Broker;
import io.grpc.stub.StreamObserver;

public class CustomObserver implements StreamObserver<Broker.ResponseMessage> {

    private String info;

    public CustomObserver(String info){
        this.info = info;
    }

    @Override
    public void onCompleted() {
        System.out.println(info+"onCompleted");
    }

    @Override
    public void onError(Throwable arg0) {
        System.out.println(info+"onError");
    }

    /**
     *
     * Receive server push messages
     */
    @Override
    public void onNext(Broker.ResponseMessage message) {
        System.out.println("Receive："+message.getResponse());
    }

}