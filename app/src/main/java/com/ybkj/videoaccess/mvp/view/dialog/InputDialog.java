package com.ybkj.videoaccess.mvp.view.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.mvp.base.BaseDialog;

/**
 * 确认收货
 * <p>
 * Created by HH on 2016/7/22.
 */
public class InputDialog extends BaseDialog {
    private TextView title;
    private View v;
    private OnConfirm onConfirm;
    private OnItemConfirm onItemConfirm;
    private View cancel;
    private EditText etPwd;
    private TextView tvPwdRight;
    private TextView tvPwdError;
    private boolean needClosed = true; // 点击确定是否隐藏弹窗

    public InputDialog(Context context) {
        super(context);
        findView(context);
        setCancelable(true);
    }

    public InputDialog(Context context, OnConfirm onConfirm) {
        super(context);
        this.onConfirm = onConfirm;
        findView(context);
        setCancelable(true);
    }

    public InputDialog(Context context, OnItemConfirm onItemConfirm) {
        super(context);
        this.onItemConfirm = onItemConfirm;
        findView(context);
        setCancelable(false);
    }

    /**
     * 初始化
     */
    private void findView(Context context) {
        v = View.inflate(context, R.layout.dialog_input, null);
        title = (TextView) v.findViewById(R.id.dialogTitle);
        etPwd = (EditText) v.findViewById(R.id.etPwd);
        tvPwdRight = (TextView) v.findViewById(R.id.tvPwdRight);
        tvPwdError = (TextView) v.findViewById(R.id.tvPwdError);
        setContentView(v);
    }

    /**
     * 设置提示标题
     */
    public InputDialog setTitle(String titleValue) {
        title.setText(titleValue);
        return this;
    }

    /**
     * 设置无标题
     */
    public InputDialog setNoTitle(boolean hasTitle) {
        title.setVisibility(hasTitle ? View.GONE : View.VISIBLE);
        return this;
    }

    public InputDialog showPwdRight() {
        tvPwdRight.setVisibility(View.VISIBLE);
        return this;
    }

    public InputDialog showPwdError() {
        tvPwdError.setVisibility(View.VISIBLE);
        return this;
    }

    public interface OnConfirm {
        void onConfirm();
    }

    public interface OnItemConfirm {
        void onConfirm();

        void onCancel();
    }
}
