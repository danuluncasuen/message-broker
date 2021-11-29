package com.utm.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: broker.proto")
public final class brokerGrpc {

  private brokerGrpc() {}

  public static final String SERVICE_NAME = "broker";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.utm.grpc.Broker.SubscribeRequest,
      com.utm.grpc.Broker.ResponseMessage> getSubscribeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "subscribe",
      requestType = com.utm.grpc.Broker.SubscribeRequest.class,
      responseType = com.utm.grpc.Broker.ResponseMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<com.utm.grpc.Broker.SubscribeRequest,
      com.utm.grpc.Broker.ResponseMessage> getSubscribeMethod() {
    io.grpc.MethodDescriptor<com.utm.grpc.Broker.SubscribeRequest, com.utm.grpc.Broker.ResponseMessage> getSubscribeMethod;
    if ((getSubscribeMethod = brokerGrpc.getSubscribeMethod) == null) {
      synchronized (brokerGrpc.class) {
        if ((getSubscribeMethod = brokerGrpc.getSubscribeMethod) == null) {
          brokerGrpc.getSubscribeMethod = getSubscribeMethod = 
              io.grpc.MethodDescriptor.<com.utm.grpc.Broker.SubscribeRequest, com.utm.grpc.Broker.ResponseMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "broker", "subscribe"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.utm.grpc.Broker.SubscribeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.utm.grpc.Broker.ResponseMessage.getDefaultInstance()))
                  .setSchemaDescriptor(new brokerMethodDescriptorSupplier("subscribe"))
                  .build();
          }
        }
     }
     return getSubscribeMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static brokerStub newStub(io.grpc.Channel channel) {
    return new brokerStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static brokerBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new brokerBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static brokerFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new brokerFutureStub(channel);
  }

  /**
   */
  public static abstract class brokerImplBase implements io.grpc.BindableService {

    /**
     */
    public void subscribe(com.utm.grpc.Broker.SubscribeRequest request,
        io.grpc.stub.StreamObserver<com.utm.grpc.Broker.ResponseMessage> responseObserver) {
      asyncUnimplementedUnaryCall(getSubscribeMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSubscribeMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                com.utm.grpc.Broker.SubscribeRequest,
                com.utm.grpc.Broker.ResponseMessage>(
                  this, METHODID_SUBSCRIBE)))
          .build();
    }
  }

  /**
   */
  public static final class brokerStub extends io.grpc.stub.AbstractStub<brokerStub> {
    private brokerStub(io.grpc.Channel channel) {
      super(channel);
    }

    private brokerStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected brokerStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new brokerStub(channel, callOptions);
    }

    /**
     */
    public void subscribe(com.utm.grpc.Broker.SubscribeRequest request,
        io.grpc.stub.StreamObserver<com.utm.grpc.Broker.ResponseMessage> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getSubscribeMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class brokerBlockingStub extends io.grpc.stub.AbstractStub<brokerBlockingStub> {
    private brokerBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private brokerBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected brokerBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new brokerBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<com.utm.grpc.Broker.ResponseMessage> subscribe(
        com.utm.grpc.Broker.SubscribeRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getSubscribeMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class brokerFutureStub extends io.grpc.stub.AbstractStub<brokerFutureStub> {
    private brokerFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private brokerFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected brokerFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new brokerFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_SUBSCRIBE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final brokerImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(brokerImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SUBSCRIBE:
          serviceImpl.subscribe((com.utm.grpc.Broker.SubscribeRequest) request,
              (io.grpc.stub.StreamObserver<com.utm.grpc.Broker.ResponseMessage>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class brokerBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    brokerBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.utm.grpc.Broker.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("broker");
    }
  }

  private static final class brokerFileDescriptorSupplier
      extends brokerBaseDescriptorSupplier {
    brokerFileDescriptorSupplier() {}
  }

  private static final class brokerMethodDescriptorSupplier
      extends brokerBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    brokerMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (brokerGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new brokerFileDescriptorSupplier())
              .addMethod(getSubscribeMethod())
              .build();
        }
      }
    }
    return result;
  }
}
