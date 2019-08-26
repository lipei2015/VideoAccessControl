package com.ybkj.videoaccess.mvp.view.activity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import com.wrtsz.api.WrtdevManager;
import com.wrtsz.intercom.master.IFaceApi;
import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.app.ConstantSys;
import com.ybkj.videoaccess.mvp.base.BaseActivity;
import com.ybkj.videoaccess.mvp.control.FaceCheckControl;
import com.ybkj.videoaccess.mvp.data.bean.DeviceFaceInfo;
import com.ybkj.videoaccess.mvp.data.bean.DeviceRecognitionResult;
import com.ybkj.videoaccess.mvp.data.bean.RequestGateOpenRecordBean;
import com.ybkj.videoaccess.mvp.data.model.FaceCheckModel;
import com.ybkj.videoaccess.mvp.presenter.FaceCheckPresenter;
import com.ybkj.videoaccess.mvp.view.dialog.PrometDialog;
import com.ybkj.videoaccess.util.DataUtil;
import com.ybkj.videoaccess.util.FileUtil;
import com.ybkj.videoaccess.util.GsonUtils;
import com.ybkj.videoaccess.util.ImageUtil;
import com.ybkj.videoaccess.util.TextToSpeechUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * 人脸识别
 * lp
 */
public class FaceCheckActivity extends BaseActivity<FaceCheckPresenter, FaceCheckModel> implements FaceCheckControl.IFaceCheckView, SurfaceHolder.Callback {
    private boolean safeToTakePicture = false;
    private Camera mCamera;
//    private SurfaceView mPreview;
    @BindView(R.id.preview) SurfaceView mPreview;
    @BindView(R.id.imageview) ImageView imageview;
    private SurfaceHolder mHolder;
    private int cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;//声明cameraId属性，设备中0为前置摄像头；一般手机0为后置摄像头，1为前置摄像头

    private int widthPixels;
    private int heightPixels;

    private PrometDialog prometDialog;
    private WrtdevManager wrtdevManager = null;
    private final int CASE_TAKE_PICTURE = 0;
    private final int CASE_DEAL_PICTURE = 1;
    private final int CASE_COUNT_DOWN = 2;      // 提示框倒计时

