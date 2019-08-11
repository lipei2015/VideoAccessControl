package com.ybkj.videoaccess.util.http;

public class CommonResult<T> {
    private boolean success;
    private T data;
    private long timestamp;
//    private String token;
    private String memo;    // 扩展字段
    private String token;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /*public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }*/

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
