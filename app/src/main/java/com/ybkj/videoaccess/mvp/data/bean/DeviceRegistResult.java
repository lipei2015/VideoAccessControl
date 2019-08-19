package com.ybkj.videoaccess.mvp.data.bean;

public class DeviceRegistResult {
    private String RetStr;      // “ok” :表示注册成功：
                                //  “failed_url_error” :使用 url 方式时， 可能返回该错误， 表示对
                                //应的 url 非法、 下载后无法解码等。
                                //        “failed_image_error” :使用 base64 方式时,可能返回该错误， 表
                                //示对应的 base64 无法解析成图片或者解析成不支持的图片。
                                //        “failed_face/reg_error” ： 表示所输入的图片无法正常注册， 可
                                //能的原因： （1） 图片有超过 1 个以上的人脸； （2） 图片人脸质量
                                //太差， 无法正常注册。 （3） 提供的图片之前已注册过。
    private String PersonId;    // 人脸唯一标识。 如果注册失败， 则该项内容为空。

    public String getRetStr() {
        return RetStr;
    }

    public void setRetStr(String retStr) {
        RetStr = retStr;
    }

    public String getPersonId() {
        return PersonId;
    }

    public void setPersonId(String personId) {
        PersonId = personId;
    }
}
