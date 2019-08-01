package com.ybkj.videoaccess.app;

/**
 * Api接口常量定义
 * <p>
 * Created by HH on 2018/1/19
 */
public class ConstantApi {
    public static final String IP = "http://manage.cp138666.info:8081";  //测试IP
    //public static final String IP = "http://new.cp138666.info:8080/ ";  //正式IP
    //public static final String IP = "http://192.168.2.165:8080";  //陈盼IP(用于调试)
    //public static final String IP = "http://192.168.2.53:8080/";  //袁立波IP(用于调试)

    /**
     * H5链接
     **/

    /******END*****/

    /**
     * 赛程信息
     */

    /**
     * 购彩
     **/

    /******END*****/

    /**
     * 用户
     **/
    public static final String SYSTEM_FEEDBACK_SUBMIT = "/api/feedback/feedback"; //提交反馈
    /******END*****/

    /**
     * 订单
     **/

    /******END*****/

    /**
     * 支付模块
     */

    /******END*****/

    /**
     * 配置信息
     **/
    public static final String GET_CONFIG = "/api/config/get"; //基础信息接口
    public static final String CHECK_VERSION = "/api/user/getVersionByname"; //检测版本更新
    /******END****/

}
