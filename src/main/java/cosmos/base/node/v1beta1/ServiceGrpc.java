package cosmos.base.node.v1beta1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Service defines the gRPC querier service for node related queries.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.45.1)",
    comments = "Source: cosmos/base/node/v1beta1/query.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ServiceGrpc {

  private ServiceGrpc() {}

  public static final String SERVICE_NAME = "cosmos.base.node.v1beta1.Service";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<cosmos.base.node.v1beta1.Query.ConfigRequest,
      cosmos.base.node.v1beta1.Query.ConfigResponse> getConfigMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Config",
      requestType = cosmos.base.node.v1beta1.Query.ConfigRequest.class,
      responseType = cosmos.base.node.v1beta1.Query.ConfigResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.base.node.v1beta1.Query.ConfigRequest,
      cosmos.base.node.v1beta1.Query.ConfigResponse> getConfigMethod() {
    io.grpc.MethodDescriptor<cosmos.base.node.v1beta1.Query.ConfigRequest, cosmos.base.node.v1beta1.Query.ConfigResponse> getConfigMethod;
    if ((getConfigMethod = ServiceGrpc.getConfigMethod) == null) {
      synchronized (ServiceGrpc.class) {
        if ((getConfigMethod = ServiceGrpc.getConfigMethod) == null) {
          ServiceGrpc.getConfigMethod = getConfigMethod =
              io.grpc.MethodDescriptor.<cosmos.base.node.v1beta1.Query.ConfigRequest, cosmos.base.node.v1beta1.Query.ConfigResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Config"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.base.node.v1beta1.Query.ConfigRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.base.node.v1beta1.Query.ConfigResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ServiceMethodDescriptorSupplier("Config"))
              .build();
        }
      }
    }
    return getConfigMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ServiceStub>() {
        @java.lang.Override
        public ServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ServiceStub(channel, callOptions);
        }
      };
    return ServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ServiceBlockingStub>() {
        @java.lang.Override
        public ServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ServiceBlockingStub(channel, callOptions);
        }
      };
    return ServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ServiceFutureStub>() {
        @java.lang.Override
        public ServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ServiceFutureStub(channel, callOptions);
        }
      };
    return ServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Service defines the gRPC querier service for node related queries.
   * </pre>
   */
  public static abstract class ServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Config queries for the operator configuration.
     * </pre>
     */
    public void config(cosmos.base.node.v1beta1.Query.ConfigRequest request,
        io.grpc.stub.StreamObserver<cosmos.base.node.v1beta1.Query.ConfigResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getConfigMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getConfigMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.base.node.v1beta1.Query.ConfigRequest,
                cosmos.base.node.v1beta1.Query.ConfigResponse>(
                  this, METHODID_CONFIG)))
          .build();
    }
  }

  /**
   * <pre>
   * Service defines the gRPC querier service for node related queries.
   * </pre>
   */
  public static final class ServiceStub extends io.grpc.stub.AbstractAsyncStub<ServiceStub> {
    private ServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Config queries for the operator configuration.
     * </pre>
     */
    public void config(cosmos.base.node.v1beta1.Query.ConfigRequest request,
        io.grpc.stub.StreamObserver<cosmos.base.node.v1beta1.Query.ConfigResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getConfigMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * Service defines the gRPC querier service for node related queries.
   * </pre>
   */
  public static final class ServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<ServiceBlockingStub> {
    private ServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Config queries for the operator configuration.
     * </pre>
     */
    public cosmos.base.node.v1beta1.Query.ConfigResponse config(cosmos.base.node.v1beta1.Query.ConfigRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getConfigMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * Service defines the gRPC querier service for node related queries.
   * </pre>
   */
  public static final class ServiceFutureStub extends io.grpc.stub.AbstractFutureStub<ServiceFutureStub> {
    private ServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Config queries for the operator configuration.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.base.node.v1beta1.Query.ConfigResponse> config(
        cosmos.base.node.v1beta1.Query.ConfigRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getConfigMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CONFIG = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CONFIG:
          serviceImpl.config((cosmos.base.node.v1beta1.Query.ConfigRequest) request,
              (io.grpc.stub.StreamObserver<cosmos.base.node.v1beta1.Query.ConfigResponse>) responseObserver);
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

  private static abstract class ServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return cosmos.base.node.v1beta1.Query.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Service");
    }
  }

  private static final class ServiceFileDescriptorSupplier
      extends ServiceBaseDescriptorSupplier {
    ServiceFileDescriptorSupplier() {}
  }

  private static final class ServiceMethodDescriptorSupplier
      extends ServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (ServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ServiceFileDescriptorSupplier())
              .addMethod(getConfigMethod())
              .build();
        }
      }
    }
    return result;
  }
}
