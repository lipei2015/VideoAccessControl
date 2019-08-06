package com.ybkj.videoaccess.mvp.control;
import com.ybkj.videoaccess.mvp.base.mvpBase.MvpBasePresenter;
import com.ybkj.videoaccess.mvp.base.mvpBase.MvpBaseView;
import com.ybkj.videoaccess.mvp.data.bean.RequestRemoteOpen;
import com.ybkj.videoaccess.mvp.data.inf.IConfigMode;

/**
 * Created by HH on 2016/8/23
 */
public interface SystemSplashControl {
    interface ISystemSplashView extends MvpBaseView {
        /**
         * 检测是否需要显示欢迎页的回调方法
         *
         * @param showWelcome
         */
        void onCheckShowFinish(boolean showWelcome);

        /**
         * 进入主页
         */
        void gotoMain();
    }

    abstract class ISystemSplashPresenter extends MvpBasePresenter<IConfigMode, ISystemSplashView> {
        /**
         * 动画结束
         */
        public abstract void endAnimation();

        /**
         * 检测是否需要显示欢迎页
         */
        public abstract void checkShowWelcome();

        /**
         * 获取版本信息
         */
        public abstract void getVersionInfo(int appType, String name);

        /**
         * 远程开门
         */
        public abstract void remoteOpenDebug(RequestRemoteOpen body);
    }
}
