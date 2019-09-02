package com.ybkj.videoaccess.util;

import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.google.zxing.client.android.util.QRRecognizeHelper;
import com.ybkj.videoaccess.app.ConstantSys;
import com.ybkj.videoaccess.eventbus.EventUserInfoQRCode;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class HomeSurfaceViewUtil implements SurfaceHolder.Callback{
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private Camera mCamera;
    private boolean safeToTakePicture = false;
//    private int cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;//声明cameraId属性，设备中0为前置摄像头；一般手机0为后置摄像头，1为前置摄像头
    private int cameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;//声明cameraId属性，设备中0为前置摄像头；一般手机0为后置摄像头，1为前置摄像头

    private final int CASE_TAKE_PICTURE = 0;
    private final int CASE_DEAL_PICTURE = 1;
    private final int CASE_COUNT_DOWN = 2;      // 提示框倒计时

    private int widthPixels;
    private int heightPixels;

    public HomeSurfaceViewUtil(SurfaceView surfaceView,int widthPixels, int heightPixels){
        this.surfaceHolder = surfaceView.getHolder();
        this.surfaceView = surfaceView;
        this.widthPixels = widthPixels;
        this.heightPixels = heightPixels;
        surfaceHolder.addCallback(this);
    }

    //定义照片保存并显示的方法
    private Camera.PictureCallback mpictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            long now = System.currentTimeMillis();
            String path = ConstantSys.QRCODE_PATH +DataUtil.getYMDHMSString(now)+".png";
            File tempfile = new File(path);//新建一个文件对象tempfile，并保存在某路径中

            try {
                FileOutputStream fos = new FileOutputStream(tempfile);
                fos.write(data);//将照片放入文件中
                fos.close();//关闭文件

                cancelTimer();

                // 拍照之后继续显示预览界面
                setStartPreview (mCamera, surfaceHolder);

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

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CASE_TAKE_PICTURE:
                    // 开始拍照
                    capture(surfaceView);
                    break;
                case CASE_DEAL_PICTURE:
                    String path = msg.getData().getString("path");
                    String result = QRRecognizeHelper.getReult(BitmapFactory.decodeFile(path));
                    Log.e("HomeSurfaceViewUtil",result+"  " + widthPixels + "  "+heightPixels);

                    EventBus.getDefault().post(new EventUserInfoQRCode(result));
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public void initCamare(){
        if (mCamera == null) {//如果此时摄像头值仍为空
            mCamera = getCamera();//则通过getCamera()方法开启摄像头
            if (surfaceHolder != null) {
                setStartPreview(mCamera, surfaceHolder);//开启预览界面
            }
        }
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

    Timer takePictureTimer;
    private void cancelTimer() {
        if (takePictureTimer != null) {
            takePictureTimer.cancel();
            takePictureTimer = null;
        }
    }

    private void startTimer() {
        Log.e("startTimer",safeToTakePicture+"");
        cancelTimer();
        takePictureTimer = new Timer();
        takePictureTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                if(safeToTakePicture) {
                    handler.sendEmptyMessage(CASE_TAKE_PICTURE);
                }
            }
        }, 2000);
    }

    /**
     * 拍照
     */
    public void takePhoto(){
        startTimer();
        /*if(safeToTakePicture) {
            capture(surfaceView);
        }*/
    }

    /**
     * 定义“拍照”方法
     * @param view
     */
    public void capture(View view) {
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setPictureFormat(ImageFormat.JPEG);//设置照片格式
        parameters.setJpegQuality(100);
        parameters.setPreviewSize(widthPixels, heightPixels);
        parameters.setPictureSize(heightPixels, widthPixels);
//        parameters.setPictureSize(widthPixels, heightPixels);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
//        mCamera.setParameters(parameters);
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
     * 定义新建预览界面的方法
     * @param holder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setStartPreview (mCamera, surfaceHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mCamera != null){
            mCamera.stopPreview();//如果预览界面改变，则首先停止预览界面
            setStartPreview(mCamera, surfaceHolder);//调整再重新打开预览界面
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamera();//预览界面销毁则释放相机
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
    public void releaseCamera() {
        if (mCamera != null) {//如果摄像头还未释放，则执行下面代码
            mCamera.stopPreview();//1.首先停止预览
            mCamera.setPreviewCallback(null);//2.预览返回值为null
            mCamera.release(); //3.释放摄像头
            mCamera = null;//4.摄像头对象值为null
        }
    }
}
