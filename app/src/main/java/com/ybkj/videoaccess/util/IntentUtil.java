package com.ybkj.videoaccess.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.ybkj.videoaccess.mvp.data.bean.WebViewBean;
import com.ybkj.videoaccess.mvp.view.activity.WebActivity;

/**
 * 跳转工具类
 * <p>
 * Created by HH on 2018/1/19.
 */

public class IntentUtil {

    public static final String PARAM_WEB = "mode";
    public static final String CUSTOMER_ACCOUNT = "customerAccount";  //客服账号Key

    /**
     * 跳转用户登陆
     */
    public static void startToUserLoginActivity(Context context) {
        //context.startActivity(new Intent(context, UserLoginActivity.class));
    }

    /**
     * 跳转用户注册
     */
    public static void startToUserRegisterActivity(Context context) {
        //context.startActivity(new Intent(context, UserRegisterActivity.class));
    }

    /**
     * 跳转H5页面
     *
     * @param context
     * @param webViewBean
     */
    public static void startToWebActivity(Context context, WebViewBean webViewBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(PARAM_WEB, webViewBean);
        context.startActivity(new Intent(context, WebActivity.class).putExtras(bundle));
    }

    /**
     * 跳转聊天界面
     *
     * @param customerAccount ： 客服账号
     */
    public static void startToChatActivity(Context context, String customerAccount) {
    }
}
