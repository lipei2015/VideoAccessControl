package com.ybkj.videoaccess.mvp.data.model;

import com.ybkj.videoaccess.mvp.data.bean.DataInfo;
import com.ybkj.videoaccess.mvp.data.bean.FullDataInfo;
import com.ybkj.videoaccess.mvp.data.bean.RequestFullDataLoadBean;
import com.ybkj.videoaccess.mvp.data.bean.RequestResourcesBean;
import com.ybkj.videoaccess.mvp.data.inf.IStartModel;
import com.ybkj.videoaccess.mvp.data.service.QXAPIService;
import com.ybkj.videoaccess.util.http.HttpUtil;
import com.ybkj.videoaccess.util.http.RxUtil;

import rx.Observable;

public class StartModel implements IStartModel {
    @Override
    public Observable<FullDataInfo> fullDataLoad(RequestFullDataLoadBean body) {
        return HttpUtil.getInstance().getRetrofit().create(QXAPIService.class)
                .fullDataLoad(body)
                .map(new HttpUtil.HttpResultFuncCommon<>())
                .compose(RxUtil.rxSchedulerHelper());
    }

    @Override
    public Observable<FullDataInfo> resources(RequestResourcesBean body) {
        return HttpUtil.getInstance().getRetrofit().create(QXAPIService.class)
                .resources(body)
                .map(new HttpUtil.HttpResultFuncCommon<>())
                .compose(RxUtil.rxSchedulerHelper());
    }
}
