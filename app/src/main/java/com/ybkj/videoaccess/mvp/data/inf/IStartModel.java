package com.ybkj.videoaccess.mvp.data.inf;

import com.ybkj.videoaccess.mvp.base.mvpBase.MvpBaseModel;
import com.ybkj.videoaccess.mvp.data.bean.DataInfo;
import com.ybkj.videoaccess.mvp.data.bean.RequestFullDataLoadBean;

import rx.Observable;

public interface IStartModel extends MvpBaseModel {

    /**
     * 新\重新装机数据下载与恢复，数据结构定义（门禁主机）
     * @param body
     * @return
     */
    Observable<DataInfo> fullDataLoad(RequestFullDataLoadBean body);
}
