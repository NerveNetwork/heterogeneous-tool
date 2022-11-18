// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: cosmos/evidence/v1beta1/evidence.proto

package cosmos.evidence.v1beta1;

import com.google.protobuf.GoGoProtos;
import com.google.protobuf.Timestamp;
import com.google.protobuf.TimestampOrBuilder;
import com.google.protobuf.TimestampProto;

public final class Evidence {
  private Evidence() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface EquivocationOrBuilder extends
      // @@protoc_insertion_point(interface_extends:cosmos.evidence.v1beta1.Equivocation)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int64 height = 1;</code>
     * @return The height.
     */
    long getHeight();

    /**
     * <code>.google.protobuf.Timestamp time = 2 [(.gogoproto.nullable) = false, (.gogoproto.stdtime) = true];</code>
     * @return Whether the time field is set.
     */
    boolean hasTime();
    /**
     * <code>.google.protobuf.Timestamp time = 2 [(.gogoproto.nullable) = false, (.gogoproto.stdtime) = true];</code>
     * @return The time.
     */
    Timestamp getTime();
    /**
     * <code>.google.protobuf.Timestamp time = 2 [(.gogoproto.nullable) = false, (.gogoproto.stdtime) = true];</code>
     */
    TimestampOrBuilder getTimeOrBuilder();

    /**
     * <code>int64 power = 3;</code>
     * @return The power.
     */
    long getPower();

