package network.nerve.heterogeneous.model;

import com.google.gson.annotations.SerializedName;

/**
 * 通用 API 响应结构
 * 用于处理 freecash.info API 的返回数据
 */
public class ApipResponse {
    @SerializedName("code")
    private Integer code;
    
    @SerializedName("message")
    private String message;
    
    @SerializedName("data")
    private Object data;
    
    @SerializedName("bestHeight")
    private Long bestHeight;
    
    @SerializedName("bestBlockId")
    private String bestBlockId;
    
    @SerializedName("nonce")
    private Long nonce;
    
    @SerializedName("got")
    private Integer got;
    
    @SerializedName("total")
    private Integer total;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Long getBestHeight() {
        return bestHeight;
    }

    public void setBestHeight(Long bestHeight) {
        this.bestHeight = bestHeight;
    }

    public String getBestBlockId() {
        return bestBlockId;
    }

    public void setBestBlockId(String bestBlockId) {
        this.bestBlockId = bestBlockId;
    }

    public Long getNonce() {
        return nonce;
    }

    public void setNonce(Long nonce) {
        this.nonce = nonce;
    }

    public Integer getGot() {
        return got;
    }

    public void setGot(Integer got) {
        this.got = got;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
