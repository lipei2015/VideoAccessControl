package com.ybkj.videoaccess.util;

import android.util.Log;
import com.ybkj.videoaccess.app.MyApp;

/**
 * Log 工具类
 * <p>
 * Created by HH on 2018/1/18
 */
public class LogUtil {

    public static void i(String info) {
        if (!MyApp.IS_RELEASE) {

            Log.i("$$--WorldCup--$$", info);
        }
    }

    public static void i(String tag, String info) {
        if (!MyApp.IS_RELEASE) {

            Log.i(tag, info);
        }
    }
}
