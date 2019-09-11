package com.ybkj.videoaccess.mvp.view.dialog;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.client.android.CaptureActivity;
import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.mvp.base.BaseDialog;
import com.ybkj.videoaccess.mvp.view.activity.HomeActivity;
import com.ybkj.videoaccess.util.ToastUtil;

/**
 * lp
 */
public class ListDialog extends BaseDialog {
    private Button button_1;
    private Button button_2;
    private Button button_3;
    private Button button_4;
    private Button button_5;
    private Button button_6;
    private Button button_7;
    private View v;
    private OnKeyDownListener onKeyDownListener;

    public ListDialog(Context context,OnKeyDownListener onKeyDownListener) {
        super(context);
        findView(context);
        setCancelable(true);
        this.onKeyDownListener = onKeyDownListener;
    }

    /**
     * 初始化
     */
    private void findView(Context context) {
        v = View.inflate(context, R.layout.dialog_list, null);
        button_1 = (Button) v.findViewById(R.id.button_1);
        button_2 = (Button) v.findViewById(R.id.button_2);
        button_3 = (Button) v.findViewById(R.id.button_3);
        button_4 = (Button) v.findViewById(R.id.button_4);
        button_5 = (Button) v.findViewById(R.id.button_5);
        button_6 = (Button) v.findViewById(R.id.button_6);
        button_7 = (Button) v.findViewById(R.id.button_7);
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
                // *
                break;
            case 136:
                // # 关闭
                dismiss();
                if(onKeyDownListener != null){
                    onKeyDownListener.onKeyDown(5);
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    public interface OnKeyDownListener {
        void onKeyDown(int keyCode);
    }
}
