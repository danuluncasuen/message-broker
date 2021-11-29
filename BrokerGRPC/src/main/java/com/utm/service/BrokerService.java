package com.utm.service;

import com.utm.datasource.DataSource;
import com.utm.entities.SubjectEntity;
import com.utm.grpc.Broker;
import com.utm.grpc.brokerGrpc;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

import java.util.Map;

public class BrokerService extends brokerGrpc.brokerImplBase {

    private final DataSource dataSource = DataSource.getDataSource();

    @Override
    public void subscribe(Broker.SubscribeRequest request, StreamObserver<Broker.ResponseMessage> responseObserver) {
        try {
            System.out.println(request);
            dataSource.addClient(request, responseObserver);
            Broker.Subject subject = Broker.Subject.newBuilder().setTitle(request.getSubject()).build();
            SubjectEntity subjectEntity = dataSource.getSubjectEntitiesBySubject(subject);
            Broker.ResponseMessage responseMessage = Broker.ResponseMessage.newBuilder()
                    .setResponse(subjectEntity.getMessages().toString()).build();
            responseObserver.onNext(responseMessage);
        } catch (IllegalArgumentException ie) {
            Broker.ResponseMessage.Builder error = Broker.ResponseMessage.newBuilder();
            error.setResponse(ie.getMessage());
            responseObserver.onNext(error.build());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void publish(Broker.Subject request, StreamObserver<Broker.ResponseMessage> responseObserver) {
        try {
            System.out.println("New publish request with the following content: " + request);
            dataSource.addIfNotPresent(request);
            notify(request);
            Broker.ResponseMessage.Builder responseMessage = Broker.ResponseMessage.newBuilder();
            responseMessage.setResponse("The message has been published");
            responseObserver.onNext(responseMessage.build());
        } catch (Exception e) {
            System.out.println("Error publishing the message " + e.getMessage());
        } finally {
            responseObserver.onCompleted();
        }
    }

    public void notify(Broker.Subject request) {
        try {
            SubjectEntity subjectEntity = dataSource.getSubjectEntitiesBySubject(request);
            for (Map.Entry<String, StreamObserver<Broker.ResponseMessage>> subscriber : subjectEntity.getSubscribers().entrySet()) {
                try {
                    Broker.ResponseMessage.Builder responseMessage = Broker.ResponseMessage.newBuilder();
                    responseMessage.setResponse(subjectEntity.getMessages().toString());
                    subscriber.getValue().onNext(responseMessage.build());
                } catch (StatusRuntimeException sre) {
                    System.out.println("Removing the client with id " + subscriber.getKey() + " because of inactivity");
                    dataSource.removeClient(request.getTitle(), subscriber.getKey());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
