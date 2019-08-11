package com.ybkj.videoaccess.mvp.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.google.zxing.client.android.util.QRCodeUtil;
import com.wrtsz.api.IWrtdevManager;
import com.wrtsz.api.WrtdevManager;
import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.mvp.base.BaseActivity;
import com.ybkj.videoaccess.util.MyDeviceInfo;
import com.ybkj.videoaccess.util.ToastUtil;
import com.ybkj.videoaccess.util.ViewUtil;

import java.util.Timer;
import java.util.TimerTask;

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
//        String mac = MyDeviceInfo.getMacDefault(this);
        String mac = MyDeviceInfo.getDeviceId(this);
        ToastUtil.showMsg(mac);
        imgCode.setImageBitmap(QRCodeUtil.createQRImage(mac,
                (int)getResources().getDimension(R.dimen.start_code_size),(int)getResources().getDimension(R.dimen.start_code_size)));

//        ViewUtil.dip2px(this,180)

//        startActivity(new Intent(this, MainActivity.class));
//        new Handler().postDelayed(() -> onFinishActivity(), 1000);

        // 先检测本地有无下载的数据，有的话直接进入主界面展示，没有的话直接每隔2秒拉取数据数据，
        // 没有数据说明没有绑定成功，拉取到了数据就说明绑定成功，开始下载数据并且进入主页播放日常视频


    }

}
