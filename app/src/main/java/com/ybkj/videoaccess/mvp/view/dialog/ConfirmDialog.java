package com.ybkj.videoaccess.mvp.view.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.mvp.base.BaseDialog;

/**
 * 确认收货
 * <p>
 * Created by HH on 2016/7/22.
 */
public class ConfirmDialog extends BaseDialog implements View.OnClickListener {
    private TextView title;
    private TextView message;
    private View v;
    private OnConfirm onConfirm;
    private OnItemConfirm onItemConfirm;
    private View cancel;
    private View line;
    private Button mConfirm;
    private boolean needClosed = true; // 点击确定是否隐藏弹窗

    public ConfirmDialog(Context context) {
        super(context);
        findView(context);
        setCancelable(true);
    }

    public ConfirmDialog(Context context, OnConfirm onConfirm) {
        super(context);
        this.onConfirm = onConfirm;
        findView(context);
        setCancelable(true);
    }

    public ConfirmDialog(Context context, OnItemConfirm onItemConfirm) {
        super(context);
        this.onItemConfirm = onItemConfirm;
        findView(context);
        setCancelable(false);
    }

    /**
     * 初始化
     */
    private void findView(Context context) {
        v = View.inflate(context, R.layout.dialog_confirm, null);
        title = (TextView) v.findViewById(R.id.dialogTitle);
        message = (TextView) v.findViewById(R.id.dialogMessage);
        cancel = v.findViewById(R.id.cancel);
        line = v.findViewById(R.id.line);
        cancel.setOnClickListener(this);
        v.findViewById(R.id.confirm).setOnClickListener(this);
        setContentView(v);
    }

    /**
     * 设置提示标题
     */
    public ConfirmDialog setTitle(String titleValue) {
        title.setText(titleValue);
        return this;
    }

    /**
     * 设置无标题
     */
    public ConfirmDialog setNoTitle(boolean hasTitle) {
        title.setVisibility(hasTitle ? View.GONE : View.VISIBLE);
        return this;
    }

    /**
     * 设置温馨信息
     */
    public ConfirmDialog setMessage(String messageValue) {
        message.setText(messageValue);
        return this;
    }

    public ConfirmDialog setLeftText(String leftText) {
        ((TextView) v.findViewById(R.id.cancel)).setText(leftText);
        return this;
    }

    public ConfirmDialog setRightText(String rightText) {
        ((TextView) v.findViewById(R.id.confirm)).setText(rightText);
        return this;
    }

    public ConfirmDialog setLeftVisiable(boolean show) {
        v.findViewById(R.id.cancel).setVisibility(show ? View.VISIBLE : View.GONE);
        v.findViewById(R.id.line).setVisibility(show ? View.VISIBLE : View.GONE);
        return this;
    }

    public void setClosedEnable(boolean needClosed){
        this.needClosed = needClosed;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                if (onItemConfirm != null) {
                    onItemConfirm.onCancel();
                }
                dismiss();
                break;
            case R.id.confirm:
                if (onConfirm != null) {
                    onConfirm.onConfirm();
                }
                if (onItemConfirm != null) {
                    onItemConfirm.onConfirm();
                }
                if(needClosed) {
                    dismiss();
                }
                break;
        }
    }

    public interface OnConfirm {
        void onConfirm();
    }

    public interface OnItemConfirm {
        void onConfirm();

        void onCancel();
    }
}
