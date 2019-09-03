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
 * 操作结果提示
 * <p>
 * Created by lp 2019年8月3日15:26:44
 */
public class PrometDialog extends BaseDialog {
    private TextView title;
    private TextView message;
    private ImageView imgSuccess;

    // 人脸注册失败提示
    private LinearLayout layout_info;
    private LinearLayout layout_regist_fail;
    private TextView tv_down_count;
    private TextView tv_content;

    private View v;
    private OnKeyDownListener onKeyDownListener;
    private boolean needClosed = true; // 点击确定是否隐藏弹窗

    public PrometDialog(Context context) {
        super(context);
        findView(context);
        setCancelable(true);
    }

    public PrometDialog(Context context, OnKeyDownListener onKeyDownListener) {
        super(context);
        this.onKeyDownListener = onKeyDownListener;
        findView(context);
        setCancelable(true);
    }

    /**
     * 初始化
     */
    private void findView(Context context) {
        v = View.inflate(context, R.layout.dialog_promet, null);
        title = (TextView) v.findViewById(R.id.dialogTitle);
        message = (TextView) v.findViewById(R.id.dialogMessage);
        layout_regist_fail = (LinearLayout) v.findViewById(R.id.layout_regist_fail);
        layout_info = (LinearLayout) v.findViewById(R.id.layout_info);
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
    public PrometDialog setTitle(String titleValue) {
        title.setText(titleValue);
        return this;
    }

    /**
     *
     * @param visable
     * @return
     */
    public PrometDialog setSuccessIconVisable(boolean visable) {
        imgSuccess.setVisibility(visable ? View.VISIBLE : View.GONE);
        return this;
    }

    public PrometDialog setSuccessIcon(int id) {
        imgSuccess.setBackgroundResource(id);
        return this;
    }

    /**
     * 设置无标题
     */
    public PrometDialog setNoTitle(boolean hasTitle) {
        title.setVisibility(hasTitle ? View.GONE : View.VISIBLE);
        return this;
    }

    /**
     * 设置温馨信息
     */
    public PrometDialog setMessage(String messageValue) {
        layout_info.setVisibility(View.VISIBLE);
        message.setText(messageValue);
        return this;
    }

    public PrometDialog setMessageTextColor(int color) {
        message.setTextColor(color);
        return this;
    }

    /**
     * 设置提示内容是否可见
     * @param hasMessage
     * @return
     */
    public PrometDialog setNoMessage(boolean hasMessage) {
        message.setVisibility(hasMessage ? View.GONE : View.VISIBLE);
        return this;
    }

    public PrometDialog showRegistError(){
        layout_regist_fail.setVisibility(View.VISIBLE);
        return this;
    }

    public void setRegistErroDownCountString(String str){
        tv_down_count.setText(str);
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
                dismiss();
                if(onKeyDownListener != null) {
                    onKeyDownListener.onRetry();
                }
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
