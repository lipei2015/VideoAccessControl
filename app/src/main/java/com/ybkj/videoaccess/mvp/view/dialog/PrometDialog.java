package com.ybkj.videoaccess.mvp.view.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.mvp.base.BaseDialog;

/**
 * 确认收货
 * <p>
 * Created by HH on 2016/7/22.
 */
public class PrometDialog extends BaseDialog {
    private TextView title;
    private TextView message;
    private View v;
    private OnConfirm onConfirm;
    private OnItemConfirm onItemConfirm;
    private boolean needClosed = true; // 点击确定是否隐藏弹窗

    public PrometDialog(Context context) {
        super(context);
        findView(context);
        setCancelable(true);
    }

    public PrometDialog(Context context, OnConfirm onConfirm) {
        super(context);
        this.onConfirm = onConfirm;
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
        message.setText(messageValue);
        return this;
    }

    public void setClosedEnable(boolean needClosed){
        this.needClosed = needClosed;
    }

    public interface OnConfirm {
        void onConfirm();
    }

    public interface OnItemConfirm {
        void onConfirm();

        void onCancel();
    }
}
