package com.ybkj.videoaccess.mvp.data.bean;

import java.util.List;

public class DeviceRecognitionResult {
    private String RetStr;  // ok 表示成功
    private List<DeviceFaceInfo> FaceInfos;
    private DeviceUserInfo UserInfo;
    private String PersonId;

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

    public DeviceUserInfo getUserInfo() {
        return UserInfo;
    }

    public void setUserInfo(DeviceUserInfo userInfo) {
        UserInfo = userInfo;
    }

    public String getPersonId() {
        return PersonId;
    }

    public void setPersonId(String personId) {
        PersonId = personId;
    }
}
