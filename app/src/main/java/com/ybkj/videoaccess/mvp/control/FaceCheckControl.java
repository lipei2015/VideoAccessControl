package com.ybkj.videoaccess.mvp.control;

import com.ybkj.videoaccess.mvp.base.mvpBase.MvpBasePresenter;
import com.ybkj.videoaccess.mvp.base.mvpBase.MvpBaseView;
import com.ybkj.videoaccess.mvp.data.bean.RequestGateOpenRecordBean;
import com.ybkj.videoaccess.mvp.data.inf.IFaceCheckModel;

public interface FaceCheckControl {
    interface IFaceCheckView extends MvpBaseView {
        /**
         * 上传开门记录结果
         * @param isSuccess
         */
        void showGateOpenRecordResult(boolean isSuccess);

        /**
         * 上传开门记录失败
         * @param requestGateOpenRecordBean
         */
        void gateOpenRecordFail(RequestGateOpenRecordBean requestGateOpenRecordBean);
    }

    abstract class IFaceCheckPresenter extends MvpBasePresenter<IFaceCheckModel, IFaceCheckView> {
        public abstract void gateOpenRecord(RequestGateOpenRecordBean requestGateOpenRecordBean);
    }
}
