package com.ybkj.videoaccess.mvp.control;

import com.ybkj.videoaccess.mvp.base.mvpBase.MvpBasePresenter;
import com.ybkj.videoaccess.mvp.base.mvpBase.MvpBaseView;
import com.ybkj.videoaccess.mvp.data.bean.RegistCheckInfo;
import com.ybkj.videoaccess.mvp.data.bean.RequestDownloadUserFaceBean;
import com.ybkj.videoaccess.mvp.data.bean.RequestUserAuthReportBean;
import com.ybkj.videoaccess.mvp.data.bean.StringMessageInfo;
import com.ybkj.videoaccess.mvp.data.inf.IFaceRegistModel;

public interface FaceRegistControl {
    interface IFaceRegistView extends MvpBaseView {
        void showCheckRegistResult(RegistCheckInfo registCheckInfo);
        void showUserAuthReportResult(StringMessageInfo stringMessageInfo);
    }

    abstract class IFaceRegistPresenter extends MvpBasePresenter<IFaceRegistModel,IFaceRegistView> {
        public abstract void downloadUserFace(RequestDownloadUserFaceBean body);
        public abstract void userAuthReport(RequestUserAuthReportBean body);
    }
}
