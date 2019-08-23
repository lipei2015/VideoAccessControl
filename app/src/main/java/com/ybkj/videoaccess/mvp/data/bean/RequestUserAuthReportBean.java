package com.ybkj.videoaccess.mvp.data.bean;

public class RequestUserAuthReportBean {
    private String pid;
    private String mac;
    private String sample;

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

    public String getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }
}
