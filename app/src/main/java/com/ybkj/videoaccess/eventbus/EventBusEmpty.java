package com.ybkj.videoaccess.eventbus;

import android.os.Bundle;

/**
 * 区分EventBus发送的消息,防止太多的EventBus消息类
 * <p>所有简单事件可以在这里声明为静态内部类</p>
 * <p>
 * Created by HH on 2018/1/19
 */
public class EventBusEmpty {

    /**
     * 首页显示Fragment消息
     */
    public static class NavFragmentEvent {
        public Bundle bundle;

        public NavFragmentEvent(Bundle bundle) {
            this.bundle = bundle;
        }
    }

    /**
     * 修改信息，发送页面信息同步消息
     */
    public static class UpdateStatusEvent {
        private int status;  //事件类型码，用于区分不同事件
        private Object value;   //信息

        public UpdateStatusEvent(int status) {
            this.status = status;
        }

        public UpdateStatusEvent(int status, Object value) {
            this.status = status;
            this.value = value;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }

    /**
     * 清除环信消息
     */
    public static class CleanChatData {

    }

}
