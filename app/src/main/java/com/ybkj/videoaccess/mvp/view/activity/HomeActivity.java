package com.ybkj.videoaccess.mvp.view.activity;

import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.view.KeyEvent;

import com.wrtsz.api.IWrtdevManager;
import com.wrtsz.api.WrtdevManager;
import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.mvp.base.BaseActivity;
import com.ybkj.videoaccess.mvp.data.model.HomeModel;
import com.ybkj.videoaccess.mvp.presenter.HomePresenter;
import com.ybkj.videoaccess.util.ToastUtil;

import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends BaseActivity<HomePresenter, HomeModel>{
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
