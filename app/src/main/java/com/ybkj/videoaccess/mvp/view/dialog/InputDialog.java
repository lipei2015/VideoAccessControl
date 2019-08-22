package com.ybkj.videoaccess.mvp.view.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
    private EditText etPwd;
    private TextView tvPwdRight;
    private TextView tvPwdError;
    private OnKeyDownListener onKeyDownListener;

    public InputDialog(Context context,OnKeyDownListener onKeyDownListener) {
        super(context);
        findView(context);
        setCancelable(true);
        this.onKeyDownListener = onKeyDownListener;
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        ToastUtil.showMsg(keyCode+"++++++++");
        switch (keyCode){
            case 7:
                // 0
                break;
            case 8:
                // 1
                if(onKeyDownListener != null){
                    onKeyDownListener.onKeyDown(1);
                }
                dismiss();
                break;
            case 9:
                // 2 输入开门密码
                /*if(inputDialog == null){
                    inputDialog = new InputDialog(HomeActivity.this);
                    inputDialog.show();
                }else{
                    if(inputDialog.isShowing()){
                        // 正在输入密码
                    }else{
                        //

                    }
                }*/

                if(onKeyDownListener != null){
                    onKeyDownListener.onKeyDown(2);
                }
                dismiss();
                break;
            case 10:
                // 3通行密码
                if(onKeyDownListener != null){
                    onKeyDownListener.onKeyDown(3);
                }
                dismiss();
                break;
            case 11:
                // 4 卡片关联
                if(onKeyDownListener != null){
                    onKeyDownListener.onKeyDown(4);
                }
                dismiss();
                break;
            case 12:
                // 5
                break;
            case 13:
                // 6
                break;
            case 14:
                // 7
                break;
            case 15:
                // 8

                break;
            case 16:
                // 9
                break;
            case 135:
                // * 确认提交密码
                onKeyDownListener.onSubmit(etPwd.getText().toString());
                break;
            case 136:
                // # 关闭
                etPwd.setText("");
                dismiss();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    public interface OnKeyDownListener {
        void onKeyDown(int keyCode);
        void onSubmit(String pwd);
    }
}
