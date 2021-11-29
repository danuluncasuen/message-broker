import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DataSource {

    private static DataSource DATA_INSTANCE;
    private static ConcurrentLinkedQueue<SubjectEntity> subjectEntities = new ConcurrentLinkedQueue<>();

    private DataSource() {}

    public static DataSource getDataSource() {
        if (DATA_INSTANCE != null) {
            return DATA_INSTANCE;
        }
        DATA_INSTANCE = new DataSource();
        return DATA_INSTANCE;
    }

    public boolean addSubscriber(ClientThread subscriber, String topic) {
        for (SubjectEntity subjectEntity : subjectEntities) {
            if (subjectEntity.getTitle().equals(topic)) {
                subjectEntity.addSubscriber(subscriber);
                return true;
            }
        }
        return false;
    }

    public void addSubject(ReceivedSubjectEntity receivedSubjectEntity) {
        for (SubjectEntity entity : subjectEntities) {
            if (entity.getTitle().equals(receivedSubjectEntity.getTitle())) {
                entity.addMessage(receivedSubjectEntity.getMessage());
                return;
            }
        }
        SubjectEntity newSubject = new SubjectEntity(receivedSubjectEntity.getTitle());
        newSubject.addMessage(receivedSubjectEntity.getMessage());
        subjectEntities.add(newSubject);
    }

    public SubjectEntity getByTopic(String topic) {

        for (SubjectEntity subjectEntity : subjectEntities) {
            if (subjectEntity.getTitle().equals(topic)) {
                return subjectEntity;
            }
        }
        return null;
    }

    public ClientThread[] getConnectionsByTopic(String title) {
        for (SubjectEntity subject : subjectEntities) {
            if (subject.getTitle().equals(title)) {
                return subject.getSubscribers();
            }
        }
        return null;
    }

    public void removeConnection(String topic, ClientThread socketLoop) {
        for (SubjectEntity subject : subjectEntities) {
            if (subject.getTitle().equals(topic)) {
                subject.removeSubscriber(socketLoop);
            }
        }
    }
}
