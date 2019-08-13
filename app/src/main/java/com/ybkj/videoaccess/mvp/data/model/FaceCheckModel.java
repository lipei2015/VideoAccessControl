package com.ybkj.videoaccess.mvp.data.model;

import com.ybkj.videoaccess.mvp.data.bean.RequestGateOpenRecordBean;
import com.ybkj.videoaccess.mvp.data.inf.IFaceCheckModel;
import com.ybkj.videoaccess.mvp.data.service.DeviceAPIService;
import com.ybkj.videoaccess.util.http.HttpUtil;
import com.ybkj.videoaccess.util.http.RxUtil;

import rx.Observable;

public class FaceCheckModel implements IFaceCheckModel {
    @Override
    public Observable<String> gateOpenRecord(RequestGateOpenRecordBean requestGateOpenRecordBean) {
        return HttpUtil.getInstance().getRetrofit().create(DeviceAPIService.class)
                .gateOpenRecord(requestGateOpenRecordBean)
                .map(new HttpUtil.HttpResultFuncCommon<>())
                .compose(RxUtil.rxSchedulerHelper());
    }
}
