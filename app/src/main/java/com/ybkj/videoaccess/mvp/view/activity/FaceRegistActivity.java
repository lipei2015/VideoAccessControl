package com.ybkj.videoaccess.mvp.view.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.google.gson.Gson;
import com.google.zxing.client.android.CaptureActivity;
import com.wrtsz.api.WrtdevManager;
import com.wrtsz.intercom.master.IFaceApi;
import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.app.ConstantSys;
import com.ybkj.videoaccess.mvp.base.BaseActivity;
import com.ybkj.videoaccess.mvp.control.FaceRegistControl;
import com.ybkj.videoaccess.mvp.data.bean.DeviceRecognitionResult;
import com.ybkj.videoaccess.mvp.data.bean.DeviceRegistResult;
import com.ybkj.videoaccess.mvp.data.bean.RegistCheckInfo;
import com.ybkj.videoaccess.mvp.data.bean.RequestDownloadUserFaceBean;
import com.ybkj.videoaccess.mvp.data.bean.RequestGateOpenRecordBean;
import com.ybkj.videoaccess.mvp.data.model.FaceRegistModel;
import com.ybkj.videoaccess.mvp.presenter.FaceRegistPresenter;
import com.ybkj.videoaccess.mvp.view.dialog.ConfirmDialog;
import com.ybkj.videoaccess.mvp.view.dialog.ListDialog;
import com.ybkj.videoaccess.util.DataUtil;
import com.ybkj.videoaccess.util.FileUtil;
import com.ybkj.videoaccess.util.GsonUtils;
import com.ybkj.videoaccess.util.ImageUtil;
import com.ybkj.videoaccess.util.PreferencesUtils;
import com.ybkj.videoaccess.util.TextToSpeechUtil;
import com.ybkj.videoaccess.util.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * 人脸信息绑定注册
 */
public class FaceRegistActivity extends BaseActivity<FaceRegistPresenter, FaceRegistModel> implements FaceRegistControl.IFaceRegistView,SurfaceHolder.Callback {
    private boolean safeToTakePicture = false;
    private Camera mCamera;
    @BindView(R.id.preview) SurfaceView mPreview;
    private SurfaceHolder mHolder;
    private int cameraId = 1;//声明cameraId属性，设备中0为前置摄像头；一般手机0为后置摄像头，1为前置摄像头

    private int widthPixels;
    private int heightPixels;

    private IFaceApi iFaceApi;
    private WrtdevManager wrtdevManager = null;
    private TextToSpeechUtil textToSpeechUtil;
    private String localPath;

    private final int CASE_TAKE_PICTURE = 0;
    private final int CASE_DEAL_PICTURE = 1;
    private final int CASE_COUNT_DOWN = 2;      // 提示框倒计时
    private ConfirmDialog confirmDialog;

    private PreferencesUtils preferencesUtils;
    private String device_id;
    private String pid;
    private String userName;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_face_regist;
    }

    @Override
    protected String setTitle() {
        return null;
    }

    @Override
    protected FaceRegistPresenter createPresenter() {
        return new FaceRegistPresenter();
    }

    @Override
    protected FaceRegistModel createModel() {
        return new FaceRegistModel();
    }

    @Override
    protected void initView() {
        pid = getIntent().getStringExtra("pid");
        Log.e("address",pid);

        mHolder = mPreview.getHolder();
        mHolder.addCallback(this);

//        textToSpeechUtil = new TextToSpeechUtil(this);
        wrtdevManager = (WrtdevManager) getSystemService("wrtsz");

        preferencesUtils = PreferencesUtils.getInstance(ConstantSys.PREFERENCE_USER_NAME);
        device_id = preferencesUtils.getString(ConstantSys.PREFERENCE_DEVICE_ID,null);

        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        widthPixels = outMetrics.widthPixels;
        heightPixels = outMetrics.heightPixels;

        localPath = ConstantSys.REGIST_PATH;
        FileUtil.createDirectory(localPath);

        //TODO 先判断传过来的用户信息是否与本栋楼匹配，匹配才进行拍照人脸注册
        RequestDownloadUserFaceBean bean = new RequestDownloadUserFaceBean();
        bean.setPid("123456");
        bean.setMac(device_id);
        bean.setOptype("1");
        mPresenter.downloadUserFace(bean);
//        startTimer();

        // 实例化远程调用设备SDK服务
//        initAidlService();
    }

    @Override
    public void showCheckRegistResult(RegistCheckInfo registCheckInfo) {
        userName = registCheckInfo.getName();
        if(!registCheckInfo.isValidation_result()) {
            startTimer();
        }else{
            // 提示验证失败
            if (confirmDialog == null) {
                confirmDialog = new ConfirmDialog(FaceRegistActivity.this);
                confirmDialog.setNoTitle(true);
                confirmDialog.setLeftVisiable(false);
                confirmDialog.setMessage("用户未经登记，不能进行登记采集(5S)");
            }
            confirmDialog.show();
            countDown = 5;
            cancelCountDownTimer();
            startCountDownTimer();
        }
    }

    Timer closeTimer;
    private void cancelCountDownTimer() {
        if (closeTimer != null) {
            closeTimer.cancel();
            closeTimer = null;
        }
    }

    private int countDown = 5;      // 提示框隐藏倒计时，从5秒开始，一秒调用一次
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

    private void initAidlService(){
        // 通过Intent指定服务端的服务名称和所在包，与远程Service进行绑定
        //参数与服务器端的action要一致,即"服务器包名.aidl接口文件名"
//        Intent intent = new Intent("com.wrtsz.intercom.master.IFaceApi");
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
                String result = iFaceApi.recognition("skfjskfjsfkd","");
                Log.e("result", "result:"+result);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

    };

    //定义照片保存并显示的方法
    private Camera.PictureCallback mpictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
