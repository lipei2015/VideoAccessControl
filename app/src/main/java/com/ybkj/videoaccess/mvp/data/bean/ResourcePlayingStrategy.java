package com.ybkj.videoaccess.mvp.data.bean;

/**
 * 媒体文件播放策略
 */
public class ResourcePlayingStrategy {
    private String begin_time;  // 启动播放时间HH24miss
    private String end_time;    // 结束播放时间段HH24miss
    private String loop;        // 是否循环播放true循环false不循环
    private String update_freq;     // 媒体文件更新频次1.30分钟 2.小时 3.天 4.周 5.月 6.季度

    public String getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getLoop() {
        return loop;
    }

    public void setLoop(String loop) {
        this.loop = loop;
    }

    public String getUpdate_freq() {
        return update_freq;
    }

    public void setUpdate_freq(String update_freq) {
        this.update_freq = update_freq;
    }
}
