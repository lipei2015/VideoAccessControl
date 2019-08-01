package com.ybkj.videoaccess.mvp.data.bean;

import java.util.List;

/**
 * 用户信息实体类
 *
 * @author Created by CH L on 2017/3/20.
 */

public class Member {

    /**
     * accountItemList : []
     * accountNo : touko2019
     * createTime : 1510643883439
     * headImage :
     * id : 286704
     * lastLoginTime : 1510643883439
     * loginIp : 59.42.239.190
     * money : [0,10,0,0]
     * nickname : tou****019
     * platform : 2
     * registerIp : 59.42.239.190
     * reservedMobile : 18302076613
     * score : [0,0]
     * state : true
     * token : 741153037
     * withdrawable : true
     */

    private String accountNo;
    private long createTime;
    private String headImage;
    private int id;
    private long lastLoginTime;
    private String loginIp;
    private String nickname;
    private int platform;
    private String registerIp;
    private String reservedMobile;
    private boolean state;
    private String token;
    private boolean withdrawable;
    private List<?> accountItemList;
    private List<Double> money;
    private List<Double> score;

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public String getRegisterIp() {
        return registerIp;
    }

    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }

    public String getReservedMobile() {
        return reservedMobile;
    }

    public void setReservedMobile(String reservedMobile) {
        this.reservedMobile = reservedMobile;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isWithdrawable() {
        return withdrawable;
    }

    public void setWithdrawable(boolean withdrawable) {
        this.withdrawable = withdrawable;
    }

    public List<?> getAccountItemList() {
        return accountItemList;
    }

    public void setAccountItemList(List<?> accountItemList) {
        this.accountItemList = accountItemList;
    }

    public List<Double> getMoney() {
        return money;
    }

    public void setMoney(List<Double> money) {
        this.money = money;
    }

    public List<Double> getScore() {
        return score;
    }

    public void setScore(List<Double> score) {
        this.score = score;
    }
}
