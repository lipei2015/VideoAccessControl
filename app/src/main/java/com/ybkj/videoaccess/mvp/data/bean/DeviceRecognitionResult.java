package com.ybkj.videoaccess.mvp.data.bean;

public class DeviceRecognitionResult {
    private String RetStr;  // ok 表示成功
    private DeviceFaceInfos FaceInfos;

    public String getRetStr() {
        return RetStr;
    }

    public void setRetStr(String retStr) {
        RetStr = retStr;
    }

    public DeviceFaceInfos getFaceInfos() {
        return FaceInfos;
    }

    public void setFaceInfos(DeviceFaceInfos faceInfos) {
        FaceInfos = faceInfos;
    }
}
