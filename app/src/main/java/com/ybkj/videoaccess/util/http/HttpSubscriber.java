package com.ybkj.videoaccess.util.http;

import com.google.gson.JsonSyntaxException;
import com.ybkj.videoaccess.util.LogUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;
import rx.Subscriber;

import static com.ybkj.videoaccess.app.ConstantSys.HttpStatus.STATUS_NETWORK_EXCEPTION;
import static com.ybkj.videoaccess.app.ConstantSys.HttpStatus.STATUS_SYSTEM_EXCEPTION;

/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 统一的错误捕捉
 * 获取服务器返回的有效数据
 * 调用者自己对请求数据进行处理
 * <p>
 * Created by HH on 17/3/3.
 */
public class HttpSubscriber<T> extends Subscriber<T> {

    private SubscriberResultListener mSubscriberResultListener;

    public HttpSubscriber(SubscriberResultListener mSubscriberResultListener) {
        this.mSubscriberResultListener = mSubscriberResultListener;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onCompleted() {

    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {

        LogUtil.i("Http Error : " + e.toString());

        if (e instanceof SocketTimeoutException || e instanceof TimeoutException || e instanceof ConnectException) {
            if (mSubscriberResultListener != null) {
                mSubscriberResultListener.onError(new HttpErrorException(STATUS_NETWORK_EXCEPTION, "服务器连接超时，请检查您的网络"));
            }
        } else if (e instanceof HttpErrorException) {
            if (mSubscriberResultListener != null) {
                mSubscriberResultListener.onError(new HttpErrorException(((HttpErrorException) e).getCode(), ((HttpErrorException) e).getMessage()));
            }
        } else if (e instanceof JsonSyntaxException) {
            // 假如导致这个异常的触发的原因是服务器的问题，那么应该让服务器知道
            // 可以再这里反馈异常信息给服务器
            if (mSubscriberResultListener != null) {
                mSubscriberResultListener.onError(new HttpErrorException(((HttpErrorException) e).getCode(), ((HttpErrorException) e).getMessage()));
            }
        } else {
            if (mSubscriberResultListener != null) {
                mSubscriberResultListener.onError(new HttpErrorException(STATUS_SYSTEM_EXCEPTION, "系统错误，请稍后重试"));
            }
        }

    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        if (mSubscriberResultListener != null) {
            mSubscriberResultListener.onSuccess(t);
        }
    }
}