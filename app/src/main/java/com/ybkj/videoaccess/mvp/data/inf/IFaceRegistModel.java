package com.ybkj.videoaccess.mvp.data.inf;

import com.ybkj.videoaccess.mvp.base.mvpBase.MvpBaseModel;
import com.ybkj.videoaccess.mvp.data.bean.RegistCheckInfo;
import com.ybkj.videoaccess.mvp.data.bean.RequestDownloadUserFaceBean;

import rx.Observable;

public interface IFaceRegistModel extends MvpBaseModel {
    Observable<RegistCheckInfo> downloadUserFace(RequestDownloadUserFaceBean body);
}
