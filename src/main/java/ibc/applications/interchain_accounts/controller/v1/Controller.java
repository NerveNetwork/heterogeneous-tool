// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ibc/applications/interchain_accounts/controller/v1/controller.proto

package ibc.applications.interchain_accounts.controller.v1;

import com.google.protobuf.GoGoProtos;

public final class Controller {
  private Controller() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface ParamsOrBuilder extends
      // @@protoc_insertion_point(interface_extends:ibc.applications.interchain_accounts.controller.v1.Params)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <pre>
     * controller_enabled enables or disables the controller submodule.
     * </pre>
     *
     * <code>bool controller_enabled = 1 [(.gogoproto.moretags) = "yaml:&#92;"controller_enabled&#92;""];</code>
     * @return The controllerEnabled.
     */
    boolean getControllerEnabled();
  }
  /**
   * <pre>
   * Params defines the set of on-chain interchain accounts parameters.
   * The following parameters may be used to disable the controller submodule.
   * </pre>
   *
   * Protobuf type {@code ibc.applications.interchain_accounts.controller.v1.Params}
   */
  public static final class Params extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:ibc.applications.interchain_accounts.controller.v1.Params)
      ParamsOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use Params.newBuilder() to construct.
    private Params(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private Params() {
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new Params();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private Params(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 8: {

              controllerEnabled_ = input.readBool();
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (com.google.protobuf.UninitializedMessageException e) {
        throw e.asInvalidProtocolBufferException().setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return ibc.applications.interchain_accounts.controller.v1.Controller.internal_static_ibc_applications_interchain_accounts_controller_v1_Params_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return ibc.applications.interchain_accounts.controller.v1.Controller.internal_static_ibc_applications_interchain_accounts_controller_v1_Params_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              ibc.applications.interchain_accounts.controller.v1.Controller.Params.class, ibc.applications.interchain_accounts.controller.v1.Controller.Params.Builder.class);
    }

    public static final int CONTROLLER_ENABLED_FIELD_NUMBER = 1;
    private boolean controllerEnabled_;
    /**
     * <pre>
     * controller_enabled enables or disables the controller submodule.
     * </pre>
     *
     * <code>bool controller_enabled = 1 [(.gogoproto.moretags) = "yaml:&#92;"controller_enabled&#92;""];</code>
     * @return The controllerEnabled.
     */
    @java.lang.Override
    public boolean getControllerEnabled() {
      return controllerEnabled_;
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (controllerEnabled_ != false) {
        output.writeBool(1, controllerEnabled_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (controllerEnabled_ != false) {
        size += com.google.protobuf.CodedOutputStream
          .computeBoolSize(1, controllerEnabled_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof ibc.applications.interchain_accounts.controller.v1.Controller.Params)) {
        return super.equals(obj);
      }
      ibc.applications.interchain_accounts.controller.v1.Controller.Params other = (ibc.applications.interchain_accounts.controller.v1.Controller.Params) obj;

      if (getControllerEnabled()
          != other.getControllerEnabled()) return false;
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + CONTROLLER_ENABLED_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
          getControllerEnabled());
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static ibc.applications.interchain_accounts.controller.v1.Controller.Params parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static ibc.applications.interchain_accounts.controller.v1.Controller.Params parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static ibc.applications.interchain_accounts.controller.v1.Controller.Params parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static ibc.applications.interchain_accounts.controller.v1.Controller.Params parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static ibc.applications.interchain_accounts.controller.v1.Controller.Params parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static ibc.applications.interchain_accounts.controller.v1.Controller.Params parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static ibc.applications.interchain_accounts.controller.v1.Controller.Params parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static ibc.applications.interchain_accounts.controller.v1.Controller.Params parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static ibc.applications.interchain_accounts.controller.v1.Controller.Params parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static ibc.applications.interchain_accounts.controller.v1.Controller.Params parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static ibc.applications.interchain_accounts.controller.v1.Controller.Params parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static ibc.applications.interchain_accounts.controller.v1.Controller.Params parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(ibc.applications.interchain_accounts.controller.v1.Controller.Params prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * <pre>
     * Params defines the set of on-chain interchain accounts parameters.
     * The following parameters may be used to disable the controller submodule.
     * </pre>
     *
     * Protobuf type {@code ibc.applications.interchain_accounts.controller.v1.Params}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:ibc.applications.interchain_accounts.controller.v1.Params)
        ibc.applications.interchain_accounts.controller.v1.Controller.ParamsOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return ibc.applications.interchain_accounts.controller.v1.Controller.internal_static_ibc_applications_interchain_accounts_controller_v1_Params_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return ibc.applications.interchain_accounts.controller.v1.Controller.internal_static_ibc_applications_interchain_accounts_controller_v1_Params_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                ibc.applications.interchain_accounts.controller.v1.Controller.Params.class, ibc.applications.interchain_accounts.controller.v1.Controller.Params.Builder.class);
      }

      // Construct using ibc.applications.interchain_accounts.controller.v1.Controller.Params.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        controllerEnabled_ = false;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return ibc.applications.interchain_accounts.controller.v1.Controller.internal_static_ibc_applications_interchain_accounts_controller_v1_Params_descriptor;
      }

      @java.lang.Override
      public ibc.applications.interchain_accounts.controller.v1.Controller.Params getDefaultInstanceForType() {
        return ibc.applications.interchain_accounts.controller.v1.Controller.Params.getDefaultInstance();
      }

      @java.lang.Override
      public ibc.applications.interchain_accounts.controller.v1.Controller.Params build() {
        ibc.applications.interchain_accounts.controller.v1.Controller.Params result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public ibc.applications.interchain_accounts.controller.v1.Controller.Params buildPartial() {
        ibc.applications.interchain_accounts.controller.v1.Controller.Params result = new ibc.applications.interchain_accounts.controller.v1.Controller.Params(this);
        result.controllerEnabled_ = controllerEnabled_;
        onBuilt();
        return result;
      }

      @java.lang.Override
      public Builder clone() {
        return super.clone();
      }
      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.setField(field, value);
      }
      @java.lang.Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @java.lang.Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof ibc.applications.interchain_accounts.controller.v1.Controller.Params) {
          return mergeFrom((ibc.applications.interchain_accounts.controller.v1.Controller.Params)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(ibc.applications.interchain_accounts.controller.v1.Controller.Params other) {
        if (other == ibc.applications.interchain_accounts.controller.v1.Controller.Params.getDefaultInstance()) return this;
        if (other.getControllerEnabled() != false) {
          setControllerEnabled(other.getControllerEnabled());
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        ibc.applications.interchain_accounts.controller.v1.Controller.Params parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (ibc.applications.interchain_accounts.controller.v1.Controller.Params) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private boolean controllerEnabled_ ;
      /**
       * <pre>
       * controller_enabled enables or disables the controller submodule.
       * </pre>
       *
       * <code>bool controller_enabled = 1 [(.gogoproto.moretags) = "yaml:&#92;"controller_enabled&#92;""];</code>
       * @return The controllerEnabled.
       */
      @java.lang.Override
      public boolean getControllerEnabled() {
        return controllerEnabled_;
      }
      /**
       * <pre>
       * controller_enabled enables or disables the controller submodule.
       * </pre>
       *
       * <code>bool controller_enabled = 1 [(.gogoproto.moretags) = "yaml:&#92;"controller_enabled&#92;""];</code>
       * @param value The controllerEnabled to set.
       * @return This builder for chaining.
       */
      public Builder setControllerEnabled(boolean value) {
        
        controllerEnabled_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       * controller_enabled enables or disables the controller submodule.
       * </pre>
       *
       * <code>bool controller_enabled = 1 [(.gogoproto.moretags) = "yaml:&#92;"controller_enabled&#92;""];</code>
       * @return This builder for chaining.
       */
      public Builder clearControllerEnabled() {
        
        controllerEnabled_ = false;
        onChanged();
        return this;
      }
      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:ibc.applications.interchain_accounts.controller.v1.Params)
    }

    // @@protoc_insertion_point(class_scope:ibc.applications.interchain_accounts.controller.v1.Params)
    private static final ibc.applications.interchain_accounts.controller.v1.Controller.Params DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new ibc.applications.interchain_accounts.controller.v1.Controller.Params();
    }

    public static ibc.applications.interchain_accounts.controller.v1.Controller.Params getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<Params>
        PARSER = new com.google.protobuf.AbstractParser<Params>() {
      @java.lang.Override
      public Params parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new Params(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<Params> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<Params> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public ibc.applications.interchain_accounts.controller.v1.Controller.Params getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ibc_applications_interchain_accounts_controller_v1_Params_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ibc_applications_interchain_accounts_controller_v1_Params_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\nCibc/applications/interchain_accounts/c" +
      "ontroller/v1/controller.proto\0222ibc.appli" +
      "cations.interchain_accounts.controller.v" +
      "1\032\024gogoproto/gogo.proto\"C\n\006Params\0229\n\022con" +
      "troller_enabled\030\001 \001(\010B\035\362\336\037\031yaml:\"control" +
      "ler_enabled\"BRZPgithub.com/cosmos/ibc-go" +
      "/v3/modules/apps/27-interchain-accounts/" +
      "controller/typesb\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          GoGoProtos.getDescriptor(),
        });
    internal_static_ibc_applications_interchain_accounts_controller_v1_Params_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_ibc_applications_interchain_accounts_controller_v1_Params_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ibc_applications_interchain_accounts_controller_v1_Params_descriptor,
        new java.lang.String[] { "ControllerEnabled", });
    com.google.protobuf.ExtensionRegistry registry =
        com.google.protobuf.ExtensionRegistry.newInstance();
    registry.add(GoGoProtos.moretags);
    com.google.protobuf.Descriptors.FileDescriptor
        .internalUpdateFileDescriptor(descriptor, registry);
    GoGoProtos.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
