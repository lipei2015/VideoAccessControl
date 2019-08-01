package com.ybkj.videoaccess.app;

import android.app.Activity;

import java.util.Stack;

/**
 * Activity管理类（使用单例模式）
 * <p> 类型与Android系统内部Activity管理 </p>
 * <p> 实现一次性关闭所有Activity </p>
 * <p> 关闭指定的Activity </p>
 * <p> 获取栈顶Activity </p>
 *
 * Created by HH on 2018/1/19
 */
public class ActivityManager {

    private static ActivityManager am = null;
    private static Stack<Activity> mStack = null;

    private ActivityManager() {

    }

    /**
     * 获取单例对象
     */
    public static ActivityManager getInstance() {
        if (null == am) {
            am = new ActivityManager();
            mStack = new Stack<>();
        }

        return am;
    }

    /**
     * 添加Activity
     *
     * @param activity
     */
    public void pushActivity(Activity activity) {
        if (activity != null) {
            mStack.add(activity);
        }
    }

    /**
     * 移除某个Activity
     *
     * @param activity
     */
    public void finishActivity(Activity activity) {
        if (null != activity) {
            mStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * finish所有的activity，保留栈底activity
     */
    public void finishAllActivity() {
        while (mStack.size() > 1) {
            Activity activity = mStack.pop();
            finishActivity(activity);
        }
    }

    /**
     * 弹出类名的activity（返回键或者滑动返回调用）
     */
    public void pupActivity(Activity activity) {
        if (mStack.size() > 0 && mStack.peek() == activity) {
            mStack.remove(activity);
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void killActivity(Class<?> cls) {
        Activity killActivity = null;
        for (Activity activity : mStack) {
            if (activity.getClass().equals(cls)) {
                killActivity = activity;
                break;
            }
        }

        if (killActivity != null) {
            finishActivity(killActivity);
        }
    }

    /**
     * 获取栈顶Activity
     *
     * @return
     */
    public Activity getTopActivity() {
        Activity topActivity = null;
        if (mStack.size() > 0) {
            topActivity = mStack.peek();
        }

        return topActivity;
    }

}