    /**
     * <code>string consensus_address = 4 [(.gogoproto.moretags) = "yaml:&#92;"consensus_address&#92;""];</code>
     * @return The consensusAddress.
     */
    java.lang.String getConsensusAddress();
    /**
     * <code>string consensus_address = 4 [(.gogoproto.moretags) = "yaml:&#92;"consensus_address&#92;""];</code>
     * @return The bytes for consensusAddress.
     */
    com.google.protobuf.ByteString
        getConsensusAddressBytes();
  }
  /**
   * <pre>
   * Equivocation implements the Evidence interface and defines evidence of double
   * signing misbehavior.
   * </pre>
   *
   * Protobuf type {@code cosmos.evidence.v1beta1.Equivocation}
   */
  public static final class Equivocation extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:cosmos.evidence.v1beta1.Equivocation)
      EquivocationOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use Equivocation.newBuilder() to construct.
    private Equivocation(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private Equivocation() {
      consensusAddress_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new Equivocation();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private Equivocation(
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

              height_ = input.readInt64();
              break;
            }
            case 18: {
              Timestamp.Builder subBuilder = null;
              if (time_ != null) {
                subBuilder = time_.toBuilder();
              }
              time_ = input.readMessage(Timestamp.parser(), extensionRegistry);
              if (subBuilder != null) {
                subBuilder.mergeFrom(time_);
                time_ = subBuilder.buildPartial();
              }

              break;
            }
            case 24: {

              power_ = input.readInt64();
              break;
            }
            case 34: {
              java.lang.String s = input.readStringRequireUtf8();

              consensusAddress_ = s;
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
      return cosmos.evidence.v1beta1.Evidence.internal_static_cosmos_evidence_v1beta1_Equivocation_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return cosmos.evidence.v1beta1.Evidence.internal_static_cosmos_evidence_v1beta1_Equivocation_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              cosmos.evidence.v1beta1.Evidence.Equivocation.class, cosmos.evidence.v1beta1.Evidence.Equivocation.Builder.class);
    }

    public static final int HEIGHT_FIELD_NUMBER = 1;
    private long height_;
    /**
     * <code>int64 height = 1;</code>
     * @return The height.
     */
    @java.lang.Override
    public long getHeight() {
      return height_;
    }

    public static final int TIME_FIELD_NUMBER = 2;
    private Timestamp time_;
    /**
     * <code>.google.protobuf.Timestamp time = 2 [(.gogoproto.nullable) = false, (.gogoproto.stdtime) = true];</code>
     * @return Whether the time field is set.
     */
    @java.lang.Override
    public boolean hasTime() {
      return time_ != null;
    }
    /**
     * <code>.google.protobuf.Timestamp time = 2 [(.gogoproto.nullable) = false, (.gogoproto.stdtime) = true];</code>
     * @return The time.
     */
    @java.lang.Override
    public Timestamp getTime() {
      return time_ == null ? Timestamp.getDefaultInstance() : time_;
    }
    /**
     * <code>.google.protobuf.Timestamp time = 2 [(.gogoproto.nullable) = false, (.gogoproto.stdtime) = true];</code>
     */
    @java.lang.Override
    public TimestampOrBuilder getTimeOrBuilder() {
      return getTime();
    }

    public static final int POWER_FIELD_NUMBER = 3;
    private long power_;
    /**
     * <code>int64 power = 3;</code>
     * @return The power.
     */
    @java.lang.Override
    public long getPower() {
      return power_;
    }

    public static final int CONSENSUS_ADDRESS_FIELD_NUMBER = 4;
    private volatile java.lang.Object consensusAddress_;
    /**
     * <code>string consensus_address = 4 [(.gogoproto.moretags) = "yaml:&#92;"consensus_address&#92;""];</code>
     * @return The consensusAddress.
     */
    @java.lang.Override
    public java.lang.String getConsensusAddress() {
      java.lang.Object ref = consensusAddress_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        consensusAddress_ = s;
        return s;
      }
    }
    /**
     * <code>string consensus_address = 4 [(.gogoproto.moretags) = "yaml:&#92;"consensus_address&#92;""];</code>
     * @return The bytes for consensusAddress.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getConsensusAddressBytes() {
      java.lang.Object ref = consensusAddress_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        consensusAddress_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
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
      if (height_ != 0L) {
        output.writeInt64(1, height_);
      }
      if (time_ != null) {
        output.writeMessage(2, getTime());
      }
      if (power_ != 0L) {
        output.writeInt64(3, power_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(consensusAddress_)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 4, consensusAddress_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (height_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(1, height_);
      }
      if (time_ != null) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(2, getTime());
      }
      if (power_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(3, power_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(consensusAddress_)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, consensusAddress_);
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
      if (!(obj instanceof cosmos.evidence.v1beta1.Evidence.Equivocation)) {
        return super.equals(obj);
      }
      cosmos.evidence.v1beta1.Evidence.Equivocation other = (cosmos.evidence.v1beta1.Evidence.Equivocation) obj;

      if (getHeight()
          != other.getHeight()) return false;
      if (hasTime() != other.hasTime()) return false;
      if (hasTime()) {
        if (!getTime()
            .equals(other.getTime())) return false;
      }
      if (getPower()
          != other.getPower()) return false;
      if (!getConsensusAddress()
          .equals(other.getConsensusAddress())) return false;
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
      hash = (37 * hash) + HEIGHT_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getHeight());
      if (hasTime()) {
        hash = (37 * hash) + TIME_FIELD_NUMBER;
        hash = (53 * hash) + getTime().hashCode();
      }
      hash = (37 * hash) + POWER_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getPower());
      hash = (37 * hash) + CONSENSUS_ADDRESS_FIELD_NUMBER;
      hash = (53 * hash) + getConsensusAddress().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static cosmos.evidence.v1beta1.Evidence.Equivocation parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static cosmos.evidence.v1beta1.Evidence.Equivocation parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static cosmos.evidence.v1beta1.Evidence.Equivocation parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static cosmos.evidence.v1beta1.Evidence.Equivocation parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static cosmos.evidence.v1beta1.Evidence.Equivocation parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static cosmos.evidence.v1beta1.Evidence.Equivocation parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static cosmos.evidence.v1beta1.Evidence.Equivocation parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static cosmos.evidence.v1beta1.Evidence.Equivocation parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static cosmos.evidence.v1beta1.Evidence.Equivocation parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static cosmos.evidence.v1beta1.Evidence.Equivocation parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static cosmos.evidence.v1beta1.Evidence.Equivocation parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static cosmos.evidence.v1beta1.Evidence.Equivocation parseFrom(
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
    public static Builder newBuilder(cosmos.evidence.v1beta1.Evidence.Equivocation prototype) {
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
     * Equivocation implements the Evidence interface and defines evidence of double
     * signing misbehavior.
     * </pre>
     *
     * Protobuf type {@code cosmos.evidence.v1beta1.Equivocation}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:cosmos.evidence.v1beta1.Equivocation)
        cosmos.evidence.v1beta1.Evidence.EquivocationOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return cosmos.evidence.v1beta1.Evidence.internal_static_cosmos_evidence_v1beta1_Equivocation_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return cosmos.evidence.v1beta1.Evidence.internal_static_cosmos_evidence_v1beta1_Equivocation_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                cosmos.evidence.v1beta1.Evidence.Equivocation.class, cosmos.evidence.v1beta1.Evidence.Equivocation.Builder.class);
      }

      // Construct using cosmos.evidence.v1beta1.Evidence.Equivocation.newBuilder()
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
        height_ = 0L;

        if (timeBuilder_ == null) {
          time_ = null;
        } else {
          time_ = null;
          timeBuilder_ = null;
        }
        power_ = 0L;

        consensusAddress_ = "";

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return cosmos.evidence.v1beta1.Evidence.internal_static_cosmos_evidence_v1beta1_Equivocation_descriptor;
      }

      @java.lang.Override
      public cosmos.evidence.v1beta1.Evidence.Equivocation getDefaultInstanceForType() {
        return cosmos.evidence.v1beta1.Evidence.Equivocation.getDefaultInstance();
      }

      @java.lang.Override
      public cosmos.evidence.v1beta1.Evidence.Equivocation build() {
        cosmos.evidence.v1beta1.Evidence.Equivocation result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public cosmos.evidence.v1beta1.Evidence.Equivocation buildPartial() {
        cosmos.evidence.v1beta1.Evidence.Equivocation result = new cosmos.evidence.v1beta1.Evidence.Equivocation(this);
        result.height_ = height_;
        if (timeBuilder_ == null) {
          result.time_ = time_;
        } else {
          result.time_ = timeBuilder_.build();
        }
        result.power_ = power_;
        result.consensusAddress_ = consensusAddress_;
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
        if (other instanceof cosmos.evidence.v1beta1.Evidence.Equivocation) {
          return mergeFrom((cosmos.evidence.v1beta1.Evidence.Equivocation)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(cosmos.evidence.v1beta1.Evidence.Equivocation other) {
        if (other == cosmos.evidence.v1beta1.Evidence.Equivocation.getDefaultInstance()) return this;
        if (other.getHeight() != 0L) {
          setHeight(other.getHeight());
        }
        if (other.hasTime()) {
          mergeTime(other.getTime());
        }
        if (other.getPower() != 0L) {
          setPower(other.getPower());
        }
        if (!other.getConsensusAddress().isEmpty()) {
          consensusAddress_ = other.consensusAddress_;
          onChanged();
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
        cosmos.evidence.v1beta1.Evidence.Equivocation parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (cosmos.evidence.v1beta1.Evidence.Equivocation) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private long height_ ;
      /**
       * <code>int64 height = 1;</code>
       * @return The height.
       */
      @java.lang.Override
      public long getHeight() {
        return height_;
      }
      /**
       * <code>int64 height = 1;</code>
       * @param value The height to set.
       * @return This builder for chaining.
       */
      public Builder setHeight(long value) {
        
        height_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 height = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearHeight() {
        
        height_ = 0L;
        onChanged();
        return this;
      }

      private Timestamp time_;
      private com.google.protobuf.SingleFieldBuilderV3<
              Timestamp, Timestamp.Builder, TimestampOrBuilder> timeBuilder_;
      /**
       * <code>.google.protobuf.Timestamp time = 2 [(.gogoproto.nullable) = false, (.gogoproto.stdtime) = true];</code>
       * @return Whether the time field is set.
       */
      public boolean hasTime() {
        return timeBuilder_ != null || time_ != null;
      }
      /**
       * <code>.google.protobuf.Timestamp time = 2 [(.gogoproto.nullable) = false, (.gogoproto.stdtime) = true];</code>
       * @return The time.
       */
      public Timestamp getTime() {
        if (timeBuilder_ == null) {
          return time_ == null ? Timestamp.getDefaultInstance() : time_;
        } else {
          return timeBuilder_.getMessage();
        }
      }
      /**
       * <code>.google.protobuf.Timestamp time = 2 [(.gogoproto.nullable) = false, (.gogoproto.stdtime) = true];</code>
       */
      public Builder setTime(Timestamp value) {
        if (timeBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          time_ = value;
          onChanged();
        } else {
          timeBuilder_.setMessage(value);
        }

        return this;
      }
      /**
       * <code>.google.protobuf.Timestamp time = 2 [(.gogoproto.nullable) = false, (.gogoproto.stdtime) = true];</code>
       */
      public Builder setTime(
          Timestamp.Builder builderForValue) {
        if (timeBuilder_ == null) {
          time_ = builderForValue.build();
          onChanged();
        } else {
          timeBuilder_.setMessage(builderForValue.build());
        }

        return this;
      }
      /**
       * <code>.google.protobuf.Timestamp time = 2 [(.gogoproto.nullable) = false, (.gogoproto.stdtime) = true];</code>
       */
      public Builder mergeTime(Timestamp value) {
        if (timeBuilder_ == null) {
          if (time_ != null) {
            time_ =
              Timestamp.newBuilder(time_).mergeFrom(value).buildPartial();
          } else {
            time_ = value;
          }
          onChanged();
        } else {
          timeBuilder_.mergeFrom(value);
        }

        return this;
      }
      /**
       * <code>.google.protobuf.Timestamp time = 2 [(.gogoproto.nullable) = false, (.gogoproto.stdtime) = true];</code>
       */
      public Builder clearTime() {
        if (timeBuilder_ == null) {
          time_ = null;
          onChanged();
        } else {
          time_ = null;
          timeBuilder_ = null;
        }

        return this;
      }
      /**
       * <code>.google.protobuf.Timestamp time = 2 [(.gogoproto.nullable) = false, (.gogoproto.stdtime) = true];</code>
       */
      public Timestamp.Builder getTimeBuilder() {
        
        onChanged();
        return getTimeFieldBuilder().getBuilder();
      }
      /**
       * <code>.google.protobuf.Timestamp time = 2 [(.gogoproto.nullable) = false, (.gogoproto.stdtime) = true];</code>
       */
      public TimestampOrBuilder getTimeOrBuilder() {
        if (timeBuilder_ != null) {
          return timeBuilder_.getMessageOrBuilder();
        } else {
          return time_ == null ?
              Timestamp.getDefaultInstance() : time_;
        }
      }
      /**
       * <code>.google.protobuf.Timestamp time = 2 [(.gogoproto.nullable) = false, (.gogoproto.stdtime) = true];</code>
       */
      private com.google.protobuf.SingleFieldBuilderV3<
              Timestamp, Timestamp.Builder, TimestampOrBuilder>
          getTimeFieldBuilder() {
        if (timeBuilder_ == null) {
          timeBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
                  Timestamp, Timestamp.Builder, TimestampOrBuilder>(
                  getTime(),
                  getParentForChildren(),
                  isClean());
          time_ = null;
        }
        return timeBuilder_;
      }

      private long power_ ;
      /**
       * <code>int64 power = 3;</code>
       * @return The power.
       */
      @java.lang.Override
      public long getPower() {
        return power_;
      }
      /**
       * <code>int64 power = 3;</code>
       * @param value The power to set.
       * @return This builder for chaining.
       */
      public Builder setPower(long value) {
        
        power_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 power = 3;</code>
       * @return This builder for chaining.
       */
      public Builder clearPower() {
        
        power_ = 0L;
        onChanged();
        return this;
      }

      private java.lang.Object consensusAddress_ = "";
      /**
       * <code>string consensus_address = 4 [(.gogoproto.moretags) = "yaml:&#92;"consensus_address&#92;""];</code>
       * @return The consensusAddress.
       */
      public java.lang.String getConsensusAddress() {
        java.lang.Object ref = consensusAddress_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          consensusAddress_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string consensus_address = 4 [(.gogoproto.moretags) = "yaml:&#92;"consensus_address&#92;""];</code>
       * @return The bytes for consensusAddress.
       */
      public com.google.protobuf.ByteString
          getConsensusAddressBytes() {
        java.lang.Object ref = consensusAddress_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          consensusAddress_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string consensus_address = 4 [(.gogoproto.moretags) = "yaml:&#92;"consensus_address&#92;""];</code>
       * @param value The consensusAddress to set.
       * @return This builder for chaining.
       */
      public Builder setConsensusAddress(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        consensusAddress_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string consensus_address = 4 [(.gogoproto.moretags) = "yaml:&#92;"consensus_address&#92;""];</code>
       * @return This builder for chaining.
       */
      public Builder clearConsensusAddress() {
        
        consensusAddress_ = getDefaultInstance().getConsensusAddress();
        onChanged();
        return this;
      }
      /**
       * <code>string consensus_address = 4 [(.gogoproto.moretags) = "yaml:&#92;"consensus_address&#92;""];</code>
       * @param value The bytes for consensusAddress to set.
       * @return This builder for chaining.
       */
      public Builder setConsensusAddressBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        consensusAddress_ = value;
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


      // @@protoc_insertion_point(builder_scope:cosmos.evidence.v1beta1.Equivocation)
    }

    // @@protoc_insertion_point(class_scope:cosmos.evidence.v1beta1.Equivocation)
    private static final cosmos.evidence.v1beta1.Evidence.Equivocation DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new cosmos.evidence.v1beta1.Evidence.Equivocation();
    }

    public static cosmos.evidence.v1beta1.Evidence.Equivocation getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<Equivocation>
        PARSER = new com.google.protobuf.AbstractParser<Equivocation>() {
      @java.lang.Override
      public Equivocation parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new Equivocation(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<Equivocation> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<Equivocation> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public cosmos.evidence.v1beta1.Evidence.Equivocation getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_cosmos_evidence_v1beta1_Equivocation_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_cosmos_evidence_v1beta1_Equivocation_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n&cosmos/evidence/v1beta1/evidence.proto" +
      "\022\027cosmos.evidence.v1beta1\032\024gogoproto/gog" +
      "o.proto\032\037google/protobuf/timestamp.proto" +
      "\"\250\001\n\014Equivocation\022\016\n\006height\030\001 \001(\003\0222\n\004tim" +
      "e\030\002 \001(\0132\032.google.protobuf.TimestampB\010\310\336\037" +
      "\000\220\337\037\001\022\r\n\005power\030\003 \001(\003\0227\n\021consensus_addres" +
      "s\030\004 \001(\tB\034\362\336\037\030yaml:\"consensus_address\":\014\230" +
      "\240\037\000\210\240\037\000\350\240\037\000B3Z-github.com/cosmos/cosmos-" +
      "sdk/x/evidence/types\250\342\036\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          GoGoProtos.getDescriptor(),
          TimestampProto.getDescriptor(),
        });
    internal_static_cosmos_evidence_v1beta1_Equivocation_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_cosmos_evidence_v1beta1_Equivocation_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_cosmos_evidence_v1beta1_Equivocation_descriptor,
        new java.lang.String[] { "Height", "Time", "Power", "ConsensusAddress", });
    com.google.protobuf.ExtensionRegistry registry =
        com.google.protobuf.ExtensionRegistry.newInstance();
    registry.add(GoGoProtos.equal);
    registry.add(GoGoProtos.equalAll);
    registry.add(GoGoProtos.goprotoGetters);
    registry.add(GoGoProtos.goprotoStringer);
    registry.add(GoGoProtos.moretags);
    registry.add(GoGoProtos.nullable);
    registry.add(GoGoProtos.stdtime);
    com.google.protobuf.Descriptors.FileDescriptor
        .internalUpdateFileDescriptor(descriptor, registry);
    GoGoProtos.getDescriptor();
    TimestampProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
