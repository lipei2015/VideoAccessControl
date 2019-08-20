package com.ybkj.videoaccess.mvp.data.model;

import com.ybkj.videoaccess.mvp.data.bean.RegistCheckInfo;
import com.ybkj.videoaccess.mvp.data.bean.RequestDownloadUserFaceBean;
import com.ybkj.videoaccess.mvp.data.inf.IFaceRegistModel;
import com.ybkj.videoaccess.mvp.data.service.QXAPIService;
import com.ybkj.videoaccess.util.http.HttpUtil;
import com.ybkj.videoaccess.util.http.RxUtil;

import rx.Observable;

public class FaceRegistModel implements IFaceRegistModel {
    @Override
    public Observable<RegistCheckInfo> downloadUserFace(RequestDownloadUserFaceBean body) {
        return HttpUtil.getInstance().getRetrofit().create(QXAPIService.class)
                .downloadUserFace(body)
                .map(new HttpUtil.HttpResultFuncCommon<>())
                .compose(RxUtil.rxSchedulerHelper());
    }
}
