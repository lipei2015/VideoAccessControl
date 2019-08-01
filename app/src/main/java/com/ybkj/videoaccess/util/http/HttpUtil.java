package com.ybkj.videoaccess.util.http;


import android.content.Context;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.app.ConstantApi;
import com.ybkj.videoaccess.app.MyApp;
import com.ybkj.videoaccess.util.FileUtil;
import com.ybkj.videoaccess.util.http.cache.InterceptorGsonCache;
import com.ybkj.videoaccess.util.http.myGsonFactory.MyGsonConverterFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by HH on 2017/7/7
 */
public class HttpUtil {

    public static HttpUtil getInstance() {
        return HttpUtil.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final HttpUtil INSTANCE = new HttpUtil();
    }

    private static final String CACHE_PATH = "httpCache";
    private static final int CACHE_SIZE = 1024 * 1024 * 50; // 缓存大小
    private static Retrofit retrofit = null;
    private static OkHttpClient okHttpClient = null;
    private Context mContext;
    private Subscriber<Object> s;
    private InterceptorGsonCache mInterceptorGsonCache;

    /**
     * 缓存时间
     *
     * @param maxCacheTime
     */
    /*public void setMaxCacheTime(int maxCacheTime) {
        this.maxCacheTime = maxCacheTime;
    }*/

    /**
     * 是否使用缓存
     */
    public HttpUtil setUseCache(boolean useCache) {
        if (mInterceptorGsonCache != null) mInterceptorGsonCache.setCache(useCache);
        return this;
    }

    public void init(Context context) {
        this.mContext = context;
        initOkHttp();
        initRetrofit();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    private void initOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        //添加Token自动登录拦截器
        builder.addInterceptor(new InterceptorToken());

        //添加cookie持久化登录拦截器
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(),
                        new SharedPrefsCookiePersistor(MyApp.getAppContext()));
        builder.cookieJar(cookieJar);

        //添加打印请求日志拦截器
        if (!MyApp.IS_RELEASE) {
            builder.addInterceptor(new InterceptorLogging());
        }

        //添加post缓存拦截器
        File cacheFile = new File(FileUtil.getCacheDir(mContext), CACHE_PATH);
        Cache cache = new Cache(cacheFile, CACHE_SIZE);

        mInterceptorGsonCache = new InterceptorGsonCache();
        builder.addNetworkInterceptor(mInterceptorGsonCache)//添加网络拦截器，用来拦截网络数据
                .addInterceptor(mInterceptorGsonCache)//添加本地缓存拦截器，用来拦截本地缓存
                .cache(cache);

        //设置超时
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);

        //错误重连
        builder.retryOnConnectionFailure(true);
        okHttpClient = builder.build();
    }

    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(ConstantApi.IP)
                .client(okHttpClient)
                .addConverterFactory(MyGsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    public static class HttpResultFunc<T> implements Func1<Result<T>, T> {

        @Override
        public T call(Result<T> result) {
            if (!result.isSuccess()) {
                if (null != result.getMsg()) {
                    throw new HttpErrorException(result.status, result.getMsg());
                } else {
                    throw new HttpErrorException(result.status, MyApp.getAppContext().getString(R.string.server_exception));
                }
            } else {
                return result.content;
            }
        }

    }

    /**
     * 取消请求
     */
    public void unSubscribed() {
        if (s != null && !s.isUnsubscribed()) {
            s.unsubscribe();
        }
    }
}
