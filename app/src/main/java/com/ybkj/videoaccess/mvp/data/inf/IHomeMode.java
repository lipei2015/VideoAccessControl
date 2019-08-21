package com.ybkj.videoaccess.mvp.data.inf;

import com.ybkj.videoaccess.mvp.base.mvpBase.MvpBaseModel;
import com.ybkj.videoaccess.mvp.data.bean.RequestGateOpenRecordBean;
import com.ybkj.videoaccess.mvp.data.bean.RequestPwdValidationbean;
import com.ybkj.videoaccess.mvp.data.bean.StringMessageInfo;

import rx.Observable;

public interface IHomeMode extends MvpBaseModel {
    Observable<StringMessageInfo> pwdValidation(RequestPwdValidationbean StringMessageInfo);

    Observable<StringMessageInfo> gateOpenRecord(RequestGateOpenRecordBean requestGateOpenRecordBean);
}
