// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ibc/applications/interchain_accounts/v1/account.proto

package ibc.applications.interchain_accounts.v1;

import com.google.protobuf.GoGoProtos;

public final class Account {
  private Account() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface InterchainAccountOrBuilder extends
      // @@protoc_insertion_point(interface_extends:ibc.applications.interchain_accounts.v1.InterchainAccount)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>.cosmos.auth.v1beta1.BaseAccount base_account = 1 [(.gogoproto.embed) = true, (.gogoproto.moretags) = "yaml:&#92;"base_account&#92;""];</code>
     * @return Whether the baseAccount field is set.
     */
    boolean hasBaseAccount();
    /**
     * <code>.cosmos.auth.v1beta1.BaseAccount base_account = 1 [(.gogoproto.embed) = true, (.gogoproto.moretags) = "yaml:&#92;"base_account&#92;""];</code>
     * @return The baseAccount.
     */
    cosmos.auth.v1beta1.Auth.BaseAccount getBaseAccount();
    /**
     * <code>.cosmos.auth.v1beta1.BaseAccount base_account = 1 [(.gogoproto.embed) = true, (.gogoproto.moretags) = "yaml:&#92;"base_account&#92;""];</code>
     */
    cosmos.auth.v1beta1.Auth.BaseAccountOrBuilder getBaseAccountOrBuilder();

