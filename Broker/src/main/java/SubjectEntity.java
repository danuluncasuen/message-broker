import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SubjectEntity {

    private String title;
    private final List<String> messages = new ArrayList<>();
    private static ConcurrentLinkedQueue<ClientThread> subscribers = new ConcurrentLinkedQueue<>();

    public SubjectEntity(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public ClientThread[] getSubscribers() {
        return subscribers.toArray(ClientThread[]::new);
    }

    public List<String> getMessages() {
        return messages;
    }

    public void addMessage(String message) {
        messages.add(message);
    }

    public void addSubscriber(ClientThread subscriber) {
        subscribers.add(subscriber);
    }
}
