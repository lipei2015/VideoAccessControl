package com.ybkj.videoaccess.mvp.presenter;

import android.content.Intent;
import android.os.Bundle;
import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.app.ConstantSys;
import com.ybkj.videoaccess.app.MyApp;
import com.ybkj.videoaccess.mvp.control.SystemSplashControl;
import com.ybkj.videoaccess.util.PreferencesUtils;

/**
 * 启动页Presenter
 * <p>
 * Created by lp on 2016/8/23 0023.
 */
public class SystemSplashPresenter extends SystemSplashControl.ISystemSplashPresenter {
    private static final int VERSION_UPDATE_FLAG = 110;

    @Override
    public void start(Intent intent, Bundle bundle) {
        getVersionInfo(VERSION_UPDATE_FLAG, MyApp.getAppContext().getString(R.string.app_name)); // 获取当前版本号

        //用于测试，1秒钟以后进入主页面
        try {
            Thread.sleep(1000);
        } catch (Exception e) {

        }
        checkShowWelcome();
    }

    @Override
    public void endAnimation() {

    }

    @Override
    public void checkShowWelcome() {
        //进入欢迎页还是主页
        boolean showWelcome = PreferencesUtils.getInstance(ConstantSys.ConfigKey.PREFERENCE_SYSTEM_CONFIG).getBoolean(ConstantSys.PREFERENCE_SHOW_WELCOME, true);
        mView.onCheckShowFinish(showWelcome);
    }

    @Override
    public void getVersionInfo(int appType, String name) {
       /* addSubscription(mModel.checkVersion(appType, name).subscribe(new HttpSubscriber<>(new SubscriberResultListener() {
            @Override
            public void onSuccess(Object o) {
                // 数据返回成功检测
                VersionInfo versionInfo = (VersionInfo) o;
                if (versionInfo != null) {
                    PreferencesUtils.getInstance(ConstantSys.PREFERENCE_CONFIG).putString(MyApp.getAppContext(),
                            ConstantSys.PREFERENCE_VERSION_INFO, new Gson().toJson(versionInfo));
                }
            }

            @Override
            public void onError(HttpErrorException errorException) {

            }
        })));*/
    }
}