//            safeToTakePicture = true;
            long now = System.currentTimeMillis();
            String path = localPath+now+".png";
            File tempfile = new File(path);//新建一个文件对象tempfile，并保存在某路径中

            try {
                FileOutputStream fos = new FileOutputStream(tempfile);
                fos.write(data);//将照片放入文件中
                fos.close();//关闭文件

                // 拍照之后继续显示预览界面
                setStartPreview (mCamera, mHolder);

                Message msg = new Message();
                msg.what = CASE_DEAL_PICTURE;
                Bundle bundle = new Bundle();
                bundle.putString("path",tempfile.getAbsolutePath());
                msg.setData(bundle);
                handler.sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CASE_TAKE_PICTURE:
                    // 开始拍照
                    capture(mPreview);
                    break;
                case CASE_DEAL_PICTURE:
                    //TODO 调用设备SDK进行人像注册，若返回人像ID就表示成功，再调用开门记录上传接口，若成功则返回到上一个界面；
                    //TODO 若识别发出语音提示"授权失败，请按 * 键重试或 # 键退出"

                    // 处理拍出来的照片
                    String path = msg.getData().getString("path");
                    String requestResult = null;

                    if(iFaceApi != null){
                        try {
                            DeviceRegistResult deviceRegistResult = GsonUtils.getGson().fromJson(iFaceApi.reg(path,null,userName,0),
                                    DeviceRegistResult.class);
                            if(deviceRegistResult.getRetStr().equals("")){
                                //TODO 注册成功,将人脸数据连同人员标识上传到后台
                            }else{
                                // 注册失败，语音提示
                            }
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case CASE_COUNT_DOWN:
                    int time = msg.arg1;
                    if(time < 0){
                        cancelCountDownTimer();
                        if(confirmDialog != null && confirmDialog.isShowing()){
                            confirmDialog.cancel();
                            finish();
                        }
                    }else{
                        confirmDialog.setMessage("用户未经登记，不能进行登记采集("+time+"S)");
                        confirmDialog.show();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    Timer takePictureTimer;
    private void cancelTimer() {
        if (takePictureTimer != null) {
            takePictureTimer.cancel();
            takePictureTimer = null;
        }
    }

    private void startTimer() {
        cancelTimer();
        takePictureTimer = new Timer();
        takePictureTimer.schedule(new TimerTask() {

            @Override
            public void run() {
//                Log.e("time", "daochu");
                if(safeToTakePicture) {
                    handler.sendEmptyMessage(CASE_TAKE_PICTURE);
                }
            }
        }, 1500);
    }

    /**
     * 定义“拍照”方法
     * @param view
     */
    public void capture(View view) {
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setPictureFormat(ImageFormat.JPEG);//设置照片格式
        parameters.setPreviewSize(widthPixels, heightPixels);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        //摄像头聚焦
        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if (success) {
                    mCamera.takePicture(null, null, mpictureCallback);
                }
            }
        });
    }

    /**
     * activity生命周期在onResume是界面应是显示状态
     */
    @Override
    public void onResume() {
        super.onResume();
        if (mCamera == null) {//如果此时摄像头值仍为空
            mCamera = getCamera();//则通过getCamera()方法开启摄像头
            if (mHolder != null) {
                setStartPreview(mCamera, mHolder);//开启预览界面
            }
        }
    }

    /**
     * activity暂停的时候释放摄像头
     */
    @Override
    public void onPause() {
        super.onPause();
        releaseCamera();
        cancelTimer();
    }

    /**
     * onResume()中提到的开启摄像头的方法
     */
    private Camera getCamera() {
        Camera camera;//声明局部变量camera
        try {
            camera = Camera.open(cameraId);
        }//根据cameraId的设置打开前置摄像头
        catch (Exception e) {
            camera = null;
            e.printStackTrace();
        }
        return camera;
    }

    /**
     * 开启预览界面
     * @param camera
     * @param holder
     */
    private void setStartPreview(Camera camera, SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
            camera.setDisplayOrientation(90);//如果没有这行你看到的预览界面就会是水平的
            camera.startPreview();
            safeToTakePicture = true;

//            capture(mPreview);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 定义释放摄像头的方法
     */
    private void releaseCamera() {
        if (mCamera != null) {//如果摄像头还未释放，则执行下面代码
            mCamera.stopPreview();//1.首先停止预览
            mCamera.setPreviewCallback(null);//2.预览返回值为null
            mCamera.release(); //3.释放摄像头
            mCamera = null;//4.摄像头对象值为null
        }
    }

    /**
     * 定义新建预览界面的方法
     * @param holder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setStartPreview (mCamera, mHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mCamera.stopPreview();//如果预览界面改变，则首先停止预览界面
        setStartPreview(mCamera, mHolder);//调整再重新打开预览界面
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamera();//预览界面销毁则释放相机
    }

    @Override
    public void onDestroy() {
//        unbindService(connection);
        super.onDestroy();
    }

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

        /*if(keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }*/

        return super.onKeyDown(keyCode, event);
    }
}
