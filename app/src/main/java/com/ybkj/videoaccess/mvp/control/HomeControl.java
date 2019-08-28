package com.ybkj.videoaccess.mvp.control;

import com.ybkj.videoaccess.mvp.base.mvpBase.MvpBasePresenter;
import com.ybkj.videoaccess.mvp.base.mvpBase.MvpBaseView;
import com.ybkj.videoaccess.mvp.data.bean.MediaInfo;
import com.ybkj.videoaccess.mvp.data.bean.RequestGateOpenRecordBean;
import com.ybkj.videoaccess.mvp.data.bean.RequestMediaDownloadBean;
import com.ybkj.videoaccess.mvp.data.bean.RequestPwdValidationbean;
import com.ybkj.videoaccess.mvp.data.inf.IHomeMode;

import java.util.List;

public interface HomeControl {
    abstract class IHomePresenter extends MvpBasePresenter<IHomeMode, IHomeView> {
        public abstract void pwdValidation(RequestPwdValidationbean requestPwdValidationbean);
        public abstract void gateOpenRecord(RequestGateOpenRecordBean requestGateOpenRecordBean);
        public abstract void mediaDownload(RequestMediaDownloadBean requestMediaDownloadBean);
    }

    interface IHomeView extends MvpBaseView {
        void showGateOpenRecordResult(String result);
        void showPwdValidation(String result);
        void showMediaDownload(List<MediaInfo> infoList);
    }
}
