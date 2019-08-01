package com.ybkj.videoaccess.mvp.view.dialog;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.mvp.base.BaseDialog;
import com.ybkj.videoaccess.util.http.HttpUtil;

/**
 * 网络请求等待框
 * 进行网络请求的时候弹出该框，提示用户等待
 *
 * @author HH
 */
public class LoadingDialog extends BaseDialog {
    private View v;
    private ImageView img;


    public LoadingDialog(Context context, String text) {
        super(context, R.style.TransDialog);

        v = View.inflate(context, R.layout.dialog_loading, null);
        img = (ImageView) v.findViewById(R.id.progress);
        findView(text);

        setContentView(v);

        setOnCancelListener(dialog -> {
            // 单击返回后取消该等待框，同时取消该次网络请求
            HttpUtil.getInstance().unSubscribed();
        });
    }

    /**
     * 初始化
     *
     * @param text 提示信息
     */
    private void findView(String text) {
        if (null == text) {
            v.findViewById(R.id.info).setVisibility(View.GONE);
        } else {
            ((TextView) v.findViewById(R.id.info)).setText(text);
        }

        ((AnimationDrawable) img.getDrawable()).start();
    }

    /**
     * 设置提示信息
     *
     * @param text
     */
    public void setText(String text) {
        if (null == text) {
            v.findViewById(R.id.info).setVisibility(View.GONE);
        } else {
            ((TextView) v.findViewById(R.id.info)).setText(text);
        }
    }

    @Override
    public void show() {
        super.show();
    }
}
