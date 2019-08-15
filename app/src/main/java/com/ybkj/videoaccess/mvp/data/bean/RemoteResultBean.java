package com.ybkj.videoaccess.mvp.data.bean;

public class RemoteResultBean {
    private String cdmtype;     // 命令类型：1.重启 2.重置 3.重新初始化 4.上报全量数据 5.上报主机日志
                                // 6.重置主机开机动画 7.禁用媒体文件 8删除媒体文件 9下载媒体文件 10.下载播放规则
    private String type;        // 类型：1.上报、2.下载
    private String timestamp;   // 命令时间yyyyMMddHH24miss
    private String mac;         // 主机标识
    private String ext;         // 下载接口

    public String getCdmtype() {
        return cdmtype;
    }

    public void setCdmtype(String cdmtype) {
        this.cdmtype = cdmtype;
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

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }
}
