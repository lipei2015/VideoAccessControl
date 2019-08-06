package com.ybkj.videoaccess.mvp.data.service;

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

public interface DeviceAPIService {
    @POST(Remot_Open_Debug)
    Observable<CommonResult<String>> remoteOpenDebug(@Body RequestRemoteOpen body);

    /**
     * 获取配置信息
     *
     * @return
     */
    @FormUrlEncoded
    @POST(GET_CONFIG)
    Observable<Result<String>> getConfig(@Field("key") String key);
}
