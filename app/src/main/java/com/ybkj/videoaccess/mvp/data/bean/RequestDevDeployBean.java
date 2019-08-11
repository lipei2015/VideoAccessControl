package com.ybkj.videoaccess.mvp.data.bean;

/**
 * devDeploy
 * 扫码需要采集数据上报字段、新装或重装由后台判断（运维APP）
 */
public class RequestDevDeployBean {
    private String type;    // 命令类型例如“INIT初始化”
    private String stdaddr; // 标准地址编码
    private String mjseq;   // 门禁主机mac地址
    private String memo;    // 扩展字段

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStdaddr() {
        return stdaddr;
    }

    public void setStdaddr(String stdaddr) {
        this.stdaddr = stdaddr;
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
}