    //定义aidl接口变量
    private IFaceApi iFaceApi;
    private TextToSpeechUtil textToSpeechUtil;
    private String localPath;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_face_check;
    }

    @Override
    protected String setTitle() {
        return null;
    }

    @Override
    protected FaceCheckPresenter createPresenter() {
        return new FaceCheckPresenter();
    }

    @Override
    protected FaceCheckModel createModel() {
        return new FaceCheckModel();
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void initView() {
        mHolder = mPreview.getHolder();
        mHolder.addCallback(this);

//        textToSpeechUtil = new TextToSpeechUtil(this);
        wrtdevManager = (WrtdevManager) getSystemService("wrtsz");

        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        widthPixels = outMetrics.widthPixels;
        heightPixels = outMetrics.heightPixels;

        localPath = ConstantSys.CHECK_PATH;
        FileUtil.createDirectory(localPath);

        //点击预览界面聚焦
        startTimer();

        // 实例化远程调用设备SDK服务
        initAidlService();
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
        }
    };

    @Override
    public void onDestroy() {
//        unbindService(connection);
        super.onDestroy();
    }

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

                cancelTimer();

                /*Intent intent = new Intent(FaceCheckActivity.this, ResultActivity.class);//新建信使对象
                intent.putExtra("picpath", tempfile.getAbsolutePath());//打包文件给信使
                startActivity(intent);//打开新的activity，即打开展示照片的布局界面
                FaceCheckActivity.this.finish();//关闭现有界面*/

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

    Timer closeTimer;
    private void cancelCountDownTimer() {
        if (closeTimer != null) {
            closeTimer.cancel();
            closeTimer = null;
        }
    }

    private int count = 3;  // 人脸识别次数
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

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CASE_TAKE_PICTURE:
                    // 开始拍照
                    capture(mPreview);
                    break;
                case CASE_DEAL_PICTURE:
                    //TODO 调用设备SDK进行人像检测，若返回人像ID就表示成功，再调用设备开门接口，再调用开门记录上传接口，若成功则返回到上一个界面
                    // 处理拍出来的照片
                    String path = msg.getData().getString("path");
                    String requestResult = null;
                    try {
                        // 进行远程调用设备人脸识别SDK
                        if(iFaceApi != null) {
                            requestResult = iFaceApi.recognition(ImageUtil.imageToBase64(path), null);
                            Log.e("requestResult",requestResult);
                            DeviceRecognitionResult deviceRecognitionResult = GsonUtils.getGson().fromJson(requestResult, DeviceRecognitionResult.class);
                            Log.e("deviceRecognitionResult",deviceRecognitionResult.getRetStr());

                            if (deviceRecognitionResult != null && deviceRecognitionResult.getRetStr().equals("ok")
                                    && deviceRecognitionResult.getUserInfo() != null
                                    && !TextUtils.isEmpty(deviceRecognitionResult.getPersonId()) ) {
                                // 人脸识别成功，先开门
                                if (wrtdevManager != null) {
//                                    int opendoor = wrtdevManager.openDoor();    // 0为开门成功，-1为失败
                                }
                                if(deviceRecognitionResult.getFaceInfos() != null && deviceRecognitionResult.getFaceInfos().size() > 0){
                                    // 人像抠图
                                    DeviceFaceInfo deviceFaceInfo = deviceRecognitionResult.getFaceInfos().get(0);
                                    Bitmap bitmap = ImageUtil.cutBitmap(path,deviceFaceInfo.getX(),deviceFaceInfo.getY(),
                                            deviceFaceInfo.getWidth(),deviceFaceInfo.getHeight());
                                    imageview.setImageBitmap(bitmap);
                                }

                                //TODO 进行开门上报，这里还需要一个人脸肖像文件base64字符串
                                RequestGateOpenRecordBean requestGateOpenRecordBean = new RequestGateOpenRecordBean();
                                requestGateOpenRecordBean.setPid(deviceRecognitionResult.getPersonId());
                                requestGateOpenRecordBean.setType("1");
                                requestGateOpenRecordBean.setTimestamp(DataUtil.getYMDHMSString(System.currentTimeMillis()));
                                mPresenter.gateOpenRecord(requestGateOpenRecordBean);
                            } else {
                                // 人脸识别，3次失败后弹出提示框“对不起，您不是授权用户（5S）”倒计时隐藏
                                count --;
                                if(count == 0) {
                                    if (prometDialog == null) {
                                        prometDialog = new PrometDialog(FaceCheckActivity.this);
                                        prometDialog.setNoTitle(true);
                                        prometDialog.setMessage("对不起，您不是授权用户(5S)");
                                    }
                                    prometDialog.show();

                                    countDown = 5;
                                    cancelCountDownTimer();
                                    startCountDownTimer();
                                }else{
                                    startTimer();
                                }
                            }
                        }else{
                            count --;
                            Log.e("count",count+"---");
                            if(count == 0) {
                                if (prometDialog == null) {
                                    prometDialog = new PrometDialog(FaceCheckActivity.this);
                                    prometDialog.setNoTitle(true);
                                    prometDialog.setMessage("对不起，您不是授权用户(5S)");
                                }
                                prometDialog.show();
                                countDown = 5;
                                cancelCountDownTimer();
                                startCountDownTimer();
                            }else{
                                startTimer();
                            }
//                            textToSpeechUtil.notifyNewMessage("你好");
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                case CASE_COUNT_DOWN:
                    int time = msg.arg1;
                    if(time < 0){
                        cancelCountDownTimer();
                        if(prometDialog != null && prometDialog.isShowing()){
                            prometDialog.cancel();
                            finish();
                        }
                    }else{
                        prometDialog.setMessage("对不起，您不是授权用户("+time+"S)");
                        prometDialog.show();
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
        }, 2000);
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
    public void showGateOpenRecordResult(boolean isSuccess) {
        if(isSuccess){
            // 上传成功，关闭当前界面
            finish();
        }else{
            // 上传失败，需记录，等有网络时再上传
        }
    }

    /**
     * 上传开门记录失败，需记录下来，等有网络的时候再重新上传
     * @param requestGateOpenRecordBean
     */
    @Override
    public void gateOpenRecordFail(RequestGateOpenRecordBean requestGateOpenRecordBean) {
        // TODO
    }
}
