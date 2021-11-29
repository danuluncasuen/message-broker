package com.utm.entities;

import com.utm.grpc.Broker;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class SubjectEntity {

    private final String title;
    private final List<String> messages = new ArrayList<>();
    private volatile ConcurrentHashMap<String, StreamObserver<Broker.ResponseMessage>> subscribers = new ConcurrentHashMap<>();

    public SubjectEntity(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void addMessage(String message) {
        messages.add(message);
    }

    public void addSubscriber(String subscriberId, StreamObserver<Broker.ResponseMessage> streamObserver) {
        try {
            if (!subscribers.containsKey(subscriberId)) {
                subscribers.put(subscriberId, streamObserver);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ConcurrentHashMap<String, StreamObserver<Broker.ResponseMessage>> getSubscribers() {
        return subscribers;
    }
}
