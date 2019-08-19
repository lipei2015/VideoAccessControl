package com.ybkj.videoaccess.mvp.data.bean;

public class InitInfoBean {
    private String type = "INIT初始化";    // 命令类型例如“INIT初始化”
    private String mjseq;   // 门禁主机唯一标志的device_id

    public InitInfoBean(String mjseq) {
        this.mjseq = mjseq;
    }

    public String getMjseq() {
        return mjseq;
    }

    public void setMjseq(String mjseq) {
        this.mjseq = mjseq;
    }
}
