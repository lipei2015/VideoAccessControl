package com.ybkj.videoaccess.mvp.control;

import com.ybkj.videoaccess.mvp.base.mvpBase.MvpBasePresenter;
import com.ybkj.videoaccess.mvp.base.mvpBase.MvpBaseView;
import com.ybkj.videoaccess.mvp.data.bean.DataInfo;
import com.ybkj.videoaccess.mvp.data.bean.FullDataInfo;
import com.ybkj.videoaccess.mvp.data.bean.RequestFullDataLoadBean;
import com.ybkj.videoaccess.mvp.data.bean.RequestResourcesBean;
import com.ybkj.videoaccess.mvp.data.inf.IStartModel;
import com.ybkj.videoaccess.util.http.HttpErrorException;

public interface StartControl {
    interface IStartView extends MvpBaseView{
        void showResources(FullDataInfo dataInfo);
        void showFullDataLoad(FullDataInfo dataInfo);
        void showFullDataFail(HttpErrorException errorException);
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
