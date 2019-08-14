package com.ybkj.videoaccess.mvp.data.bean;

/**
 * 10.密码开门数据交互定义
 */
public class RequestPwdValidationbean {
    private String pid;     // 人员唯一标识
    private String type;    // 开门类型：4.密码、5.访客密码
    private String timestamp;   // 开门时间
    private String mac;     // 门禁主机标识
    private String pwd;     // 访客密码

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
