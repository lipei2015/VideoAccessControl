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
    private TextView title;
    private TextView message;
    private ImageView imgSuccess;

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
        title = (TextView) v.findViewById(R.id.dialogTitle);
        message = (TextView) v.findViewById(R.id.dialogMessage);
        layout_regist_fail = (LinearLayout) v.findViewById(R.id.layout_regist_fail);
        tv_down_count = (TextView) v.findViewById(R.id.tv_down_count);
        tv_content = (TextView) v.findViewById(R.id.tv_content);
        imgSuccess = (ImageView) v.findViewById(R.id.imgSuccess);
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
    public BindCardDialog setTitle(String titleValue) {
        title.setText(titleValue);
        return this;
    }

    /**
     *
     * @param visable
     * @return
     */
    public BindCardDialog setSuccessIconVisable(boolean visable) {
        imgSuccess.setVisibility(visable ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * 设置无标题
     */
    public BindCardDialog setNoTitle(boolean hasTitle) {
        title.setVisibility(hasTitle ? View.GONE : View.VISIBLE);
        return this;
    }

    /**
     * 设置温馨信息
     */
    public BindCardDialog setMessage(String messageValue) {
        message.setText(messageValue);
        return this;
    }

    /**
     * 设置提示内容是否可见
     * @param hasMessage
     * @return
     */
    public BindCardDialog setNoMessage(boolean hasMessage) {
        message.setVisibility(hasMessage ? View.GONE : View.VISIBLE);
        return this;
    }

    public BindCardDialog showRegistError(){
        layout_regist_fail.setVisibility(View.VISIBLE);
        return this;
    }

    public void setRegistErroDownCountString(String str){
        tv_down_count.setText(str);
    }

    public void setClosedEnable(boolean needClosed){
        this.needClosed = needClosed;
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
