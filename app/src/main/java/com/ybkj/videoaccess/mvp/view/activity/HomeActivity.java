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
import android.os.RemoteException;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.zxing.client.android.CaptureActivity;
import com.wrtsz.api.WrtdevManager;
import com.wrtsz.intercom.master.IFaceApi;
import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.mvp.base.BaseActivity;
import com.ybkj.videoaccess.mvp.control.HomeControl;
import com.ybkj.videoaccess.mvp.data.bean.RemoteResultBean;
import com.ybkj.videoaccess.mvp.data.bean.VedioInfo;
import com.ybkj.videoaccess.mvp.data.model.HomeModel;
import com.ybkj.videoaccess.mvp.presenter.HomePresenter;
import com.ybkj.videoaccess.mvp.view.dialog.InputDialog;
import com.ybkj.videoaccess.mvp.view.dialog.ListDialog;
import com.ybkj.videoaccess.util.AudioMngHelper;
import com.ybkj.videoaccess.util.CommonUtil;
import com.ybkj.videoaccess.util.DataUtil;
import com.ybkj.videoaccess.util.LogUtil;
import com.ybkj.videoaccess.util.ToastUtil;
import com.ybkj.videoaccess.websocket.JWebSocketClient;
import com.ybkj.videoaccess.websocket.JWebSocketClientService;
import com.ybkj.videoaccess.weight.VedioHorizontalScorllView;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * 主界面日常视频播放
 */
public class HomeActivity extends BaseActivity<HomePresenter, HomeModel> implements HomeControl.IHomeView {
    @BindView(R.id.horizontalScorllView) VedioHorizontalScorllView horizontalScorllView;
    private WrtdevManager wrtdevManager = null;
    private Timer timer;
    private TimerTask timerTask;

    // WebSocket服务
    private JWebSocketClient client;
    private JWebSocketClientService.JWebSocketClientBinder binder;
    private JWebSocketClientService jWebSClientService;
    private RemoteMessageReceiver remoteMessageReceiver;

    //定义aidl接口变量
    private IFaceApi iFaceApi;
    public final static int SCANNING_REQUEST_CODE = 1;

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
//        startJWebSClientService();
        //绑定服务
//        bindService();
        //注册广播
//        registerReceiver();

        // 实例化远程调用设备SDK服务
        initAidlService();

//        AudioMngHelper audioMngHelper = new AudioMngHelper(this);
//        audioMngHelper.setAudioType(AudioMngHelper.TYPE_MUSIC);
//        audioMngHelper.setAudioType(AudioMngHelper.TYPE_ALARM);
//        audioMngHelper.setAudioType(AudioMngHelper.TYPE_RING);
//        audioMngHelper.setVoice100(60);

//        startActivity(new Intent(HomeActivity.this, FaceCheckActivity.class));

//        byte[] bytes = new byte[]{23,33,55,55};
//        Log.e("ByteBuffer", Integer.parseInt(DataUtil.bytesToHexString(bytes),16)+"");

        List<VedioInfo> vedioInfoList = new ArrayList<>();
        for(int i=0;i<5;i++) {
            VedioInfo vedioInfo = new VedioInfo("123","我是测试视频"+i);
            vedioInfoList.add(vedioInfo);
        }
        horizontalScorllView.initData(vedioInfoList, new VedioHorizontalScorllView.IOnItemCheck() {
            @Override
            public void onItemCheck(VedioInfo vedioInfo) {

            }
        });

