package com.ybkj.videoaccess.util.http;

import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 接口日志输出拦截类
 * <p>
 * Created by HH on 2017/3/1
 */
public class InterceptorLogging implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static final String REQUEST_TAG = "Http Request";
    private static final String RESPONSE_TAG = "Http Response";

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        //获取请求参数
        RequestBody requestBody = request.body();
        String paramsStr = null;
        if (requestBody != null) {
            Buffer buffer1 = new Buffer();
            requestBody.writeTo(buffer1);
            Charset charset1 = Charset.forName("UTF-8");
            MediaType contentType1 = requestBody.contentType();
            if (contentType1 != null) {
                charset1 = contentType1.charset(UTF8);
            }
            paramsStr = buffer1.readString(charset1);
        }
        String requestStartMessage = "\nmethod：" + request.method() + "\nurl：" + request.url() + "\nparam：" + paramsStr
                + "\nparam：" + request.headers().toString();
        Log.i(REQUEST_TAG, "$$----$$" + requestStartMessage);

        //获取服务器返回数据
        Response response = chain.proceed(request);
        ResponseBody responseBody = response.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.buffer();
        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(UTF8);
        }
        Log.i(RESPONSE_TAG, "$$----$$\n" + buffer.clone().readString(charset));

        return response;
    }
}
