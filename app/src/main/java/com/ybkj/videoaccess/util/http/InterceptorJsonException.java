package com.ybkj.videoaccess.util.http;

import com.ybkj.videoaccess.util.LogUtil;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * json解析错误过滤类
 * <p>
 * Created by HH on 2017/3/1
 */
public class InterceptorJsonException implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

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
        String result = buffer.clone().readString(charset);

        //获取单个彩种有可能会出现彩种列表的错误解析
        /*if (request.url().toString().contains(LOTTERY_LIST)) {
            if (!result.contains("[") && !result.contains("]")) {
                return response;
            } else {
                LogUtil.i("$$----$$获取该接口出现错误：" + request.url().toString());
                return null;
            }
        }*/

        //服务器端返回不合理的数据结构
        if(result.contains("status") && result.contains("msg") && result.contains("content")){
            return response;
        }else{
            LogUtil.i("返回了不合理的数据结构："+result);
        }

        return null;
    }
}
