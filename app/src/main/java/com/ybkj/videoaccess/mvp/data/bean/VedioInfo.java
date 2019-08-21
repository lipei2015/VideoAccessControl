package com.ybkj.videoaccess.mvp.data.bean;

public class VedioInfo {
    private String path;
    private String content;

    public VedioInfo(String path, String content) {
        this.path = path;
        this.content = content;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
