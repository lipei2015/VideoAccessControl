package com.ybkj.videoaccess.eventbus;

public class EventUserInfoQRCode {
    private String codeResult;

    public EventUserInfoQRCode(String codeResult) {
        this.codeResult = codeResult;
    }

    public String getCodeResult() {
        return codeResult;
    }

    public void setCodeResult(String codeResult) {
        this.codeResult = codeResult;
    }
}
