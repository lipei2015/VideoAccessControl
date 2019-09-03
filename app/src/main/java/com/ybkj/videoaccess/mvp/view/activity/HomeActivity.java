package com.ybkj.videoaccess.mvp.view.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.gson.Gson;
import com.google.zxing.client.android.CaptureActivity;
import com.google.zxing.client.android.util.QRRecognizeHelper;
import com.wrtsz.api.WrtdevManager;
import com.wrtsz.intercom.master.IFaceApi;
import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.app.ConstantSys;
import com.ybkj.videoaccess.eventbus.EventUserInfoQRCode;
import com.ybkj.videoaccess.mvp.base.BaseActivity;
import com.ybkj.videoaccess.mvp.control.HomeControl;
import com.ybkj.videoaccess.mvp.data.bean.MediaInfo;
import com.ybkj.videoaccess.mvp.data.bean.RemoteResultBean;
import com.ybkj.videoaccess.mvp.data.bean.RequestICardReportBean;
import com.ybkj.videoaccess.mvp.data.bean.RequestMediaDownloadBean;
import com.ybkj.videoaccess.mvp.data.bean.RequestPwdValidationbean;
import com.ybkj.videoaccess.mvp.data.bean.VedioInfo;
import com.ybkj.videoaccess.mvp.data.model.HomeModel;
import com.ybkj.videoaccess.mvp.presenter.HomePresenter;
import com.ybkj.videoaccess.mvp.view.dialog.BindCardDialog;
import com.ybkj.videoaccess.mvp.view.dialog.InputDialog;
import com.ybkj.videoaccess.mvp.view.dialog.ListDialog;
import com.ybkj.videoaccess.mvp.view.dialog.PrometDialog;
import com.ybkj.videoaccess.util.AudioMngHelper;
import com.ybkj.videoaccess.util.CommonUtil;
import com.ybkj.videoaccess.util.DataUtil;
import com.ybkj.videoaccess.util.FileUtil;
import com.ybkj.videoaccess.util.HomeSurfaceViewUtil;
import com.ybkj.videoaccess.util.LogUtil;
import com.ybkj.videoaccess.util.PreferencesUtils;
import com.ybkj.videoaccess.util.ToastUtil;
import com.ybkj.videoaccess.websocket.JWebSocketClient;
import com.ybkj.videoaccess.websocket.JWebSocketClientService;
import com.ybkj.videoaccess.weight.VedioHorizontalScorllView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
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
    @BindView(R.id.videoView) VideoView videoView;
    @BindView(R.id.surfaceView) SurfaceView surfaceView;
    private WrtdevManager wrtdevManager = null;
    private Timer timer;
    private TimerTask timerTask;
    private File[] videoFiles;
    private int currentPlayPosition = -1;

    // WebSocket服务
    private JWebSocketClient client;
    private JWebSocketClientService.JWebSocketClientBinder binder;
    private JWebSocketClientService jWebSClientService;
    private RemoteMessageReceiver remoteMessageReceiver;

    private PreferencesUtils preferencesUtils;
    private String deviceId;
    private HomeSurfaceViewUtil homeSurfaceViewUtil;

    // 全天不停止时间处理器
    private Timer aliveTimer;
    private TimerTask aliveTimerTask;
    private EventUserInfoQRCode infoQRCode;     // 扫描用户二维码时候扫描的信息
    private int icCardCreateState = 0;          // 开卡状态：0 非开卡状态；1 开卡状态且监听用户刷卡；2 开卡状态且已获取到卡号在上传信息

    //定义aidl接口变量
    private IFaceApi iFaceApi;
    public final static int FACE_REGIST = 1;  // 人脸注册绑定
    public final static int IC_CARD_CREATE_SCAN = 2;  // IC卡开卡扫码

    private final int FACE_APPEAR = 0;
    private final int NO_FACE = 1;
    private final int CASE_COUNT_DOWN = 2;      // 提示框倒计时
    private final int CREATE_CARD_READING = 3;      // 开卡正在读卡

    @Override
    protected int setLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected HomeModel createModel() {
        return new HomeModel();
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    protected String setTitle() {
        return null;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        preferencesUtils = PreferencesUtils.getInstance(ConstantSys.PREFERENCE_USER_NAME);
        deviceId = preferencesUtils.getString(ConstantSys.PREFERENCE_DEVICE_ID,null);

        // 创建存放二维码照片的文件夹
        FileUtil.createDirectory(ConstantSys.QRCODE_PATH);

//        initWrtdev();

        //启动远程监听服务
//        startJWebSClientService();
        //绑定服务
//        bindService();
        //注册广播
//        registerReceiver();

        // 实例化远程调用设备SDK服务
//        initAidlService();

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

//        initVideoInfo();

        // 测试重启设备
//        CommonUtil.RebootDevice(HomeActivity.this);

        RequestMediaDownloadBean requestMediaDownloadBean = new RequestMediaDownloadBean();
        requestMediaDownloadBean.setMac(deviceId);
        requestMediaDownloadBean.setTimestamp(DataUtil.getYMDHMSString(System.currentTimeMillis()));
        mPresenter.mediaDownload(requestMediaDownloadBean);

        // 开启全天时间处理器
        startAliveTimer();

        /*String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/截屏/123456789.png";//文件路径
        String result = QRRecognizeHelper.getReult(BitmapFactory.decodeFile(path));
        Log.e("QRRecognizeHelper",result+"");*/

        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int widthPixels = outMetrics.widthPixels;
        int heightPixels = outMetrics.heightPixels;
        homeSurfaceViewUtil = new HomeSurfaceViewUtil(surfaceView,widthPixels,heightPixels);
    }

    private long dealTime = 0;
    /**
     * 开启全天时间处理器
     */
    private void startAliveTimer() {
        aliveTimer = new Timer();
        aliveTimerTask = new TimerTask() {
            @Override
            public void run() {
                long now = System.currentTimeMillis();
                // 距离上次去请求媒体数据要大于2小时
                if(now - dealTime > 2 * 60 * 60 * 100) {
                    int value = Integer.parseInt(DataUtil.getHMtring(now));
                    Log.e("startAliveTimer", "当前时间：" + value);
                    // <100 就是要当前时间在凌晨12点和1点之间才执行操作
                    if (value <= 100) {
                        RequestMediaDownloadBean requestMediaDownloadBean = new RequestMediaDownloadBean();
                        requestMediaDownloadBean.setMac(preferencesUtils.getString(ConstantSys.PREFERENCE_DEVICE_ID,null));
                        requestMediaDownloadBean.setTimestamp(DataUtil.getYMDHMSString(System.currentTimeMillis()));
                        mPresenter.mediaDownload(requestMediaDownloadBean);
                    }
                }
            }
        };
        // 每10分钟判断一次是否是凌晨 12点到 1 点之间
        aliveTimer.schedule(aliveTimerTask, 0, 10 * 60 * 1000);//延时1s，每隔500毫秒执行一次run方法
    }

    private void initVideoInfo() {
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int widthPixels = outMetrics.widthPixels;

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) videoView.getLayoutParams();
        params.height = 1920 * widthPixels / 1080;//1080 x width;
        videoView.setLayoutParams(params);
        File directoryFile = new File(ConstantSys.HOME_VEDIO_PATH);
        if(directoryFile.exists()){
            videoFiles = directoryFile.listFiles();
            if(videoFiles != null && videoFiles.length > 0){
                currentPlayPosition = 0;
                videoView.setVideoPath(videoFiles[0].getAbsolutePath());

                videoView.setBackgroundResource(0);
                videoView.start();
            }
        }
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                currentPlayPosition ++;
                if(videoFiles.length > currentPlayPosition) {
                    videoView.setVideoPath(videoFiles[currentPlayPosition].getAbsolutePath());
                    videoView.start();
                }
            }
        });
