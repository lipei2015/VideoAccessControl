package com.ybkj.videoaccess.util.http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ybkj.videoaccess.util.LogUtil;
import com.ybkj.videoaccess.util.ToastUtil;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

import static com.ybkj.videoaccess.app.ConstantSys.HttpStatus.STATUS_NEED_LOGIN;
import static com.ybkj.videoaccess.app.ConstantSys.HttpStatus.STATUS_UNKNOWN_ERROR;

/**
 * 全局自动刷新Token的拦截器
 * <p>
 * Created by HH on 2017/3/1
 */
public class InterceptorToken implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

        int code = getCode(response);
        if (code == -1) {
            return null;
        }

        if (code != STATUS_NEED_LOGIN) {
            return response;
        }

        return response;
        /*if (UserUtil.getUserInfoForKeyStr(PREFERENCE_USER_KEY_TOKEN) == null) {
            // 清空数据或者通过账号密码登录
            UserUtil.exitLogin(); // 清空数据
            Intent intent = new Intent(MyApp.getAppContext(), UserLoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MyApp.getAppContext().startActivity(intent);
            return response;
        } else {
            //使用Token静默登录
            try {
                *//*
                在非主线程中直接new Handler() 会报如下的错误:
                java.lang.RuntimeException: Can't create handler inside thread that has not called Looper.prepare()
                原因是非主线程中默认没有创建Looper对象，需要先调用Looper.prepare()启用Looper。
                Looper.loop(); 让Looper开始工作，从消息队列里取消息，处理消息。
                注意：写在Looper.loop()之后的代码不会被执行，这个函数内部应该是一个循环，当调用mHandler.getLooper().quit()后，loop才会中止，其后的代码才能得以运行。
                基于以上知识，可实现主线程给子线程（非主线程）发送消息。
                *//*
                Looper.prepare();
                Result<Member> result = HttpUtil.getInstance().getRetrofit().create(UserAPIService.class)
                        .userLoginByToken(String.valueOf(UserSynchUtil.getUserId()), UserSynchUtil.getUserInfoForKeyStr(PREFERENCE_USER_KEY_TOKEN))
                        .execute().body();
                Looper.loop();
                if (result.status == STATUS_SUCCESS) {
                    //重新请求
                    UserSynchUtil.loginSuccess(result.getContent());
                    return chain.proceed(request);
                } else {
                    // 清空数据或者通过账号密码登录
                    UserSynchUtil.exitLogin(); // 清空数据
                    Intent intent = new Intent(MyApp.getAppContext(), UserLoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    MyApp.getAppContext().startActivity(intent);
                    return response;
                }
            } catch (Exception e) {
                LogUtil.i("Token重登录异常：" + e.toString());
                // 清空数据或者通过账号密码登录
                UserSynchUtil.exitLogin(); // 清空数据
                Intent intent = new Intent(MyApp.getAppContext(), UserLoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MyApp.getAppContext().startActivity(intent);
                return response;
            }
        }*/
    }

    //private boolean isOne = true;

    /**
     * 获取返回码
     *
     * @param originalResponse
     * @return
     */
    private int getCode(Response originalResponse) {
        ResponseBody responseBody = originalResponse.body();
        BufferedSource source = responseBody.source();
        try {
            source.request(Long.MAX_VALUE);
        } catch (IOException e) {
            return STATUS_UNKNOWN_ERROR;
        }
        Buffer buffer = source.buffer();
        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(UTF8);
        }

        String result = new String(buffer.clone().readString(charset));
        if (result != null && result.contains("status") && result.contains("msg") && result.contains("content")) {
            Result<Object> result1Vo = new Gson().fromJson(result, new TypeToken<Result<Object>>() {
            }.getType());
           /*  //测试数据
           if (result1Vo.getContent().toString().contains("token") && isOne){
                isOne = false;
                return STATUS_NEED_LOGIN;
            }*/
            return result1Vo.status;
        } else if(result != null && result.contains("success") && result.contains("timestamp")){
            CommonResult<Object> commonResult = new Gson().fromJson(result, new TypeToken<CommonResult<Object>>() {
            }.getType());
            return 1;
        }else {
            LogUtil.i("返回了不合理的数据结构：" + result);
        }

        return -1;
    }
}