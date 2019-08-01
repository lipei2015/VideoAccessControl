package com.ybkj.videoaccess.mvp.data.inf;

import com.ybkj.videoaccess.mvp.base.mvpBase.MvpBaseModel;
import com.ybkj.videoaccess.mvp.data.bean.VersionInfo;
import rx.Observable;

/**
 * 彩种数据接口
 * <p>
 * Created by HH on 2017/3/29.
 */

public interface IConfigMode extends MvpBaseModel {
    /**
     * 检测版本更新
     *
     * @param appType    App类型（0：安卓，1：ios）
     * @param name       Android传“欢乐彩票”
     * @return
     */
    Observable<VersionInfo> checkVersion(int appType, String name);
}
