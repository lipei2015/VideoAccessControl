package com.ybkj.videoaccess.mvp.data.service;

import com.ybkj.videoaccess.mvp.data.bean.DataInfo;
import com.ybkj.videoaccess.mvp.data.bean.MediaInfo;
import com.ybkj.videoaccess.mvp.data.bean.FullDataInfo;
import com.ybkj.videoaccess.mvp.data.bean.RegistCheckInfo;
import com.ybkj.videoaccess.mvp.data.bean.RequestDevDeployBean;
import com.ybkj.videoaccess.mvp.data.bean.RequestDownloadUserFaceBean;
import com.ybkj.videoaccess.mvp.data.bean.RequestFullDataLoadBean;
import com.ybkj.videoaccess.mvp.data.bean.RequestGateOpenRecordBean;
import com.ybkj.videoaccess.mvp.data.bean.RequestICardReportBean;
import com.ybkj.videoaccess.mvp.data.bean.RequestMediaDownloadBean;
import com.ybkj.videoaccess.mvp.data.bean.RequestPwdValidationbean;
import com.ybkj.videoaccess.mvp.data.bean.RequestRemoteOpen;
import com.ybkj.videoaccess.mvp.data.bean.RequestResourcesBean;
import com.ybkj.videoaccess.mvp.data.bean.RequestUserAuthReportBean;
import com.ybkj.videoaccess.mvp.data.bean.StringMessageInfo;
import com.ybkj.videoaccess.util.http.CommonResult;
import com.ybkj.videoaccess.util.http.Result;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

import static com.ybkj.videoaccess.app.ConstantApi.GET_CONFIG;
import static com.ybkj.videoaccess.app.ConstantApi.ICardReport;
import static com.ybkj.videoaccess.app.ConstantApi.MediaDownload;
import static com.ybkj.videoaccess.app.ConstantApi.Remot_Open_Debug;
import static com.ybkj.videoaccess.app.ConstantApi.devDeploy;
import static com.ybkj.videoaccess.app.ConstantApi.downloadUserFace;
import static com.ybkj.videoaccess.app.ConstantApi.fullDataLoad;
import static com.ybkj.videoaccess.app.ConstantApi.gateOpenRecord;
import static com.ybkj.videoaccess.app.ConstantApi.pwdValidation;
import static com.ybkj.videoaccess.app.ConstantApi.resources;
import static com.ybkj.videoaccess.app.ConstantApi.userAuthReport;

public interface QXAPIService {
    @POST(Remot_Open_Debug)
    Observable<CommonResult<DataInfo>> remoteOpenDebug(@Body RequestRemoteOpen body);

    /**
     * 扫码需要采集数据上报字段、新装或重装由后台判断（运维APP）
     * @param body
     * @return
     */
    @Headers("urlname:qxapi")
    @POST(devDeploy)
    Observable<CommonResult<DataInfo>> devDeploy(@Body RequestDevDeployBean body);

    /**
     * 新\重新装机数据下载与恢复，数据结构定义（门禁主机）
     * @param body
     * @return
     */
    @Headers("urlname:qxapi")
    @POST(fullDataLoad)
    Observable<CommonResult<FullDataInfo>> fullDataLoad(@Body RequestFullDataLoadBean body);

    /**
     * 初始化资源请求接口
     * @param body
     * @return
     */
    @Headers("urlname:qxapi")
    @POST(resources)
    Observable<CommonResult<FullDataInfo>> resources(@Body RequestResourcesBean body);

    /**
     * 此接口是针对于用户注册的时候的用户信息拉取，即此时拉取过来的用户是未完成人脸数据核验的居住人员。
     * 即经过房东审批但是没有验证人脸，通过居住人员通过app向门禁主机展示二维码实现人脸登记之前拉取人员信息。
     * @param body
     * @return
     */
    @POST(downloadUserFace)
    Observable<CommonResult<RegistCheckInfo>> downloadUserFace(@Body RequestDownloadUserFaceBean body);

    /**
     * 人相授权信息上报数据
     * @param body
     * @return
     */
    @POST(userAuthReport)
    Observable<CommonResult<StringMessageInfo>> userAuthReport(@Body RequestUserAuthReportBean body);

    /**
     * IC卡信息上报数据
     * @param body
     * @return
     */
    @POST(ICardReport)
    Observable<CommonResult<StringMessageInfo>> iCardReport(@Body RequestICardReportBean body);

    /**
     * 开门记录上传
     * @param body
     * @return
     */
    @Headers("urlname:qxapi")
    @POST(gateOpenRecord)
    Observable<CommonResult<StringMessageInfo>> gateOpenRecord(@Body RequestGateOpenRecordBean body);

    /**
     * 10.密码开门数据交互定义
     * @param body
     * @return
     */
    @Headers("urlname:qxapi")
    @POST(pwdValidation)
    Observable<CommonResult<StringMessageInfo>> pwdValidation(@Body RequestPwdValidationbean body);


    /**
     * 14.媒体文件下载接口，该接口需要根据媒体规则，每日凌晨去服务器端检查新的媒体文件并获取到文件清单并下载。
     * @param body
     * @return
     */
    @POST(MediaDownload)
    Observable<CommonResult<List<MediaInfo>>> mediaDownload(@Body RequestMediaDownloadBean body);

    /**
     * 获取配置信息
     *
     * @return
     */
    @Headers("urlname:qxapi")
    @FormUrlEncoded
    @POST(GET_CONFIG)
    Observable<Result<String>> getConfig(@Field("key") String key);
}
