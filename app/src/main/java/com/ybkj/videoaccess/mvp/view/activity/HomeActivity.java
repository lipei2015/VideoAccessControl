package com.ybkj.videoaccess.mvp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.view.KeyEvent;

import com.wrtsz.api.WrtdevManager;
import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.mvp.base.BaseActivity;
import com.ybkj.videoaccess.mvp.data.model.HomeModel;
import com.ybkj.videoaccess.mvp.presenter.HomePresenter;
import com.ybkj.videoaccess.util.LogUtil;
import com.ybkj.videoaccess.util.ToastUtil;

import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends BaseActivity<HomePresenter, HomeModel>{
    private WrtdevManager wrtdevManager = null;
    private Timer timer;
    private TimerTask timerTask;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected String setTitle() {
        return null;
    }

    @Override
    protected void initView() {
        initWrtdev();
    }

    private void initWrtdev() {
//        wrtdevManager = (WrtdevManager) getSystemService(Context.WRTSZ_SERVICE);
        wrtdevManager = (WrtdevManager) getSystemService("wrtsz");

        startTimer();
    }

    /**
     * 开始有无人像出现监听
     */
    private void startTimer(){
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                int value = wrtdevManager.getMicroWaveState();

                Message message = new Message();
                message.what = value;
                faceHandler.sendMessage(message);
            }
        };
        timer.schedule(timerTask, 3000, 1500);//延时1s，每隔500毫秒执行一次run方法
    }

    /**
     * 取消有无人像出现监听
     */
    private void cancelTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    Handler faceHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            LogUtil.i(msg.what+"++");
            switch (msg.what){
                case 1:
                    // 有人出现
                    cancelTimer();
                    startActivity(new Intent(HomeActivity.this,CustomCameraActivity.class));
                    break;
                case 0:
                    // 无人出现

                    break;
            }
            super.handleMessage(msg);
        }
    };

    /**
     * 监听数字输入
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        ToastUtil.showMsg(keyCode+"--------");
        switch (keyCode){
            case 7:
                // 0
                break;
            case 8:
                // 1
                break;
            case 9:
                // 2
                break;
            case 10:
                // 3
                break;
            case 11:
                // 4

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
                // #
                break;

        }
        return super.onKeyDown(keyCode, event);
    }
}
