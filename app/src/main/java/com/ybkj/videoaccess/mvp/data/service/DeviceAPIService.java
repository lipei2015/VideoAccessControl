package com.ybkj.videoaccess.mvp.data.service;

import com.ybkj.videoaccess.util.http.Result;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

import static com.ybkj.videoaccess.app.DeviceApi.D_RECOGNITION;

public interface DeviceAPIService {
    @FormUrlEncoded
    @POST(D_RECOGNITION)
    Observable<Result<String>> recognition(@Field("image") String image);
}
