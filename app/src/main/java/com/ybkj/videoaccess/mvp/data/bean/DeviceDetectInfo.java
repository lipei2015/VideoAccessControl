package com.ybkj.videoaccess.mvp.data.bean;

import java.util.List;

public class DeviceDetectInfo {
    private String RetStr;  // ok 表示成功
    private List<DeviceFaceInfo> FaceInfos;

    public String getRetStr() {
        return RetStr;
    }

    public void setRetStr(String retStr) {
        RetStr = retStr;
    }

    public List<DeviceFaceInfo> getFaceInfos() {
        return FaceInfos;
    }

    public void setFaceInfos(List<DeviceFaceInfo> faceInfos) {
        FaceInfos = faceInfos;
    }
}
