package com.ybkj.videoaccess.mvp.data.model;

import com.ybkj.videoaccess.mvp.data.bean.VersionInfo;
import com.ybkj.videoaccess.mvp.data.inf.IConfigMode;
import com.ybkj.videoaccess.mvp.data.service.SystemAPIService;
import com.ybkj.videoaccess.util.http.HttpUtil;
import com.ybkj.videoaccess.util.http.RxUtil;

import rx.Observable;

/**
 * 配置数据Mode
 * <p>
 * Created by HH on 2018/1/19.
 */

public class ConfigMode implements IConfigMode {

    @Override
    public Observable<VersionInfo> checkVersion(int appType, String name) {
//        return null;
        return HttpUtil.getInstance().getRetrofit().create(SystemAPIService.class)
                .checkVersion(appType, name)
                .map(new HttpUtil.HttpResultFunc<>())
                .compose(RxUtil.rxSchedulerHelper());
    }
}
