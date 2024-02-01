package cosmos.staking.v1beta1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Msg defines the staking Msg service.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.45.1)",
    comments = "Source: cosmos/staking/v1beta1/tx.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class MsgGrpc {

  private MsgGrpc() {}

  public static final String SERVICE_NAME = "cosmos.staking.v1beta1.Msg";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<cosmos.staking.v1beta1.Tx.MsgCreateValidator,
      cosmos.staking.v1beta1.Tx.MsgCreateValidatorResponse> getCreateValidatorMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateValidator",
      requestType = cosmos.staking.v1beta1.Tx.MsgCreateValidator.class,
      responseType = cosmos.staking.v1beta1.Tx.MsgCreateValidatorResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.staking.v1beta1.Tx.MsgCreateValidator,
      cosmos.staking.v1beta1.Tx.MsgCreateValidatorResponse> getCreateValidatorMethod() {
    io.grpc.MethodDescriptor<cosmos.staking.v1beta1.Tx.MsgCreateValidator, cosmos.staking.v1beta1.Tx.MsgCreateValidatorResponse> getCreateValidatorMethod;
    if ((getCreateValidatorMethod = MsgGrpc.getCreateValidatorMethod) == null) {
      synchronized (MsgGrpc.class) {
        if ((getCreateValidatorMethod = MsgGrpc.getCreateValidatorMethod) == null) {
          MsgGrpc.getCreateValidatorMethod = getCreateValidatorMethod =
              io.grpc.MethodDescriptor.<cosmos.staking.v1beta1.Tx.MsgCreateValidator, cosmos.staking.v1beta1.Tx.MsgCreateValidatorResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateValidator"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.Tx.MsgCreateValidator.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.Tx.MsgCreateValidatorResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MsgMethodDescriptorSupplier("CreateValidator"))
              .build();
        }
      }
    }
    return getCreateValidatorMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cosmos.staking.v1beta1.Tx.MsgEditValidator,
      cosmos.staking.v1beta1.Tx.MsgEditValidatorResponse> getEditValidatorMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "EditValidator",
      requestType = cosmos.staking.v1beta1.Tx.MsgEditValidator.class,
      responseType = cosmos.staking.v1beta1.Tx.MsgEditValidatorResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.staking.v1beta1.Tx.MsgEditValidator,
      cosmos.staking.v1beta1.Tx.MsgEditValidatorResponse> getEditValidatorMethod() {
    io.grpc.MethodDescriptor<cosmos.staking.v1beta1.Tx.MsgEditValidator, cosmos.staking.v1beta1.Tx.MsgEditValidatorResponse> getEditValidatorMethod;
    if ((getEditValidatorMethod = MsgGrpc.getEditValidatorMethod) == null) {
      synchronized (MsgGrpc.class) {
        if ((getEditValidatorMethod = MsgGrpc.getEditValidatorMethod) == null) {
          MsgGrpc.getEditValidatorMethod = getEditValidatorMethod =
              io.grpc.MethodDescriptor.<cosmos.staking.v1beta1.Tx.MsgEditValidator, cosmos.staking.v1beta1.Tx.MsgEditValidatorResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "EditValidator"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.Tx.MsgEditValidator.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.Tx.MsgEditValidatorResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MsgMethodDescriptorSupplier("EditValidator"))
              .build();
        }
      }
    }
    return getEditValidatorMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cosmos.staking.v1beta1.Tx.MsgDelegate,
      cosmos.staking.v1beta1.Tx.MsgDelegateResponse> getDelegateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Delegate",
      requestType = cosmos.staking.v1beta1.Tx.MsgDelegate.class,
      responseType = cosmos.staking.v1beta1.Tx.MsgDelegateResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.staking.v1beta1.Tx.MsgDelegate,
      cosmos.staking.v1beta1.Tx.MsgDelegateResponse> getDelegateMethod() {
    io.grpc.MethodDescriptor<cosmos.staking.v1beta1.Tx.MsgDelegate, cosmos.staking.v1beta1.Tx.MsgDelegateResponse> getDelegateMethod;
    if ((getDelegateMethod = MsgGrpc.getDelegateMethod) == null) {
      synchronized (MsgGrpc.class) {
        if ((getDelegateMethod = MsgGrpc.getDelegateMethod) == null) {
          MsgGrpc.getDelegateMethod = getDelegateMethod =
              io.grpc.MethodDescriptor.<cosmos.staking.v1beta1.Tx.MsgDelegate, cosmos.staking.v1beta1.Tx.MsgDelegateResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Delegate"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.Tx.MsgDelegate.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.Tx.MsgDelegateResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MsgMethodDescriptorSupplier("Delegate"))
              .build();
        }
      }
    }
    return getDelegateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cosmos.staking.v1beta1.Tx.MsgBeginRedelegate,
      cosmos.staking.v1beta1.Tx.MsgBeginRedelegateResponse> getBeginRedelegateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "BeginRedelegate",
      requestType = cosmos.staking.v1beta1.Tx.MsgBeginRedelegate.class,
      responseType = cosmos.staking.v1beta1.Tx.MsgBeginRedelegateResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.staking.v1beta1.Tx.MsgBeginRedelegate,
      cosmos.staking.v1beta1.Tx.MsgBeginRedelegateResponse> getBeginRedelegateMethod() {
    io.grpc.MethodDescriptor<cosmos.staking.v1beta1.Tx.MsgBeginRedelegate, cosmos.staking.v1beta1.Tx.MsgBeginRedelegateResponse> getBeginRedelegateMethod;
    if ((getBeginRedelegateMethod = MsgGrpc.getBeginRedelegateMethod) == null) {
      synchronized (MsgGrpc.class) {
        if ((getBeginRedelegateMethod = MsgGrpc.getBeginRedelegateMethod) == null) {
          MsgGrpc.getBeginRedelegateMethod = getBeginRedelegateMethod =
              io.grpc.MethodDescriptor.<cosmos.staking.v1beta1.Tx.MsgBeginRedelegate, cosmos.staking.v1beta1.Tx.MsgBeginRedelegateResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "BeginRedelegate"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.Tx.MsgBeginRedelegate.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.Tx.MsgBeginRedelegateResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MsgMethodDescriptorSupplier("BeginRedelegate"))
              .build();
        }
      }
    }
    return getBeginRedelegateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cosmos.staking.v1beta1.Tx.MsgUndelegate,
      cosmos.staking.v1beta1.Tx.MsgUndelegateResponse> getUndelegateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Undelegate",
      requestType = cosmos.staking.v1beta1.Tx.MsgUndelegate.class,
      responseType = cosmos.staking.v1beta1.Tx.MsgUndelegateResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.staking.v1beta1.Tx.MsgUndelegate,
      cosmos.staking.v1beta1.Tx.MsgUndelegateResponse> getUndelegateMethod() {
    io.grpc.MethodDescriptor<cosmos.staking.v1beta1.Tx.MsgUndelegate, cosmos.staking.v1beta1.Tx.MsgUndelegateResponse> getUndelegateMethod;
    if ((getUndelegateMethod = MsgGrpc.getUndelegateMethod) == null) {
      synchronized (MsgGrpc.class) {
        if ((getUndelegateMethod = MsgGrpc.getUndelegateMethod) == null) {
          MsgGrpc.getUndelegateMethod = getUndelegateMethod =
              io.grpc.MethodDescriptor.<cosmos.staking.v1beta1.Tx.MsgUndelegate, cosmos.staking.v1beta1.Tx.MsgUndelegateResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Undelegate"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.Tx.MsgUndelegate.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.Tx.MsgUndelegateResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MsgMethodDescriptorSupplier("Undelegate"))
              .build();
        }
      }
    }
    return getUndelegateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cosmos.staking.v1beta1.Tx.MsgUnbondValidator,
      cosmos.staking.v1beta1.Tx.MsgUnbondValidatorResponse> getUnbondValidatorMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UnbondValidator",
      requestType = cosmos.staking.v1beta1.Tx.MsgUnbondValidator.class,
      responseType = cosmos.staking.v1beta1.Tx.MsgUnbondValidatorResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.staking.v1beta1.Tx.MsgUnbondValidator,
      cosmos.staking.v1beta1.Tx.MsgUnbondValidatorResponse> getUnbondValidatorMethod() {
    io.grpc.MethodDescriptor<cosmos.staking.v1beta1.Tx.MsgUnbondValidator, cosmos.staking.v1beta1.Tx.MsgUnbondValidatorResponse> getUnbondValidatorMethod;
    if ((getUnbondValidatorMethod = MsgGrpc.getUnbondValidatorMethod) == null) {
      synchronized (MsgGrpc.class) {
        if ((getUnbondValidatorMethod = MsgGrpc.getUnbondValidatorMethod) == null) {
          MsgGrpc.getUnbondValidatorMethod = getUnbondValidatorMethod =
              io.grpc.MethodDescriptor.<cosmos.staking.v1beta1.Tx.MsgUnbondValidator, cosmos.staking.v1beta1.Tx.MsgUnbondValidatorResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UnbondValidator"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.Tx.MsgUnbondValidator.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.Tx.MsgUnbondValidatorResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MsgMethodDescriptorSupplier("UnbondValidator"))
              .build();
        }
      }
    }
    return getUnbondValidatorMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cosmos.staking.v1beta1.Tx.MsgCancelUnbondingDelegation,
      cosmos.staking.v1beta1.Tx.MsgCancelUnbondingDelegationResponse> getCancelUnbondingDelegationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CancelUnbondingDelegation",
      requestType = cosmos.staking.v1beta1.Tx.MsgCancelUnbondingDelegation.class,
      responseType = cosmos.staking.v1beta1.Tx.MsgCancelUnbondingDelegationResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.staking.v1beta1.Tx.MsgCancelUnbondingDelegation,
      cosmos.staking.v1beta1.Tx.MsgCancelUnbondingDelegationResponse> getCancelUnbondingDelegationMethod() {
    io.grpc.MethodDescriptor<cosmos.staking.v1beta1.Tx.MsgCancelUnbondingDelegation, cosmos.staking.v1beta1.Tx.MsgCancelUnbondingDelegationResponse> getCancelUnbondingDelegationMethod;
    if ((getCancelUnbondingDelegationMethod = MsgGrpc.getCancelUnbondingDelegationMethod) == null) {
      synchronized (MsgGrpc.class) {
        if ((getCancelUnbondingDelegationMethod = MsgGrpc.getCancelUnbondingDelegationMethod) == null) {
          MsgGrpc.getCancelUnbondingDelegationMethod = getCancelUnbondingDelegationMethod =
              io.grpc.MethodDescriptor.<cosmos.staking.v1beta1.Tx.MsgCancelUnbondingDelegation, cosmos.staking.v1beta1.Tx.MsgCancelUnbondingDelegationResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CancelUnbondingDelegation"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.Tx.MsgCancelUnbondingDelegation.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.Tx.MsgCancelUnbondingDelegationResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MsgMethodDescriptorSupplier("CancelUnbondingDelegation"))
              .build();
        }
      }
    }
    return getCancelUnbondingDelegationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cosmos.staking.v1beta1.Tx.MsgTokenizeShares,
      cosmos.staking.v1beta1.Tx.MsgTokenizeSharesResponse> getTokenizeSharesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "TokenizeShares",
      requestType = cosmos.staking.v1beta1.Tx.MsgTokenizeShares.class,
      responseType = cosmos.staking.v1beta1.Tx.MsgTokenizeSharesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.staking.v1beta1.Tx.MsgTokenizeShares,
      cosmos.staking.v1beta1.Tx.MsgTokenizeSharesResponse> getTokenizeSharesMethod() {
    io.grpc.MethodDescriptor<cosmos.staking.v1beta1.Tx.MsgTokenizeShares, cosmos.staking.v1beta1.Tx.MsgTokenizeSharesResponse> getTokenizeSharesMethod;
    if ((getTokenizeSharesMethod = MsgGrpc.getTokenizeSharesMethod) == null) {
      synchronized (MsgGrpc.class) {
        if ((getTokenizeSharesMethod = MsgGrpc.getTokenizeSharesMethod) == null) {
          MsgGrpc.getTokenizeSharesMethod = getTokenizeSharesMethod =
              io.grpc.MethodDescriptor.<cosmos.staking.v1beta1.Tx.MsgTokenizeShares, cosmos.staking.v1beta1.Tx.MsgTokenizeSharesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "TokenizeShares"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.Tx.MsgTokenizeShares.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.Tx.MsgTokenizeSharesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MsgMethodDescriptorSupplier("TokenizeShares"))
              .build();
        }
      }
    }
    return getTokenizeSharesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cosmos.staking.v1beta1.Tx.MsgRedeemTokensForShares,
      cosmos.staking.v1beta1.Tx.MsgRedeemTokensForSharesResponse> getRedeemTokensForSharesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RedeemTokensForShares",
      requestType = cosmos.staking.v1beta1.Tx.MsgRedeemTokensForShares.class,
      responseType = cosmos.staking.v1beta1.Tx.MsgRedeemTokensForSharesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.staking.v1beta1.Tx.MsgRedeemTokensForShares,
      cosmos.staking.v1beta1.Tx.MsgRedeemTokensForSharesResponse> getRedeemTokensForSharesMethod() {
    io.grpc.MethodDescriptor<cosmos.staking.v1beta1.Tx.MsgRedeemTokensForShares, cosmos.staking.v1beta1.Tx.MsgRedeemTokensForSharesResponse> getRedeemTokensForSharesMethod;
    if ((getRedeemTokensForSharesMethod = MsgGrpc.getRedeemTokensForSharesMethod) == null) {
      synchronized (MsgGrpc.class) {
        if ((getRedeemTokensForSharesMethod = MsgGrpc.getRedeemTokensForSharesMethod) == null) {
          MsgGrpc.getRedeemTokensForSharesMethod = getRedeemTokensForSharesMethod =
              io.grpc.MethodDescriptor.<cosmos.staking.v1beta1.Tx.MsgRedeemTokensForShares, cosmos.staking.v1beta1.Tx.MsgRedeemTokensForSharesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RedeemTokensForShares"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.Tx.MsgRedeemTokensForShares.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.Tx.MsgRedeemTokensForSharesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MsgMethodDescriptorSupplier("RedeemTokensForShares"))
              .build();
        }
      }
    }
    return getRedeemTokensForSharesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cosmos.staking.v1beta1.Tx.MsgTransferTokenizeShareRecord,
      cosmos.staking.v1beta1.Tx.MsgTransferTokenizeShareRecordResponse> getTransferTokenizeShareRecordMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "TransferTokenizeShareRecord",
      requestType = cosmos.staking.v1beta1.Tx.MsgTransferTokenizeShareRecord.class,
      responseType = cosmos.staking.v1beta1.Tx.MsgTransferTokenizeShareRecordResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.staking.v1beta1.Tx.MsgTransferTokenizeShareRecord,
      cosmos.staking.v1beta1.Tx.MsgTransferTokenizeShareRecordResponse> getTransferTokenizeShareRecordMethod() {
    io.grpc.MethodDescriptor<cosmos.staking.v1beta1.Tx.MsgTransferTokenizeShareRecord, cosmos.staking.v1beta1.Tx.MsgTransferTokenizeShareRecordResponse> getTransferTokenizeShareRecordMethod;
    if ((getTransferTokenizeShareRecordMethod = MsgGrpc.getTransferTokenizeShareRecordMethod) == null) {
      synchronized (MsgGrpc.class) {
        if ((getTransferTokenizeShareRecordMethod = MsgGrpc.getTransferTokenizeShareRecordMethod) == null) {
          MsgGrpc.getTransferTokenizeShareRecordMethod = getTransferTokenizeShareRecordMethod =
              io.grpc.MethodDescriptor.<cosmos.staking.v1beta1.Tx.MsgTransferTokenizeShareRecord, cosmos.staking.v1beta1.Tx.MsgTransferTokenizeShareRecordResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "TransferTokenizeShareRecord"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.Tx.MsgTransferTokenizeShareRecord.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.Tx.MsgTransferTokenizeShareRecordResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MsgMethodDescriptorSupplier("TransferTokenizeShareRecord"))
              .build();
        }
      }
    }
    return getTransferTokenizeShareRecordMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cosmos.staking.v1beta1.Tx.MsgDisableTokenizeShares,
      cosmos.staking.v1beta1.Tx.MsgDisableTokenizeSharesResponse> getDisableTokenizeSharesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DisableTokenizeShares",
      requestType = cosmos.staking.v1beta1.Tx.MsgDisableTokenizeShares.class,
      responseType = cosmos.staking.v1beta1.Tx.MsgDisableTokenizeSharesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.staking.v1beta1.Tx.MsgDisableTokenizeShares,
      cosmos.staking.v1beta1.Tx.MsgDisableTokenizeSharesResponse> getDisableTokenizeSharesMethod() {
    io.grpc.MethodDescriptor<cosmos.staking.v1beta1.Tx.MsgDisableTokenizeShares, cosmos.staking.v1beta1.Tx.MsgDisableTokenizeSharesResponse> getDisableTokenizeSharesMethod;
    if ((getDisableTokenizeSharesMethod = MsgGrpc.getDisableTokenizeSharesMethod) == null) {
      synchronized (MsgGrpc.class) {
        if ((getDisableTokenizeSharesMethod = MsgGrpc.getDisableTokenizeSharesMethod) == null) {
          MsgGrpc.getDisableTokenizeSharesMethod = getDisableTokenizeSharesMethod =
              io.grpc.MethodDescriptor.<cosmos.staking.v1beta1.Tx.MsgDisableTokenizeShares, cosmos.staking.v1beta1.Tx.MsgDisableTokenizeSharesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DisableTokenizeShares"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.Tx.MsgDisableTokenizeShares.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.Tx.MsgDisableTokenizeSharesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MsgMethodDescriptorSupplier("DisableTokenizeShares"))
              .build();
        }
      }
    }
    return getDisableTokenizeSharesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cosmos.staking.v1beta1.Tx.MsgEnableTokenizeShares,
      cosmos.staking.v1beta1.Tx.MsgEnableTokenizeSharesResponse> getEnableTokenizeSharesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "EnableTokenizeShares",
      requestType = cosmos.staking.v1beta1.Tx.MsgEnableTokenizeShares.class,
      responseType = cosmos.staking.v1beta1.Tx.MsgEnableTokenizeSharesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.staking.v1beta1.Tx.MsgEnableTokenizeShares,
      cosmos.staking.v1beta1.Tx.MsgEnableTokenizeSharesResponse> getEnableTokenizeSharesMethod() {
    io.grpc.MethodDescriptor<cosmos.staking.v1beta1.Tx.MsgEnableTokenizeShares, cosmos.staking.v1beta1.Tx.MsgEnableTokenizeSharesResponse> getEnableTokenizeSharesMethod;
    if ((getEnableTokenizeSharesMethod = MsgGrpc.getEnableTokenizeSharesMethod) == null) {
      synchronized (MsgGrpc.class) {
        if ((getEnableTokenizeSharesMethod = MsgGrpc.getEnableTokenizeSharesMethod) == null) {
          MsgGrpc.getEnableTokenizeSharesMethod = getEnableTokenizeSharesMethod =
              io.grpc.MethodDescriptor.<cosmos.staking.v1beta1.Tx.MsgEnableTokenizeShares, cosmos.staking.v1beta1.Tx.MsgEnableTokenizeSharesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "EnableTokenizeShares"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.Tx.MsgEnableTokenizeShares.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.Tx.MsgEnableTokenizeSharesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MsgMethodDescriptorSupplier("EnableTokenizeShares"))
              .build();
        }
      }
    }
    return getEnableTokenizeSharesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cosmos.staking.v1beta1.Tx.MsgValidatorBond,
      cosmos.staking.v1beta1.Tx.MsgValidatorBondResponse> getValidatorBondMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ValidatorBond",
      requestType = cosmos.staking.v1beta1.Tx.MsgValidatorBond.class,
      responseType = cosmos.staking.v1beta1.Tx.MsgValidatorBondResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.staking.v1beta1.Tx.MsgValidatorBond,
      cosmos.staking.v1beta1.Tx.MsgValidatorBondResponse> getValidatorBondMethod() {
    io.grpc.MethodDescriptor<cosmos.staking.v1beta1.Tx.MsgValidatorBond, cosmos.staking.v1beta1.Tx.MsgValidatorBondResponse> getValidatorBondMethod;
    if ((getValidatorBondMethod = MsgGrpc.getValidatorBondMethod) == null) {
      synchronized (MsgGrpc.class) {
        if ((getValidatorBondMethod = MsgGrpc.getValidatorBondMethod) == null) {
          MsgGrpc.getValidatorBondMethod = getValidatorBondMethod =
              io.grpc.MethodDescriptor.<cosmos.staking.v1beta1.Tx.MsgValidatorBond, cosmos.staking.v1beta1.Tx.MsgValidatorBondResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ValidatorBond"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.Tx.MsgValidatorBond.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.Tx.MsgValidatorBondResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MsgMethodDescriptorSupplier("ValidatorBond"))
              .build();
        }
      }
    }
    return getValidatorBondMethod;
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
   * Msg defines the staking Msg service.
   * </pre>
   */
  public static abstract class MsgImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * CreateValidator defines a method for creating a new validator.
     * </pre>
     */
    public void createValidator(cosmos.staking.v1beta1.Tx.MsgCreateValidator request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.Tx.MsgCreateValidatorResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateValidatorMethod(), responseObserver);
    }

    /**
     * <pre>
     * EditValidator defines a method for editing an existing validator.
     * </pre>
     */
    public void editValidator(cosmos.staking.v1beta1.Tx.MsgEditValidator request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.Tx.MsgEditValidatorResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getEditValidatorMethod(), responseObserver);
    }

    /**
     * <pre>
     * Delegate defines a method for performing a delegation of coins
     * from a delegator to a validator.
     * </pre>
     */
    public void delegate(cosmos.staking.v1beta1.Tx.MsgDelegate request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.Tx.MsgDelegateResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDelegateMethod(), responseObserver);
    }

    /**
     * <pre>
     * BeginRedelegate defines a method for performing a redelegation
     * of coins from a delegator and source validator to a destination validator.
     * </pre>
     */
    public void beginRedelegate(cosmos.staking.v1beta1.Tx.MsgBeginRedelegate request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.Tx.MsgBeginRedelegateResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getBeginRedelegateMethod(), responseObserver);
    }

    /**
     * <pre>
     * Undelegate defines a method for performing an undelegation from a
     * delegate and a validator.
     * This allows a validator to stop their services and jail themselves without
     * experiencing a slash
     * </pre>
     */
    public void undelegate(cosmos.staking.v1beta1.Tx.MsgUndelegate request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.Tx.MsgUndelegateResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUndelegateMethod(), responseObserver);
    }

    /**
     * <pre>
     * UnbondValidator defines a method for performing the status transition for a validator
     * from bonded to unbonding
     * </pre>
     */
    public void unbondValidator(cosmos.staking.v1beta1.Tx.MsgUnbondValidator request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.Tx.MsgUnbondValidatorResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUnbondValidatorMethod(), responseObserver);
    }

    /**
     * <pre>
     * CancelUnbondingDelegation defines a method for performing canceling the unbonding delegation
     * and delegate back to previous validator.
     * This has been backported from SDK 46 as a desirable safety feature for LSM.
     * If a liquid staking provider is exploited and the exploiter initiates an undelegation,
     * having access to CancelUnbondingDelegation allows the liquid staking provider to cancel
     * the undelegation with a software upgrade and thus avoid loss of user funds
     * </pre>
     */
    public void cancelUnbondingDelegation(cosmos.staking.v1beta1.Tx.MsgCancelUnbondingDelegation request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.Tx.MsgCancelUnbondingDelegationResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCancelUnbondingDelegationMethod(), responseObserver);
    }

    /**
     * <pre>
     * TokenizeShares defines a method for tokenizing shares from a validator.
     * </pre>
     */
    public void tokenizeShares(cosmos.staking.v1beta1.Tx.MsgTokenizeShares request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.Tx.MsgTokenizeSharesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getTokenizeSharesMethod(), responseObserver);
    }

    /**
     * <pre>
     * RedeemTokensForShares defines a method for redeeming tokens from a validator for
     * shares.
     * </pre>
     */
    public void redeemTokensForShares(cosmos.staking.v1beta1.Tx.MsgRedeemTokensForShares request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.Tx.MsgRedeemTokensForSharesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRedeemTokensForSharesMethod(), responseObserver);
    }

    /**
     * <pre>
     * TransferTokenizeShareRecord defines a method to transfer ownership of
     * TokenizeShareRecord
     * </pre>
     */
    public void transferTokenizeShareRecord(cosmos.staking.v1beta1.Tx.MsgTransferTokenizeShareRecord request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.Tx.MsgTransferTokenizeShareRecordResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getTransferTokenizeShareRecordMethod(), responseObserver);
    }

    /**
     * <pre>
     * DisableTokenizeShares defines a method to prevent the tokenization of an addresses stake
     * </pre>
     */
    public void disableTokenizeShares(cosmos.staking.v1beta1.Tx.MsgDisableTokenizeShares request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.Tx.MsgDisableTokenizeSharesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDisableTokenizeSharesMethod(), responseObserver);
    }

    /**
     * <pre>
     * EnableTokenizeShares defines a method to re-enable the tokenization of an addresseses stake
     * after it has been disabled
     * </pre>
     */
    public void enableTokenizeShares(cosmos.staking.v1beta1.Tx.MsgEnableTokenizeShares request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.Tx.MsgEnableTokenizeSharesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getEnableTokenizeSharesMethod(), responseObserver);
    }

    /**
     * <pre>
     * ValidatorBond defines a method for performing a validator self-bond
     * </pre>
     */
    public void validatorBond(cosmos.staking.v1beta1.Tx.MsgValidatorBond request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.Tx.MsgValidatorBondResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getValidatorBondMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCreateValidatorMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.staking.v1beta1.Tx.MsgCreateValidator,
                cosmos.staking.v1beta1.Tx.MsgCreateValidatorResponse>(
                  this, METHODID_CREATE_VALIDATOR)))
          .addMethod(
            getEditValidatorMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.staking.v1beta1.Tx.MsgEditValidator,
                cosmos.staking.v1beta1.Tx.MsgEditValidatorResponse>(
                  this, METHODID_EDIT_VALIDATOR)))
          .addMethod(
            getDelegateMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.staking.v1beta1.Tx.MsgDelegate,
                cosmos.staking.v1beta1.Tx.MsgDelegateResponse>(
                  this, METHODID_DELEGATE)))
          .addMethod(
            getBeginRedelegateMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.staking.v1beta1.Tx.MsgBeginRedelegate,
                cosmos.staking.v1beta1.Tx.MsgBeginRedelegateResponse>(
                  this, METHODID_BEGIN_REDELEGATE)))
          .addMethod(
            getUndelegateMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.staking.v1beta1.Tx.MsgUndelegate,
                cosmos.staking.v1beta1.Tx.MsgUndelegateResponse>(
                  this, METHODID_UNDELEGATE)))
          .addMethod(
            getUnbondValidatorMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.staking.v1beta1.Tx.MsgUnbondValidator,
                cosmos.staking.v1beta1.Tx.MsgUnbondValidatorResponse>(
                  this, METHODID_UNBOND_VALIDATOR)))
          .addMethod(
            getCancelUnbondingDelegationMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.staking.v1beta1.Tx.MsgCancelUnbondingDelegation,
                cosmos.staking.v1beta1.Tx.MsgCancelUnbondingDelegationResponse>(
                  this, METHODID_CANCEL_UNBONDING_DELEGATION)))
          .addMethod(
            getTokenizeSharesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.staking.v1beta1.Tx.MsgTokenizeShares,
                cosmos.staking.v1beta1.Tx.MsgTokenizeSharesResponse>(
                  this, METHODID_TOKENIZE_SHARES)))
          .addMethod(
            getRedeemTokensForSharesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.staking.v1beta1.Tx.MsgRedeemTokensForShares,
                cosmos.staking.v1beta1.Tx.MsgRedeemTokensForSharesResponse>(
                  this, METHODID_REDEEM_TOKENS_FOR_SHARES)))
          .addMethod(
            getTransferTokenizeShareRecordMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.staking.v1beta1.Tx.MsgTransferTokenizeShareRecord,
                cosmos.staking.v1beta1.Tx.MsgTransferTokenizeShareRecordResponse>(
                  this, METHODID_TRANSFER_TOKENIZE_SHARE_RECORD)))
          .addMethod(
            getDisableTokenizeSharesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.staking.v1beta1.Tx.MsgDisableTokenizeShares,
                cosmos.staking.v1beta1.Tx.MsgDisableTokenizeSharesResponse>(
                  this, METHODID_DISABLE_TOKENIZE_SHARES)))
          .addMethod(
            getEnableTokenizeSharesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.staking.v1beta1.Tx.MsgEnableTokenizeShares,
                cosmos.staking.v1beta1.Tx.MsgEnableTokenizeSharesResponse>(
                  this, METHODID_ENABLE_TOKENIZE_SHARES)))
          .addMethod(
            getValidatorBondMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.staking.v1beta1.Tx.MsgValidatorBond,
                cosmos.staking.v1beta1.Tx.MsgValidatorBondResponse>(
                  this, METHODID_VALIDATOR_BOND)))
          .build();
    }
  }

  /**
   * <pre>
   * Msg defines the staking Msg service.
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
     * CreateValidator defines a method for creating a new validator.
     * </pre>
     */
    public void createValidator(cosmos.staking.v1beta1.Tx.MsgCreateValidator request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.Tx.MsgCreateValidatorResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateValidatorMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * EditValidator defines a method for editing an existing validator.
     * </pre>
     */
    public void editValidator(cosmos.staking.v1beta1.Tx.MsgEditValidator request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.Tx.MsgEditValidatorResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getEditValidatorMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Delegate defines a method for performing a delegation of coins
     * from a delegator to a validator.
     * </pre>
     */
    public void delegate(cosmos.staking.v1beta1.Tx.MsgDelegate request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.Tx.MsgDelegateResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDelegateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * BeginRedelegate defines a method for performing a redelegation
     * of coins from a delegator and source validator to a destination validator.
     * </pre>
     */
    public void beginRedelegate(cosmos.staking.v1beta1.Tx.MsgBeginRedelegate request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.Tx.MsgBeginRedelegateResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getBeginRedelegateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Undelegate defines a method for performing an undelegation from a
     * delegate and a validator.
     * This allows a validator to stop their services and jail themselves without
     * experiencing a slash
     * </pre>
     */
    public void undelegate(cosmos.staking.v1beta1.Tx.MsgUndelegate request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.Tx.MsgUndelegateResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUndelegateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * UnbondValidator defines a method for performing the status transition for a validator
     * from bonded to unbonding
     * </pre>
     */
    public void unbondValidator(cosmos.staking.v1beta1.Tx.MsgUnbondValidator request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.Tx.MsgUnbondValidatorResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUnbondValidatorMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * CancelUnbondingDelegation defines a method for performing canceling the unbonding delegation
     * and delegate back to previous validator.
     * This has been backported from SDK 46 as a desirable safety feature for LSM.
     * If a liquid staking provider is exploited and the exploiter initiates an undelegation,
     * having access to CancelUnbondingDelegation allows the liquid staking provider to cancel
     * the undelegation with a software upgrade and thus avoid loss of user funds
     * </pre>
     */
    public void cancelUnbondingDelegation(cosmos.staking.v1beta1.Tx.MsgCancelUnbondingDelegation request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.Tx.MsgCancelUnbondingDelegationResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCancelUnbondingDelegationMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * TokenizeShares defines a method for tokenizing shares from a validator.
     * </pre>
     */
    public void tokenizeShares(cosmos.staking.v1beta1.Tx.MsgTokenizeShares request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.Tx.MsgTokenizeSharesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getTokenizeSharesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * RedeemTokensForShares defines a method for redeeming tokens from a validator for
     * shares.
     * </pre>
     */
    public void redeemTokensForShares(cosmos.staking.v1beta1.Tx.MsgRedeemTokensForShares request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.Tx.MsgRedeemTokensForSharesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRedeemTokensForSharesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * TransferTokenizeShareRecord defines a method to transfer ownership of
     * TokenizeShareRecord
     * </pre>
     */
    public void transferTokenizeShareRecord(cosmos.staking.v1beta1.Tx.MsgTransferTokenizeShareRecord request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.Tx.MsgTransferTokenizeShareRecordResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getTransferTokenizeShareRecordMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * DisableTokenizeShares defines a method to prevent the tokenization of an addresses stake
     * </pre>
     */
    public void disableTokenizeShares(cosmos.staking.v1beta1.Tx.MsgDisableTokenizeShares request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.Tx.MsgDisableTokenizeSharesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDisableTokenizeSharesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * EnableTokenizeShares defines a method to re-enable the tokenization of an addresseses stake
     * after it has been disabled
     * </pre>
     */
    public void enableTokenizeShares(cosmos.staking.v1beta1.Tx.MsgEnableTokenizeShares request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.Tx.MsgEnableTokenizeSharesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getEnableTokenizeSharesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * ValidatorBond defines a method for performing a validator self-bond
     * </pre>
     */
    public void validatorBond(cosmos.staking.v1beta1.Tx.MsgValidatorBond request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.Tx.MsgValidatorBondResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getValidatorBondMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * Msg defines the staking Msg service.
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
     * CreateValidator defines a method for creating a new validator.
     * </pre>
     */
    public cosmos.staking.v1beta1.Tx.MsgCreateValidatorResponse createValidator(cosmos.staking.v1beta1.Tx.MsgCreateValidator request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateValidatorMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * EditValidator defines a method for editing an existing validator.
     * </pre>
     */
    public cosmos.staking.v1beta1.Tx.MsgEditValidatorResponse editValidator(cosmos.staking.v1beta1.Tx.MsgEditValidator request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getEditValidatorMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Delegate defines a method for performing a delegation of coins
     * from a delegator to a validator.
     * </pre>
     */
    public cosmos.staking.v1beta1.Tx.MsgDelegateResponse delegate(cosmos.staking.v1beta1.Tx.MsgDelegate request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDelegateMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * BeginRedelegate defines a method for performing a redelegation
     * of coins from a delegator and source validator to a destination validator.
     * </pre>
     */
    public cosmos.staking.v1beta1.Tx.MsgBeginRedelegateResponse beginRedelegate(cosmos.staking.v1beta1.Tx.MsgBeginRedelegate request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getBeginRedelegateMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Undelegate defines a method for performing an undelegation from a
     * delegate and a validator.
     * This allows a validator to stop their services and jail themselves without
     * experiencing a slash
     * </pre>
     */
    public cosmos.staking.v1beta1.Tx.MsgUndelegateResponse undelegate(cosmos.staking.v1beta1.Tx.MsgUndelegate request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUndelegateMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * UnbondValidator defines a method for performing the status transition for a validator
     * from bonded to unbonding
     * </pre>
     */
    public cosmos.staking.v1beta1.Tx.MsgUnbondValidatorResponse unbondValidator(cosmos.staking.v1beta1.Tx.MsgUnbondValidator request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUnbondValidatorMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * CancelUnbondingDelegation defines a method for performing canceling the unbonding delegation
     * and delegate back to previous validator.
     * This has been backported from SDK 46 as a desirable safety feature for LSM.
     * If a liquid staking provider is exploited and the exploiter initiates an undelegation,
     * having access to CancelUnbondingDelegation allows the liquid staking provider to cancel
     * the undelegation with a software upgrade and thus avoid loss of user funds
     * </pre>
     */
    public cosmos.staking.v1beta1.Tx.MsgCancelUnbondingDelegationResponse cancelUnbondingDelegation(cosmos.staking.v1beta1.Tx.MsgCancelUnbondingDelegation request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCancelUnbondingDelegationMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * TokenizeShares defines a method for tokenizing shares from a validator.
     * </pre>
     */
    public cosmos.staking.v1beta1.Tx.MsgTokenizeSharesResponse tokenizeShares(cosmos.staking.v1beta1.Tx.MsgTokenizeShares request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getTokenizeSharesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * RedeemTokensForShares defines a method for redeeming tokens from a validator for
     * shares.
     * </pre>
     */
    public cosmos.staking.v1beta1.Tx.MsgRedeemTokensForSharesResponse redeemTokensForShares(cosmos.staking.v1beta1.Tx.MsgRedeemTokensForShares request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRedeemTokensForSharesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * TransferTokenizeShareRecord defines a method to transfer ownership of
     * TokenizeShareRecord
     * </pre>
     */
    public cosmos.staking.v1beta1.Tx.MsgTransferTokenizeShareRecordResponse transferTokenizeShareRecord(cosmos.staking.v1beta1.Tx.MsgTransferTokenizeShareRecord request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getTransferTokenizeShareRecordMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * DisableTokenizeShares defines a method to prevent the tokenization of an addresses stake
     * </pre>
     */
    public cosmos.staking.v1beta1.Tx.MsgDisableTokenizeSharesResponse disableTokenizeShares(cosmos.staking.v1beta1.Tx.MsgDisableTokenizeShares request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDisableTokenizeSharesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * EnableTokenizeShares defines a method to re-enable the tokenization of an addresseses stake
     * after it has been disabled
     * </pre>
     */
    public cosmos.staking.v1beta1.Tx.MsgEnableTokenizeSharesResponse enableTokenizeShares(cosmos.staking.v1beta1.Tx.MsgEnableTokenizeShares request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getEnableTokenizeSharesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * ValidatorBond defines a method for performing a validator self-bond
     * </pre>
     */
    public cosmos.staking.v1beta1.Tx.MsgValidatorBondResponse validatorBond(cosmos.staking.v1beta1.Tx.MsgValidatorBond request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getValidatorBondMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * Msg defines the staking Msg service.
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
     * CreateValidator defines a method for creating a new validator.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.staking.v1beta1.Tx.MsgCreateValidatorResponse> createValidator(
        cosmos.staking.v1beta1.Tx.MsgCreateValidator request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateValidatorMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * EditValidator defines a method for editing an existing validator.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.staking.v1beta1.Tx.MsgEditValidatorResponse> editValidator(
        cosmos.staking.v1beta1.Tx.MsgEditValidator request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getEditValidatorMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Delegate defines a method for performing a delegation of coins
     * from a delegator to a validator.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.staking.v1beta1.Tx.MsgDelegateResponse> delegate(
        cosmos.staking.v1beta1.Tx.MsgDelegate request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDelegateMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * BeginRedelegate defines a method for performing a redelegation
     * of coins from a delegator and source validator to a destination validator.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.staking.v1beta1.Tx.MsgBeginRedelegateResponse> beginRedelegate(
        cosmos.staking.v1beta1.Tx.MsgBeginRedelegate request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getBeginRedelegateMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Undelegate defines a method for performing an undelegation from a
     * delegate and a validator.
     * This allows a validator to stop their services and jail themselves without
     * experiencing a slash
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.staking.v1beta1.Tx.MsgUndelegateResponse> undelegate(
        cosmos.staking.v1beta1.Tx.MsgUndelegate request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUndelegateMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * UnbondValidator defines a method for performing the status transition for a validator
     * from bonded to unbonding
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.staking.v1beta1.Tx.MsgUnbondValidatorResponse> unbondValidator(
        cosmos.staking.v1beta1.Tx.MsgUnbondValidator request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUnbondValidatorMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * CancelUnbondingDelegation defines a method for performing canceling the unbonding delegation
     * and delegate back to previous validator.
     * This has been backported from SDK 46 as a desirable safety feature for LSM.
     * If a liquid staking provider is exploited and the exploiter initiates an undelegation,
     * having access to CancelUnbondingDelegation allows the liquid staking provider to cancel
     * the undelegation with a software upgrade and thus avoid loss of user funds
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.staking.v1beta1.Tx.MsgCancelUnbondingDelegationResponse> cancelUnbondingDelegation(
        cosmos.staking.v1beta1.Tx.MsgCancelUnbondingDelegation request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCancelUnbondingDelegationMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * TokenizeShares defines a method for tokenizing shares from a validator.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.staking.v1beta1.Tx.MsgTokenizeSharesResponse> tokenizeShares(
        cosmos.staking.v1beta1.Tx.MsgTokenizeShares request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getTokenizeSharesMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * RedeemTokensForShares defines a method for redeeming tokens from a validator for
     * shares.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.staking.v1beta1.Tx.MsgRedeemTokensForSharesResponse> redeemTokensForShares(
        cosmos.staking.v1beta1.Tx.MsgRedeemTokensForShares request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRedeemTokensForSharesMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * TransferTokenizeShareRecord defines a method to transfer ownership of
     * TokenizeShareRecord
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.staking.v1beta1.Tx.MsgTransferTokenizeShareRecordResponse> transferTokenizeShareRecord(
        cosmos.staking.v1beta1.Tx.MsgTransferTokenizeShareRecord request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getTransferTokenizeShareRecordMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * DisableTokenizeShares defines a method to prevent the tokenization of an addresses stake
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.staking.v1beta1.Tx.MsgDisableTokenizeSharesResponse> disableTokenizeShares(
        cosmos.staking.v1beta1.Tx.MsgDisableTokenizeShares request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDisableTokenizeSharesMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * EnableTokenizeShares defines a method to re-enable the tokenization of an addresseses stake
     * after it has been disabled
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.staking.v1beta1.Tx.MsgEnableTokenizeSharesResponse> enableTokenizeShares(
        cosmos.staking.v1beta1.Tx.MsgEnableTokenizeShares request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getEnableTokenizeSharesMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * ValidatorBond defines a method for performing a validator self-bond
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.staking.v1beta1.Tx.MsgValidatorBondResponse> validatorBond(
        cosmos.staking.v1beta1.Tx.MsgValidatorBond request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getValidatorBondMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_VALIDATOR = 0;
  private static final int METHODID_EDIT_VALIDATOR = 1;
  private static final int METHODID_DELEGATE = 2;
  private static final int METHODID_BEGIN_REDELEGATE = 3;
  private static final int METHODID_UNDELEGATE = 4;
  private static final int METHODID_UNBOND_VALIDATOR = 5;
  private static final int METHODID_CANCEL_UNBONDING_DELEGATION = 6;
  private static final int METHODID_TOKENIZE_SHARES = 7;
  private static final int METHODID_REDEEM_TOKENS_FOR_SHARES = 8;
  private static final int METHODID_TRANSFER_TOKENIZE_SHARE_RECORD = 9;
  private static final int METHODID_DISABLE_TOKENIZE_SHARES = 10;
  private static final int METHODID_ENABLE_TOKENIZE_SHARES = 11;
  private static final int METHODID_VALIDATOR_BOND = 12;

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
        case METHODID_CREATE_VALIDATOR:
          serviceImpl.createValidator((cosmos.staking.v1beta1.Tx.MsgCreateValidator) request,
              (io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.Tx.MsgCreateValidatorResponse>) responseObserver);
          break;
        case METHODID_EDIT_VALIDATOR:
          serviceImpl.editValidator((cosmos.staking.v1beta1.Tx.MsgEditValidator) request,
              (io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.Tx.MsgEditValidatorResponse>) responseObserver);
          break;
        case METHODID_DELEGATE:
          serviceImpl.delegate((cosmos.staking.v1beta1.Tx.MsgDelegate) request,
              (io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.Tx.MsgDelegateResponse>) responseObserver);
          break;
        case METHODID_BEGIN_REDELEGATE:
          serviceImpl.beginRedelegate((cosmos.staking.v1beta1.Tx.MsgBeginRedelegate) request,
              (io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.Tx.MsgBeginRedelegateResponse>) responseObserver);
          break;
        case METHODID_UNDELEGATE:
          serviceImpl.undelegate((cosmos.staking.v1beta1.Tx.MsgUndelegate) request,
              (io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.Tx.MsgUndelegateResponse>) responseObserver);
          break;
        case METHODID_UNBOND_VALIDATOR:
          serviceImpl.unbondValidator((cosmos.staking.v1beta1.Tx.MsgUnbondValidator) request,
              (io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.Tx.MsgUnbondValidatorResponse>) responseObserver);
          break;
        case METHODID_CANCEL_UNBONDING_DELEGATION:
          serviceImpl.cancelUnbondingDelegation((cosmos.staking.v1beta1.Tx.MsgCancelUnbondingDelegation) request,
              (io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.Tx.MsgCancelUnbondingDelegationResponse>) responseObserver);
          break;
        case METHODID_TOKENIZE_SHARES:
          serviceImpl.tokenizeShares((cosmos.staking.v1beta1.Tx.MsgTokenizeShares) request,
              (io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.Tx.MsgTokenizeSharesResponse>) responseObserver);
          break;
        case METHODID_REDEEM_TOKENS_FOR_SHARES:
          serviceImpl.redeemTokensForShares((cosmos.staking.v1beta1.Tx.MsgRedeemTokensForShares) request,
              (io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.Tx.MsgRedeemTokensForSharesResponse>) responseObserver);
          break;
        case METHODID_TRANSFER_TOKENIZE_SHARE_RECORD:
          serviceImpl.transferTokenizeShareRecord((cosmos.staking.v1beta1.Tx.MsgTransferTokenizeShareRecord) request,
              (io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.Tx.MsgTransferTokenizeShareRecordResponse>) responseObserver);
          break;
        case METHODID_DISABLE_TOKENIZE_SHARES:
          serviceImpl.disableTokenizeShares((cosmos.staking.v1beta1.Tx.MsgDisableTokenizeShares) request,
              (io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.Tx.MsgDisableTokenizeSharesResponse>) responseObserver);
          break;
        case METHODID_ENABLE_TOKENIZE_SHARES:
          serviceImpl.enableTokenizeShares((cosmos.staking.v1beta1.Tx.MsgEnableTokenizeShares) request,
              (io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.Tx.MsgEnableTokenizeSharesResponse>) responseObserver);
          break;
        case METHODID_VALIDATOR_BOND:
          serviceImpl.validatorBond((cosmos.staking.v1beta1.Tx.MsgValidatorBond) request,
              (io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.Tx.MsgValidatorBondResponse>) responseObserver);
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
      return cosmos.staking.v1beta1.Tx.getDescriptor();
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
              .addMethod(getCreateValidatorMethod())
              .addMethod(getEditValidatorMethod())
              .addMethod(getDelegateMethod())
              .addMethod(getBeginRedelegateMethod())
              .addMethod(getUndelegateMethod())
              .addMethod(getUnbondValidatorMethod())
              .addMethod(getCancelUnbondingDelegationMethod())
              .addMethod(getTokenizeSharesMethod())
              .addMethod(getRedeemTokensForSharesMethod())
              .addMethod(getTransferTokenizeShareRecordMethod())
              .addMethod(getDisableTokenizeSharesMethod())
              .addMethod(getEnableTokenizeSharesMethod())
              .addMethod(getValidatorBondMethod())
              .build();
        }
      }
    }
    return result;
  }
}
