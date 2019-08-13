package com.ybkj.videoaccess.mvp.data.bean;

/**
 * 9.开门记录上传
 */
public class RequestGateOpenRecordBean {
    private String pid;         // 人员唯一标识
    private String type;        // 开门类型：1.人脸、2.刷卡、3.手机、4.密码、5.访客密码、6.蓝牙
    private String timestamp;   // 开门时间yyyyMMddHH24mis
    private String MAC;         // 门禁主机标识

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

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }
}
