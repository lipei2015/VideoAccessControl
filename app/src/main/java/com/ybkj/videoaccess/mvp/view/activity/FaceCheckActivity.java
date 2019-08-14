package com.ybkj.videoaccess.mvp.view.activity;

import android.content.Intent;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.wrtsz.api.WrtdevManager;
import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.mvp.base.BaseActivity;
import com.ybkj.videoaccess.mvp.control.FaceCheckControl;
import com.ybkj.videoaccess.mvp.data.bean.RequestGateOpenRecordBean;
import com.ybkj.videoaccess.mvp.data.model.FaceCheckModel;
import com.ybkj.videoaccess.mvp.presenter.FaceCheckPresenter;
import com.ybkj.videoaccess.util.DataUtil;
import com.ybkj.videoaccess.util.ImageUtil;

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
    private SurfaceHolder mHolder;
    private int cameraId = 0;//声明cameraId属性，设备中0为前置摄像头；一般手机0为后置摄像头，1为前置摄像头

    private int widthPixels;
    private int heightPixels;

    private WrtdevManager wrtdevManager = null;

    @Override
    protected int setLayoutId() {
        return R.layout.custom;
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

    @Override
    protected void initView() {
        mHolder = mPreview.getHolder();
        mHolder.addCallback(this);

        wrtdevManager = (WrtdevManager) getSystemService("wrtsz");

        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        widthPixels = outMetrics.widthPixels;
        heightPixels = outMetrics.heightPixels;

        //点击预览界面聚焦
        /*mPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.autoFocus(null);
            }
        });*/
//        mCamera.autoFocus(null);

        startTimer();
        mPresenter.recognition("45465456456");
//        mPresenter.gateOpenRecord(new RequestGateOpenRecordBean());
    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom);
        mPreview = (SurfaceView)findViewById(R.id.preview);//初始化预览界面
        mHolder = mPreview.getHolder();
        mHolder.addCallback(this);

        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        widthPixels = outMetrics.widthPixels;
        heightPixels = outMetrics.heightPixels;

        //点击预览界面聚焦
        *//*mPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.autoFocus(null);
            }
        });*//*
//        mCamera.autoFocus(null);

        startTimer();
    }*/

    //定义照片保存并显示的方法
    private Camera.PictureCallback mpictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
//            safeToTakePicture = true;
            long now = System.currentTimeMillis();
            String path = "/sdcard/emp"+now+".png";
            File tempfile = new File(path);//新建一个文件对象tempfile，并保存在某路径中

            try {
                FileOutputStream fos = new FileOutputStream(tempfile);
                fos.write(data);//将照片放入文件中
                fos.close();//关闭文件

                Intent intent = new Intent(FaceCheckActivity.this, ResultActivity.class);//新建信使对象
                intent.putExtra("picpath", tempfile.getAbsolutePath());//打包文件给信使
                startActivity(intent);//打开新的activity，即打开展示照片的布局界面

                FaceCheckActivity.this.finish();//关闭现有界面

                //TODO 调用设备SDK进行人像检测，若返回人像ID就表示成功，再调用设备开门接口，再调用开门记录上传接口，若成功则返回到上一个界面
                ImageUtil.imageToBase64(tempfile.getAbsolutePath());

                RequestGateOpenRecordBean requestGateOpenRecordBean = new RequestGateOpenRecordBean();
                requestGateOpenRecordBean.setPid("");
                requestGateOpenRecordBean.setType("1");
                requestGateOpenRecordBean.setTimestamp(DataUtil.getYMDHMSString(System.currentTimeMillis()));
                mPresenter.gateOpenRecord(requestGateOpenRecordBean);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    Timer mTimeoutTimer;
    private void cancelTimer() {
        if (mTimeoutTimer != null) {
            mTimeoutTimer.cancel();
            mTimeoutTimer = null;
        }
    }

    private void startTimer() {
        cancelTimer();
        mTimeoutTimer = new Timer();
        mTimeoutTimer.schedule(new TimerTask() {

            @Override
            public void run() {
//                Log.e("time", "daochu");
                if(safeToTakePicture) {
                    mHandler.sendEmptyMessage(0);
                }
            }
        }, 1000);
    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            capture(mPreview);
            super.handleMessage(msg);
        }
    };

    //定义“拍照”方法
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

    //activity生命周期在onResume是界面应是显示状态
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

    //activity暂停的时候释放摄像头
    @Override
    public void onPause() {
        super.onPause();
        releaseCamera();
        cancelTimer();
    }

    //onResume()中提到的开启摄像头的方法
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

    //开启预览界面
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

    //定义释放摄像头的方法
    private void releaseCamera() {
        if (mCamera != null) {//如果摄像头还未释放，则执行下面代码
            mCamera.stopPreview();//1.首先停止预览
            mCamera.setPreviewCallback(null);//2.预览返回值为null
            mCamera.release(); //3.释放摄像头
            mCamera = null;//4.摄像头对象值为null
        }
    }

    //定义新建预览界面的方法
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setStartPreview(mCamera, mHolder);
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
    public void showGateOpenRecordResult(String result) {

    }
}
