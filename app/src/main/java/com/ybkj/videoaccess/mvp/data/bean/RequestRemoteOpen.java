package com.ybkj.videoaccess.mvp.data.bean;

public class RequestRemoteOpen {
    private String pid;
    private String mac;
    private String type;
    private String timestamp;

    public RequestRemoteOpen(String pid, String mac, String type, String timestamp) {
        this.pid = pid;
        this.mac = mac;
        this.type = type;
        this.timestamp = timestamp;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
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
}
