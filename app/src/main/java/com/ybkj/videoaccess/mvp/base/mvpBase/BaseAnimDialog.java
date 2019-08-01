package com.ybkj.videoaccess.mvp.base.mvpBase;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.util.ViewUtil;

import butterknife.ButterKnife;

/**
 * 下拉弹出框基类
 * <p> 实现全屏 </p>
 * <p> 由下向上弹出 </p>
 *
 * Created by HH on 2016/7/8.
 */
public abstract class BaseAnimDialog extends Dialog {
    private View view;

    public BaseAnimDialog(Context context) {
        super(context, R.style.Dialog);

        // 去掉默认标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = setView();
        ButterKnife.bind(this, view);
        initView(view);

        //全屏显示
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = ViewUtil.getScreenWidth(getContext());
        getWindow().setAttributes(lp);
    }

    /**
     * 设置View，由子类注入
     * @return
     */
    protected abstract View setView();

    /**
     * 初始化View
     *
     * @param view
     */
    public void initView(View view) {
        getWindow().setWindowAnimations(R.style.AnimBottom);
        setContentView(view);
    }
}
