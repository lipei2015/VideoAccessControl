package com.ybkj.videoaccess.mvp.data.bean;

/**
 * (2)IC卡映射信息
 */
public class ResourceICards {
    private String pid;     // 人员信息唯一编码
    private String ic_seq;  // IC卡片唯一标识
    private String status;  // 启停标识，true启用false停用

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getIc_seq() {
        return ic_seq;
    }

    public void setIc_seq(String ic_seq) {
        this.ic_seq = ic_seq;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
