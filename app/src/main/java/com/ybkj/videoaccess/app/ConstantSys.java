package com.ybkj.videoaccess.app;

import android.os.Environment;

/**
 * 系统常量定义
 * <p>
 * Created by HH on 2018/1/19
 */
public class ConstantSys {

    /**
     * User相关
     **/
    public static final String PREFERENCE_USER_NAME = "VideoAccessControl";  //user文件名
    public static final String PREFERENCE_USER_KEY_ID = "id"; //Id
    public static final String PREFERENCE_USER_KEY_HEADIMG = "headImg";
    public static final String PREFERENCE_USER_KEY_HEADIMG_LOCAL = "headImgLocal"; //本地头像地址
    public static final String PREFERENCE_USER_KEY_MOBILE = "mobile";
    public static final String PREFERENCE_USER_KEY_NICK = "nickName"; //昵称
    public static final String PREFERENCE_USER_KEY_TOKEN = "token";  //Token
    public static final String PREFERENCE_USER_KEY_MONEY = "user_money";  //用户余额
    public static final String PREFERENCE_USER_BALANCE_YES = "user_balance_yes";  //可提现余额
    public static final String PREFERENCE_USER_BALANCE_NO = "user_balance_no";  //不可提现余额
    public static final String PREFERENCE_USER_CAN_WITHDRAWALS = "can_withdrawals";
    public static final String PREFERENCE_USER_KEY_SCORE = "score"; // 积分

    public static final String PREFERENCE_DOWNLOADED_DATA = "downloadedData"; // 是否已下载过数据
    public static final String PREFERENCE_DEVICE_ID = "device_id"; // 设备唯一ID
    /******END*****/

    /**
     * 系统配置相关
     **/
    public static final String PREFERENCE_SHOW_WELCOME = "showWelcome";  //是否显示欢迎页
    public static final String PREFERENCE_VERSION_INFO = "versionInfo";  // 版本信息
    /******END*****/

    /**
     * 第三方APP KEY
     **/
    public static final String APPKEY_UMENG = "5d5a459c3fc195fcc7000eab";//友盟AppKey
    /******END*****/

    /**
     * SD卡目录
     **/
    public static final String CHECK_PATH = "/sdcard/faceCheckPic/";
    public static final String REGIST_PATH = "/sdcard/faceRegistPic/";
    public static final String QRCODE_PATH = "/sdcard/qrcodeRegistPic/";
    public static final String HOME_VEDIO_PATH2 = "/sdcard/homeVedio/";
    public static final String HOME_VEDIO_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/homeVedio/";
    public static final String MAIN_PATH = "/sdcard/faceinfo/";
    public static final String CACHE_PATH = MAIN_PATH + "/Cache"; //所有缓存目录
    public static final String CACHE_IMG = CACHE_PATH + "/CacheImg";  //Glide图片缓存目录
    public static final String FILE_TEMP = CACHE_PATH + "/Temp"; //用户头像、APK、上传的图片文件夹【临时文件夹，方便清除】
    /******END*****/

    /**
     * 服务器返回状态码
     */
    public static class HttpStatus {
        //客户端自定义//
        public static final int STATUS_NETWORK_EXCEPTION = 0X0001;  //网络错误
        public static final int STATUS_SYSTEM_EXCEPTION = 0X0002;  //系统错误

        //服务器返回规范//
        public static final int STATUS_NEED_PAY_PASSWORD = 9; //还没有设置支付密码
        public static final int STATUS_LOGIN_FAIL = 18;       //账号或密码错误
        public static final int STATUS_BET_CLOSE = 9;  //已经关盘
        public static final int STATUS_BET_MONEY_LACK = 10;  //余额不足
        public static final int STATUS_SUCCESS = 0; //(成功)
        public static final int STATUS_UNKNOWN_ERROR = 1;     // (未知错误)
        public static final int STATUS_UNDEAL_ERROR = 2;     // (未处理的异常)
        public static final int STATUS_PERMISSION_ERROR = 3;     // (权限不足)
        public static final int STATUS_PARAM_ERROR = 5;     //(参数错误)
        public static final int STATUS_NOTFIND_URL_ERROR = 6;     //(找不到URL)
        public static final int STATUS_NEED_LOGIN = 7;     // (需要登录)
        public static final int STATUS_TIMEOUT_ERROR = 9;     //(超时)
        public static final int STATUS_MISMATCH_ERROR = 10;     // (不匹配)
        public static final int STATUS_SOMATH_ERROR = 11;     // (太频繁)
        public static final int STATUS_FORMAT_ERROR = 12;     //(格式错误)
        public static final int STATUS_SOMANY_ERROR = 13;     // (太多)
        public static final int STATUS_ALREADY_EXISTED_ERROR = 14;     //(已经存在)
    }
    /******END*****/

    /**
     * 基础配置信息KEY
     */
    public static class ConfigKey {
        public static final String PREFERENCE_SYSTEM_CONFIG = "systemSharedConfig";  // 系统SharedPreferences数据
    }
    /******END*****/

    /**
     * 上传图片类型
     */
    public static class UpdateImageType {
        public static final String UPDATE_HEAD_IMAGE = "t";  //上传头像类型
    }
}
