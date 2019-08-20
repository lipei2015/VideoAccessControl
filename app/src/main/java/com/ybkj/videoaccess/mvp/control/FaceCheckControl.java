package com.ybkj.videoaccess.mvp.control;

import com.ybkj.videoaccess.mvp.base.mvpBase.MvpBasePresenter;
import com.ybkj.videoaccess.mvp.base.mvpBase.MvpBaseView;
import com.ybkj.videoaccess.mvp.data.bean.RequestGateOpenRecordBean;
import com.ybkj.videoaccess.mvp.data.inf.IFaceCheckModel;

public interface FaceCheckControl {
    interface IFaceCheckView extends MvpBaseView {
        void showGateOpenRecordResult(String result);
    }

    abstract class IFaceCheckPresenter extends MvpBasePresenter<IFaceCheckModel, IFaceCheckView> {
        public abstract void recognition(String image);
        public abstract void gateOpenRecord(RequestGateOpenRecordBean requestGateOpenRecordBean);
    }
}
