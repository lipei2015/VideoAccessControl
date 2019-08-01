package com.ybkj.videoaccess.mvp.data.service;
import com.ybkj.videoaccess.mvp.data.bean.VersionInfo;
import com.ybkj.videoaccess.util.http.Result;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

import static com.ybkj.videoaccess.app.ConstantApi.CHECK_VERSION;
import static com.ybkj.videoaccess.app.ConstantApi.GET_CONFIG;
import static com.ybkj.videoaccess.app.ConstantApi.SYSTEM_FEEDBACK_SUBMIT;

/**
 * 系统接口
 * <p>
 * Created by HH on 2017/3/6.
 */

public interface SystemAPIService {

    /**
     * 上传图片 -- 单图片
     *
     * @param url
     * @param maps
     * @return
     */
    @Multipart
    @POST
    Observable<Result<Object>> uploadImg(@Url String url, @PartMap Map<String, RequestBody> maps);
    //Observable<Result<Object>> uploadImg(@Url String url, @Part  List<MultipartBody.Part> partList);

    /**
     * 上传图片 -- 多图片
     *
     * @param url
     * @param maps
     * @return
     */
    @Multipart
    @POST
    Observable<Result<List<String>>> uploadImgMore(@Url String url, @PartMap Map<String, RequestBody> maps);

    /**
     * 提交反馈
     * @param content 内容
     * @param pictures 图片数组
     * @return String
     */
    @FormUrlEncoded
    @POST(SYSTEM_FEEDBACK_SUBMIT)
    Observable<Result<String>> feedbackSubmit(@Field("content") String content, @Field("pictures") String pictures);

    /**
     * 检测版本更新
     *
     * @param type    App类型（0：安卓，1：ios）
     * @param name       Android传“欢乐彩票”
     * @return
     */
    @FormUrlEncoded
    @POST(CHECK_VERSION)
    Observable<Result<VersionInfo>> checkVersion(@Field("type") int type, @Field("name") String name);

    /**
     * 获取配置信息
     *
     * @return
     */
    @FormUrlEncoded
    @POST(GET_CONFIG)
    Observable<Result<String>> getConfig(@Field("key") String key);

    /**
     * 下载文件接口
     * @param fileUrl
     * @return
     */
    @Streaming
    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl);
}
