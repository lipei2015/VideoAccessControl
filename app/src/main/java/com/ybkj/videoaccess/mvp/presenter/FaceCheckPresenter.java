package com.ybkj.videoaccess.mvp.presenter;

import android.content.Intent;
import android.os.Bundle;

import com.ybkj.videoaccess.mvp.control.FaceCheckControl;
import com.ybkj.videoaccess.mvp.data.bean.RequestGateOpenRecordBean;
import com.ybkj.videoaccess.util.LogUtil;
import com.ybkj.videoaccess.util.http.HttpErrorException;
import com.ybkj.videoaccess.util.http.HttpSubscriber;
import com.ybkj.videoaccess.util.http.SubscriberResultListener;

public class FaceCheckPresenter extends FaceCheckControl.IFaceCheckPresenter {

    @Override
    public void gateOpenRecord(RequestGateOpenRecordBean requestGateOpenRecordBean) {
        addSubscription(mModel.gateOpenRecord(requestGateOpenRecordBean).subscribe(new HttpSubscriber<>(new SubscriberResultListener() {
            @Override
            public void onSuccess(Object o) {
                // 数据返回成功检测
                String result = (String) o;
//                LogUtil.i(result.getData().getMessage()+"");
                /*VersionInfo versionInfo = (VersionInfo) o;
                if (versionInfo != null) {
                    PreferencesUtils.getInstance(ConstantSys.PREFERENCE_CONFIG).putString(MyApp.getAppContext(),
                            ConstantSys.PREFERENCE_VERSION_INFO, new Gson().toJson(versionInfo));
                }*/
                mView.showGateOpenRecordResult(result);
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
