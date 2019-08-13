package com.ybkj.videoaccess.mvp.control;

import com.ybkj.videoaccess.mvp.base.mvpBase.MvpBasePresenter;
import com.ybkj.videoaccess.mvp.data.bean.RequestGateOpenRecordBean;
import com.ybkj.videoaccess.mvp.data.inf.IFaceCheckModel;

public interface FaceCheckControl {
    abstract class IFaceCheckPresenter extends MvpBasePresenter<IFaceCheckModel, IFaceCheckView> {
        public abstract void gateOpenRecord(RequestGateOpenRecordBean requestGateOpenRecordBean);
    }

    interface IFaceCheckView{
        void showGateOpenRecordResult(String result);
    }
}
