package org.tron.trident.api;

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
 * <pre>
 * NOTE: All other solidified APIs are useless.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.31.0)",
    comments = "Source: api/api.proto")
public final class WalletSolidityGrpc {

  private WalletSolidityGrpc() {}

  public static final String SERVICE_NAME = "protocol.WalletSolidity";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<GrpcAPI.AccountAddressMessage,
      org.tron.trident.proto.Response.Account> getGetAccountMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetAccount",
      requestType = GrpcAPI.AccountAddressMessage.class,
      responseType = org.tron.trident.proto.Response.Account.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<GrpcAPI.AccountAddressMessage,
      org.tron.trident.proto.Response.Account> getGetAccountMethod() {
    io.grpc.MethodDescriptor<GrpcAPI.AccountAddressMessage, org.tron.trident.proto.Response.Account> getGetAccountMethod;
    if ((getGetAccountMethod = WalletSolidityGrpc.getGetAccountMethod) == null) {
      synchronized (WalletSolidityGrpc.class) {
        if ((getGetAccountMethod = WalletSolidityGrpc.getGetAccountMethod) == null) {
          WalletSolidityGrpc.getGetAccountMethod = getGetAccountMethod =
              io.grpc.MethodDescriptor.<GrpcAPI.AccountAddressMessage, org.tron.trident.proto.Response.Account>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetAccount"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GrpcAPI.AccountAddressMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.trident.proto.Response.Account.getDefaultInstance()))
              .setSchemaDescriptor(new WalletSolidityMethodDescriptorSupplier("GetAccount"))
              .build();
        }
      }
    }
    return getGetAccountMethod;
  }

  private static volatile io.grpc.MethodDescriptor<GrpcAPI.EmptyMessage,
      org.tron.trident.proto.Response.BlockExtention> getGetNowBlock2Method;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetNowBlock2",
      requestType = GrpcAPI.EmptyMessage.class,
      responseType = org.tron.trident.proto.Response.BlockExtention.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<GrpcAPI.EmptyMessage,
      org.tron.trident.proto.Response.BlockExtention> getGetNowBlock2Method() {
    io.grpc.MethodDescriptor<GrpcAPI.EmptyMessage, org.tron.trident.proto.Response.BlockExtention> getGetNowBlock2Method;
    if ((getGetNowBlock2Method = WalletSolidityGrpc.getGetNowBlock2Method) == null) {
      synchronized (WalletSolidityGrpc.class) {
        if ((getGetNowBlock2Method = WalletSolidityGrpc.getGetNowBlock2Method) == null) {
          WalletSolidityGrpc.getGetNowBlock2Method = getGetNowBlock2Method =
              io.grpc.MethodDescriptor.<GrpcAPI.EmptyMessage, org.tron.trident.proto.Response.BlockExtention>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetNowBlock2"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GrpcAPI.EmptyMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.trident.proto.Response.BlockExtention.getDefaultInstance()))
              .setSchemaDescriptor(new WalletSolidityMethodDescriptorSupplier("GetNowBlock2"))
              .build();
        }
      }
    }
    return getGetNowBlock2Method;
  }

  private static volatile io.grpc.MethodDescriptor<GrpcAPI.BytesMessage,
      org.tron.trident.proto.Chain.Transaction> getGetTransactionByIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetTransactionById",
      requestType = GrpcAPI.BytesMessage.class,
      responseType = org.tron.trident.proto.Chain.Transaction.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<GrpcAPI.BytesMessage,
      org.tron.trident.proto.Chain.Transaction> getGetTransactionByIdMethod() {
    io.grpc.MethodDescriptor<GrpcAPI.BytesMessage, org.tron.trident.proto.Chain.Transaction> getGetTransactionByIdMethod;
    if ((getGetTransactionByIdMethod = WalletSolidityGrpc.getGetTransactionByIdMethod) == null) {
      synchronized (WalletSolidityGrpc.class) {
        if ((getGetTransactionByIdMethod = WalletSolidityGrpc.getGetTransactionByIdMethod) == null) {
          WalletSolidityGrpc.getGetTransactionByIdMethod = getGetTransactionByIdMethod =
              io.grpc.MethodDescriptor.<GrpcAPI.BytesMessage, org.tron.trident.proto.Chain.Transaction>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetTransactionById"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GrpcAPI.BytesMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.trident.proto.Chain.Transaction.getDefaultInstance()))
              .setSchemaDescriptor(new WalletSolidityMethodDescriptorSupplier("GetTransactionById"))
              .build();
        }
      }
    }
    return getGetTransactionByIdMethod;
  }

