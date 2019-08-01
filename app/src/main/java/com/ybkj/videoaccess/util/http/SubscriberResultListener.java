package com.ybkj.videoaccess.util.http;

/**
 * 网络请求接口回调
 * <p>
 * Created by HH on 2017/3/3.
 */

public interface SubscriberResultListener<T> {
    /**
     * 请求返回有效数据
     *
     * @param t
     */
    void onSuccess(T t);

    /**
     * 请求返回错误
     *
     * @param errorException
     */
    void onError(HttpErrorException errorException);
}
