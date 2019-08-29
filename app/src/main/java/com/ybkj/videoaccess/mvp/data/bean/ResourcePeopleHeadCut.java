package com.ybkj.videoaccess.mvp.data.bean;

/**
 * 人相数据
 */
public class ResourcePeopleHeadCut {
    private String pid;
    private String img_filaname;    // 人员标识与人相映射关系
    private String status;      // 启停标识，true启用false停用

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getImg_filaname() {
        return img_filaname;
    }

    public void setImg_filaname(String img_filaname) {
        this.img_filaname = img_filaname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
