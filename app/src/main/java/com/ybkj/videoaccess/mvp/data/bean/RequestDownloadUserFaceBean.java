package com.ybkj.videoaccess.mvp.data.bean;

public class RequestDownloadUserFaceBean {
    private String pid;     // 人员唯一标识
    private String mac;     // 门禁主机唯一标识
    private String optype;  // 操作人脸 1.人脸认证

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

    public String getOptype() {
        return optype;
    }

    public void setOptype(String optype) {
        this.optype = optype;
    }
}
