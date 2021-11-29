package com.utm.datasource;

import com.utm.entities.SubjectEntity;
import com.utm.grpc.Broker;
import io.grpc.stub.StreamObserver;

import java.nio.channels.AlreadyConnectedException;
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

    public void addIfNotPresent(Broker.Subject request) {
        for (SubjectEntity subject : subjectEntities) {
            if (subject.getTitle().equals(request.getTitle())) {
                subject.getMessages().add(request.getContent());
                return;
            }
        }
        SubjectEntity toBeAdded = new SubjectEntity(request.getTitle());
        toBeAdded.getMessages().add(request.getContent());
        subjectEntities.add(toBeAdded);
    }

    public ConcurrentLinkedQueue<SubjectEntity> getSubjectEntities() {
        return subjectEntities;
    }

    public void addClient(Broker.SubscribeRequest request, StreamObserver<Broker.ResponseMessage> responseObserver) {
        for (SubjectEntity subject : subjectEntities) {
            if (subject.getTitle().equals(request.getSubject())) {
                if (!subject.getSubscribers().containsKey(request.getClientId())) {
                    subject.addSubscriber(request.getClientId(), responseObserver);
                    return;
                } else {
                    throw new IllegalArgumentException("The client is already subscribed to this subject");
                }
            }
        }
        throw new IllegalArgumentException("No such subject");
    }

    public SubjectEntity getSubjectEntitiesBySubject(Broker.Subject subject) {
        for (SubjectEntity subjectEntity : subjectEntities) {
            if (subjectEntity.getTitle().equals(subject.getTitle())) {
                return subjectEntity;
            }
        }
        return null;
    }

    public void removeClient(String title, String id) {
        for (SubjectEntity subject : subjectEntities) {
            if (subject.getTitle().equals(title)) {
                subject.getSubscribers().remove(id);
            }
        }
    }
}
