package com.ybkj.videoaccess.mvp.control;

import com.ybkj.videoaccess.mvp.base.mvpBase.MvpBasePresenter;
import com.ybkj.videoaccess.mvp.base.mvpBase.MvpBaseView;
import com.ybkj.videoaccess.mvp.data.bean.RequestGateOpenRecordBean;
import com.ybkj.videoaccess.mvp.data.inf.IHomeMode;

public interface HomeControl {
    abstract class IHomePresenter extends MvpBasePresenter<IHomeMode, IHomeView> {
//        public abstract void gateOpenRecord(RequestGateOpenRecordBean requestGateOpenRecordBean);
    }

    interface IHomeView extends MvpBaseView {
//        public void showGateOpenRecordResult(String result);
    }
}