        // 测试重启设备
//        CommonUtil.RebootDevice(HomeActivity.this);
    }

    private void initAidlService(){
        // 通过Intent指定服务端的服务名称和所在包，与远程Service进行绑定
        //参数与服务器端的action要一致,即"服务器包名.aidl接口文件名"
        //Intent intent = new Intent("com.wrtsz.intercom.master.IFaceApi");
        Intent intent = new Intent("com.wrtsz.intercom.master.WRT_FACE_SERVICE");

        //Android5.0后无法只通过隐式Intent绑定远程Service
        //需要通过setPackage()方法指定包名
        intent.setPackage("com.wrtsz.intercom.master");

        //绑定服务,传入intent和ServiceConnection对象
        boolean iss = bindService(intent, connection, Context.BIND_AUTO_CREATE);
        Log.e("iss",iss+"---");
    }

    //创建ServiceConnection的匿名类
    private ServiceConnection connection = new ServiceConnection() {
        //重写onServiceConnected()方法和onServiceDisconnected()方法
        //在Activity与Service建立关联和解除关联的时候调用
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("onServiceDisconnected", "aidl远程服务断开成功");
        }

        //在Activity与Service建立关联时调用
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("onServiceConnected", "aidl远程服务连接成功");
            //IFaceApi.Stub.asInterface()方法将传入的IBinder对象传换成了mAIDL_Service对象
            iFaceApi = IFaceApi.Stub.asInterface(service);
            try {
                //通过该对象调用在MyAIDLService.aidl文件中定义的接口方法,从而实现跨进程通信
                String result1 = iFaceApi.recognition_config(10,1);
                Log.e("result1", "result:"+result1);
//                String result = iFaceApi.recognition("skfjskfjsfkd","");
//                Log.e("result", "result:"+result);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

    };

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
            RemoteResultBean remoteResultBean = new Gson().fromJson(message,RemoteResultBean.class);
            Log.e("onReceive", "收到："+message);
        }
    }

    private void initWrtdev() {
        wrtdevManager = (WrtdevManager) getSystemService("wrtsz");

//        Log.e("openDoor",wrtdevManager.openDoor()+"++");
//        Log.e("openLed1",wrtdevManager.openLed(1)+"++");
//        Log.e("openLed0",wrtdevManager.openLed(0)+"++");

//        Log.e("getMagnetometerStatus",wrtdevManager.getMagnetometerStatus()+"++");

//        startTimer();
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

                byte[] bytes = wrtdevManager.getIcCardNo();
                if(bytes != null && bytes.length > 0){
                    for(byte bt:bytes){
//                        Log.e("openDoor",bt+"++");
                    }
                }
                Log.e("ICCarcNumber", Integer.parseInt(DataUtil.bytesToHexString(bytes),16)+"   "+value);
            }
        };
        timer.schedule(timerTask, 1000, 2000);//延时1s，每隔500毫秒执行一次run方法
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

    @Override
    public void onResume() {
//        startTimer();
        super.onResume();
    }

    private ListDialog listDialog;      // 选项列表框
    private InputDialog inputDialog;    // 密码输入框

    /**
     * 监听数字输入
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(listDialog == null){
            listDialog = new ListDialog(HomeActivity.this, new ListDialog.OnKeyDownListener() {
                @Override
                public void onKeyDown(int key) {
                    switch (key){
                        case 1:
                            // 人脸注册
                            Intent intent = new Intent(HomeActivity.this, CaptureActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivityForResult(intent, SCANNING_REQUEST_CODE);
                            break;
                        case 2:
                            // 2 输入开门密码
                            if(inputDialog == null){
                                inputDialog = new InputDialog(HomeActivity.this, new InputDialog.OnKeyDownListener() {
                                    @Override
                                    public void onKeyDown(int keyCode) {

                                    }

                                    @Override
                                    public void onSubmit(String pwd) {
                                        // 门禁主机APP保存当前楼栋的住户和密码规则（非明文），
                                        // 在用户输入密码的时候进行验证，验证通过之后调用进门记录接口生成开门记录

                                    }
                                });
                                inputDialog.show();
                            }else{
                                inputDialog.show();
                            }
                            CommonUtil.hiddenSoftInput(HomeActivity.this);
                            break;
                        case 3:
                            // 3 呼叫业主
                            break;
                        case 4:
                            // 4 卡片关联

                            break;
                        case 5:
                            // #关闭
                            break;
                    }
                }
            });
            /*Window window = listDialog.getWindow();
            if (listDialog != null && window != null) {
                WindowManager.LayoutParams attr = window.getAttributes();
                if (attr != null) {
                    attr.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    attr.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    attr.gravity = Gravity.CENTER;//设置dialog 在布局中的位置
                }
            }*/
            listDialog.show();
        }else{
            listDialog.show();
        }

        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(this, CaptureActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, SCANNING_REQUEST_CODE);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 上传开门记录结果
     * @param result
     */
    @Override
    public void showGateOpenRecordResult(String result) {
//        mPresenter.gateOpenRecord(null);
    }

    /**
     * 密码开门结果
     * @param result
     */
    @Override
    public void showPwdValidation(String result) {

    }

}
