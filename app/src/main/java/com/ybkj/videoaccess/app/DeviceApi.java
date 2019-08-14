package com.ybkj.videoaccess.app;

public class DeviceApi {
    private static DeviceApi deviceApi;
    private DeviceApi(){

    }
    public static DeviceApi getInstance(){
        if(deviceApi == null){
            deviceApi = new DeviceApi();
        }
        return deviceApi;
    }
//    public static final String IP = "http://127.0.0.1/";
    public String IP = "https://192.168.1.5/";


    public static final String D_RECOGNITION = "wrtsz/dev_api/face/recognition"; //基础信息接口

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }
}
