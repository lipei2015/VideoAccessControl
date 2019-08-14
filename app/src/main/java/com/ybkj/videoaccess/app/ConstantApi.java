package com.ybkj.videoaccess.app;

/**
 * Api接口常量定义
 * <p>
 * Created by HH on 2018/1/19
 */
public class ConstantApi {
    public static final String IP = "http://132.232.146.67:8989";  //测试IP

//    public static final String IP_DEVICE = "https://192.168.10.15/wrtsz/dev_api/";  //测试IP https://192.168.10.15/wrtsz/dev_api/face/recognition?
    public static final String IP_DEVICE = "http://127.0.0.1/wrtsz/dev_api/";  //测试IP https://192.168.10.15/wrtsz/dev_api/face/recognition?
    //public static final String IP = "http://new.cp138666.info:8080/ ";  //正式IP
    //public static final String IP = "http://192.168.2.165:8080";  //陈盼IP(用于调试)
    //public static final String IP = "http://192.168.2.53:8080/";  //袁立波IP(用于调试)


    /**
     * 配置信息
     **/
    public static final String GET_CONFIG = "/api/config/get"; //基础信息接口
    public static final String CHECK_VERSION = "/api/user/getVersionByname"; //检测版本更新
    /******END****/

    /**
     * 远程开门
     */
    public static final String Remot_Open_Debug= "remoteOpenDebug";
    /**
     * 2.扫码需要采集数据上报字段、新装或重装由后台判断（运维APP）
     */
    public static final String devDeploy= "devDeploy";
    /**
     * 新\重新装机数据下载与恢复，数据结构定义（门禁主机）
     */
    public static final String fullDataLoad= "fullDataLoad";
    /**
     * 初始化资源请求接口
     */
    public static final String resources= "resources";
    /**
     * 包含了居住在楼宇中登记的人员信息，此处是针对刚刚初始化之后的全量数据拉取
     */
    public static final String propleInfo= "propleInfo";
    /**
     * IC卡映射信息记录了人员唯一标识和IC卡的映射对应关系，同时包含了卡片启用和停用的状态
     */
    public static final String ICards= "ICards";

    /**
     * 人相数据是一个数据包（ZIP文件），启用包含一个mapping.data文件，和若干人相样本文件，mapping文件包含了人员信息标识和人
     */
    public static final String peopleHeadCut= "peopleHeadCut";

    /**
     * (4)摄像头配置信息，一个加密JSON文件，结构暂无法定义
     */
    public static final String ipcSettings= "ipcSettings";
    /**
     * (5)媒体文件播放策略，用来控制门禁主机通用播放配置单独的json文件
     */
    public static final String playingStrategy= "playingStrategy";
    /**
     * (6)媒体文件，媒体文件作为文件包下载，包含单个文件播放顺序配置以及媒体文件，媒体文件播放支持图片和视频播放。下面表格介绍了配置文件media.cfg文件的字段
     */
    public static final String mediasDownload= "mediasDownload";
    /**
     *主机分屏策略，暂未定义
     */
    public static final String screenSplitStrategy= "screenSplitStrategy";
    /**
     * 4.居住人员信息下载数据结构定义
     */
    public static final String downloadUserFace= "downloadUserFace";

    /**
     * 5.人相授权信息上报数据结构定义，此接口是人脸授权完成之后将人脸数据连同人员标识上传到后台
     */
    public static final String userAuthReport= "userAuthReport";

    /**
     * IC卡信息上报数据结构定义
     */
    public static final String ICardReport= "ICardReport";

    /**
     * 大门常开检测上报数据结构定义，硬件设备检测大门常卡，并通过门禁APP上报门禁常开检测信息
     */
    public static final String gateOpenAlert= "gateOpenAlert";

    /**
     * 8.摄像头联动抓拍数据结构定义，暂无定义，需要参考onvif协议确定
     */
    public static final String IPCSettings= "IPCSettings";
    /**
     * 9.开门记录上传
     */
    public static final String gateOpenRecord= "gateOpenRecord";
    /**
     * 密码开门数据交互定义
     */
    public static final String pwdValidation= "pwdValidation";
    /**
     * 14.媒体文件下载接口，该接口需要根据媒体规则，每日凌晨去服务器端检查新的媒体文件并获取到文件清单并下载。
     */
    public static final String MediaDownload= "MediaDownload";
    /**
     * 播放规则下发定义
     */
    public static final String PlayRulesDownload= "PlayRulesDownload";
}
