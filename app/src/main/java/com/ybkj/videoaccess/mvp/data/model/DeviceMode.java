package com.ybkj.videoaccess.mvp.data.model;


import com.ybkj.videoaccess.mvp.data.bean.DataInfo;
import com.ybkj.videoaccess.mvp.data.bean.RequestRemoteOpen;
import com.ybkj.videoaccess.mvp.data.inf.IDeviceMode;
import com.ybkj.videoaccess.mvp.data.service.DeviceAPIService;
import com.ybkj.videoaccess.util.http.CommonResult;
import com.ybkj.videoaccess.util.http.HttpUtil;
import com.ybkj.videoaccess.util.http.RxUtil;

import rx.Observable;

public class DeviceMode implements IDeviceMode {
    @Override
    public Observable<DataInfo> remoteOpenDebug(RequestRemoteOpen body) {
        return HttpUtil.getInstance().getRetrofit().create(DeviceAPIService.class)
                .remoteOpenDebug(body)
                .map(new HttpUtil.HttpResultFuncCommon<>())
                .compose(RxUtil.rxSchedulerHelper());
//        return null;
        /*HttpUtil.getInstance().getRetrofit().create(DeviceAPIService.class)
                .remoteOpenDebug(body).subscribe()*/

    }
}
