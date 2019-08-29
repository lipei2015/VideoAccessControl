package com.ybkj.videoaccess.mvp.data.bean;

/**
 * 居住人员登记信息
 */
public class ResourcePropleInfo {
    private String pid;     // 人员唯一标识
    private String name;    // 仅包含姓氏,后台拼装好，比如XX先生/女士
    private String status;  // True可用、false不可用
    private String roomno;  // 房间编码

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRoomno() {
        return roomno;
    }

    public void setRoomno(String roomno) {
        this.roomno = roomno;
    }
}
