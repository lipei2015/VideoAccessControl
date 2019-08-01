package com.ybkj.videoaccess.util.http;

import static com.ybkj.videoaccess.app.ConstantSys.HttpStatus.STATUS_SUCCESS;

/**
 * 服务器返回统一数据
 *
 * @param <T>
 */
public class Result<T> {

    public int status;
    public String msg;
    public T content;

    /**
     * 获取数据成功以及业务成功
     *
     * @return
     */
    public boolean isSuccess() {
        return status == STATUS_SUCCESS;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
