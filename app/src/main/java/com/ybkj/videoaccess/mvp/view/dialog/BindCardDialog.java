package com.ybkj.videoaccess.mvp.view.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.mvp.base.BaseDialog;

/**
 * IC卡开卡dialog
 * <p>
 * Created by lp 2019年8月30日15:31:02
 */
public class BindCardDialog extends BaseDialog {
    private TextView tv_promet1;
    private TextView tv_promet2;
    private TextView tv_promet3;
    private ImageView img_icon;

    // 人脸注册失败提示
    private LinearLayout layout_regist_fail;
    private TextView tv_down_count;
    private TextView tv_content;

    private View v;
    private OnKeyDownListener onKeyDownListener;
    private boolean needClosed = true; // 点击确定是否隐藏弹窗

    public BindCardDialog(Context context) {
        super(context);
        findView(context);
        setCancelable(true);
    }

    public BindCardDialog(Context context, OnKeyDownListener onKeyDownListener) {
        super(context);
        this.onKeyDownListener = onKeyDownListener;
        findView(context);
        setCancelable(true);
    }

    /**
     * 初始化
     */
    private void findView(Context context) {
        v = View.inflate(context, R.layout.bind_card_dialog, null);
        tv_promet1 = (TextView) v.findViewById(R.id.tv_promet1);
        tv_promet2 = (TextView) v.findViewById(R.id.tv_promet2);
        tv_promet3 = (TextView) v.findViewById(R.id.tv_promet3);
        img_icon = (ImageView) v.findViewById(R.id.img_icon);
        setContentView(v);

        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams attr = window.getAttributes();
            if (attr != null) {
                attr.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                attr.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                attr.gravity = Gravity.CENTER;//设置dialog 在布局中的位置
            }
        }
    }

    /**
     * 设置提示标题
     */
    public BindCardDialog setPromet1(String value) {
        tv_promet1.setText(value);
        return this;
    }

    public BindCardDialog setPromet2(String value) {
        tv_promet2.setText(value);
        tv_promet2.setVisibility(View.VISIBLE);
        return this;
    }

    public BindCardDialog setPromet2TextColor(int color) {
        tv_promet2.setTextColor(color);
        return this;
    }

    public BindCardDialog setPromet3TextColor(int color) {
        tv_promet3.setTextColor(color);
        return this;
    }

    public BindCardDialog setPromet3(String value) {
        tv_promet3.setText(value);
        tv_promet3.setVisibility(View.VISIBLE);
        return this;
    }

    public interface OnKeyDownListener {
        void onRetry();
        void onExit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case 135:
                // * 重试
                if(onKeyDownListener != null) {
                    onKeyDownListener.onRetry();
                }
                dismiss();
                break;
            case 136:
                // # 关闭
                if(onKeyDownListener != null) {
                    onKeyDownListener.onExit();
                }
                dismiss();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
