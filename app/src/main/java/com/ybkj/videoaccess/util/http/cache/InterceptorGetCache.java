package com.ybkj.videoaccess.util.http.cache;

import com.ybkj.videoaccess.app.MyApp;
import com.ybkj.videoaccess.util.LogUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.ybkj.videoaccess.util.CommonUtil.isNetwork;

/**
 * get缓存方式拦截器
 *
 * @author Created by CH L on 2017/4/7.
 */

public class InterceptorGetCache implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //if (!CommonUtil.isNetwork(mContext) || isUseCache) {
        if (!isNetwork(MyApp.getAppContext())) { // 没网时强制使用缓存
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            LogUtil.i("OkHttp", "没网时强制使用缓存");
        } else if (isNetwork(MyApp.getAppContext())) {
            //网络可用
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_NETWORK)
                    .build();
        }
        Response response = chain.proceed(request);
        if (isNetwork(MyApp.getAppContext())) {
            int maxAge = 60;
            // 有网络时 设置缓存超时时间60秒
            return response.newBuilder()
                    .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public,max-age=" + maxAge)
                    .build();
        } else {
            LogUtil.i("OkHttp", "网络不可用响应拦截");
            // 无网络时，设置超时为6小时
            int maxStale = 60 * 60 * 6;
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control",
                            "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
    }
}
