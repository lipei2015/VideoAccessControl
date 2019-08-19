package com.ybkj.videoaccess.mvp.data.bean;

public class RequestResourcesBean {
    private String mjseq;   // 门禁主机mac地址
    private String token;    // 服务器下发的鉴权码
    private String res_type;    // 请求资源的类型 1.IC卡信息;2.人相数据;3.摄像头配置信息;4.媒体文件播放策略;5.媒体文件;6.主机分屏策略

    public String getMjseq() {
        return mjseq;
    }

    public void setMjseq(String mjseq) {
        this.mjseq = mjseq;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRes_type() {
        return res_type;
    }

    public void setRes_type(String res_type) {
        this.res_type = res_type;
    }
}
