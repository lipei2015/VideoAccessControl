package com.ybkj.videoaccess.mvp.data.service;

import com.ybkj.videoaccess.mvp.data.bean.DataInfo;
import com.ybkj.videoaccess.mvp.data.bean.RequestDevDeployBean;
import com.ybkj.videoaccess.mvp.data.bean.RequestFullDataLoadBean;
import com.ybkj.videoaccess.mvp.data.bean.RequestGateOpenRecordBean;
import com.ybkj.videoaccess.mvp.data.bean.RequestRemoteOpen;
import com.ybkj.videoaccess.util.http.CommonResult;
import com.ybkj.videoaccess.util.http.Result;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

import static com.ybkj.videoaccess.app.ConstantApi.GET_CONFIG;
import static com.ybkj.videoaccess.app.ConstantApi.Remot_Open_Debug;
import static com.ybkj.videoaccess.app.ConstantApi.devDeploy;
import static com.ybkj.videoaccess.app.ConstantApi.fullDataLoad;
import static com.ybkj.videoaccess.app.ConstantApi.gateOpenRecord;

public interface DeviceAPIService {
    @POST(Remot_Open_Debug)
    Observable<CommonResult<DataInfo>> remoteOpenDebug(@Body RequestRemoteOpen body);

    /**
     * 扫码需要采集数据上报字段、新装或重装由后台判断（运维APP）
     * @param body
     * @return
     */
    @POST(devDeploy)
    Observable<CommonResult<DataInfo>> devDeploy(@Body RequestDevDeployBean body);

    /**
     * 新\重新装机数据下载与恢复，数据结构定义（门禁主机）
     * @param body
     * @return
     */
    @POST(fullDataLoad)
    Observable<CommonResult<DataInfo>> fullDataLoad(@Body RequestFullDataLoadBean body);


    /**
     * 开门记录上传
     * @param body
     * @return
     */
    @POST(gateOpenRecord)
    Observable<CommonResult<String>> gateOpenRecord(@Body RequestGateOpenRecordBean body);

    /**
     * 获取配置信息
     *
     * @return
     */
    @FormUrlEncoded
    @POST(GET_CONFIG)
    Observable<Result<String>> getConfig(@Field("key") String key);
}
