package com.ybkj.videoaccess.mvp.presenter;

import android.content.Intent;
import android.os.Bundle;

import com.ybkj.videoaccess.mvp.control.FaceRegistControl;
import com.ybkj.videoaccess.mvp.data.bean.RegistCheckInfo;
import com.ybkj.videoaccess.mvp.data.bean.RequestDownloadUserFaceBean;
import com.ybkj.videoaccess.mvp.data.bean.RequestUserAuthReportBean;
import com.ybkj.videoaccess.mvp.data.bean.StringMessageInfo;
import com.ybkj.videoaccess.util.LogUtil;
import com.ybkj.videoaccess.util.http.HttpErrorException;
import com.ybkj.videoaccess.util.http.HttpSubscriber;
import com.ybkj.videoaccess.util.http.SubscriberResultListener;

public class FaceRegistPresenter extends FaceRegistControl.IFaceRegistPresenter {
    @Override
    public void downloadUserFace(RequestDownloadUserFaceBean body) {
        addSubscription(mModel.downloadUserFace(body).subscribe(new HttpSubscriber<>(new SubscriberResultListener() {
            @Override
            public void onSuccess(Object o) {
                // 数据返回成功检测
                RegistCheckInfo result = (RegistCheckInfo) o;
                mView.showCheckRegistResult(result);
            }

            @Override
            public void onError(HttpErrorException errorException) {
                LogUtil.i(errorException.getMessage()+"");
            }
        })));
    }

    @Override
    public void userAuthReport(RequestUserAuthReportBean body) {
        addSubscription(mModel.userAuthReport(body).subscribe(new HttpSubscriber<>(new SubscriberResultListener() {
            @Override
            public void onSuccess(Object o) {
                // 数据返回成功检测
                StringMessageInfo result = (StringMessageInfo) o;
                mView.showUserAuthReportResult(result);
            }

            @Override
            public void onError(HttpErrorException errorException) {
                LogUtil.i(errorException.getMessage()+"");
            }
        })));
    }

    @Override
    public void start(Intent intent, Bundle bundle) {

    }
}
