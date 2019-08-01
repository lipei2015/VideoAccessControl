package com.ybkj.videoaccess.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.umeng.analytics.MobclickAgent;
import com.ybkj.videoaccess.util.http.HttpUtil;

import static com.ybkj.videoaccess.app.ConstantSys.APPKEY_UMENG;

/**
 * 全局配置类
 * <p>
 * Created by HH on 2018/1/19
 */
public class MyApp extends Application {
    public static boolean IS_RELEASE = false;
    private static Context context;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        /*if (!IS_RELEASE) {
            *//** 调试测试版,内存检测以及错误捕捉【发布版需去除】 **//*
            LeakCanary.install(this);
        } else {
        }*/

        //获取Context
        context = getApplicationContext();

        //初始化网络请求工具
        HttpUtil.getInstance().init(this);

        //友盟应用统计
        initUMENG();
    }

    /**
     * 获取全局Context
     */
    public static Context getAppContext() {
        return context;
    }

    /**
     * 友盟应用统计
     */
    private void initUMENG() {
        MobclickAgent.startWithConfigure(new MobclickAgent.UMAnalyticsConfig(getAppContext(), APPKEY_UMENG, null));
    }

}
