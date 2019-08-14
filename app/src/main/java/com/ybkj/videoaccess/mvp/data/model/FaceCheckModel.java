package com.ybkj.videoaccess.mvp.data.model;

import com.ybkj.videoaccess.mvp.data.bean.RequestGateOpenRecordBean;
import com.ybkj.videoaccess.mvp.data.bean.StringMessageInfo;
import com.ybkj.videoaccess.mvp.data.inf.IFaceCheckModel;
import com.ybkj.videoaccess.mvp.data.service.DeviceAPIService;
import com.ybkj.videoaccess.mvp.data.service.QXAPIService;
import com.ybkj.videoaccess.util.http.HttpUtil;
import com.ybkj.videoaccess.util.http.RxUtil;

import rx.Observable;

public class FaceCheckModel implements IFaceCheckModel {
    @Override
    public Observable<String> recognition(String image) {
        return HttpUtil.getInstance().getRetrofit().create(DeviceAPIService.class)
                .recognition(image)
                .map(new HttpUtil.HttpResultFunc<>())
                .compose(RxUtil.rxSchedulerHelper());
    }

    @Override
    public Observable<StringMessageInfo> gateOpenRecord(RequestGateOpenRecordBean requestGateOpenRecordBean) {
        return HttpUtil.getInstance().getRetrofit().create(QXAPIService.class)
                .gateOpenRecord(requestGateOpenRecordBean)
                .map(new HttpUtil.HttpResultFuncCommon<>())
                .compose(RxUtil.rxSchedulerHelper());
    }
}
