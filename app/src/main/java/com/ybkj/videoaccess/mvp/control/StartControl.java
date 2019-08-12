package com.ybkj.videoaccess.mvp.control;

import com.ybkj.videoaccess.mvp.base.mvpBase.MvpBasePresenter;
import com.ybkj.videoaccess.mvp.base.mvpBase.MvpBaseView;
import com.ybkj.videoaccess.mvp.data.bean.DataInfo;
import com.ybkj.videoaccess.mvp.data.bean.RequestFullDataLoadBean;
import com.ybkj.videoaccess.mvp.data.inf.IStartModel;

public interface StartControl {
    interface IStartView extends MvpBaseView{
        void showFullDataLoad(DataInfo dataInfo);
    }

    abstract class IStartPresenter extends MvpBasePresenter<IStartModel,IStartView>{
        public abstract void fullDataLoad(RequestFullDataLoadBean requestFullDataLoadBean);
    }
}
