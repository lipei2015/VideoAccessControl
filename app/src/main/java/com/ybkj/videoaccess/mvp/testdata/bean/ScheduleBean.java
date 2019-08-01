package com.ybkj.videoaccess.mvp.testdata.bean;

/**
 * 赛程Bean
 *
 * Created by HH on 2018/1/20.
 */

public class ScheduleBean {
    private String home;
    private String away;
    private String time;
    private String group;

    public ScheduleBean(String home, String away, String time, String group){
        this.home = home;
        this.away = away;
        this.time = time;
        this.group = group;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getAway() {
        return away;
    }

    public void setAway(String away) {
        this.away = away;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
