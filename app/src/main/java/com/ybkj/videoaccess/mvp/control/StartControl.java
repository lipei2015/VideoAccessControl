package com.ybkj.videoaccess.mvp.control;

import com.ybkj.videoaccess.mvp.base.mvpBase.MvpBasePresenter;
import com.ybkj.videoaccess.mvp.base.mvpBase.MvpBaseView;
import com.ybkj.videoaccess.mvp.data.bean.DataInfo;
import com.ybkj.videoaccess.mvp.data.bean.FullDataInfo;
import com.ybkj.videoaccess.mvp.data.bean.RequestFullDataLoadBean;
import com.ybkj.videoaccess.mvp.data.bean.RequestResourcesBean;
import com.ybkj.videoaccess.mvp.data.inf.IStartModel;

public interface StartControl {
    interface IStartView extends MvpBaseView{
        void showFullDataLoad(FullDataInfo dataInfo);
    }

    abstract class IStartPresenter extends MvpBasePresenter<IStartModel,IStartView>{
        public abstract void fullDataLoad(RequestFullDataLoadBean requestFullDataLoadBean);

        /**
         * 初始化资源请求接口
         * @param bean
         */
        public abstract void resources(RequestResourcesBean bean);
    }
}
