package com.ybkj.videoaccess.mvp.presenter;

import android.content.Intent;
import android.os.Bundle;

import com.ybkj.videoaccess.mvp.control.HomeControl;
import com.ybkj.videoaccess.mvp.data.bean.MediaInfo;
import com.ybkj.videoaccess.mvp.data.bean.RequestGateOpenRecordBean;
import com.ybkj.videoaccess.mvp.data.bean.RequestMediaDownloadBean;
import com.ybkj.videoaccess.mvp.data.bean.RequestPwdValidationbean;
import com.ybkj.videoaccess.mvp.data.bean.StringMessageInfo;
import com.ybkj.videoaccess.util.LogUtil;
import com.ybkj.videoaccess.util.http.HttpErrorException;
import com.ybkj.videoaccess.util.http.HttpSubscriber;
import com.ybkj.videoaccess.util.http.SubscriberResultListener;

import java.util.List;

public class HomePresenter extends HomeControl.IHomePresenter{

    @Override
    public void start(Intent intent, Bundle bundle) {

    }

    @Override
    public void pwdValidation(RequestPwdValidationbean requestPwdValidationbean) {
        addSubscription(mModel.pwdValidation(requestPwdValidationbean).subscribe(new HttpSubscriber<>(new SubscriberResultListener() {
            @Override
            public void onSuccess(Object o) {
                // 数据返回成功检测
                StringMessageInfo result = (StringMessageInfo) o;
                LogUtil.i(result.getMessage()+"");

                mView.showPwdValidation(true,result.getMessage());
            }

            @Override
            public void onError(HttpErrorException errorException) {
                LogUtil.i(errorException.getMessage()+"");
                mView.showPwdValidation(false,errorException.getMessage());
            }
        })));
    }

    @Override
    public void gateOpenRecord(RequestGateOpenRecordBean requestGateOpenRecordBean) {
        addSubscription(mModel.gateOpenRecord(requestGateOpenRecordBean).subscribe(new HttpSubscriber<>(new SubscriberResultListener() {
            @Override
            public void onSuccess(Object o) {
                // 数据返回成功检测
                StringMessageInfo result = (StringMessageInfo) o;
                LogUtil.i(result.getMessage()+"");

//                mView.showPwdValidation(result.getMessage());
            }

            @Override
            public void onError(HttpErrorException errorException) {
                LogUtil.i(errorException.getMessage()+"");
            }
        })));
    }

    @Override
    public void mediaDownload(RequestMediaDownloadBean requestMediaDownloadBean) {
        addSubscription(mModel.mediaDownload(requestMediaDownloadBean).subscribe(new HttpSubscriber<>(new SubscriberResultListener() {
            @Override
            public void onSuccess(Object o) {
                // 数据返回成功检测
                List<MediaInfo> result = (List<MediaInfo>) o;
                LogUtil.i(result.size()+"");

                mView.showMediaDownload(result);
            }

            @Override
            public void onError(HttpErrorException errorException) {
                LogUtil.i(errorException.getMessage()+"");
            }
        })));
    }
}
