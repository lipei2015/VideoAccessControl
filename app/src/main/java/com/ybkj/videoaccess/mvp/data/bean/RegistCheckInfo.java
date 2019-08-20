package com.ybkj.videoaccess.mvp.data.bean;

public class RegistCheckInfo {
    private String pid;         // 人员唯一标识
    private String name;        // 仅包含姓氏,后台拼装好，比如XX先生/女士，如果没获取到则为空
    private boolean validation_result;   // 后台验证是否可以进行人脸注册登记True验证通过、false不通过
    private String roomno;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isValidation_result() {
        return validation_result;
    }

    public void setValidation_result(boolean validation_result) {
        this.validation_result = validation_result;
    }

    /*public String getValidation_result() {
        return validation_result;
    }

    public void setValidation_result(String validation_result) {
        this.validation_result = validation_result;
    }*/

    public String getRoomno() {
        return roomno;
    }

    public void setRoomno(String roomno) {
        this.roomno = roomno;
    }
}
