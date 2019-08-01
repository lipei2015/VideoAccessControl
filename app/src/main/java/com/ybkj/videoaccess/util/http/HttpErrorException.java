package com.ybkj.videoaccess.util.http;

/**
 * 网络所有错误异常
 * <p>
 *
 * Created by HH on 2017/3/3
 */

public class HttpErrorException extends RuntimeException {
    private int code;  //状态码
    private String message;  //错误信息

    public HttpErrorException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
