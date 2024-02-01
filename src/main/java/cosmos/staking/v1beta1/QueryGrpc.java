package cosmos.staking.v1beta1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Query defines the gRPC querier service.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.45.1)",
    comments = "Source: cosmos/staking/v1beta1/query.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class QueryGrpc {

  private QueryGrpc() {}

  public static final String SERVICE_NAME = "cosmos.staking.v1beta1.Query";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorsRequest,
      cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorsResponse> getValidatorsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Validators",
      requestType = cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorsRequest.class,
      responseType = cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorsRequest,
      cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorsResponse> getValidatorsMethod() {
    io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorsRequest, cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorsResponse> getValidatorsMethod;
    if ((getValidatorsMethod = QueryGrpc.getValidatorsMethod) == null) {
      synchronized (QueryGrpc.class) {
        if ((getValidatorsMethod = QueryGrpc.getValidatorsMethod) == null) {
          QueryGrpc.getValidatorsMethod = getValidatorsMethod =
              io.grpc.MethodDescriptor.<cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorsRequest, cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Validators"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QueryMethodDescriptorSupplier("Validators"))
              .build();
        }
      }
    }
    return getValidatorsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorRequest,
      cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorResponse> getValidatorMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Validator",
      requestType = cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorRequest.class,
      responseType = cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorRequest,
      cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorResponse> getValidatorMethod() {
    io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorRequest, cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorResponse> getValidatorMethod;
    if ((getValidatorMethod = QueryGrpc.getValidatorMethod) == null) {
      synchronized (QueryGrpc.class) {
        if ((getValidatorMethod = QueryGrpc.getValidatorMethod) == null) {
          QueryGrpc.getValidatorMethod = getValidatorMethod =
              io.grpc.MethodDescriptor.<cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorRequest, cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Validator"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QueryMethodDescriptorSupplier("Validator"))
              .build();
        }
      }
    }
    return getValidatorMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorDelegationsRequest,
      cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorDelegationsResponse> getValidatorDelegationsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ValidatorDelegations",
      requestType = cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorDelegationsRequest.class,
      responseType = cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorDelegationsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorDelegationsRequest,
      cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorDelegationsResponse> getValidatorDelegationsMethod() {
    io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorDelegationsRequest, cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorDelegationsResponse> getValidatorDelegationsMethod;
    if ((getValidatorDelegationsMethod = QueryGrpc.getValidatorDelegationsMethod) == null) {
      synchronized (QueryGrpc.class) {
        if ((getValidatorDelegationsMethod = QueryGrpc.getValidatorDelegationsMethod) == null) {
          QueryGrpc.getValidatorDelegationsMethod = getValidatorDelegationsMethod =
              io.grpc.MethodDescriptor.<cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorDelegationsRequest, cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorDelegationsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ValidatorDelegations"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorDelegationsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorDelegationsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QueryMethodDescriptorSupplier("ValidatorDelegations"))
              .build();
        }
      }
    }
    return getValidatorDelegationsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorUnbondingDelegationsRequest,
      cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorUnbondingDelegationsResponse> getValidatorUnbondingDelegationsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ValidatorUnbondingDelegations",
      requestType = cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorUnbondingDelegationsRequest.class,
      responseType = cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorUnbondingDelegationsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorUnbondingDelegationsRequest,
      cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorUnbondingDelegationsResponse> getValidatorUnbondingDelegationsMethod() {
    io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorUnbondingDelegationsRequest, cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorUnbondingDelegationsResponse> getValidatorUnbondingDelegationsMethod;
    if ((getValidatorUnbondingDelegationsMethod = QueryGrpc.getValidatorUnbondingDelegationsMethod) == null) {
      synchronized (QueryGrpc.class) {
        if ((getValidatorUnbondingDelegationsMethod = QueryGrpc.getValidatorUnbondingDelegationsMethod) == null) {
          QueryGrpc.getValidatorUnbondingDelegationsMethod = getValidatorUnbondingDelegationsMethod =
              io.grpc.MethodDescriptor.<cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorUnbondingDelegationsRequest, cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorUnbondingDelegationsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ValidatorUnbondingDelegations"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorUnbondingDelegationsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorUnbondingDelegationsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QueryMethodDescriptorSupplier("ValidatorUnbondingDelegations"))
              .build();
        }
      }
    }
    return getValidatorUnbondingDelegationsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryDelegationRequest,
      cosmos.staking.v1beta1.QueryOuterClass.QueryDelegationResponse> getDelegationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Delegation",
      requestType = cosmos.staking.v1beta1.QueryOuterClass.QueryDelegationRequest.class,
      responseType = cosmos.staking.v1beta1.QueryOuterClass.QueryDelegationResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryDelegationRequest,
      cosmos.staking.v1beta1.QueryOuterClass.QueryDelegationResponse> getDelegationMethod() {
    io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryDelegationRequest, cosmos.staking.v1beta1.QueryOuterClass.QueryDelegationResponse> getDelegationMethod;
    if ((getDelegationMethod = QueryGrpc.getDelegationMethod) == null) {
      synchronized (QueryGrpc.class) {
        if ((getDelegationMethod = QueryGrpc.getDelegationMethod) == null) {
          QueryGrpc.getDelegationMethod = getDelegationMethod =
              io.grpc.MethodDescriptor.<cosmos.staking.v1beta1.QueryOuterClass.QueryDelegationRequest, cosmos.staking.v1beta1.QueryOuterClass.QueryDelegationResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Delegation"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryDelegationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryDelegationResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QueryMethodDescriptorSupplier("Delegation"))
              .build();
        }
      }
    }
    return getDelegationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryUnbondingDelegationRequest,
      cosmos.staking.v1beta1.QueryOuterClass.QueryUnbondingDelegationResponse> getUnbondingDelegationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UnbondingDelegation",
      requestType = cosmos.staking.v1beta1.QueryOuterClass.QueryUnbondingDelegationRequest.class,
      responseType = cosmos.staking.v1beta1.QueryOuterClass.QueryUnbondingDelegationResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryUnbondingDelegationRequest,
      cosmos.staking.v1beta1.QueryOuterClass.QueryUnbondingDelegationResponse> getUnbondingDelegationMethod() {
    io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryUnbondingDelegationRequest, cosmos.staking.v1beta1.QueryOuterClass.QueryUnbondingDelegationResponse> getUnbondingDelegationMethod;
    if ((getUnbondingDelegationMethod = QueryGrpc.getUnbondingDelegationMethod) == null) {
      synchronized (QueryGrpc.class) {
        if ((getUnbondingDelegationMethod = QueryGrpc.getUnbondingDelegationMethod) == null) {
          QueryGrpc.getUnbondingDelegationMethod = getUnbondingDelegationMethod =
              io.grpc.MethodDescriptor.<cosmos.staking.v1beta1.QueryOuterClass.QueryUnbondingDelegationRequest, cosmos.staking.v1beta1.QueryOuterClass.QueryUnbondingDelegationResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UnbondingDelegation"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryUnbondingDelegationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryUnbondingDelegationResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QueryMethodDescriptorSupplier("UnbondingDelegation"))
              .build();
        }
      }
    }
    return getUnbondingDelegationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorDelegationsRequest,
      cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorDelegationsResponse> getDelegatorDelegationsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DelegatorDelegations",
      requestType = cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorDelegationsRequest.class,
      responseType = cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorDelegationsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorDelegationsRequest,
      cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorDelegationsResponse> getDelegatorDelegationsMethod() {
    io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorDelegationsRequest, cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorDelegationsResponse> getDelegatorDelegationsMethod;
    if ((getDelegatorDelegationsMethod = QueryGrpc.getDelegatorDelegationsMethod) == null) {
      synchronized (QueryGrpc.class) {
        if ((getDelegatorDelegationsMethod = QueryGrpc.getDelegatorDelegationsMethod) == null) {
          QueryGrpc.getDelegatorDelegationsMethod = getDelegatorDelegationsMethod =
              io.grpc.MethodDescriptor.<cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorDelegationsRequest, cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorDelegationsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DelegatorDelegations"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorDelegationsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorDelegationsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QueryMethodDescriptorSupplier("DelegatorDelegations"))
              .build();
        }
      }
    }
    return getDelegatorDelegationsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorUnbondingDelegationsRequest,
      cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorUnbondingDelegationsResponse> getDelegatorUnbondingDelegationsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DelegatorUnbondingDelegations",
      requestType = cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorUnbondingDelegationsRequest.class,
      responseType = cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorUnbondingDelegationsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorUnbondingDelegationsRequest,
      cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorUnbondingDelegationsResponse> getDelegatorUnbondingDelegationsMethod() {
    io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorUnbondingDelegationsRequest, cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorUnbondingDelegationsResponse> getDelegatorUnbondingDelegationsMethod;
    if ((getDelegatorUnbondingDelegationsMethod = QueryGrpc.getDelegatorUnbondingDelegationsMethod) == null) {
      synchronized (QueryGrpc.class) {
        if ((getDelegatorUnbondingDelegationsMethod = QueryGrpc.getDelegatorUnbondingDelegationsMethod) == null) {
          QueryGrpc.getDelegatorUnbondingDelegationsMethod = getDelegatorUnbondingDelegationsMethod =
              io.grpc.MethodDescriptor.<cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorUnbondingDelegationsRequest, cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorUnbondingDelegationsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DelegatorUnbondingDelegations"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorUnbondingDelegationsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorUnbondingDelegationsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QueryMethodDescriptorSupplier("DelegatorUnbondingDelegations"))
              .build();
        }
      }
    }
    return getDelegatorUnbondingDelegationsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryRedelegationsRequest,
      cosmos.staking.v1beta1.QueryOuterClass.QueryRedelegationsResponse> getRedelegationsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Redelegations",
      requestType = cosmos.staking.v1beta1.QueryOuterClass.QueryRedelegationsRequest.class,
      responseType = cosmos.staking.v1beta1.QueryOuterClass.QueryRedelegationsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryRedelegationsRequest,
      cosmos.staking.v1beta1.QueryOuterClass.QueryRedelegationsResponse> getRedelegationsMethod() {
    io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryRedelegationsRequest, cosmos.staking.v1beta1.QueryOuterClass.QueryRedelegationsResponse> getRedelegationsMethod;
    if ((getRedelegationsMethod = QueryGrpc.getRedelegationsMethod) == null) {
      synchronized (QueryGrpc.class) {
        if ((getRedelegationsMethod = QueryGrpc.getRedelegationsMethod) == null) {
          QueryGrpc.getRedelegationsMethod = getRedelegationsMethod =
              io.grpc.MethodDescriptor.<cosmos.staking.v1beta1.QueryOuterClass.QueryRedelegationsRequest, cosmos.staking.v1beta1.QueryOuterClass.QueryRedelegationsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Redelegations"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryRedelegationsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryRedelegationsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QueryMethodDescriptorSupplier("Redelegations"))
              .build();
        }
      }
    }
    return getRedelegationsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorsRequest,
      cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorsResponse> getDelegatorValidatorsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DelegatorValidators",
      requestType = cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorsRequest.class,
      responseType = cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorsRequest,
      cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorsResponse> getDelegatorValidatorsMethod() {
    io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorsRequest, cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorsResponse> getDelegatorValidatorsMethod;
    if ((getDelegatorValidatorsMethod = QueryGrpc.getDelegatorValidatorsMethod) == null) {
      synchronized (QueryGrpc.class) {
        if ((getDelegatorValidatorsMethod = QueryGrpc.getDelegatorValidatorsMethod) == null) {
          QueryGrpc.getDelegatorValidatorsMethod = getDelegatorValidatorsMethod =
              io.grpc.MethodDescriptor.<cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorsRequest, cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DelegatorValidators"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QueryMethodDescriptorSupplier("DelegatorValidators"))
              .build();
        }
      }
    }
    return getDelegatorValidatorsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorRequest,
      cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorResponse> getDelegatorValidatorMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DelegatorValidator",
      requestType = cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorRequest.class,
      responseType = cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorRequest,
      cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorResponse> getDelegatorValidatorMethod() {
    io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorRequest, cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorResponse> getDelegatorValidatorMethod;
    if ((getDelegatorValidatorMethod = QueryGrpc.getDelegatorValidatorMethod) == null) {
      synchronized (QueryGrpc.class) {
        if ((getDelegatorValidatorMethod = QueryGrpc.getDelegatorValidatorMethod) == null) {
          QueryGrpc.getDelegatorValidatorMethod = getDelegatorValidatorMethod =
              io.grpc.MethodDescriptor.<cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorRequest, cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DelegatorValidator"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QueryMethodDescriptorSupplier("DelegatorValidator"))
              .build();
        }
      }
    }
    return getDelegatorValidatorMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryHistoricalInfoRequest,
      cosmos.staking.v1beta1.QueryOuterClass.QueryHistoricalInfoResponse> getHistoricalInfoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "HistoricalInfo",
      requestType = cosmos.staking.v1beta1.QueryOuterClass.QueryHistoricalInfoRequest.class,
      responseType = cosmos.staking.v1beta1.QueryOuterClass.QueryHistoricalInfoResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryHistoricalInfoRequest,
      cosmos.staking.v1beta1.QueryOuterClass.QueryHistoricalInfoResponse> getHistoricalInfoMethod() {
    io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryHistoricalInfoRequest, cosmos.staking.v1beta1.QueryOuterClass.QueryHistoricalInfoResponse> getHistoricalInfoMethod;
    if ((getHistoricalInfoMethod = QueryGrpc.getHistoricalInfoMethod) == null) {
      synchronized (QueryGrpc.class) {
        if ((getHistoricalInfoMethod = QueryGrpc.getHistoricalInfoMethod) == null) {
          QueryGrpc.getHistoricalInfoMethod = getHistoricalInfoMethod =
              io.grpc.MethodDescriptor.<cosmos.staking.v1beta1.QueryOuterClass.QueryHistoricalInfoRequest, cosmos.staking.v1beta1.QueryOuterClass.QueryHistoricalInfoResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "HistoricalInfo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryHistoricalInfoRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryHistoricalInfoResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QueryMethodDescriptorSupplier("HistoricalInfo"))
              .build();
        }
      }
    }
    return getHistoricalInfoMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryPoolRequest,
      cosmos.staking.v1beta1.QueryOuterClass.QueryPoolResponse> getPoolMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Pool",
      requestType = cosmos.staking.v1beta1.QueryOuterClass.QueryPoolRequest.class,
      responseType = cosmos.staking.v1beta1.QueryOuterClass.QueryPoolResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryPoolRequest,
      cosmos.staking.v1beta1.QueryOuterClass.QueryPoolResponse> getPoolMethod() {
    io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryPoolRequest, cosmos.staking.v1beta1.QueryOuterClass.QueryPoolResponse> getPoolMethod;
    if ((getPoolMethod = QueryGrpc.getPoolMethod) == null) {
      synchronized (QueryGrpc.class) {
        if ((getPoolMethod = QueryGrpc.getPoolMethod) == null) {
          QueryGrpc.getPoolMethod = getPoolMethod =
              io.grpc.MethodDescriptor.<cosmos.staking.v1beta1.QueryOuterClass.QueryPoolRequest, cosmos.staking.v1beta1.QueryOuterClass.QueryPoolResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Pool"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryPoolRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryPoolResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QueryMethodDescriptorSupplier("Pool"))
              .build();
        }
      }
    }
    return getPoolMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryParamsRequest,
      cosmos.staking.v1beta1.QueryOuterClass.QueryParamsResponse> getParamsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Params",
      requestType = cosmos.staking.v1beta1.QueryOuterClass.QueryParamsRequest.class,
      responseType = cosmos.staking.v1beta1.QueryOuterClass.QueryParamsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryParamsRequest,
      cosmos.staking.v1beta1.QueryOuterClass.QueryParamsResponse> getParamsMethod() {
    io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryParamsRequest, cosmos.staking.v1beta1.QueryOuterClass.QueryParamsResponse> getParamsMethod;
    if ((getParamsMethod = QueryGrpc.getParamsMethod) == null) {
      synchronized (QueryGrpc.class) {
        if ((getParamsMethod = QueryGrpc.getParamsMethod) == null) {
          QueryGrpc.getParamsMethod = getParamsMethod =
              io.grpc.MethodDescriptor.<cosmos.staking.v1beta1.QueryOuterClass.QueryParamsRequest, cosmos.staking.v1beta1.QueryOuterClass.QueryParamsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Params"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryParamsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryParamsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QueryMethodDescriptorSupplier("Params"))
              .build();
        }
      }
    }
    return getParamsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByIdRequest,
      cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByIdResponse> getTokenizeShareRecordByIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "TokenizeShareRecordById",
      requestType = cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByIdRequest.class,
      responseType = cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByIdResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByIdRequest,
      cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByIdResponse> getTokenizeShareRecordByIdMethod() {
    io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByIdRequest, cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByIdResponse> getTokenizeShareRecordByIdMethod;
    if ((getTokenizeShareRecordByIdMethod = QueryGrpc.getTokenizeShareRecordByIdMethod) == null) {
      synchronized (QueryGrpc.class) {
        if ((getTokenizeShareRecordByIdMethod = QueryGrpc.getTokenizeShareRecordByIdMethod) == null) {
          QueryGrpc.getTokenizeShareRecordByIdMethod = getTokenizeShareRecordByIdMethod =
              io.grpc.MethodDescriptor.<cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByIdRequest, cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByIdResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "TokenizeShareRecordById"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByIdRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByIdResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QueryMethodDescriptorSupplier("TokenizeShareRecordById"))
              .build();
        }
      }
    }
    return getTokenizeShareRecordByIdMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByDenomRequest,
      cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByDenomResponse> getTokenizeShareRecordByDenomMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "TokenizeShareRecordByDenom",
      requestType = cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByDenomRequest.class,
      responseType = cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByDenomResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByDenomRequest,
      cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByDenomResponse> getTokenizeShareRecordByDenomMethod() {
    io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByDenomRequest, cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByDenomResponse> getTokenizeShareRecordByDenomMethod;
    if ((getTokenizeShareRecordByDenomMethod = QueryGrpc.getTokenizeShareRecordByDenomMethod) == null) {
      synchronized (QueryGrpc.class) {
        if ((getTokenizeShareRecordByDenomMethod = QueryGrpc.getTokenizeShareRecordByDenomMethod) == null) {
          QueryGrpc.getTokenizeShareRecordByDenomMethod = getTokenizeShareRecordByDenomMethod =
              io.grpc.MethodDescriptor.<cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByDenomRequest, cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByDenomResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "TokenizeShareRecordByDenom"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByDenomRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByDenomResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QueryMethodDescriptorSupplier("TokenizeShareRecordByDenom"))
              .build();
        }
      }
    }
    return getTokenizeShareRecordByDenomMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordsOwnedRequest,
      cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordsOwnedResponse> getTokenizeShareRecordsOwnedMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "TokenizeShareRecordsOwned",
      requestType = cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordsOwnedRequest.class,
      responseType = cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordsOwnedResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordsOwnedRequest,
      cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordsOwnedResponse> getTokenizeShareRecordsOwnedMethod() {
    io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordsOwnedRequest, cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordsOwnedResponse> getTokenizeShareRecordsOwnedMethod;
    if ((getTokenizeShareRecordsOwnedMethod = QueryGrpc.getTokenizeShareRecordsOwnedMethod) == null) {
      synchronized (QueryGrpc.class) {
        if ((getTokenizeShareRecordsOwnedMethod = QueryGrpc.getTokenizeShareRecordsOwnedMethod) == null) {
          QueryGrpc.getTokenizeShareRecordsOwnedMethod = getTokenizeShareRecordsOwnedMethod =
              io.grpc.MethodDescriptor.<cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordsOwnedRequest, cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordsOwnedResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "TokenizeShareRecordsOwned"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordsOwnedRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordsOwnedResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QueryMethodDescriptorSupplier("TokenizeShareRecordsOwned"))
              .build();
        }
      }
    }
    return getTokenizeShareRecordsOwnedMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryAllTokenizeShareRecordsRequest,
      cosmos.staking.v1beta1.QueryOuterClass.QueryAllTokenizeShareRecordsResponse> getAllTokenizeShareRecordsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AllTokenizeShareRecords",
      requestType = cosmos.staking.v1beta1.QueryOuterClass.QueryAllTokenizeShareRecordsRequest.class,
      responseType = cosmos.staking.v1beta1.QueryOuterClass.QueryAllTokenizeShareRecordsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryAllTokenizeShareRecordsRequest,
      cosmos.staking.v1beta1.QueryOuterClass.QueryAllTokenizeShareRecordsResponse> getAllTokenizeShareRecordsMethod() {
    io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryAllTokenizeShareRecordsRequest, cosmos.staking.v1beta1.QueryOuterClass.QueryAllTokenizeShareRecordsResponse> getAllTokenizeShareRecordsMethod;
    if ((getAllTokenizeShareRecordsMethod = QueryGrpc.getAllTokenizeShareRecordsMethod) == null) {
      synchronized (QueryGrpc.class) {
        if ((getAllTokenizeShareRecordsMethod = QueryGrpc.getAllTokenizeShareRecordsMethod) == null) {
          QueryGrpc.getAllTokenizeShareRecordsMethod = getAllTokenizeShareRecordsMethod =
              io.grpc.MethodDescriptor.<cosmos.staking.v1beta1.QueryOuterClass.QueryAllTokenizeShareRecordsRequest, cosmos.staking.v1beta1.QueryOuterClass.QueryAllTokenizeShareRecordsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AllTokenizeShareRecords"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryAllTokenizeShareRecordsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryAllTokenizeShareRecordsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QueryMethodDescriptorSupplier("AllTokenizeShareRecords"))
              .build();
        }
      }
    }
    return getAllTokenizeShareRecordsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryLastTokenizeShareRecordIdRequest,
      cosmos.staking.v1beta1.QueryOuterClass.QueryLastTokenizeShareRecordIdResponse> getLastTokenizeShareRecordIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "LastTokenizeShareRecordId",
      requestType = cosmos.staking.v1beta1.QueryOuterClass.QueryLastTokenizeShareRecordIdRequest.class,
      responseType = cosmos.staking.v1beta1.QueryOuterClass.QueryLastTokenizeShareRecordIdResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryLastTokenizeShareRecordIdRequest,
      cosmos.staking.v1beta1.QueryOuterClass.QueryLastTokenizeShareRecordIdResponse> getLastTokenizeShareRecordIdMethod() {
    io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryLastTokenizeShareRecordIdRequest, cosmos.staking.v1beta1.QueryOuterClass.QueryLastTokenizeShareRecordIdResponse> getLastTokenizeShareRecordIdMethod;
    if ((getLastTokenizeShareRecordIdMethod = QueryGrpc.getLastTokenizeShareRecordIdMethod) == null) {
      synchronized (QueryGrpc.class) {
        if ((getLastTokenizeShareRecordIdMethod = QueryGrpc.getLastTokenizeShareRecordIdMethod) == null) {
          QueryGrpc.getLastTokenizeShareRecordIdMethod = getLastTokenizeShareRecordIdMethod =
              io.grpc.MethodDescriptor.<cosmos.staking.v1beta1.QueryOuterClass.QueryLastTokenizeShareRecordIdRequest, cosmos.staking.v1beta1.QueryOuterClass.QueryLastTokenizeShareRecordIdResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "LastTokenizeShareRecordId"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryLastTokenizeShareRecordIdRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryLastTokenizeShareRecordIdResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QueryMethodDescriptorSupplier("LastTokenizeShareRecordId"))
              .build();
        }
      }
    }
    return getLastTokenizeShareRecordIdMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryTotalTokenizeSharedAssetsRequest,
      cosmos.staking.v1beta1.QueryOuterClass.QueryTotalTokenizeSharedAssetsResponse> getTotalTokenizeSharedAssetsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "TotalTokenizeSharedAssets",
      requestType = cosmos.staking.v1beta1.QueryOuterClass.QueryTotalTokenizeSharedAssetsRequest.class,
      responseType = cosmos.staking.v1beta1.QueryOuterClass.QueryTotalTokenizeSharedAssetsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryTotalTokenizeSharedAssetsRequest,
      cosmos.staking.v1beta1.QueryOuterClass.QueryTotalTokenizeSharedAssetsResponse> getTotalTokenizeSharedAssetsMethod() {
    io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryTotalTokenizeSharedAssetsRequest, cosmos.staking.v1beta1.QueryOuterClass.QueryTotalTokenizeSharedAssetsResponse> getTotalTokenizeSharedAssetsMethod;
    if ((getTotalTokenizeSharedAssetsMethod = QueryGrpc.getTotalTokenizeSharedAssetsMethod) == null) {
      synchronized (QueryGrpc.class) {
        if ((getTotalTokenizeSharedAssetsMethod = QueryGrpc.getTotalTokenizeSharedAssetsMethod) == null) {
          QueryGrpc.getTotalTokenizeSharedAssetsMethod = getTotalTokenizeSharedAssetsMethod =
              io.grpc.MethodDescriptor.<cosmos.staking.v1beta1.QueryOuterClass.QueryTotalTokenizeSharedAssetsRequest, cosmos.staking.v1beta1.QueryOuterClass.QueryTotalTokenizeSharedAssetsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "TotalTokenizeSharedAssets"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryTotalTokenizeSharedAssetsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryTotalTokenizeSharedAssetsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QueryMethodDescriptorSupplier("TotalTokenizeSharedAssets"))
              .build();
        }
      }
    }
    return getTotalTokenizeSharedAssetsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryTotalLiquidStaked,
      cosmos.staking.v1beta1.QueryOuterClass.QueryTotalLiquidStakedResponse> getTotalLiquidStakedMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "TotalLiquidStaked",
      requestType = cosmos.staking.v1beta1.QueryOuterClass.QueryTotalLiquidStaked.class,
      responseType = cosmos.staking.v1beta1.QueryOuterClass.QueryTotalLiquidStakedResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryTotalLiquidStaked,
      cosmos.staking.v1beta1.QueryOuterClass.QueryTotalLiquidStakedResponse> getTotalLiquidStakedMethod() {
    io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryTotalLiquidStaked, cosmos.staking.v1beta1.QueryOuterClass.QueryTotalLiquidStakedResponse> getTotalLiquidStakedMethod;
    if ((getTotalLiquidStakedMethod = QueryGrpc.getTotalLiquidStakedMethod) == null) {
      synchronized (QueryGrpc.class) {
        if ((getTotalLiquidStakedMethod = QueryGrpc.getTotalLiquidStakedMethod) == null) {
          QueryGrpc.getTotalLiquidStakedMethod = getTotalLiquidStakedMethod =
              io.grpc.MethodDescriptor.<cosmos.staking.v1beta1.QueryOuterClass.QueryTotalLiquidStaked, cosmos.staking.v1beta1.QueryOuterClass.QueryTotalLiquidStakedResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "TotalLiquidStaked"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryTotalLiquidStaked.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryTotalLiquidStakedResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QueryMethodDescriptorSupplier("TotalLiquidStaked"))
              .build();
        }
      }
    }
    return getTotalLiquidStakedMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareLockInfo,
      cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareLockInfoResponse> getTokenizeShareLockInfoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "TokenizeShareLockInfo",
      requestType = cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareLockInfo.class,
      responseType = cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareLockInfoResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareLockInfo,
      cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareLockInfoResponse> getTokenizeShareLockInfoMethod() {
    io.grpc.MethodDescriptor<cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareLockInfo, cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareLockInfoResponse> getTokenizeShareLockInfoMethod;
    if ((getTokenizeShareLockInfoMethod = QueryGrpc.getTokenizeShareLockInfoMethod) == null) {
      synchronized (QueryGrpc.class) {
        if ((getTokenizeShareLockInfoMethod = QueryGrpc.getTokenizeShareLockInfoMethod) == null) {
          QueryGrpc.getTokenizeShareLockInfoMethod = getTokenizeShareLockInfoMethod =
              io.grpc.MethodDescriptor.<cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareLockInfo, cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareLockInfoResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "TokenizeShareLockInfo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareLockInfo.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareLockInfoResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QueryMethodDescriptorSupplier("TokenizeShareLockInfo"))
              .build();
        }
      }
    }
    return getTokenizeShareLockInfoMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static QueryStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<QueryStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<QueryStub>() {
        @java.lang.Override
        public QueryStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new QueryStub(channel, callOptions);
        }
      };
    return QueryStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static QueryBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<QueryBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<QueryBlockingStub>() {
        @java.lang.Override
        public QueryBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new QueryBlockingStub(channel, callOptions);
        }
      };
    return QueryBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static QueryFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<QueryFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<QueryFutureStub>() {
        @java.lang.Override
        public QueryFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new QueryFutureStub(channel, callOptions);
        }
      };
    return QueryFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Query defines the gRPC querier service.
   * </pre>
   */
  public static abstract class QueryImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Validators queries all validators that match the given status.
     * </pre>
     */
    public void validators(cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorsRequest request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getValidatorsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Validator queries validator info for given validator address.
     * </pre>
     */
    public void validator(cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorRequest request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getValidatorMethod(), responseObserver);
    }

    /**
     * <pre>
     * ValidatorDelegations queries delegate info for given validator.
     * </pre>
     */
    public void validatorDelegations(cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorDelegationsRequest request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorDelegationsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getValidatorDelegationsMethod(), responseObserver);
    }

    /**
     * <pre>
     * ValidatorUnbondingDelegations queries unbonding delegations of a validator.
     * </pre>
     */
    public void validatorUnbondingDelegations(cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorUnbondingDelegationsRequest request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorUnbondingDelegationsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getValidatorUnbondingDelegationsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Delegation queries delegate info for given validator delegator pair.
     * </pre>
     */
    public void delegation(cosmos.staking.v1beta1.QueryOuterClass.QueryDelegationRequest request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryDelegationResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDelegationMethod(), responseObserver);
    }

    /**
     * <pre>
     * UnbondingDelegation queries unbonding info for given validator delegator
     * pair.
     * </pre>
     */
    public void unbondingDelegation(cosmos.staking.v1beta1.QueryOuterClass.QueryUnbondingDelegationRequest request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryUnbondingDelegationResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUnbondingDelegationMethod(), responseObserver);
    }

    /**
     * <pre>
     * DelegatorDelegations queries all delegations of a given delegator address.
     * </pre>
     */
    public void delegatorDelegations(cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorDelegationsRequest request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorDelegationsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDelegatorDelegationsMethod(), responseObserver);
    }

    /**
     * <pre>
     * DelegatorUnbondingDelegations queries all unbonding delegations of a given
     * delegator address.
     * </pre>
     */
    public void delegatorUnbondingDelegations(cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorUnbondingDelegationsRequest request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorUnbondingDelegationsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDelegatorUnbondingDelegationsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Redelegations queries redelegations of given address.
     * </pre>
     */
    public void redelegations(cosmos.staking.v1beta1.QueryOuterClass.QueryRedelegationsRequest request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryRedelegationsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRedelegationsMethod(), responseObserver);
    }

    /**
     * <pre>
     * DelegatorValidators queries all validators info for given delegator
     * address.
     * </pre>
     */
    public void delegatorValidators(cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorsRequest request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDelegatorValidatorsMethod(), responseObserver);
    }

    /**
     * <pre>
     * DelegatorValidator queries validator info for given delegator validator
     * pair.
     * </pre>
     */
    public void delegatorValidator(cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorRequest request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDelegatorValidatorMethod(), responseObserver);
    }

    /**
     * <pre>
     * HistoricalInfo queries the historical info for given height.
     * </pre>
     */
    public void historicalInfo(cosmos.staking.v1beta1.QueryOuterClass.QueryHistoricalInfoRequest request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryHistoricalInfoResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getHistoricalInfoMethod(), responseObserver);
    }

    /**
     * <pre>
     * Pool queries the pool info.
     * </pre>
     */
    public void pool(cosmos.staking.v1beta1.QueryOuterClass.QueryPoolRequest request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryPoolResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPoolMethod(), responseObserver);
    }

    /**
     * <pre>
     * Parameters queries the staking parameters.
     * </pre>
     */
    public void params(cosmos.staking.v1beta1.QueryOuterClass.QueryParamsRequest request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryParamsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getParamsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Query for individual tokenize share record information by share by id
     * </pre>
     */
    public void tokenizeShareRecordById(cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByIdRequest request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByIdResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getTokenizeShareRecordByIdMethod(), responseObserver);
    }

    /**
     * <pre>
     * Query for individual tokenize share record information by share denom
     * </pre>
     */
    public void tokenizeShareRecordByDenom(cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByDenomRequest request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByDenomResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getTokenizeShareRecordByDenomMethod(), responseObserver);
    }

    /**
     * <pre>
     * Query tokenize share records by address
     * </pre>
     */
    public void tokenizeShareRecordsOwned(cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordsOwnedRequest request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordsOwnedResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getTokenizeShareRecordsOwnedMethod(), responseObserver);
    }

    /**
     * <pre>
     * Query for all tokenize share records
     * </pre>
     */
    public void allTokenizeShareRecords(cosmos.staking.v1beta1.QueryOuterClass.QueryAllTokenizeShareRecordsRequest request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryAllTokenizeShareRecordsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAllTokenizeShareRecordsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Query for last tokenize share record id
     * </pre>
     */
    public void lastTokenizeShareRecordId(cosmos.staking.v1beta1.QueryOuterClass.QueryLastTokenizeShareRecordIdRequest request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryLastTokenizeShareRecordIdResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getLastTokenizeShareRecordIdMethod(), responseObserver);
    }

    /**
     * <pre>
     * Query for total tokenized staked assets
     * </pre>
     */
    public void totalTokenizeSharedAssets(cosmos.staking.v1beta1.QueryOuterClass.QueryTotalTokenizeSharedAssetsRequest request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryTotalTokenizeSharedAssetsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getTotalTokenizeSharedAssetsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Query for total liquid staked (including tokenized shares or owned by an liquid staking provider)
     * </pre>
     */
    public void totalLiquidStaked(cosmos.staking.v1beta1.QueryOuterClass.QueryTotalLiquidStaked request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryTotalLiquidStakedResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getTotalLiquidStakedMethod(), responseObserver);
    }

    /**
     * <pre>
     * Query tokenize share locks
     * </pre>
     */
    public void tokenizeShareLockInfo(cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareLockInfo request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareLockInfoResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getTokenizeShareLockInfoMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getValidatorsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorsRequest,
                cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorsResponse>(
                  this, METHODID_VALIDATORS)))
          .addMethod(
            getValidatorMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorRequest,
                cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorResponse>(
                  this, METHODID_VALIDATOR)))
          .addMethod(
            getValidatorDelegationsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorDelegationsRequest,
                cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorDelegationsResponse>(
                  this, METHODID_VALIDATOR_DELEGATIONS)))
          .addMethod(
            getValidatorUnbondingDelegationsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorUnbondingDelegationsRequest,
                cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorUnbondingDelegationsResponse>(
                  this, METHODID_VALIDATOR_UNBONDING_DELEGATIONS)))
          .addMethod(
            getDelegationMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.staking.v1beta1.QueryOuterClass.QueryDelegationRequest,
                cosmos.staking.v1beta1.QueryOuterClass.QueryDelegationResponse>(
                  this, METHODID_DELEGATION)))
          .addMethod(
            getUnbondingDelegationMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.staking.v1beta1.QueryOuterClass.QueryUnbondingDelegationRequest,
                cosmos.staking.v1beta1.QueryOuterClass.QueryUnbondingDelegationResponse>(
                  this, METHODID_UNBONDING_DELEGATION)))
          .addMethod(
            getDelegatorDelegationsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorDelegationsRequest,
                cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorDelegationsResponse>(
                  this, METHODID_DELEGATOR_DELEGATIONS)))
          .addMethod(
            getDelegatorUnbondingDelegationsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorUnbondingDelegationsRequest,
                cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorUnbondingDelegationsResponse>(
                  this, METHODID_DELEGATOR_UNBONDING_DELEGATIONS)))
          .addMethod(
            getRedelegationsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.staking.v1beta1.QueryOuterClass.QueryRedelegationsRequest,
                cosmos.staking.v1beta1.QueryOuterClass.QueryRedelegationsResponse>(
                  this, METHODID_REDELEGATIONS)))
          .addMethod(
            getDelegatorValidatorsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorsRequest,
                cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorsResponse>(
                  this, METHODID_DELEGATOR_VALIDATORS)))
          .addMethod(
            getDelegatorValidatorMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorRequest,
                cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorResponse>(
                  this, METHODID_DELEGATOR_VALIDATOR)))
          .addMethod(
            getHistoricalInfoMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.staking.v1beta1.QueryOuterClass.QueryHistoricalInfoRequest,
                cosmos.staking.v1beta1.QueryOuterClass.QueryHistoricalInfoResponse>(
                  this, METHODID_HISTORICAL_INFO)))
          .addMethod(
            getPoolMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.staking.v1beta1.QueryOuterClass.QueryPoolRequest,
                cosmos.staking.v1beta1.QueryOuterClass.QueryPoolResponse>(
                  this, METHODID_POOL)))
          .addMethod(
            getParamsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.staking.v1beta1.QueryOuterClass.QueryParamsRequest,
                cosmos.staking.v1beta1.QueryOuterClass.QueryParamsResponse>(
                  this, METHODID_PARAMS)))
          .addMethod(
            getTokenizeShareRecordByIdMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByIdRequest,
                cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByIdResponse>(
                  this, METHODID_TOKENIZE_SHARE_RECORD_BY_ID)))
          .addMethod(
            getTokenizeShareRecordByDenomMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByDenomRequest,
                cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByDenomResponse>(
                  this, METHODID_TOKENIZE_SHARE_RECORD_BY_DENOM)))
          .addMethod(
            getTokenizeShareRecordsOwnedMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordsOwnedRequest,
                cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordsOwnedResponse>(
                  this, METHODID_TOKENIZE_SHARE_RECORDS_OWNED)))
          .addMethod(
            getAllTokenizeShareRecordsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.staking.v1beta1.QueryOuterClass.QueryAllTokenizeShareRecordsRequest,
                cosmos.staking.v1beta1.QueryOuterClass.QueryAllTokenizeShareRecordsResponse>(
                  this, METHODID_ALL_TOKENIZE_SHARE_RECORDS)))
          .addMethod(
            getLastTokenizeShareRecordIdMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.staking.v1beta1.QueryOuterClass.QueryLastTokenizeShareRecordIdRequest,
                cosmos.staking.v1beta1.QueryOuterClass.QueryLastTokenizeShareRecordIdResponse>(
                  this, METHODID_LAST_TOKENIZE_SHARE_RECORD_ID)))
          .addMethod(
            getTotalTokenizeSharedAssetsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.staking.v1beta1.QueryOuterClass.QueryTotalTokenizeSharedAssetsRequest,
                cosmos.staking.v1beta1.QueryOuterClass.QueryTotalTokenizeSharedAssetsResponse>(
                  this, METHODID_TOTAL_TOKENIZE_SHARED_ASSETS)))
          .addMethod(
            getTotalLiquidStakedMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.staking.v1beta1.QueryOuterClass.QueryTotalLiquidStaked,
                cosmos.staking.v1beta1.QueryOuterClass.QueryTotalLiquidStakedResponse>(
                  this, METHODID_TOTAL_LIQUID_STAKED)))
          .addMethod(
            getTokenizeShareLockInfoMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareLockInfo,
                cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareLockInfoResponse>(
                  this, METHODID_TOKENIZE_SHARE_LOCK_INFO)))
          .build();
    }
  }

  /**
   * <pre>
   * Query defines the gRPC querier service.
   * </pre>
   */
  public static final class QueryStub extends io.grpc.stub.AbstractAsyncStub<QueryStub> {
    private QueryStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected QueryStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new QueryStub(channel, callOptions);
    }

    /**
     * <pre>
     * Validators queries all validators that match the given status.
     * </pre>
     */
    public void validators(cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorsRequest request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getValidatorsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Validator queries validator info for given validator address.
     * </pre>
     */
    public void validator(cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorRequest request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getValidatorMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * ValidatorDelegations queries delegate info for given validator.
     * </pre>
     */
    public void validatorDelegations(cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorDelegationsRequest request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorDelegationsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getValidatorDelegationsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * ValidatorUnbondingDelegations queries unbonding delegations of a validator.
     * </pre>
     */
    public void validatorUnbondingDelegations(cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorUnbondingDelegationsRequest request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorUnbondingDelegationsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getValidatorUnbondingDelegationsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Delegation queries delegate info for given validator delegator pair.
     * </pre>
     */
    public void delegation(cosmos.staking.v1beta1.QueryOuterClass.QueryDelegationRequest request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryDelegationResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDelegationMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * UnbondingDelegation queries unbonding info for given validator delegator
     * pair.
     * </pre>
     */
    public void unbondingDelegation(cosmos.staking.v1beta1.QueryOuterClass.QueryUnbondingDelegationRequest request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryUnbondingDelegationResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUnbondingDelegationMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * DelegatorDelegations queries all delegations of a given delegator address.
     * </pre>
     */
    public void delegatorDelegations(cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorDelegationsRequest request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorDelegationsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDelegatorDelegationsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * DelegatorUnbondingDelegations queries all unbonding delegations of a given
     * delegator address.
     * </pre>
     */
    public void delegatorUnbondingDelegations(cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorUnbondingDelegationsRequest request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorUnbondingDelegationsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDelegatorUnbondingDelegationsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Redelegations queries redelegations of given address.
     * </pre>
     */
    public void redelegations(cosmos.staking.v1beta1.QueryOuterClass.QueryRedelegationsRequest request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryRedelegationsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRedelegationsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * DelegatorValidators queries all validators info for given delegator
     * address.
     * </pre>
     */
    public void delegatorValidators(cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorsRequest request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDelegatorValidatorsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * DelegatorValidator queries validator info for given delegator validator
     * pair.
     * </pre>
     */
    public void delegatorValidator(cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorRequest request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDelegatorValidatorMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * HistoricalInfo queries the historical info for given height.
     * </pre>
     */
    public void historicalInfo(cosmos.staking.v1beta1.QueryOuterClass.QueryHistoricalInfoRequest request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryHistoricalInfoResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getHistoricalInfoMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Pool queries the pool info.
     * </pre>
     */
    public void pool(cosmos.staking.v1beta1.QueryOuterClass.QueryPoolRequest request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryPoolResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPoolMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Parameters queries the staking parameters.
     * </pre>
     */
    public void params(cosmos.staking.v1beta1.QueryOuterClass.QueryParamsRequest request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryParamsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getParamsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Query for individual tokenize share record information by share by id
     * </pre>
     */
    public void tokenizeShareRecordById(cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByIdRequest request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByIdResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getTokenizeShareRecordByIdMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Query for individual tokenize share record information by share denom
     * </pre>
     */
    public void tokenizeShareRecordByDenom(cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByDenomRequest request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByDenomResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getTokenizeShareRecordByDenomMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Query tokenize share records by address
     * </pre>
     */
    public void tokenizeShareRecordsOwned(cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordsOwnedRequest request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordsOwnedResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getTokenizeShareRecordsOwnedMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Query for all tokenize share records
     * </pre>
     */
    public void allTokenizeShareRecords(cosmos.staking.v1beta1.QueryOuterClass.QueryAllTokenizeShareRecordsRequest request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryAllTokenizeShareRecordsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAllTokenizeShareRecordsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Query for last tokenize share record id
     * </pre>
     */
    public void lastTokenizeShareRecordId(cosmos.staking.v1beta1.QueryOuterClass.QueryLastTokenizeShareRecordIdRequest request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryLastTokenizeShareRecordIdResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getLastTokenizeShareRecordIdMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Query for total tokenized staked assets
     * </pre>
     */
    public void totalTokenizeSharedAssets(cosmos.staking.v1beta1.QueryOuterClass.QueryTotalTokenizeSharedAssetsRequest request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryTotalTokenizeSharedAssetsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getTotalTokenizeSharedAssetsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Query for total liquid staked (including tokenized shares or owned by an liquid staking provider)
     * </pre>
     */
    public void totalLiquidStaked(cosmos.staking.v1beta1.QueryOuterClass.QueryTotalLiquidStaked request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryTotalLiquidStakedResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getTotalLiquidStakedMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Query tokenize share locks
     * </pre>
     */
    public void tokenizeShareLockInfo(cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareLockInfo request,
        io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareLockInfoResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getTokenizeShareLockInfoMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * Query defines the gRPC querier service.
   * </pre>
   */
  public static final class QueryBlockingStub extends io.grpc.stub.AbstractBlockingStub<QueryBlockingStub> {
    private QueryBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected QueryBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new QueryBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Validators queries all validators that match the given status.
     * </pre>
     */
    public cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorsResponse validators(cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getValidatorsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Validator queries validator info for given validator address.
     * </pre>
     */
    public cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorResponse validator(cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getValidatorMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * ValidatorDelegations queries delegate info for given validator.
     * </pre>
     */
    public cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorDelegationsResponse validatorDelegations(cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorDelegationsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getValidatorDelegationsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * ValidatorUnbondingDelegations queries unbonding delegations of a validator.
     * </pre>
     */
    public cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorUnbondingDelegationsResponse validatorUnbondingDelegations(cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorUnbondingDelegationsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getValidatorUnbondingDelegationsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Delegation queries delegate info for given validator delegator pair.
     * </pre>
     */
    public cosmos.staking.v1beta1.QueryOuterClass.QueryDelegationResponse delegation(cosmos.staking.v1beta1.QueryOuterClass.QueryDelegationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDelegationMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * UnbondingDelegation queries unbonding info for given validator delegator
     * pair.
     * </pre>
     */
    public cosmos.staking.v1beta1.QueryOuterClass.QueryUnbondingDelegationResponse unbondingDelegation(cosmos.staking.v1beta1.QueryOuterClass.QueryUnbondingDelegationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUnbondingDelegationMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * DelegatorDelegations queries all delegations of a given delegator address.
     * </pre>
     */
    public cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorDelegationsResponse delegatorDelegations(cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorDelegationsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDelegatorDelegationsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * DelegatorUnbondingDelegations queries all unbonding delegations of a given
     * delegator address.
     * </pre>
     */
    public cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorUnbondingDelegationsResponse delegatorUnbondingDelegations(cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorUnbondingDelegationsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDelegatorUnbondingDelegationsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Redelegations queries redelegations of given address.
     * </pre>
     */
    public cosmos.staking.v1beta1.QueryOuterClass.QueryRedelegationsResponse redelegations(cosmos.staking.v1beta1.QueryOuterClass.QueryRedelegationsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRedelegationsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * DelegatorValidators queries all validators info for given delegator
     * address.
     * </pre>
     */
    public cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorsResponse delegatorValidators(cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDelegatorValidatorsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * DelegatorValidator queries validator info for given delegator validator
     * pair.
     * </pre>
     */
    public cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorResponse delegatorValidator(cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDelegatorValidatorMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * HistoricalInfo queries the historical info for given height.
     * </pre>
     */
    public cosmos.staking.v1beta1.QueryOuterClass.QueryHistoricalInfoResponse historicalInfo(cosmos.staking.v1beta1.QueryOuterClass.QueryHistoricalInfoRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getHistoricalInfoMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Pool queries the pool info.
     * </pre>
     */
    public cosmos.staking.v1beta1.QueryOuterClass.QueryPoolResponse pool(cosmos.staking.v1beta1.QueryOuterClass.QueryPoolRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPoolMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Parameters queries the staking parameters.
     * </pre>
     */
    public cosmos.staking.v1beta1.QueryOuterClass.QueryParamsResponse params(cosmos.staking.v1beta1.QueryOuterClass.QueryParamsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getParamsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Query for individual tokenize share record information by share by id
     * </pre>
     */
    public cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByIdResponse tokenizeShareRecordById(cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByIdRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getTokenizeShareRecordByIdMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Query for individual tokenize share record information by share denom
     * </pre>
     */
    public cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByDenomResponse tokenizeShareRecordByDenom(cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByDenomRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getTokenizeShareRecordByDenomMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Query tokenize share records by address
     * </pre>
     */
    public cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordsOwnedResponse tokenizeShareRecordsOwned(cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordsOwnedRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getTokenizeShareRecordsOwnedMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Query for all tokenize share records
     * </pre>
     */
    public cosmos.staking.v1beta1.QueryOuterClass.QueryAllTokenizeShareRecordsResponse allTokenizeShareRecords(cosmos.staking.v1beta1.QueryOuterClass.QueryAllTokenizeShareRecordsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAllTokenizeShareRecordsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Query for last tokenize share record id
     * </pre>
     */
    public cosmos.staking.v1beta1.QueryOuterClass.QueryLastTokenizeShareRecordIdResponse lastTokenizeShareRecordId(cosmos.staking.v1beta1.QueryOuterClass.QueryLastTokenizeShareRecordIdRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getLastTokenizeShareRecordIdMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Query for total tokenized staked assets
     * </pre>
     */
    public cosmos.staking.v1beta1.QueryOuterClass.QueryTotalTokenizeSharedAssetsResponse totalTokenizeSharedAssets(cosmos.staking.v1beta1.QueryOuterClass.QueryTotalTokenizeSharedAssetsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getTotalTokenizeSharedAssetsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Query for total liquid staked (including tokenized shares or owned by an liquid staking provider)
     * </pre>
     */
    public cosmos.staking.v1beta1.QueryOuterClass.QueryTotalLiquidStakedResponse totalLiquidStaked(cosmos.staking.v1beta1.QueryOuterClass.QueryTotalLiquidStaked request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getTotalLiquidStakedMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Query tokenize share locks
     * </pre>
     */
    public cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareLockInfoResponse tokenizeShareLockInfo(cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareLockInfo request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getTokenizeShareLockInfoMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * Query defines the gRPC querier service.
   * </pre>
   */
  public static final class QueryFutureStub extends io.grpc.stub.AbstractFutureStub<QueryFutureStub> {
    private QueryFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected QueryFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new QueryFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Validators queries all validators that match the given status.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorsResponse> validators(
        cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getValidatorsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Validator queries validator info for given validator address.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorResponse> validator(
        cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getValidatorMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * ValidatorDelegations queries delegate info for given validator.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorDelegationsResponse> validatorDelegations(
        cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorDelegationsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getValidatorDelegationsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * ValidatorUnbondingDelegations queries unbonding delegations of a validator.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorUnbondingDelegationsResponse> validatorUnbondingDelegations(
        cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorUnbondingDelegationsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getValidatorUnbondingDelegationsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Delegation queries delegate info for given validator delegator pair.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.staking.v1beta1.QueryOuterClass.QueryDelegationResponse> delegation(
        cosmos.staking.v1beta1.QueryOuterClass.QueryDelegationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDelegationMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * UnbondingDelegation queries unbonding info for given validator delegator
     * pair.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.staking.v1beta1.QueryOuterClass.QueryUnbondingDelegationResponse> unbondingDelegation(
        cosmos.staking.v1beta1.QueryOuterClass.QueryUnbondingDelegationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUnbondingDelegationMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * DelegatorDelegations queries all delegations of a given delegator address.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorDelegationsResponse> delegatorDelegations(
        cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorDelegationsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDelegatorDelegationsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * DelegatorUnbondingDelegations queries all unbonding delegations of a given
     * delegator address.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorUnbondingDelegationsResponse> delegatorUnbondingDelegations(
        cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorUnbondingDelegationsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDelegatorUnbondingDelegationsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Redelegations queries redelegations of given address.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.staking.v1beta1.QueryOuterClass.QueryRedelegationsResponse> redelegations(
        cosmos.staking.v1beta1.QueryOuterClass.QueryRedelegationsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRedelegationsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * DelegatorValidators queries all validators info for given delegator
     * address.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorsResponse> delegatorValidators(
        cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDelegatorValidatorsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * DelegatorValidator queries validator info for given delegator validator
     * pair.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorResponse> delegatorValidator(
        cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDelegatorValidatorMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * HistoricalInfo queries the historical info for given height.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.staking.v1beta1.QueryOuterClass.QueryHistoricalInfoResponse> historicalInfo(
        cosmos.staking.v1beta1.QueryOuterClass.QueryHistoricalInfoRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getHistoricalInfoMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Pool queries the pool info.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.staking.v1beta1.QueryOuterClass.QueryPoolResponse> pool(
        cosmos.staking.v1beta1.QueryOuterClass.QueryPoolRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPoolMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Parameters queries the staking parameters.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.staking.v1beta1.QueryOuterClass.QueryParamsResponse> params(
        cosmos.staking.v1beta1.QueryOuterClass.QueryParamsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getParamsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Query for individual tokenize share record information by share by id
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByIdResponse> tokenizeShareRecordById(
        cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByIdRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getTokenizeShareRecordByIdMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Query for individual tokenize share record information by share denom
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByDenomResponse> tokenizeShareRecordByDenom(
        cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByDenomRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getTokenizeShareRecordByDenomMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Query tokenize share records by address
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordsOwnedResponse> tokenizeShareRecordsOwned(
        cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordsOwnedRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getTokenizeShareRecordsOwnedMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Query for all tokenize share records
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.staking.v1beta1.QueryOuterClass.QueryAllTokenizeShareRecordsResponse> allTokenizeShareRecords(
        cosmos.staking.v1beta1.QueryOuterClass.QueryAllTokenizeShareRecordsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAllTokenizeShareRecordsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Query for last tokenize share record id
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.staking.v1beta1.QueryOuterClass.QueryLastTokenizeShareRecordIdResponse> lastTokenizeShareRecordId(
        cosmos.staking.v1beta1.QueryOuterClass.QueryLastTokenizeShareRecordIdRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getLastTokenizeShareRecordIdMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Query for total tokenized staked assets
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.staking.v1beta1.QueryOuterClass.QueryTotalTokenizeSharedAssetsResponse> totalTokenizeSharedAssets(
        cosmos.staking.v1beta1.QueryOuterClass.QueryTotalTokenizeSharedAssetsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getTotalTokenizeSharedAssetsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Query for total liquid staked (including tokenized shares or owned by an liquid staking provider)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.staking.v1beta1.QueryOuterClass.QueryTotalLiquidStakedResponse> totalLiquidStaked(
        cosmos.staking.v1beta1.QueryOuterClass.QueryTotalLiquidStaked request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getTotalLiquidStakedMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Query tokenize share locks
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareLockInfoResponse> tokenizeShareLockInfo(
        cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareLockInfo request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getTokenizeShareLockInfoMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_VALIDATORS = 0;
  private static final int METHODID_VALIDATOR = 1;
  private static final int METHODID_VALIDATOR_DELEGATIONS = 2;
  private static final int METHODID_VALIDATOR_UNBONDING_DELEGATIONS = 3;
  private static final int METHODID_DELEGATION = 4;
  private static final int METHODID_UNBONDING_DELEGATION = 5;
  private static final int METHODID_DELEGATOR_DELEGATIONS = 6;
  private static final int METHODID_DELEGATOR_UNBONDING_DELEGATIONS = 7;
  private static final int METHODID_REDELEGATIONS = 8;
  private static final int METHODID_DELEGATOR_VALIDATORS = 9;
  private static final int METHODID_DELEGATOR_VALIDATOR = 10;
  private static final int METHODID_HISTORICAL_INFO = 11;
  private static final int METHODID_POOL = 12;
  private static final int METHODID_PARAMS = 13;
  private static final int METHODID_TOKENIZE_SHARE_RECORD_BY_ID = 14;
  private static final int METHODID_TOKENIZE_SHARE_RECORD_BY_DENOM = 15;
  private static final int METHODID_TOKENIZE_SHARE_RECORDS_OWNED = 16;
  private static final int METHODID_ALL_TOKENIZE_SHARE_RECORDS = 17;
  private static final int METHODID_LAST_TOKENIZE_SHARE_RECORD_ID = 18;
  private static final int METHODID_TOTAL_TOKENIZE_SHARED_ASSETS = 19;
  private static final int METHODID_TOTAL_LIQUID_STAKED = 20;
  private static final int METHODID_TOKENIZE_SHARE_LOCK_INFO = 21;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final QueryImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(QueryImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_VALIDATORS:
          serviceImpl.validators((cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorsRequest) request,
              (io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorsResponse>) responseObserver);
          break;
        case METHODID_VALIDATOR:
          serviceImpl.validator((cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorRequest) request,
              (io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorResponse>) responseObserver);
          break;
        case METHODID_VALIDATOR_DELEGATIONS:
          serviceImpl.validatorDelegations((cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorDelegationsRequest) request,
              (io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorDelegationsResponse>) responseObserver);
          break;
        case METHODID_VALIDATOR_UNBONDING_DELEGATIONS:
          serviceImpl.validatorUnbondingDelegations((cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorUnbondingDelegationsRequest) request,
              (io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryValidatorUnbondingDelegationsResponse>) responseObserver);
          break;
        case METHODID_DELEGATION:
          serviceImpl.delegation((cosmos.staking.v1beta1.QueryOuterClass.QueryDelegationRequest) request,
              (io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryDelegationResponse>) responseObserver);
          break;
        case METHODID_UNBONDING_DELEGATION:
          serviceImpl.unbondingDelegation((cosmos.staking.v1beta1.QueryOuterClass.QueryUnbondingDelegationRequest) request,
              (io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryUnbondingDelegationResponse>) responseObserver);
          break;
        case METHODID_DELEGATOR_DELEGATIONS:
          serviceImpl.delegatorDelegations((cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorDelegationsRequest) request,
              (io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorDelegationsResponse>) responseObserver);
          break;
        case METHODID_DELEGATOR_UNBONDING_DELEGATIONS:
          serviceImpl.delegatorUnbondingDelegations((cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorUnbondingDelegationsRequest) request,
              (io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorUnbondingDelegationsResponse>) responseObserver);
          break;
        case METHODID_REDELEGATIONS:
          serviceImpl.redelegations((cosmos.staking.v1beta1.QueryOuterClass.QueryRedelegationsRequest) request,
              (io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryRedelegationsResponse>) responseObserver);
          break;
        case METHODID_DELEGATOR_VALIDATORS:
          serviceImpl.delegatorValidators((cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorsRequest) request,
              (io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorsResponse>) responseObserver);
          break;
        case METHODID_DELEGATOR_VALIDATOR:
          serviceImpl.delegatorValidator((cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorRequest) request,
              (io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryDelegatorValidatorResponse>) responseObserver);
          break;
        case METHODID_HISTORICAL_INFO:
          serviceImpl.historicalInfo((cosmos.staking.v1beta1.QueryOuterClass.QueryHistoricalInfoRequest) request,
              (io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryHistoricalInfoResponse>) responseObserver);
          break;
        case METHODID_POOL:
          serviceImpl.pool((cosmos.staking.v1beta1.QueryOuterClass.QueryPoolRequest) request,
              (io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryPoolResponse>) responseObserver);
          break;
        case METHODID_PARAMS:
          serviceImpl.params((cosmos.staking.v1beta1.QueryOuterClass.QueryParamsRequest) request,
              (io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryParamsResponse>) responseObserver);
          break;
        case METHODID_TOKENIZE_SHARE_RECORD_BY_ID:
          serviceImpl.tokenizeShareRecordById((cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByIdRequest) request,
              (io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByIdResponse>) responseObserver);
          break;
        case METHODID_TOKENIZE_SHARE_RECORD_BY_DENOM:
          serviceImpl.tokenizeShareRecordByDenom((cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByDenomRequest) request,
              (io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordByDenomResponse>) responseObserver);
          break;
        case METHODID_TOKENIZE_SHARE_RECORDS_OWNED:
          serviceImpl.tokenizeShareRecordsOwned((cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordsOwnedRequest) request,
              (io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareRecordsOwnedResponse>) responseObserver);
          break;
        case METHODID_ALL_TOKENIZE_SHARE_RECORDS:
          serviceImpl.allTokenizeShareRecords((cosmos.staking.v1beta1.QueryOuterClass.QueryAllTokenizeShareRecordsRequest) request,
              (io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryAllTokenizeShareRecordsResponse>) responseObserver);
          break;
        case METHODID_LAST_TOKENIZE_SHARE_RECORD_ID:
          serviceImpl.lastTokenizeShareRecordId((cosmos.staking.v1beta1.QueryOuterClass.QueryLastTokenizeShareRecordIdRequest) request,
              (io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryLastTokenizeShareRecordIdResponse>) responseObserver);
          break;
        case METHODID_TOTAL_TOKENIZE_SHARED_ASSETS:
          serviceImpl.totalTokenizeSharedAssets((cosmos.staking.v1beta1.QueryOuterClass.QueryTotalTokenizeSharedAssetsRequest) request,
              (io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryTotalTokenizeSharedAssetsResponse>) responseObserver);
          break;
        case METHODID_TOTAL_LIQUID_STAKED:
          serviceImpl.totalLiquidStaked((cosmos.staking.v1beta1.QueryOuterClass.QueryTotalLiquidStaked) request,
              (io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryTotalLiquidStakedResponse>) responseObserver);
          break;
        case METHODID_TOKENIZE_SHARE_LOCK_INFO:
          serviceImpl.tokenizeShareLockInfo((cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareLockInfo) request,
              (io.grpc.stub.StreamObserver<cosmos.staking.v1beta1.QueryOuterClass.QueryTokenizeShareLockInfoResponse>) responseObserver);
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

  private static abstract class QueryBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    QueryBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return cosmos.staking.v1beta1.QueryOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Query");
    }
  }

  private static final class QueryFileDescriptorSupplier
      extends QueryBaseDescriptorSupplier {
    QueryFileDescriptorSupplier() {}
  }

  private static final class QueryMethodDescriptorSupplier
      extends QueryBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    QueryMethodDescriptorSupplier(String methodName) {
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
      synchronized (QueryGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new QueryFileDescriptorSupplier())
              .addMethod(getValidatorsMethod())
              .addMethod(getValidatorMethod())
              .addMethod(getValidatorDelegationsMethod())
              .addMethod(getValidatorUnbondingDelegationsMethod())
              .addMethod(getDelegationMethod())
              .addMethod(getUnbondingDelegationMethod())
              .addMethod(getDelegatorDelegationsMethod())
              .addMethod(getDelegatorUnbondingDelegationsMethod())
              .addMethod(getRedelegationsMethod())
              .addMethod(getDelegatorValidatorsMethod())
              .addMethod(getDelegatorValidatorMethod())
              .addMethod(getHistoricalInfoMethod())
              .addMethod(getPoolMethod())
              .addMethod(getParamsMethod())
              .addMethod(getTokenizeShareRecordByIdMethod())
              .addMethod(getTokenizeShareRecordByDenomMethod())
              .addMethod(getTokenizeShareRecordsOwnedMethod())
              .addMethod(getAllTokenizeShareRecordsMethod())
              .addMethod(getLastTokenizeShareRecordIdMethod())
              .addMethod(getTotalTokenizeSharedAssetsMethod())
              .addMethod(getTotalLiquidStakedMethod())
              .addMethod(getTokenizeShareLockInfoMethod())
              .build();
        }
      }
    }
    return result;
  }
}
