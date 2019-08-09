package com.ybkj.videoaccess.mvp.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.widget.ImageView;

import com.google.zxing.client.android.util.QRCodeUtil;
import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.mvp.base.BaseActivity;
import com.ybkj.videoaccess.util.MyDeviceInfo;
import com.ybkj.videoaccess.util.ViewUtil;

import butterknife.BindView;

public class StartActivity extends BaseActivity {
    @BindView(R.id.imgCode) ImageView imgCode;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_start;
    }

    @Override
    protected String setTitle() {
        return null;
    }

    @Override
    protected void initView() {
        imgCode.setImageBitmap(QRCodeUtil.createQRImage(MyDeviceInfo.getMacDefault(this),
                ViewUtil.dip2px(this,180),ViewUtil.dip2px(this,180)));

//        startActivity(new Intent(this, MainActivity.class));
//        new Handler().postDelayed(() -> onFinishActivity(), 1000);
    }
}
