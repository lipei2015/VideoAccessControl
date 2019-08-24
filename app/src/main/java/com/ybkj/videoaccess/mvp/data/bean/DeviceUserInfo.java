package com.ybkj.videoaccess.mvp.data.bean;

public class DeviceUserInfo {
    private String PersonName;
    private String Age;
    private boolean BlackList;
    private String Gender;      // 性别 M：男，F女

    public String getPersonName() {
        return PersonName;
    }

    public void setPersonName(String personName) {
        PersonName = personName;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public boolean isBlackList() {
        return BlackList;
    }

    public void setBlackList(boolean blackList) {
        BlackList = blackList;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }
}
