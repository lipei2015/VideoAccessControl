package com.ybkj.videoaccess.util;

/**
 * 防止按钮被连续快速点击
 *
 * Created by HH on 2018/1/19
 */
public class NotClickDoubleUtil {

        private static long lastClickTime;

    /**
     * 连续点击事件在500毫秒以内过滤
     * @return
     */
    public static boolean isFastDoubleClick() {
            long time = System.currentTimeMillis();
            if ( time - lastClickTime < 500) {
                return true;
            }
            lastClickTime = time;
            return false;
        }
    }
