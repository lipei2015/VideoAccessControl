package com.ybkj.videoaccess.mvp.view.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;

import com.wrtsz.api.WrtdevManager;
import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.mvp.base.BaseActivity;
import com.ybkj.videoaccess.mvp.data.model.HomeModel;
import com.ybkj.videoaccess.mvp.presenter.HomePresenter;
import com.ybkj.videoaccess.mvp.view.dialog.ListDialog;
import com.ybkj.videoaccess.util.LogUtil;
import com.ybkj.videoaccess.util.ToastUtil;
import com.ybkj.videoaccess.websocket.JWebSocketClient;
import com.ybkj.videoaccess.websocket.JWebSocketClientService;

import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends BaseActivity<HomePresenter, HomeModel>{
    private WrtdevManager wrtdevManager = null;
    private Timer timer;
    private TimerTask timerTask;

    private JWebSocketClient client;
    private JWebSocketClientService.JWebSocketClientBinder binder;
    private JWebSocketClientService jWebSClientService;
    private RemoteMessageReceiver remoteMessageReceiver;

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

        //启动远程监听服务
        startJWebSClientService();
        //绑定服务
        bindService();
        //注册广播
        registerReceiver();

//        startActivity(new Intent(HomeActivity.this, FaceCheckActivity.class));
    }

    /**
     * 绑定服务
     */
    private void bindService() {
        Intent bindIntent = new Intent(this, JWebSocketClientService.class);
        bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE);
    }
    /**
     * 启动服务（websocket客户端服务）
     */
    private void startJWebSClientService() {
        Intent intent = new Intent(this, JWebSocketClientService.class);
        startService(intent);
    }

    /**
     * 动态注册广播
     */
    private void registerReceiver() {
        remoteMessageReceiver = new RemoteMessageReceiver();
        IntentFilter filter = new IntentFilter("com.xch.servicecallback.content");
        registerReceiver(remoteMessageReceiver, filter);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.e("HomeActivity", "服务与活动成功绑定");
            binder = (JWebSocketClientService.JWebSocketClientBinder) iBinder;
            jWebSClientService = binder.getService();
            client = jWebSClientService.client;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e("HomeActivity", "服务与活动成功断开");
        }
    };

    private class RemoteMessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message=intent.getStringExtra("message");
            Log.e("onReceive", "收到："+message);
            /*ChatMessage chatMessage=new ChatMessage();
            chatMessage.setContent(message);
            chatMessage.setIsMeSend(0);
            chatMessage.setIsRead(1);
            chatMessage.setTime(System.currentTimeMillis()+"");
            chatMessageList.add(chatMessage);
            initChatMsgListView();*/
        }
    }

    private void initWrtdev() {
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
                /*int value = wrtdevManager.getMicroWaveState();

                Message message = new Message();
                message.what = value;
                faceHandler.sendMessage(message);*/
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
                    startActivity(new Intent(HomeActivity.this, FaceCheckActivity.class));
                    break;
                case 0:
                    // 无人出现

                    break;
            }
            super.handleMessage(msg);
        }
    };

    private ListDialog listDialog;

    /**
     * 监听数字输入
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        ToastUtil.showMsg(keyCode+"--------");

        /*if(listDialog == null){
            listDialog = new ListDialog(HomeActivity.this);
            listDialog.show();
        }else{
            if(listDialog.isShowing()){
                switch (keyCode){
                    case 7:
                        // 0
                        break;
                    case 8:
                        // 1
                        listDialog.onItemClick(1);
                        break;
                    case 9:
                        // 2
                        listDialog.onItemClick(2);
                        break;
                    case 10:
                        // 3
                        listDialog.onItemClick(3);
                        break;
                    case 11:
                        // 4
                        listDialog.onItemClick(4);
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
            }else{
                listDialog.show();
            }
        }*/

        /*if(keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }*/

        return super.onKeyDown(keyCode, event);
    }
}
