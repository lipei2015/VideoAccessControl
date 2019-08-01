package com.ybkj.videoaccess.mvp.data.bean;

/**
 * Created by HH on 2016/8/4 0004.
 */
public class VersionInfo {
    public String url;
    public int appType;     // 110：Android；111：IOS
    public int app_id;
    public String createTime;
    public int id;
    public int status;
    public int upgradeType;        // 0：不升级；1：升级；2：强制升级
    public String update_time;
    public String upgradePoint;    // 升级提示
    public String versionCode;     // 版本号
    public int version_id;
    public int version_mini;
    public String name; // 名称
    public String hideVersion;  // 隐藏版本

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getAppType() {
        return appType;
    }

    public void setAppType(int appType) {
        this.appType = appType;
    }

    public int getApp_id() {
        return app_id;
    }

    public void setApp_id(int app_id) {
        this.app_id = app_id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUpgradeType() {
        return upgradeType;
    }

    public void setUpgradeType(int upgradeType) {
        this.upgradeType = upgradeType;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getUpgrade_point() {
        return upgradePoint;
    }

    public void setUpgrade_point(String upgradePoint) {
        this.upgradePoint = upgradePoint;
    }

    public String getVersion_code() {
        return versionCode;
    }

    public void setVersion_code(String versionCode) {
        this.versionCode = versionCode;
    }

    public int getVersion_id() {
        return version_id;
    }

    public void setVersion_id(int version_id) {
        this.version_id = version_id;
    }

    public int getVersion_mini() {
        return version_mini;
    }

    public void setVersion_mini(int version_mini) {
        this.version_mini = version_mini;
    }

    public String getHideVersion() {
        return hideVersion;
    }

    public void setHideVersion(String hideVersion) {
        this.hideVersion = hideVersion;
    }
}
