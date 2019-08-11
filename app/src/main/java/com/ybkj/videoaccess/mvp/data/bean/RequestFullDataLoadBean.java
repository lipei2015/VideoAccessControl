package com.ybkj.videoaccess.mvp.data.bean;

/**
 * 新\重新装机数据下载与恢复，数据结构定义（门禁主机）
 */
public class RequestFullDataLoadBean {
    private String type;    // 命令类型例如“FULL_LOAD”
    private String mjseq;   // 门禁主机mac地址
    private String memo;    // 扩展字段
    private String timestamp;   // 时间戳yyyyMMddHH24miss

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMjseq() {
        return mjseq;
    }

    public void setMjseq(String mjseq) {
        this.mjseq = mjseq;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
