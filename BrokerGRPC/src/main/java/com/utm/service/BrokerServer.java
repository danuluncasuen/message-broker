package com.utm.service;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class BrokerServer {

    private  int port;
    private  Server server;

    public BrokerServer(int port) throws IOException {
        this(ServerBuilder.forPort(port), port);
    }

    public BrokerServer(ServerBuilder<?> serverBuilder, int port) {
        this.port = port;
        this.server = serverBuilder.addService(new BrokerService()).build();
    }

    public void start() throws IOException {
        server.start();
        System.out.println("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {

            public void run() {
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                BrokerServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws Exception{
        BrokerServer server = new BrokerServer(8083);
        server.start();
        while (true) {}
    }

}
