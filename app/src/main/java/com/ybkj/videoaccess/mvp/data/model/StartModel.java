package com.ybkj.videoaccess.mvp.data.model;

import com.ybkj.videoaccess.mvp.data.bean.DataInfo;
import com.ybkj.videoaccess.mvp.data.bean.RequestFullDataLoadBean;
import com.ybkj.videoaccess.mvp.data.inf.IStartModel;
import com.ybkj.videoaccess.mvp.data.service.QXAPIService;
import com.ybkj.videoaccess.util.http.HttpUtil;
import com.ybkj.videoaccess.util.http.RxUtil;

import rx.Observable;

public class StartModel implements IStartModel {
    @Override
    public Observable<DataInfo> fullDataLoad(RequestFullDataLoadBean body) {
        return HttpUtil.getInstance().getRetrofit().create(QXAPIService.class)
                .fullDataLoad(body)
                .map(new HttpUtil.HttpResultFuncCommon<>())
                .compose(RxUtil.rxSchedulerHelper());
    }
}