//        videoView.start();
    }

    private void initAidlService(){
        // 通过Intent指定服务端的服务名称和所在包，与远程Service进行绑定,参数与服务器端的action要一致,即"服务器包名.aidl接口文件名"
        //Intent intent = new Intent("com.wrtsz.intercom.master.IFaceApi");
        Intent intent = new Intent("com.wrtsz.intercom.master.WRT_FACE_SERVICE");

        //Android5.0后无法只通过隐式Intent绑定远程Service
        //需要通过setPackage()方法指定包名
        intent.setPackage("com.wrtsz.intercom.master");

        //绑定服务,传入intent和ServiceConnection对象
        boolean iss = bindService(intent, connection, Context.BIND_AUTO_CREATE);
        Log.e("iss",iss+"---");
    }

    /**
     * 创建ServiceConnection的匿名类
     */
    private ServiceConnection connection = new ServiceConnection() {
        //重写onServiceConnected()方法和onServiceDisconnected()方法,在Activity与Service建立关联和解除关联的时候调用
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
                String result1 = iFaceApi.unreg("1");
                Log.e("result1", "result:"+result1);

//                String unregResult = iFaceApi.unreg("1");
//                Log.e("unregResult", "unregResult:"+unregResult);
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

    @SuppressLint("WrongConstant")
    private void initWrtdev() {
        wrtdevManager = (WrtdevManager) getSystemService("wrtsz");
//        Log.e("getMagnetometerStatus",wrtdevManager.getMagnetometerStatus()+"++");

//        startTimer();
    }

    private int showTime = 0;
    /**
     * 开始有无人像出现监听
     */
    private void startTimer(){
        cancelTimer();
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
//                faceHandler.sendEmptyMessage(0);

                int value = wrtdevManager.getMicroWaveState();
                Message message = new Message();
                message.what = value;
                handler.sendMessage(message);

                byte[] bytes = wrtdevManager.getIcCardNo();
                if(bytes != null && bytes.length > 0 && icCardCreateState == 1){
                    for(byte bt:bytes){
                        //Log.e("openDoor",bt+"++");
                    }
                    handler.sendEmptyMessage(CREATE_CARD_READING);

                    int icno = Integer.parseInt(DataUtil.bytesToHexString(bytes),16);
                    if(bindCardDialog != null && bindCardDialog.isShowing()){
                        // 当前处于开卡过程
                        RequestICardReportBean bean = new RequestICardReportBean();
                        bean.setIcno(String.valueOf(icno));
                        bean.setMac(deviceId);
                        bean.setPid(infoQRCode.getCodeResult());
                        mPresenter.iCardReport(bean);

                        icCardCreateState = 2;  // 开卡状态修改为正在上报开卡信息状态
                    }
                    Log.e("ICCarcNumber", icno+"   "+value);
                }
            }
        };
        timer.schedule(timerTask, 2000, 1000);//延时1s，每隔500毫秒执行一次run方法
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

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            LogUtil.i(msg.what+"++");
            switch (msg.what){
                case FACE_APPEAR:
                    // 有人出现
                    showTime ++;
                     Log.e("startTimer", "  --- "+showTime);
                    if(showTime >= 2) {
                        cancelTimer();
                        startActivity(new Intent(HomeActivity.this, FaceCheckActivity.class));
                        showTime = 0;
                    }
                    break;
                case NO_FACE:
                    // 无人出现
                    Log.e("startTimer", "  nobody "+showTime);
                    showTime = 0;
                    /*ddd --;
                    if(ddd == 0){
                        cancelTimer();
                    }*/
                    break;
                case CASE_COUNT_DOWN:
                    int time = msg.arg1;
                    if(time < 0){
                        cancelCountDownTimer();
                        if(bindCardDialog != null && bindCardDialog.isShowing()){
                            bindCardDialog.cancel();
                        }
                    }else{
                        bindCardDialog.setPromet1("请出示住户验证码（"+time+"S）");
                        bindCardDialog.show();
                    }
                    break;
                case CREATE_CARD_READING:
                    // 更新开卡状态
                    bindCardDialog.setPromet2TextColor(getResources().getColor(R.color.black));
                    bindCardDialog.setPromet3TextColor(getResources().getColor(R.color.green));
                    bindCardDialog.setPromet3("正在读卡请稍后");
                    bindCardDialog.show();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onResume() {
//        startTimer();
        if(videoView.canPause()){
            videoView.start();
        }
        homeSurfaceViewUtil.initCamare();
        super.onResume();
    }

    @Override
    public void onPause() {
//        cancelTimer();
        if(videoView.isPlaying()){
            videoView.pause();
        }
        homeSurfaceViewUtil.releaseCamera();
        super.onPause();
    }

    @Override
    protected void onStop() {
//        cancelTimer();
        if(videoView.isPlaying()){
            videoView.pause();
        }
        homeSurfaceViewUtil.releaseCamera();
        super.onStop();
    }

    private ListDialog listDialog;      // 选项列表框
    private InputDialog inputDialog;    // 密码输入框
    private BindCardDialog bindCardDialog;    // 开卡提示框

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
                            intent.putExtra(CaptureActivity.SCAN_TYPE,CaptureActivity.SCAN_TYPE_FACE_REGIST);
                            startActivityForResult(intent, FACE_REGIST);

                            cancelTimer();
                            break;
                        case 2:
                            // 2 输入开门密码
                            if(inputDialog == null){
                                inputDialog = new InputDialog(HomeActivity.this, new InputDialog.OnKeyDownListener() {
                                    @Override
                                    public void onSubmit(String pwd) {
                                        // 门禁主机APP保存当前楼栋的住户和密码规则（非明文），
                                        // 在用户输入密码的时候进行验证，验证通过之后调用进门记录接口生成开门记录
                                        RequestPwdValidationbean bean = new RequestPwdValidationbean();
                                        bean.setMac(deviceId);
                                        bean.setPwd(pwd);
                                        bean.setType("4");
                                        bean.setTimestamp(DataUtil.getYMDHMSString(System.currentTimeMillis()));
                                        mPresenter.pwdValidation(bean);
                                    }
                                });
                                inputDialog.show();

                                cancelTimer();
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
//                            homeSurfaceViewUtil.takePhoto();
                            if(bindCardDialog == null){
                                bindCardDialog = new BindCardDialog(HomeActivity.this);
                                bindCardDialog.setPromet1("请出示住户验证码（15S）");
                            }
                            countDown = 15;
                            cancelCountDownTimer();
                            bindCardDialog.show();
                            break;
                        case 5:
                            // #关闭

                            // 关闭了列表框，要重新开始监听人像和IC卡刷卡
                            startTimer();
                            break;
                    }
                }
            });
            listDialog.show();
        }else{
            listDialog.show();
        }

        if(keyCode == KeyEvent.KEYCODE_BACK) {
            if(bindCardDialog == null){
                bindCardDialog = new BindCardDialog(HomeActivity.this, new BindCardDialog.OnKeyDownListener() {
                    @Override
                    public void onRetry() {

                    }

                    @Override
                    public void onExit() {

                    }
                });
            }
            bindCardDialog.setPromet1("请出示住户验证码（15S）");
            bindCardDialog.show();

            countDown = 15;
            cancelCountDownTimer();
            startCountDownTimer();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private Timer closeTimer;
    private int countDown = 15;      // 提示框隐藏倒计时，从15秒开始，一秒调用一次
    private void cancelCountDownTimer() {
        if (closeTimer != null) {
            closeTimer.cancel();
            closeTimer = null;
        }
    }
    private void startCountDownTimer() {
        closeTimer = new Timer();
        closeTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                Message message = new Message();
                message.what = CASE_COUNT_DOWN;
                countDown -- ;
                message.arg1 = countDown;
                handler.sendMessage(message);
            }
        }, 1000,1000);
    }

    /**
     * 接收到扫描出来的二维码数据
     * @param infoQRCode
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveUserInfoQRCode(EventUserInfoQRCode infoQRCode){
        this.infoQRCode = infoQRCode;
        if(countDown >= 0){
            icCardCreateState = 1;  // 修改状态为开卡状态中且监听用户刷卡

            bindCardDialog.setPromet1("1.请出示住户验证码");
            bindCardDialog.setPromet2("2.请将卡片放到读卡器");
            bindCardDialog.setPromet2TextColor(getResources().getColor(R.color.green));
        }
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
    public void showPwdValidation(boolean isSuccess,String result) {
        // 密码正确，开门
        if(isSuccess) {
            if (wrtdevManager != null) {
                int opendoor = wrtdevManager.openDoor();    // 0为开门成功，-1为失败
                if (opendoor == 0) {
                    // 开门成功
                    inputDialog.showPwdError();
                } else {
                    // TODO 开门失败
                }
            }
        }else{
            inputDialog.showPwdError();
        }
    }

    /**
     * 获取到媒体资源文件信息
     * @param infoList
     */
    @Override
    public void showMediaDownload(List<MediaInfo> infoList) {

    }

    private PrometDialog prometDialog;
    @Override
    public void showICardReportResult(boolean isSuccess, String result) {
        icCardCreateState = 0;  // 开卡状态恢复成非监听状态
        if(prometDialog == null){
            prometDialog = new PrometDialog(HomeActivity.this);
        }
        if(isSuccess){
            // 开卡成功，弹出提示框
            prometDialog.setMessage("发卡成功");
            prometDialog.setSuccessIconVisable(true);
        }else{
            prometDialog.setMessage("发卡失败，请按 * 键重试或 # 退出");
            prometDialog.showRegistError();
        }
        prometDialog.show();
    }

}
