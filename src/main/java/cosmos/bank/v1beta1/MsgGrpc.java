package cosmos.bank.v1beta1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Msg defines the bank Msg service.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.45.1)",
    comments = "Source: cosmos/bank/v1beta1/tx.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class MsgGrpc {

  private MsgGrpc() {}

  public static final String SERVICE_NAME = "cosmos.bank.v1beta1.Msg";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<cosmos.bank.v1beta1.Tx.MsgSend,
      cosmos.bank.v1beta1.Tx.MsgSendResponse> getSendMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Send",
      requestType = cosmos.bank.v1beta1.Tx.MsgSend.class,
      responseType = cosmos.bank.v1beta1.Tx.MsgSendResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.bank.v1beta1.Tx.MsgSend,
      cosmos.bank.v1beta1.Tx.MsgSendResponse> getSendMethod() {
    io.grpc.MethodDescriptor<cosmos.bank.v1beta1.Tx.MsgSend, cosmos.bank.v1beta1.Tx.MsgSendResponse> getSendMethod;
    if ((getSendMethod = MsgGrpc.getSendMethod) == null) {
      synchronized (MsgGrpc.class) {
        if ((getSendMethod = MsgGrpc.getSendMethod) == null) {
          MsgGrpc.getSendMethod = getSendMethod =
              io.grpc.MethodDescriptor.<cosmos.bank.v1beta1.Tx.MsgSend, cosmos.bank.v1beta1.Tx.MsgSendResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Send"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.bank.v1beta1.Tx.MsgSend.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.bank.v1beta1.Tx.MsgSendResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MsgMethodDescriptorSupplier("Send"))
              .build();
        }
      }
    }
    return getSendMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cosmos.bank.v1beta1.Tx.MsgMultiSend,
      cosmos.bank.v1beta1.Tx.MsgMultiSendResponse> getMultiSendMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "MultiSend",
      requestType = cosmos.bank.v1beta1.Tx.MsgMultiSend.class,
      responseType = cosmos.bank.v1beta1.Tx.MsgMultiSendResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.bank.v1beta1.Tx.MsgMultiSend,
      cosmos.bank.v1beta1.Tx.MsgMultiSendResponse> getMultiSendMethod() {
    io.grpc.MethodDescriptor<cosmos.bank.v1beta1.Tx.MsgMultiSend, cosmos.bank.v1beta1.Tx.MsgMultiSendResponse> getMultiSendMethod;
    if ((getMultiSendMethod = MsgGrpc.getMultiSendMethod) == null) {
      synchronized (MsgGrpc.class) {
        if ((getMultiSendMethod = MsgGrpc.getMultiSendMethod) == null) {
          MsgGrpc.getMultiSendMethod = getMultiSendMethod =
              io.grpc.MethodDescriptor.<cosmos.bank.v1beta1.Tx.MsgMultiSend, cosmos.bank.v1beta1.Tx.MsgMultiSendResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "MultiSend"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.bank.v1beta1.Tx.MsgMultiSend.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.bank.v1beta1.Tx.MsgMultiSendResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MsgMethodDescriptorSupplier("MultiSend"))
              .build();
        }
      }
    }
    return getMultiSendMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cosmos.bank.v1beta1.Tx.MsgBurn,
      cosmos.bank.v1beta1.Tx.MsgBurnResponse> getBurnMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Burn",
      requestType = cosmos.bank.v1beta1.Tx.MsgBurn.class,
      responseType = cosmos.bank.v1beta1.Tx.MsgBurnResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.bank.v1beta1.Tx.MsgBurn,
      cosmos.bank.v1beta1.Tx.MsgBurnResponse> getBurnMethod() {
    io.grpc.MethodDescriptor<cosmos.bank.v1beta1.Tx.MsgBurn, cosmos.bank.v1beta1.Tx.MsgBurnResponse> getBurnMethod;
    if ((getBurnMethod = MsgGrpc.getBurnMethod) == null) {
      synchronized (MsgGrpc.class) {
        if ((getBurnMethod = MsgGrpc.getBurnMethod) == null) {
          MsgGrpc.getBurnMethod = getBurnMethod =
              io.grpc.MethodDescriptor.<cosmos.bank.v1beta1.Tx.MsgBurn, cosmos.bank.v1beta1.Tx.MsgBurnResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Burn"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.bank.v1beta1.Tx.MsgBurn.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.bank.v1beta1.Tx.MsgBurnResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MsgMethodDescriptorSupplier("Burn"))
              .build();
        }
      }
    }
    return getBurnMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cosmos.bank.v1beta1.Tx.MsgUpdateParams,
      cosmos.bank.v1beta1.Tx.MsgUpdateParamsResponse> getUpdateParamsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateParams",
      requestType = cosmos.bank.v1beta1.Tx.MsgUpdateParams.class,
      responseType = cosmos.bank.v1beta1.Tx.MsgUpdateParamsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.bank.v1beta1.Tx.MsgUpdateParams,
      cosmos.bank.v1beta1.Tx.MsgUpdateParamsResponse> getUpdateParamsMethod() {
    io.grpc.MethodDescriptor<cosmos.bank.v1beta1.Tx.MsgUpdateParams, cosmos.bank.v1beta1.Tx.MsgUpdateParamsResponse> getUpdateParamsMethod;
    if ((getUpdateParamsMethod = MsgGrpc.getUpdateParamsMethod) == null) {
      synchronized (MsgGrpc.class) {
        if ((getUpdateParamsMethod = MsgGrpc.getUpdateParamsMethod) == null) {
          MsgGrpc.getUpdateParamsMethod = getUpdateParamsMethod =
              io.grpc.MethodDescriptor.<cosmos.bank.v1beta1.Tx.MsgUpdateParams, cosmos.bank.v1beta1.Tx.MsgUpdateParamsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpdateParams"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.bank.v1beta1.Tx.MsgUpdateParams.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.bank.v1beta1.Tx.MsgUpdateParamsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MsgMethodDescriptorSupplier("UpdateParams"))
              .build();
        }
      }
    }
    return getUpdateParamsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cosmos.bank.v1beta1.Tx.MsgSetSendEnabled,
      cosmos.bank.v1beta1.Tx.MsgSetSendEnabledResponse> getSetSendEnabledMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SetSendEnabled",
      requestType = cosmos.bank.v1beta1.Tx.MsgSetSendEnabled.class,
      responseType = cosmos.bank.v1beta1.Tx.MsgSetSendEnabledResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.bank.v1beta1.Tx.MsgSetSendEnabled,
      cosmos.bank.v1beta1.Tx.MsgSetSendEnabledResponse> getSetSendEnabledMethod() {
    io.grpc.MethodDescriptor<cosmos.bank.v1beta1.Tx.MsgSetSendEnabled, cosmos.bank.v1beta1.Tx.MsgSetSendEnabledResponse> getSetSendEnabledMethod;
    if ((getSetSendEnabledMethod = MsgGrpc.getSetSendEnabledMethod) == null) {
      synchronized (MsgGrpc.class) {
        if ((getSetSendEnabledMethod = MsgGrpc.getSetSendEnabledMethod) == null) {
          MsgGrpc.getSetSendEnabledMethod = getSetSendEnabledMethod =
              io.grpc.MethodDescriptor.<cosmos.bank.v1beta1.Tx.MsgSetSendEnabled, cosmos.bank.v1beta1.Tx.MsgSetSendEnabledResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SetSendEnabled"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.bank.v1beta1.Tx.MsgSetSendEnabled.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.bank.v1beta1.Tx.MsgSetSendEnabledResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MsgMethodDescriptorSupplier("SetSendEnabled"))
              .build();
        }
      }
    }
    return getSetSendEnabledMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static MsgStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MsgStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MsgStub>() {
        @java.lang.Override
        public MsgStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MsgStub(channel, callOptions);
        }
      };
    return MsgStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static MsgBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MsgBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MsgBlockingStub>() {
        @java.lang.Override
        public MsgBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MsgBlockingStub(channel, callOptions);
        }
      };
    return MsgBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static MsgFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MsgFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MsgFutureStub>() {
        @java.lang.Override
        public MsgFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MsgFutureStub(channel, callOptions);
        }
      };
    return MsgFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Msg defines the bank Msg service.
   * </pre>
   */
  public static abstract class MsgImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Send defines a method for sending coins from one account to another account.
     * </pre>
     */
    public void send(cosmos.bank.v1beta1.Tx.MsgSend request,
        io.grpc.stub.StreamObserver<cosmos.bank.v1beta1.Tx.MsgSendResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSendMethod(), responseObserver);
    }

    /**
     * <pre>
     * MultiSend defines a method for sending coins from some accounts to other accounts.
     * </pre>
     */
    public void multiSend(cosmos.bank.v1beta1.Tx.MsgMultiSend request,
        io.grpc.stub.StreamObserver<cosmos.bank.v1beta1.Tx.MsgMultiSendResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getMultiSendMethod(), responseObserver);
    }

    /**
     * <pre>
     * Burn defines a method for burning coins by an account.
     * Since: cosmos-sdk 0.51
     * </pre>
     */
    public void burn(cosmos.bank.v1beta1.Tx.MsgBurn request,
        io.grpc.stub.StreamObserver<cosmos.bank.v1beta1.Tx.MsgBurnResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getBurnMethod(), responseObserver);
    }

    /**
     * <pre>
     * UpdateParams defines a governance operation for updating the x/bank module parameters.
     * The authority is defined in the keeper.
     * Since: cosmos-sdk 0.47
     * </pre>
     */
    public void updateParams(cosmos.bank.v1beta1.Tx.MsgUpdateParams request,
        io.grpc.stub.StreamObserver<cosmos.bank.v1beta1.Tx.MsgUpdateParamsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateParamsMethod(), responseObserver);
    }

    /**
     * <pre>
     * SetSendEnabled is a governance operation for setting the SendEnabled flag
     * on any number of Denoms. Only the entries to add or update should be
     * included. Entries that already exist in the store, but that aren't
     * included in this message, will be left unchanged.
     * Since: cosmos-sdk 0.47
     * </pre>
     */
    public void setSendEnabled(cosmos.bank.v1beta1.Tx.MsgSetSendEnabled request,
        io.grpc.stub.StreamObserver<cosmos.bank.v1beta1.Tx.MsgSetSendEnabledResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSetSendEnabledMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSendMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.bank.v1beta1.Tx.MsgSend,
                cosmos.bank.v1beta1.Tx.MsgSendResponse>(
                  this, METHODID_SEND)))
          .addMethod(
            getMultiSendMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.bank.v1beta1.Tx.MsgMultiSend,
                cosmos.bank.v1beta1.Tx.MsgMultiSendResponse>(
                  this, METHODID_MULTI_SEND)))
          .addMethod(
            getBurnMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.bank.v1beta1.Tx.MsgBurn,
                cosmos.bank.v1beta1.Tx.MsgBurnResponse>(
                  this, METHODID_BURN)))
          .addMethod(
            getUpdateParamsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.bank.v1beta1.Tx.MsgUpdateParams,
                cosmos.bank.v1beta1.Tx.MsgUpdateParamsResponse>(
                  this, METHODID_UPDATE_PARAMS)))
          .addMethod(
            getSetSendEnabledMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.bank.v1beta1.Tx.MsgSetSendEnabled,
                cosmos.bank.v1beta1.Tx.MsgSetSendEnabledResponse>(
                  this, METHODID_SET_SEND_ENABLED)))
          .build();
    }
  }

  /**
   * <pre>
   * Msg defines the bank Msg service.
   * </pre>
   */
  public static final class MsgStub extends io.grpc.stub.AbstractAsyncStub<MsgStub> {
    private MsgStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MsgStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MsgStub(channel, callOptions);
    }

    /**
     * <pre>
     * Send defines a method for sending coins from one account to another account.
     * </pre>
     */
    public void send(cosmos.bank.v1beta1.Tx.MsgSend request,
        io.grpc.stub.StreamObserver<cosmos.bank.v1beta1.Tx.MsgSendResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSendMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * MultiSend defines a method for sending coins from some accounts to other accounts.
     * </pre>
     */
    public void multiSend(cosmos.bank.v1beta1.Tx.MsgMultiSend request,
        io.grpc.stub.StreamObserver<cosmos.bank.v1beta1.Tx.MsgMultiSendResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getMultiSendMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Burn defines a method for burning coins by an account.
     * Since: cosmos-sdk 0.51
     * </pre>
     */
    public void burn(cosmos.bank.v1beta1.Tx.MsgBurn request,
        io.grpc.stub.StreamObserver<cosmos.bank.v1beta1.Tx.MsgBurnResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getBurnMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * UpdateParams defines a governance operation for updating the x/bank module parameters.
     * The authority is defined in the keeper.
     * Since: cosmos-sdk 0.47
     * </pre>
     */
    public void updateParams(cosmos.bank.v1beta1.Tx.MsgUpdateParams request,
        io.grpc.stub.StreamObserver<cosmos.bank.v1beta1.Tx.MsgUpdateParamsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateParamsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * SetSendEnabled is a governance operation for setting the SendEnabled flag
     * on any number of Denoms. Only the entries to add or update should be
     * included. Entries that already exist in the store, but that aren't
     * included in this message, will be left unchanged.
     * Since: cosmos-sdk 0.47
     * </pre>
     */
    public void setSendEnabled(cosmos.bank.v1beta1.Tx.MsgSetSendEnabled request,
        io.grpc.stub.StreamObserver<cosmos.bank.v1beta1.Tx.MsgSetSendEnabledResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSetSendEnabledMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * Msg defines the bank Msg service.
   * </pre>
   */
  public static final class MsgBlockingStub extends io.grpc.stub.AbstractBlockingStub<MsgBlockingStub> {
    private MsgBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MsgBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MsgBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Send defines a method for sending coins from one account to another account.
     * </pre>
     */
    public cosmos.bank.v1beta1.Tx.MsgSendResponse send(cosmos.bank.v1beta1.Tx.MsgSend request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSendMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * MultiSend defines a method for sending coins from some accounts to other accounts.
     * </pre>
     */
    public cosmos.bank.v1beta1.Tx.MsgMultiSendResponse multiSend(cosmos.bank.v1beta1.Tx.MsgMultiSend request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getMultiSendMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Burn defines a method for burning coins by an account.
     * Since: cosmos-sdk 0.51
     * </pre>
     */
    public cosmos.bank.v1beta1.Tx.MsgBurnResponse burn(cosmos.bank.v1beta1.Tx.MsgBurn request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getBurnMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * UpdateParams defines a governance operation for updating the x/bank module parameters.
     * The authority is defined in the keeper.
     * Since: cosmos-sdk 0.47
     * </pre>
     */
    public cosmos.bank.v1beta1.Tx.MsgUpdateParamsResponse updateParams(cosmos.bank.v1beta1.Tx.MsgUpdateParams request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateParamsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * SetSendEnabled is a governance operation for setting the SendEnabled flag
     * on any number of Denoms. Only the entries to add or update should be
     * included. Entries that already exist in the store, but that aren't
     * included in this message, will be left unchanged.
     * Since: cosmos-sdk 0.47
     * </pre>
     */
    public cosmos.bank.v1beta1.Tx.MsgSetSendEnabledResponse setSendEnabled(cosmos.bank.v1beta1.Tx.MsgSetSendEnabled request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSetSendEnabledMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * Msg defines the bank Msg service.
   * </pre>
   */
  public static final class MsgFutureStub extends io.grpc.stub.AbstractFutureStub<MsgFutureStub> {
    private MsgFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MsgFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MsgFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Send defines a method for sending coins from one account to another account.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.bank.v1beta1.Tx.MsgSendResponse> send(
        cosmos.bank.v1beta1.Tx.MsgSend request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSendMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * MultiSend defines a method for sending coins from some accounts to other accounts.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.bank.v1beta1.Tx.MsgMultiSendResponse> multiSend(
        cosmos.bank.v1beta1.Tx.MsgMultiSend request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getMultiSendMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Burn defines a method for burning coins by an account.
     * Since: cosmos-sdk 0.51
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.bank.v1beta1.Tx.MsgBurnResponse> burn(
        cosmos.bank.v1beta1.Tx.MsgBurn request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getBurnMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * UpdateParams defines a governance operation for updating the x/bank module parameters.
     * The authority is defined in the keeper.
     * Since: cosmos-sdk 0.47
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.bank.v1beta1.Tx.MsgUpdateParamsResponse> updateParams(
        cosmos.bank.v1beta1.Tx.MsgUpdateParams request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateParamsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * SetSendEnabled is a governance operation for setting the SendEnabled flag
     * on any number of Denoms. Only the entries to add or update should be
     * included. Entries that already exist in the store, but that aren't
     * included in this message, will be left unchanged.
     * Since: cosmos-sdk 0.47
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.bank.v1beta1.Tx.MsgSetSendEnabledResponse> setSendEnabled(
        cosmos.bank.v1beta1.Tx.MsgSetSendEnabled request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSetSendEnabledMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SEND = 0;
  private static final int METHODID_MULTI_SEND = 1;
  private static final int METHODID_BURN = 2;
  private static final int METHODID_UPDATE_PARAMS = 3;
  private static final int METHODID_SET_SEND_ENABLED = 4;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final MsgImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(MsgImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SEND:
          serviceImpl.send((cosmos.bank.v1beta1.Tx.MsgSend) request,
              (io.grpc.stub.StreamObserver<cosmos.bank.v1beta1.Tx.MsgSendResponse>) responseObserver);
          break;
        case METHODID_MULTI_SEND:
          serviceImpl.multiSend((cosmos.bank.v1beta1.Tx.MsgMultiSend) request,
              (io.grpc.stub.StreamObserver<cosmos.bank.v1beta1.Tx.MsgMultiSendResponse>) responseObserver);
          break;
        case METHODID_BURN:
          serviceImpl.burn((cosmos.bank.v1beta1.Tx.MsgBurn) request,
              (io.grpc.stub.StreamObserver<cosmos.bank.v1beta1.Tx.MsgBurnResponse>) responseObserver);
          break;
        case METHODID_UPDATE_PARAMS:
          serviceImpl.updateParams((cosmos.bank.v1beta1.Tx.MsgUpdateParams) request,
              (io.grpc.stub.StreamObserver<cosmos.bank.v1beta1.Tx.MsgUpdateParamsResponse>) responseObserver);
          break;
        case METHODID_SET_SEND_ENABLED:
          serviceImpl.setSendEnabled((cosmos.bank.v1beta1.Tx.MsgSetSendEnabled) request,
              (io.grpc.stub.StreamObserver<cosmos.bank.v1beta1.Tx.MsgSetSendEnabledResponse>) responseObserver);
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

  private static abstract class MsgBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    MsgBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return cosmos.bank.v1beta1.Tx.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Msg");
    }
  }

  private static final class MsgFileDescriptorSupplier
      extends MsgBaseDescriptorSupplier {
    MsgFileDescriptorSupplier() {}
  }

  private static final class MsgMethodDescriptorSupplier
      extends MsgBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    MsgMethodDescriptorSupplier(String methodName) {
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
      synchronized (MsgGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new MsgFileDescriptorSupplier())
              .addMethod(getSendMethod())
              .addMethod(getMultiSendMethod())
              .addMethod(getBurnMethod())
              .addMethod(getUpdateParamsMethod())
              .addMethod(getSetSendEnabledMethod())
              .build();
        }
      }
    }
    return result;
  }
}
