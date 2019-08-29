package com.ybkj.videoaccess.mvp.data.bean;

public class RequestICardReportBean {
    private String pid;
    private String icno;    // IC卡号
    private String mac;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getIcno() {
        return icno;
    }

    public void setIcno(String icno) {
        this.icno = icno;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