    /**
     * <code>string account_owner = 2 [(.gogoproto.moretags) = "yaml:&#92;"account_owner&#92;""];</code>
     * @return The accountOwner.
     */
    java.lang.String getAccountOwner();
    /**
     * <code>string account_owner = 2 [(.gogoproto.moretags) = "yaml:&#92;"account_owner&#92;""];</code>
     * @return The bytes for accountOwner.
     */
    com.google.protobuf.ByteString
        getAccountOwnerBytes();
  }
  /**
   * <pre>
   * An InterchainAccount is defined as a BaseAccount &amp; the address of the account owner on the controller chain
   * </pre>
   *
   * Protobuf type {@code ibc.applications.interchain_accounts.v1.InterchainAccount}
   */
  public static final class InterchainAccount extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:ibc.applications.interchain_accounts.v1.InterchainAccount)
      InterchainAccountOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use InterchainAccount.newBuilder() to construct.
    private InterchainAccount(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private InterchainAccount() {
      accountOwner_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new InterchainAccount();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private InterchainAccount(
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
            case 10: {
              cosmos.auth.v1beta1.Auth.BaseAccount.Builder subBuilder = null;
              if (baseAccount_ != null) {
                subBuilder = baseAccount_.toBuilder();
              }
              baseAccount_ = input.readMessage(cosmos.auth.v1beta1.Auth.BaseAccount.parser(), extensionRegistry);
              if (subBuilder != null) {
                subBuilder.mergeFrom(baseAccount_);
                baseAccount_ = subBuilder.buildPartial();
              }

              break;
            }
            case 18: {
              java.lang.String s = input.readStringRequireUtf8();

              accountOwner_ = s;
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
      return ibc.applications.interchain_accounts.v1.Account.internal_static_ibc_applications_interchain_accounts_v1_InterchainAccount_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return ibc.applications.interchain_accounts.v1.Account.internal_static_ibc_applications_interchain_accounts_v1_InterchainAccount_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              ibc.applications.interchain_accounts.v1.Account.InterchainAccount.class, ibc.applications.interchain_accounts.v1.Account.InterchainAccount.Builder.class);
    }

    public static final int BASE_ACCOUNT_FIELD_NUMBER = 1;
    private cosmos.auth.v1beta1.Auth.BaseAccount baseAccount_;
    /**
     * <code>.cosmos.auth.v1beta1.BaseAccount base_account = 1 [(.gogoproto.embed) = true, (.gogoproto.moretags) = "yaml:&#92;"base_account&#92;""];</code>
     * @return Whether the baseAccount field is set.
     */
    @java.lang.Override
    public boolean hasBaseAccount() {
      return baseAccount_ != null;
    }
    /**
     * <code>.cosmos.auth.v1beta1.BaseAccount base_account = 1 [(.gogoproto.embed) = true, (.gogoproto.moretags) = "yaml:&#92;"base_account&#92;""];</code>
     * @return The baseAccount.
     */
    @java.lang.Override
    public cosmos.auth.v1beta1.Auth.BaseAccount getBaseAccount() {
      return baseAccount_ == null ? cosmos.auth.v1beta1.Auth.BaseAccount.getDefaultInstance() : baseAccount_;
    }
    /**
     * <code>.cosmos.auth.v1beta1.BaseAccount base_account = 1 [(.gogoproto.embed) = true, (.gogoproto.moretags) = "yaml:&#92;"base_account&#92;""];</code>
     */
    @java.lang.Override
    public cosmos.auth.v1beta1.Auth.BaseAccountOrBuilder getBaseAccountOrBuilder() {
      return getBaseAccount();
    }

    public static final int ACCOUNT_OWNER_FIELD_NUMBER = 2;
    private volatile java.lang.Object accountOwner_;
    /**
     * <code>string account_owner = 2 [(.gogoproto.moretags) = "yaml:&#92;"account_owner&#92;""];</code>
     * @return The accountOwner.
     */
    @java.lang.Override
    public java.lang.String getAccountOwner() {
      java.lang.Object ref = accountOwner_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        accountOwner_ = s;
        return s;
      }
    }
    /**
     * <code>string account_owner = 2 [(.gogoproto.moretags) = "yaml:&#92;"account_owner&#92;""];</code>
     * @return The bytes for accountOwner.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getAccountOwnerBytes() {
      java.lang.Object ref = accountOwner_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        accountOwner_ = b;
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
      if (baseAccount_ != null) {
        output.writeMessage(1, getBaseAccount());
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(accountOwner_)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, accountOwner_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (baseAccount_ != null) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(1, getBaseAccount());
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(accountOwner_)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, accountOwner_);
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
      if (!(obj instanceof ibc.applications.interchain_accounts.v1.Account.InterchainAccount)) {
        return super.equals(obj);
      }
      ibc.applications.interchain_accounts.v1.Account.InterchainAccount other = (ibc.applications.interchain_accounts.v1.Account.InterchainAccount) obj;

      if (hasBaseAccount() != other.hasBaseAccount()) return false;
      if (hasBaseAccount()) {
        if (!getBaseAccount()
            .equals(other.getBaseAccount())) return false;
      }
      if (!getAccountOwner()
          .equals(other.getAccountOwner())) return false;
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
      if (hasBaseAccount()) {
        hash = (37 * hash) + BASE_ACCOUNT_FIELD_NUMBER;
        hash = (53 * hash) + getBaseAccount().hashCode();
      }
      hash = (37 * hash) + ACCOUNT_OWNER_FIELD_NUMBER;
      hash = (53 * hash) + getAccountOwner().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static ibc.applications.interchain_accounts.v1.Account.InterchainAccount parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static ibc.applications.interchain_accounts.v1.Account.InterchainAccount parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static ibc.applications.interchain_accounts.v1.Account.InterchainAccount parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static ibc.applications.interchain_accounts.v1.Account.InterchainAccount parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static ibc.applications.interchain_accounts.v1.Account.InterchainAccount parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static ibc.applications.interchain_accounts.v1.Account.InterchainAccount parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static ibc.applications.interchain_accounts.v1.Account.InterchainAccount parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static ibc.applications.interchain_accounts.v1.Account.InterchainAccount parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static ibc.applications.interchain_accounts.v1.Account.InterchainAccount parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static ibc.applications.interchain_accounts.v1.Account.InterchainAccount parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static ibc.applications.interchain_accounts.v1.Account.InterchainAccount parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static ibc.applications.interchain_accounts.v1.Account.InterchainAccount parseFrom(
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
    public static Builder newBuilder(ibc.applications.interchain_accounts.v1.Account.InterchainAccount prototype) {
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
     * An InterchainAccount is defined as a BaseAccount &amp; the address of the account owner on the controller chain
     * </pre>
     *
     * Protobuf type {@code ibc.applications.interchain_accounts.v1.InterchainAccount}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:ibc.applications.interchain_accounts.v1.InterchainAccount)
        ibc.applications.interchain_accounts.v1.Account.InterchainAccountOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return ibc.applications.interchain_accounts.v1.Account.internal_static_ibc_applications_interchain_accounts_v1_InterchainAccount_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return ibc.applications.interchain_accounts.v1.Account.internal_static_ibc_applications_interchain_accounts_v1_InterchainAccount_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                ibc.applications.interchain_accounts.v1.Account.InterchainAccount.class, ibc.applications.interchain_accounts.v1.Account.InterchainAccount.Builder.class);
      }

      // Construct using ibc.applications.interchain_accounts.v1.Account.InterchainAccount.newBuilder()
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
        if (baseAccountBuilder_ == null) {
          baseAccount_ = null;
        } else {
          baseAccount_ = null;
          baseAccountBuilder_ = null;
        }
        accountOwner_ = "";

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return ibc.applications.interchain_accounts.v1.Account.internal_static_ibc_applications_interchain_accounts_v1_InterchainAccount_descriptor;
      }

      @java.lang.Override
      public ibc.applications.interchain_accounts.v1.Account.InterchainAccount getDefaultInstanceForType() {
        return ibc.applications.interchain_accounts.v1.Account.InterchainAccount.getDefaultInstance();
      }

      @java.lang.Override
      public ibc.applications.interchain_accounts.v1.Account.InterchainAccount build() {
        ibc.applications.interchain_accounts.v1.Account.InterchainAccount result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public ibc.applications.interchain_accounts.v1.Account.InterchainAccount buildPartial() {
        ibc.applications.interchain_accounts.v1.Account.InterchainAccount result = new ibc.applications.interchain_accounts.v1.Account.InterchainAccount(this);
        if (baseAccountBuilder_ == null) {
          result.baseAccount_ = baseAccount_;
        } else {
          result.baseAccount_ = baseAccountBuilder_.build();
        }
        result.accountOwner_ = accountOwner_;
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
        if (other instanceof ibc.applications.interchain_accounts.v1.Account.InterchainAccount) {
          return mergeFrom((ibc.applications.interchain_accounts.v1.Account.InterchainAccount)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(ibc.applications.interchain_accounts.v1.Account.InterchainAccount other) {
        if (other == ibc.applications.interchain_accounts.v1.Account.InterchainAccount.getDefaultInstance()) return this;
        if (other.hasBaseAccount()) {
          mergeBaseAccount(other.getBaseAccount());
        }
        if (!other.getAccountOwner().isEmpty()) {
          accountOwner_ = other.accountOwner_;
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
        ibc.applications.interchain_accounts.v1.Account.InterchainAccount parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (ibc.applications.interchain_accounts.v1.Account.InterchainAccount) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private cosmos.auth.v1beta1.Auth.BaseAccount baseAccount_;
      private com.google.protobuf.SingleFieldBuilderV3<
          cosmos.auth.v1beta1.Auth.BaseAccount, cosmos.auth.v1beta1.Auth.BaseAccount.Builder, cosmos.auth.v1beta1.Auth.BaseAccountOrBuilder> baseAccountBuilder_;
      /**
       * <code>.cosmos.auth.v1beta1.BaseAccount base_account = 1 [(.gogoproto.embed) = true, (.gogoproto.moretags) = "yaml:&#92;"base_account&#92;""];</code>
       * @return Whether the baseAccount field is set.
       */
      public boolean hasBaseAccount() {
        return baseAccountBuilder_ != null || baseAccount_ != null;
      }
      /**
       * <code>.cosmos.auth.v1beta1.BaseAccount base_account = 1 [(.gogoproto.embed) = true, (.gogoproto.moretags) = "yaml:&#92;"base_account&#92;""];</code>
       * @return The baseAccount.
       */
      public cosmos.auth.v1beta1.Auth.BaseAccount getBaseAccount() {
        if (baseAccountBuilder_ == null) {
          return baseAccount_ == null ? cosmos.auth.v1beta1.Auth.BaseAccount.getDefaultInstance() : baseAccount_;
        } else {
          return baseAccountBuilder_.getMessage();
        }
      }
      /**
       * <code>.cosmos.auth.v1beta1.BaseAccount base_account = 1 [(.gogoproto.embed) = true, (.gogoproto.moretags) = "yaml:&#92;"base_account&#92;""];</code>
       */
      public Builder setBaseAccount(cosmos.auth.v1beta1.Auth.BaseAccount value) {
        if (baseAccountBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          baseAccount_ = value;
          onChanged();
        } else {
          baseAccountBuilder_.setMessage(value);
        }

        return this;
      }
      /**
       * <code>.cosmos.auth.v1beta1.BaseAccount base_account = 1 [(.gogoproto.embed) = true, (.gogoproto.moretags) = "yaml:&#92;"base_account&#92;""];</code>
       */
      public Builder setBaseAccount(
          cosmos.auth.v1beta1.Auth.BaseAccount.Builder builderForValue) {
        if (baseAccountBuilder_ == null) {
          baseAccount_ = builderForValue.build();
          onChanged();
        } else {
          baseAccountBuilder_.setMessage(builderForValue.build());
        }

        return this;
      }
      /**
       * <code>.cosmos.auth.v1beta1.BaseAccount base_account = 1 [(.gogoproto.embed) = true, (.gogoproto.moretags) = "yaml:&#92;"base_account&#92;""];</code>
       */
      public Builder mergeBaseAccount(cosmos.auth.v1beta1.Auth.BaseAccount value) {
        if (baseAccountBuilder_ == null) {
          if (baseAccount_ != null) {
            baseAccount_ =
              cosmos.auth.v1beta1.Auth.BaseAccount.newBuilder(baseAccount_).mergeFrom(value).buildPartial();
          } else {
            baseAccount_ = value;
          }
          onChanged();
        } else {
          baseAccountBuilder_.mergeFrom(value);
        }

        return this;
      }
      /**
       * <code>.cosmos.auth.v1beta1.BaseAccount base_account = 1 [(.gogoproto.embed) = true, (.gogoproto.moretags) = "yaml:&#92;"base_account&#92;""];</code>
       */
      public Builder clearBaseAccount() {
        if (baseAccountBuilder_ == null) {
          baseAccount_ = null;
          onChanged();
        } else {
          baseAccount_ = null;
          baseAccountBuilder_ = null;
        }

        return this;
      }
      /**
       * <code>.cosmos.auth.v1beta1.BaseAccount base_account = 1 [(.gogoproto.embed) = true, (.gogoproto.moretags) = "yaml:&#92;"base_account&#92;""];</code>
       */
      public cosmos.auth.v1beta1.Auth.BaseAccount.Builder getBaseAccountBuilder() {
        
        onChanged();
        return getBaseAccountFieldBuilder().getBuilder();
      }
      /**
       * <code>.cosmos.auth.v1beta1.BaseAccount base_account = 1 [(.gogoproto.embed) = true, (.gogoproto.moretags) = "yaml:&#92;"base_account&#92;""];</code>
       */
      public cosmos.auth.v1beta1.Auth.BaseAccountOrBuilder getBaseAccountOrBuilder() {
        if (baseAccountBuilder_ != null) {
          return baseAccountBuilder_.getMessageOrBuilder();
        } else {
          return baseAccount_ == null ?
              cosmos.auth.v1beta1.Auth.BaseAccount.getDefaultInstance() : baseAccount_;
        }
      }
      /**
       * <code>.cosmos.auth.v1beta1.BaseAccount base_account = 1 [(.gogoproto.embed) = true, (.gogoproto.moretags) = "yaml:&#92;"base_account&#92;""];</code>
       */
      private com.google.protobuf.SingleFieldBuilderV3<
          cosmos.auth.v1beta1.Auth.BaseAccount, cosmos.auth.v1beta1.Auth.BaseAccount.Builder, cosmos.auth.v1beta1.Auth.BaseAccountOrBuilder> 
          getBaseAccountFieldBuilder() {
        if (baseAccountBuilder_ == null) {
          baseAccountBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
              cosmos.auth.v1beta1.Auth.BaseAccount, cosmos.auth.v1beta1.Auth.BaseAccount.Builder, cosmos.auth.v1beta1.Auth.BaseAccountOrBuilder>(
                  getBaseAccount(),
                  getParentForChildren(),
                  isClean());
          baseAccount_ = null;
        }
        return baseAccountBuilder_;
      }

      private java.lang.Object accountOwner_ = "";
      /**
       * <code>string account_owner = 2 [(.gogoproto.moretags) = "yaml:&#92;"account_owner&#92;""];</code>
       * @return The accountOwner.
       */
      public java.lang.String getAccountOwner() {
        java.lang.Object ref = accountOwner_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          accountOwner_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string account_owner = 2 [(.gogoproto.moretags) = "yaml:&#92;"account_owner&#92;""];</code>
       * @return The bytes for accountOwner.
       */
      public com.google.protobuf.ByteString
          getAccountOwnerBytes() {
        java.lang.Object ref = accountOwner_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          accountOwner_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string account_owner = 2 [(.gogoproto.moretags) = "yaml:&#92;"account_owner&#92;""];</code>
       * @param value The accountOwner to set.
       * @return This builder for chaining.
       */
      public Builder setAccountOwner(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        accountOwner_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string account_owner = 2 [(.gogoproto.moretags) = "yaml:&#92;"account_owner&#92;""];</code>
       * @return This builder for chaining.
       */
      public Builder clearAccountOwner() {
        
        accountOwner_ = getDefaultInstance().getAccountOwner();
        onChanged();
        return this;
      }
      /**
       * <code>string account_owner = 2 [(.gogoproto.moretags) = "yaml:&#92;"account_owner&#92;""];</code>
       * @param value The bytes for accountOwner to set.
       * @return This builder for chaining.
       */
      public Builder setAccountOwnerBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        accountOwner_ = value;
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


      // @@protoc_insertion_point(builder_scope:ibc.applications.interchain_accounts.v1.InterchainAccount)
    }

    // @@protoc_insertion_point(class_scope:ibc.applications.interchain_accounts.v1.InterchainAccount)
    private static final ibc.applications.interchain_accounts.v1.Account.InterchainAccount DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new ibc.applications.interchain_accounts.v1.Account.InterchainAccount();
    }

    public static ibc.applications.interchain_accounts.v1.Account.InterchainAccount getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<InterchainAccount>
        PARSER = new com.google.protobuf.AbstractParser<InterchainAccount>() {
      @java.lang.Override
      public InterchainAccount parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new InterchainAccount(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<InterchainAccount> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<InterchainAccount> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public ibc.applications.interchain_accounts.v1.Account.InterchainAccount getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ibc_applications_interchain_accounts_v1_InterchainAccount_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ibc_applications_interchain_accounts_v1_InterchainAccount_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n5ibc/applications/interchain_accounts/v" +
      "1/account.proto\022\'ibc.applications.interc" +
      "hain_accounts.v1\032\031cosmos_proto/cosmos.pr" +
      "oto\032\024gogoproto/gogo.proto\032\036cosmos/auth/v" +
      "1beta1/auth.proto\"\271\001\n\021InterchainAccount\022" +
      "S\n\014base_account\030\001 \001(\0132 .cosmos.auth.v1be" +
      "ta1.BaseAccountB\033\320\336\037\001\362\336\037\023yaml:\"base_acco" +
      "unt\"\022/\n\raccount_owner\030\002 \001(\tB\030\362\336\037\024yaml:\"a" +
      "ccount_owner\":\036\210\240\037\000\230\240\037\000\322\264-\022InterchainAcc" +
      "ountIBGZEgithub.com/cosmos/ibc-go/v3/mod" +
      "ules/apps/27-interchain-accounts/typesb\006" +
      "proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          cosmos_proto.Cosmos.getDescriptor(),
          GoGoProtos.getDescriptor(),
          cosmos.auth.v1beta1.Auth.getDescriptor(),
        });
    internal_static_ibc_applications_interchain_accounts_v1_InterchainAccount_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_ibc_applications_interchain_accounts_v1_InterchainAccount_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ibc_applications_interchain_accounts_v1_InterchainAccount_descriptor,
        new java.lang.String[] { "BaseAccount", "AccountOwner", });
    com.google.protobuf.ExtensionRegistry registry =
        com.google.protobuf.ExtensionRegistry.newInstance();
    registry.add(cosmos_proto.Cosmos.implementsInterface);
    registry.add(GoGoProtos.embed);
    registry.add(GoGoProtos.goprotoGetters);
    registry.add(GoGoProtos.goprotoStringer);
    registry.add(GoGoProtos.moretags);
    com.google.protobuf.Descriptors.FileDescriptor
        .internalUpdateFileDescriptor(descriptor, registry);
    cosmos_proto.Cosmos.getDescriptor();
    GoGoProtos.getDescriptor();
    cosmos.auth.v1beta1.Auth.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
