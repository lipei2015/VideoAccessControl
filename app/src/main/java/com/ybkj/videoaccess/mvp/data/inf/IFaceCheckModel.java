package com.ybkj.videoaccess.mvp.data.inf;

import com.ybkj.videoaccess.mvp.base.mvpBase.MvpBaseModel;
import com.ybkj.videoaccess.mvp.data.bean.RequestGateOpenRecordBean;
import com.ybkj.videoaccess.mvp.data.bean.StringMessageInfo;

import rx.Observable;

public interface IFaceCheckModel extends MvpBaseModel {
    Observable<String> recognition(String image);
    Observable<StringMessageInfo> gateOpenRecord(RequestGateOpenRecordBean requestGateOpenRecordBean);
}
