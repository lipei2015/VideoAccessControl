package com.ybkj.videoaccess.util.http.cache;

import com.ybkj.videoaccess.app.MyApp;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

import static com.ybkj.videoaccess.util.CommonUtil.isNetwork;

/**
 * Gson持久化截取保存数据
 *
 * @author Created by CH L on 2017/4/7.
 */

public class InterceptorGsonCache implements Interceptor {

    private final CacheManager mCacheManager;
    private boolean mIsCache = false;// 是否使用缓存 默认关闭

    public InterceptorGsonCache() {
        mCacheManager = CacheManager.getInstance();
    }

    public void setCache(boolean cache) {
        mIsCache = cache;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (!mIsCache) return chain.proceed(chain.request());
        Response response;
        Request request = chain.request();
        RequestBody requestBody = request.body();
        Charset charset = Charset.forName("UTF-8");
        String url = request.url().toString();
        StringBuilder sb = new StringBuilder();
        sb.append(url);

        if (request.method().equals("POST")) {
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(Charset.forName("UTF-8"));
            }
            Buffer buffer = new Buffer();
            try {
                requestBody.writeTo(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            sb.append(buffer.readString(charset));
            buffer.close();
        }

        String key = sb.toString();
        // 不使用缓存 或者 网络可用 的情况下
        if (isNetwork(MyApp.getAppContext())) {
            int maxAge = 60; //有网络缓存60秒
            //如果网络正常，执行请求。
            Response originalResponse = chain.proceed(request);
            //获取MediaType，用于重新构建ResponseBody
            MediaType contentType = originalResponse.body().contentType();
            BufferedSource source = originalResponse.body().source();
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.buffer();
            if (contentType != null) {
                charset = contentType.charset(Charset.forName("UTF-8"));
            }
            String json = buffer.clone().readString(charset);
            response = originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    //重新构建body，原因在于body只能调用一次，之后就关闭了。
                    .body(ResponseBody.create(contentType, json))
                    .build();

            mCacheManager.putCache(key, json);
        } else {
            //没有网络的时候，由于Okhttp没有缓存post请求，所以不要调用chain.proceed(request)，会导致连接不上服务器而抛出异常（504）
            String cache = mCacheManager.getCache(key);//读取缓存
            int maxStale = 60 * 60 * 6; // 没有网络缓存6个小时
            //构建一个新的response响应结果
            response = new Response.Builder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .body(ResponseBody.create(MediaType.parse("application/json"), cache))
                    .request(request)
                    .protocol(Protocol.HTTP_1_1)
                    .code(200)
                    .build();
        }
        return response;
    }
}
