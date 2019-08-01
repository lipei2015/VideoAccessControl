package com.ybkj.videoaccess.util;

import android.widget.Toast;
import com.ybkj.videoaccess.app.MyApp;

/**
 * Toast工具类
 * <p> 防止重复显示Toast </p>
 * <p/>
 * Created by HH on 2018/1/18
 */
public class ToastUtil {

    private static String oldMsg;
    private static Toast toast;
    private static long oldTime = 0;

    /**
     * 显示字符串
     *
     * @param msg
     */
    public static void showMsg(String msg) {
        if (toast == null) {
            toast = Toast.makeText(MyApp.getAppContext(), msg, Toast.LENGTH_SHORT);
            //toast.setGravity(Gravity.CENTER, 0, 0); // toast显示屏幕中间
            toast.show();
            oldTime = System.currentTimeMillis();
        } else {
            long currentTime = System.currentTimeMillis();
            if (msg.equals(oldMsg)) {
                if (currentTime - oldTime > Toast.LENGTH_SHORT) {
                    toast.show();
                }
            } else {
                oldMsg = msg;
                toast.setText(msg);
                toast.show();
            }
            oldTime = currentTime;
        }
    }

    /**
     * 使用显示字符串资源
     *
     * @param id
     */
    public static void showMsg(int id) {
        showMsg(MyApp.getAppContext().getString(id));
    }

}
