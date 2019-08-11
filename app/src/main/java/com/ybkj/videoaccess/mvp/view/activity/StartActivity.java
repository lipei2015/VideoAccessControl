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

        initWrtdev();

        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int widthPixels = outMetrics.widthPixels;
        int heightPixels = outMetrics.heightPixels;
        Log.i("StartActivity", "widthPixels = " + widthPixels + ",heightPixels = " + heightPixels);
        //widthPixels = 1440, heightPixels = 2768
    }

    private void initWrtdev() {
        WrtdevManager wrtdevManager = new WrtdevManager(new IWrtdevManager() {
            @Override
            public IBinder asBinder() {
                return null;
            }

            /**
             * 返回微波检测状态：1为有人，0为无人，-1为错误
             * @return
             * @throws RemoteException */

            @Override
            public int getMicroWaveState() throws RemoteException {
                return 0;
            }

            @Override
            public byte[] getIcCardNo() throws RemoteException {
                return new byte[0];
            }

            @Override
            public int openLed(int i) throws RemoteException {
                return 0;
            }

            @Override
            public int openDoor() throws RemoteException {
                return 0;
            }
        }, new Handler(){
            @Override
            public void handleMessage(Message msg) {
                ToastUtil.showMsg(msg.what+"   99999999999999");
                super.handleMessage(msg);
            }
        });

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                int value = wrtdevManager.getMicroWaveState();

                Message message = new Message();
                message.what = value;
                faceHandler.sendMessage(message);
            }
        };
        timer.schedule(timerTask, 5000, 1500);//延时1s，每隔500毫秒执行一次run方法
    }

    Handler faceHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            ToastUtil.showMsg(msg.what+"   --------");
            if (msg.what == 1) {
                //do something
            }
            super.handleMessage(msg);
        }
    };

}
