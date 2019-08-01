package com.ybkj.videoaccess.mvp.base;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import com.ybkj.videoaccess.R;

/**
 * 定义通用基类Dialog, 去除标题
 *
 * Created by HH on 2016/7/5.
 */
public class BaseDialog extends Dialog {

    public BaseDialog(Context context) {
        super(context, R.style.Dialog);

        // 去除标题，去除背景
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public BaseDialog(Context context, int styleRes) {
        super(context, styleRes);

        // 去除标题，去除背景
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

}
