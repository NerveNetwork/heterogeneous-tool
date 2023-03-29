package cosmos.reflection.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Package cosmos.reflection.v1 provides support for inspecting protobuf
 * file descriptors.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.45.1)",
    comments = "Source: cosmos/reflection/v1/reflection.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ReflectionServiceGrpc {

  private ReflectionServiceGrpc() {}

  public static final String SERVICE_NAME = "cosmos.reflection.v1.ReflectionService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<cosmos.reflection.v1.Reflection.FileDescriptorsRequest,
      cosmos.reflection.v1.Reflection.FileDescriptorsResponse> getFileDescriptorsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "FileDescriptors",
      requestType = cosmos.reflection.v1.Reflection.FileDescriptorsRequest.class,
      responseType = cosmos.reflection.v1.Reflection.FileDescriptorsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.reflection.v1.Reflection.FileDescriptorsRequest,
      cosmos.reflection.v1.Reflection.FileDescriptorsResponse> getFileDescriptorsMethod() {
    io.grpc.MethodDescriptor<cosmos.reflection.v1.Reflection.FileDescriptorsRequest, cosmos.reflection.v1.Reflection.FileDescriptorsResponse> getFileDescriptorsMethod;
    if ((getFileDescriptorsMethod = ReflectionServiceGrpc.getFileDescriptorsMethod) == null) {
      synchronized (ReflectionServiceGrpc.class) {
        if ((getFileDescriptorsMethod = ReflectionServiceGrpc.getFileDescriptorsMethod) == null) {
          ReflectionServiceGrpc.getFileDescriptorsMethod = getFileDescriptorsMethod =
              io.grpc.MethodDescriptor.<cosmos.reflection.v1.Reflection.FileDescriptorsRequest, cosmos.reflection.v1.Reflection.FileDescriptorsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "FileDescriptors"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.reflection.v1.Reflection.FileDescriptorsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.reflection.v1.Reflection.FileDescriptorsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ReflectionServiceMethodDescriptorSupplier("FileDescriptors"))
              .build();
        }
      }
    }
    return getFileDescriptorsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ReflectionServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ReflectionServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ReflectionServiceStub>() {
        @java.lang.Override
        public ReflectionServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ReflectionServiceStub(channel, callOptions);
        }
      };
    return ReflectionServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ReflectionServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ReflectionServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ReflectionServiceBlockingStub>() {
        @java.lang.Override
        public ReflectionServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ReflectionServiceBlockingStub(channel, callOptions);
        }
      };
    return ReflectionServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ReflectionServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ReflectionServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ReflectionServiceFutureStub>() {
        @java.lang.Override
        public ReflectionServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ReflectionServiceFutureStub(channel, callOptions);
        }
      };
    return ReflectionServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Package cosmos.reflection.v1 provides support for inspecting protobuf
   * file descriptors.
   * </pre>
   */
  public static abstract class ReflectionServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * FileDescriptors queries all the file descriptors in the app in order
     * to enable easier generation of dynamic clients.
     * </pre>
     */
    public void fileDescriptors(cosmos.reflection.v1.Reflection.FileDescriptorsRequest request,
        io.grpc.stub.StreamObserver<cosmos.reflection.v1.Reflection.FileDescriptorsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFileDescriptorsMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getFileDescriptorsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.reflection.v1.Reflection.FileDescriptorsRequest,
                cosmos.reflection.v1.Reflection.FileDescriptorsResponse>(
                  this, METHODID_FILE_DESCRIPTORS)))
          .build();
    }
  }

  /**
   * <pre>
   * Package cosmos.reflection.v1 provides support for inspecting protobuf
   * file descriptors.
   * </pre>
   */
  public static final class ReflectionServiceStub extends io.grpc.stub.AbstractAsyncStub<ReflectionServiceStub> {
    private ReflectionServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ReflectionServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ReflectionServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * FileDescriptors queries all the file descriptors in the app in order
     * to enable easier generation of dynamic clients.
     * </pre>
     */
    public void fileDescriptors(cosmos.reflection.v1.Reflection.FileDescriptorsRequest request,
        io.grpc.stub.StreamObserver<cosmos.reflection.v1.Reflection.FileDescriptorsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFileDescriptorsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * Package cosmos.reflection.v1 provides support for inspecting protobuf
   * file descriptors.
   * </pre>
   */
  public static final class ReflectionServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<ReflectionServiceBlockingStub> {
    private ReflectionServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ReflectionServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ReflectionServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * FileDescriptors queries all the file descriptors in the app in order
     * to enable easier generation of dynamic clients.
     * </pre>
     */
    public cosmos.reflection.v1.Reflection.FileDescriptorsResponse fileDescriptors(cosmos.reflection.v1.Reflection.FileDescriptorsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFileDescriptorsMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * Package cosmos.reflection.v1 provides support for inspecting protobuf
   * file descriptors.
   * </pre>
   */
  public static final class ReflectionServiceFutureStub extends io.grpc.stub.AbstractFutureStub<ReflectionServiceFutureStub> {
    private ReflectionServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ReflectionServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ReflectionServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * FileDescriptors queries all the file descriptors in the app in order
     * to enable easier generation of dynamic clients.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.reflection.v1.Reflection.FileDescriptorsResponse> fileDescriptors(
        cosmos.reflection.v1.Reflection.FileDescriptorsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFileDescriptorsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_FILE_DESCRIPTORS = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ReflectionServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ReflectionServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_FILE_DESCRIPTORS:
          serviceImpl.fileDescriptors((cosmos.reflection.v1.Reflection.FileDescriptorsRequest) request,
              (io.grpc.stub.StreamObserver<cosmos.reflection.v1.Reflection.FileDescriptorsResponse>) responseObserver);
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

  private static abstract class ReflectionServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ReflectionServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return cosmos.reflection.v1.Reflection.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ReflectionService");
    }
  }

  private static final class ReflectionServiceFileDescriptorSupplier
      extends ReflectionServiceBaseDescriptorSupplier {
    ReflectionServiceFileDescriptorSupplier() {}
  }

  private static final class ReflectionServiceMethodDescriptorSupplier
      extends ReflectionServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ReflectionServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (ReflectionServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ReflectionServiceFileDescriptorSupplier())
              .addMethod(getFileDescriptorsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