  private static volatile io.grpc.MethodDescriptor<GrpcAPI.BytesMessage,
      GrpcAPI.NumberMessage> getGetRewardInfoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetRewardInfo",
      requestType = GrpcAPI.BytesMessage.class,
      responseType = GrpcAPI.NumberMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<GrpcAPI.BytesMessage,
      GrpcAPI.NumberMessage> getGetRewardInfoMethod() {
    io.grpc.MethodDescriptor<GrpcAPI.BytesMessage, GrpcAPI.NumberMessage> getGetRewardInfoMethod;
    if ((getGetRewardInfoMethod = WalletSolidityGrpc.getGetRewardInfoMethod) == null) {
      synchronized (WalletSolidityGrpc.class) {
        if ((getGetRewardInfoMethod = WalletSolidityGrpc.getGetRewardInfoMethod) == null) {
          WalletSolidityGrpc.getGetRewardInfoMethod = getGetRewardInfoMethod =
              io.grpc.MethodDescriptor.<GrpcAPI.BytesMessage, GrpcAPI.NumberMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetRewardInfo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GrpcAPI.BytesMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GrpcAPI.NumberMessage.getDefaultInstance()))
              .setSchemaDescriptor(new WalletSolidityMethodDescriptorSupplier("GetRewardInfo"))
              .build();
        }
      }
    }
    return getGetRewardInfoMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static WalletSolidityStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WalletSolidityStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<WalletSolidityStub>() {
        @Override
        public WalletSolidityStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new WalletSolidityStub(channel, callOptions);
        }
      };
    return WalletSolidityStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static WalletSolidityBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WalletSolidityBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<WalletSolidityBlockingStub>() {
        @Override
        public WalletSolidityBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new WalletSolidityBlockingStub(channel, callOptions);
        }
      };
    return WalletSolidityBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static WalletSolidityFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WalletSolidityFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<WalletSolidityFutureStub>() {
        @Override
        public WalletSolidityFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new WalletSolidityFutureStub(channel, callOptions);
        }
      };
    return WalletSolidityFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * NOTE: All other solidified APIs are useless.
   * </pre>
   */
  public static abstract class WalletSolidityImplBase implements io.grpc.BindableService {

    /**
     */
    public void getAccount(GrpcAPI.AccountAddressMessage request,
                           io.grpc.stub.StreamObserver<org.tron.trident.proto.Response.Account> responseObserver) {
      asyncUnimplementedUnaryCall(getGetAccountMethod(), responseObserver);
    }

    /**
     */
    public void getNowBlock2(GrpcAPI.EmptyMessage request,
                             io.grpc.stub.StreamObserver<org.tron.trident.proto.Response.BlockExtention> responseObserver) {
      asyncUnimplementedUnaryCall(getGetNowBlock2Method(), responseObserver);
    }

    /**
     * <pre>
     * rpc GetBlockByLatestNum2(NumberMessage) returns (BlockListExtention) {}
     * </pre>
     */
    public void getTransactionById(GrpcAPI.BytesMessage request,
                                   io.grpc.stub.StreamObserver<org.tron.trident.proto.Chain.Transaction> responseObserver) {
      asyncUnimplementedUnaryCall(getGetTransactionByIdMethod(), responseObserver);
    }

    /**
     */
    public void getRewardInfo(GrpcAPI.BytesMessage request,
                              io.grpc.stub.StreamObserver<GrpcAPI.NumberMessage> responseObserver) {
      asyncUnimplementedUnaryCall(getGetRewardInfoMethod(), responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetAccountMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                GrpcAPI.AccountAddressMessage,
                org.tron.trident.proto.Response.Account>(
                  this, METHODID_GET_ACCOUNT)))
          .addMethod(
            getGetNowBlock2Method(),
            asyncUnaryCall(
              new MethodHandlers<
                GrpcAPI.EmptyMessage,
                org.tron.trident.proto.Response.BlockExtention>(
                  this, METHODID_GET_NOW_BLOCK2)))
          .addMethod(
            getGetTransactionByIdMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                GrpcAPI.BytesMessage,
                org.tron.trident.proto.Chain.Transaction>(
                  this, METHODID_GET_TRANSACTION_BY_ID)))
          .addMethod(
            getGetRewardInfoMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                GrpcAPI.BytesMessage,
                GrpcAPI.NumberMessage>(
                  this, METHODID_GET_REWARD_INFO)))
          .build();
    }
  }

  /**
   * <pre>
   * NOTE: All other solidified APIs are useless.
   * </pre>
   */
  public static final class WalletSolidityStub extends io.grpc.stub.AbstractAsyncStub<WalletSolidityStub> {
    private WalletSolidityStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected WalletSolidityStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WalletSolidityStub(channel, callOptions);
    }

    /**
     */
    public void getAccount(GrpcAPI.AccountAddressMessage request,
                           io.grpc.stub.StreamObserver<org.tron.trident.proto.Response.Account> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetAccountMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getNowBlock2(GrpcAPI.EmptyMessage request,
                             io.grpc.stub.StreamObserver<org.tron.trident.proto.Response.BlockExtention> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetNowBlock2Method(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * rpc GetBlockByLatestNum2(NumberMessage) returns (BlockListExtention) {}
     * </pre>
     */
    public void getTransactionById(GrpcAPI.BytesMessage request,
                                   io.grpc.stub.StreamObserver<org.tron.trident.proto.Chain.Transaction> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetTransactionByIdMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getRewardInfo(GrpcAPI.BytesMessage request,
                              io.grpc.stub.StreamObserver<GrpcAPI.NumberMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetRewardInfoMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * NOTE: All other solidified APIs are useless.
   * </pre>
   */
  public static final class WalletSolidityBlockingStub extends io.grpc.stub.AbstractBlockingStub<WalletSolidityBlockingStub> {
    private WalletSolidityBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected WalletSolidityBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WalletSolidityBlockingStub(channel, callOptions);
    }

    /**
     */
    public org.tron.trident.proto.Response.Account getAccount(GrpcAPI.AccountAddressMessage request) {
      return blockingUnaryCall(
          getChannel(), getGetAccountMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.tron.trident.proto.Response.BlockExtention getNowBlock2(GrpcAPI.EmptyMessage request) {
      return blockingUnaryCall(
          getChannel(), getGetNowBlock2Method(), getCallOptions(), request);
    }

    /**
     * <pre>
     * rpc GetBlockByLatestNum2(NumberMessage) returns (BlockListExtention) {}
     * </pre>
     */
    public org.tron.trident.proto.Chain.Transaction getTransactionById(GrpcAPI.BytesMessage request) {
      return blockingUnaryCall(
          getChannel(), getGetTransactionByIdMethod(), getCallOptions(), request);
    }

    /**
     */
    public GrpcAPI.NumberMessage getRewardInfo(GrpcAPI.BytesMessage request) {
      return blockingUnaryCall(
          getChannel(), getGetRewardInfoMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * NOTE: All other solidified APIs are useless.
   * </pre>
   */
  public static final class WalletSolidityFutureStub extends io.grpc.stub.AbstractFutureStub<WalletSolidityFutureStub> {
    private WalletSolidityFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected WalletSolidityFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WalletSolidityFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.trident.proto.Response.Account> getAccount(
        GrpcAPI.AccountAddressMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getGetAccountMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.trident.proto.Response.BlockExtention> getNowBlock2(
        GrpcAPI.EmptyMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getGetNowBlock2Method(), getCallOptions()), request);
    }

    /**
     * <pre>
     * rpc GetBlockByLatestNum2(NumberMessage) returns (BlockListExtention) {}
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.trident.proto.Chain.Transaction> getTransactionById(
        GrpcAPI.BytesMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getGetTransactionByIdMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<GrpcAPI.NumberMessage> getRewardInfo(
        GrpcAPI.BytesMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getGetRewardInfoMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_ACCOUNT = 0;
  private static final int METHODID_GET_NOW_BLOCK2 = 1;
  private static final int METHODID_GET_TRANSACTION_BY_ID = 2;
  private static final int METHODID_GET_REWARD_INFO = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final WalletSolidityImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(WalletSolidityImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_ACCOUNT:
          serviceImpl.getAccount((GrpcAPI.AccountAddressMessage) request,
              (io.grpc.stub.StreamObserver<org.tron.trident.proto.Response.Account>) responseObserver);
          break;
        case METHODID_GET_NOW_BLOCK2:
          serviceImpl.getNowBlock2((GrpcAPI.EmptyMessage) request,
              (io.grpc.stub.StreamObserver<org.tron.trident.proto.Response.BlockExtention>) responseObserver);
          break;
        case METHODID_GET_TRANSACTION_BY_ID:
          serviceImpl.getTransactionById((GrpcAPI.BytesMessage) request,
              (io.grpc.stub.StreamObserver<org.tron.trident.proto.Chain.Transaction>) responseObserver);
          break;
        case METHODID_GET_REWARD_INFO:
          serviceImpl.getRewardInfo((GrpcAPI.BytesMessage) request,
              (io.grpc.stub.StreamObserver<GrpcAPI.NumberMessage>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class WalletSolidityBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    WalletSolidityBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return GrpcAPI.getDescriptor();
    }

    @Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("WalletSolidity");
    }
  }

  private static final class WalletSolidityFileDescriptorSupplier
      extends WalletSolidityBaseDescriptorSupplier {
    WalletSolidityFileDescriptorSupplier() {}
  }

  private static final class WalletSolidityMethodDescriptorSupplier
      extends WalletSolidityBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    WalletSolidityMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (WalletSolidityGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new WalletSolidityFileDescriptorSupplier())
              .addMethod(getGetAccountMethod())
              .addMethod(getGetNowBlock2Method())
              .addMethod(getGetTransactionByIdMethod())
              .addMethod(getGetRewardInfoMethod())
              .build();
        }
      }
    }
    return result;
  }
}
