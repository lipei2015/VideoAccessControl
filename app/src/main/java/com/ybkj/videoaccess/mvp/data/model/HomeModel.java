package com.ybkj.videoaccess.mvp.data.model;

import com.ybkj.videoaccess.mvp.data.bean.FullDataInfo;
import com.ybkj.videoaccess.mvp.data.bean.RequestGateOpenRecordBean;
import com.ybkj.videoaccess.mvp.data.bean.RequestPwdValidationbean;
import com.ybkj.videoaccess.mvp.data.bean.StringMessageInfo;
import com.ybkj.videoaccess.mvp.data.inf.IHomeMode;
import com.ybkj.videoaccess.mvp.data.service.QXAPIService;
import com.ybkj.videoaccess.util.http.HttpUtil;
import com.ybkj.videoaccess.util.http.RxUtil;

import rx.Observable;

public class HomeModel implements IHomeMode {

    @Override
    public Observable<StringMessageInfo> pwdValidation(RequestPwdValidationbean requestPwdValidationbean) {
        return HttpUtil.getInstance().getRetrofit().create(QXAPIService.class)
                .pwdValidation(requestPwdValidationbean)
                .map(new HttpUtil.HttpResultFuncCommon<>())
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
