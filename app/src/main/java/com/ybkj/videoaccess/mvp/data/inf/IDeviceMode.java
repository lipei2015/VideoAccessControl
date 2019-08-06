package com.ybkj.videoaccess.mvp.data.inf;

import com.ybkj.videoaccess.mvp.base.mvpBase.MvpBaseModel;
import com.ybkj.videoaccess.mvp.data.bean.RequestRemoteOpen;
import com.ybkj.videoaccess.util.http.CommonResult;

import rx.Observable;

public interface IDeviceMode extends MvpBaseModel {
    Observable<String> remoteOpenDebug(RequestRemoteOpen body);
}
