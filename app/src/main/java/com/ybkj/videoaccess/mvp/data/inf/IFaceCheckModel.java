package com.ybkj.videoaccess.mvp.data.inf;

import com.ybkj.videoaccess.mvp.base.mvpBase.MvpBaseModel;
import com.ybkj.videoaccess.mvp.data.bean.RequestGateOpenRecordBean;

import rx.Observable;

public interface IFaceCheckModel extends MvpBaseModel {
    Observable<String> recognition(String image);
    Observable<String> gateOpenRecord(RequestGateOpenRecordBean requestGateOpenRecordBean);
}
