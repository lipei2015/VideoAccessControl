package com.ybkj.videoaccess.mvp.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ybkj.videoaccess.mvp.control.StartControl;
import com.ybkj.videoaccess.mvp.data.bean.DataInfo;
import com.ybkj.videoaccess.mvp.data.bean.FullDataInfo;
import com.ybkj.videoaccess.mvp.data.bean.RequestFullDataLoadBean;
import com.ybkj.videoaccess.mvp.data.bean.RequestResourcesBean;
import com.ybkj.videoaccess.util.LogUtil;
import com.ybkj.videoaccess.util.http.HttpErrorException;
import com.ybkj.videoaccess.util.http.HttpSubscriber;
import com.ybkj.videoaccess.util.http.SubscriberResultListener;

public class StartPresenter extends StartControl.IStartPresenter {
    @Override
    public void start(Intent intent, Bundle bundle) {

    }

    @Override
    public void fullDataLoad(RequestFullDataLoadBean requestFullDataLoadBean) {
        addSubscription(mModel.fullDataLoad(requestFullDataLoadBean).subscribe(new HttpSubscriber<>(new SubscriberResultListener() {
            @Override
            public void onSuccess(Object o) {
                // 数据返回成功检测
                FullDataInfo result = (FullDataInfo) o;
                LogUtil.i(result.getTOKEN()+"");

                mView.showFullDataLoad(result);
            }

            @Override
            public void onError(HttpErrorException errorException) {
                LogUtil.i(errorException.getMessage()+"");
                mView.showFullDataFail(errorException);
            }
        })));
    }

    @Override
    public void resources(RequestResourcesBean bean) {
        addSubscription(mModel.resources(bean).subscribe(new HttpSubscriber<>(new SubscriberResultListener() {
            @Override
            public void onSuccess(Object o) {
                // 数据返回成功检测
                FullDataInfo result = (FullDataInfo) o;
                LogUtil.i(result.getTOKEN()+"");

                mView.showResources(result);
            }

            @Override
            public void onError(HttpErrorException errorException) {
                LogUtil.i(errorException.getMessage()+"");
            }
        })));
    }
}
