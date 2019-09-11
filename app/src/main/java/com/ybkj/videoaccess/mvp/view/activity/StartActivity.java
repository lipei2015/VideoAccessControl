package com.ybkj.videoaccess.mvp.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.zxing.client.android.util.QRCodeUtil;
import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.app.ConstantSys;
import com.ybkj.videoaccess.app.DeviceApi;
import com.ybkj.videoaccess.mvp.base.BaseActivity;
import com.ybkj.videoaccess.mvp.control.StartControl;
import com.ybkj.videoaccess.mvp.data.bean.FullDataInfo;
import com.ybkj.videoaccess.mvp.data.bean.InitInfoBean;
import com.ybkj.videoaccess.mvp.data.bean.RequestFullDataLoadBean;
import com.ybkj.videoaccess.mvp.data.bean.RequestResourcesBean;
import com.ybkj.videoaccess.mvp.data.model.StartModel;
import com.ybkj.videoaccess.mvp.presenter.StartPresenter;
import com.ybkj.videoaccess.util.AudioMngHelper;
import com.ybkj.videoaccess.util.DataUtil;
import com.ybkj.videoaccess.util.FileUtil;
import com.ybkj.videoaccess.util.MyDeviceInfo;
import com.ybkj.videoaccess.util.PreferencesUtils;
import com.ybkj.videoaccess.util.ToastUtil;
import com.ybkj.videoaccess.util.VedioDownLoadAsyncTask;
import com.ybkj.videoaccess.util.http.HttpErrorException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class StartActivity extends BaseActivity<StartPresenter, StartModel> implements StartControl.IStartView{
    @BindView(R.id.imgCode) ImageView imgCode;

    private PreferencesUtils preferencesUtils;
    private String device_id;

    private String testUrl = "http://223.82.247.121/sh.yinyuetai.com/uploads/videos/common/74B4015B716A4A2BEC4D61B0ABB366BB.mp4";
    private String testUrl1 = "http://223.82.247.122/hd.yinyuetai.com/uploads/videos/common/2FE0016B08002492A8638CFAC9AABF2E.mp4?sc=c28ee11f084edf50";
    private String testUrl2 = "http://223.82.247.122/hd.yinyuetai.com/uploads/videos/common/ECEC016B8895E4A044C6B72FCF773B25.mp4?sc=a7d5f7c038c05eb8";

    @Override
    protected int setLayoutId() {
        return R.layout.activity_start;
    }

    @Override
    protected String setTitle() {
        return null;
    }

    @Override
    protected StartModel createModel() {
        return new StartModel();
    }

    @Override
    protected StartPresenter createPresenter() {
        return new StartPresenter();
    }

    @Override
    protected void initView() {
        preferencesUtils = PreferencesUtils.getInstance(ConstantSys.PREFERENCE_USER_NAME);

        device_id = preferencesUtils.getString(ConstantSys.PREFERENCE_DEVICE_ID,null);
        if(TextUtils.isEmpty(device_id)){
            device_id = MyDeviceInfo.getDeviceId(this);
            preferencesUtils.putString(ConstantSys.PREFERENCE_DEVICE_ID,device_id);
        }
//        String mac = MyDeviceInfo.getMacDefault(this);
//        String mac = MyDeviceInfo.getDeviceId(this);
//        ToastUtil.showMsg(mac);
        InitInfoBean initInfoBean = new InitInfoBean(device_id);
        imgCode.setImageBitmap(QRCodeUtil.createQRImage(new Gson().toJson(initInfoBean),
                (int)getResources().getDimension(R.dimen.start_code_size),(int)getResources().getDimension(R.dimen.start_code_size)));

//        String ip = "http://"+CommonUtil.getIPAddress(this);
//        String ip = "http://192.168.1.23";
//        ToastUtil.showMsg(DeviceApi.getInstance().getIP()+"  ");
        Log.e("ip",DeviceApi.getInstance().getIP()+"   "+device_id);
//        DeviceApi.getInstance().setIP(ip);


        boolean downloaded = preferencesUtils.getBoolean(ConstantSys.PREFERENCE_DOWNLOADED_DATA,false);

        if(!downloaded){
            startActivity(new Intent(this, HomeActivity.class));
            new Handler().postDelayed(() -> onFinishActivity(), 1000);
        }else{
            // 开始初始化获取资源数据
            RequestFullDataLoadBean requestFullDataLoadBean = new RequestFullDataLoadBean();
            requestFullDataLoadBean.setMemo("");
            requestFullDataLoadBean.setType("FULL_LOAD");
            requestFullDataLoadBean.setMjseq(device_id);
            requestFullDataLoadBean.setTimestamp(DataUtil.getYMDHMSString(System.currentTimeMillis()));
            mPresenter.fullDataLoad(requestFullDataLoadBean);
        }

//        ViewUtil.dip2px(this,180)showFullDataLoad

//        startActivity(new Intent(this, MainActivity.class));
//        new Handler().postDelayed(() -> onFinishActivity(), 1000);

        // 先检测本地有无下载的数据，有的话直接进入主界面展示，没有的话直接每隔2秒拉取数据数据，
        // 没有数据说明没有绑定成功，拉取到了数据就说明绑定成功，开始下载数据并且进入主页播放日常视频

        AudioMngHelper audioMngHelper = new AudioMngHelper(this);
        audioMngHelper.setAudioType(AudioMngHelper.TYPE_MUSIC);
//        audioMngHelper.setVoice100(60);

        FileUtil.createDirectory(ConstantSys.HOME_VEDIO_PATH);
        List<String> urlList = new ArrayList<>();
        urlList.add(testUrl);
        urlList.add(testUrl1);
        urlList.add(testUrl2);
        VedioDownLoadAsyncTask vedioDownLoadAsyncTask = new VedioDownLoadAsyncTask(this, urlList, new VedioDownLoadAsyncTask.IOnVedioDownLoadFinish() {
            @Override
            public void onFinished() {
                // 下载完成，开始播放媒体文件
                startActivity(new Intent(StartActivity.this, HomeActivity.class));
                new Handler().postDelayed(() -> onFinishActivity(), 1000);
            }
        });
//        vedioDownLoadAsyncTask.execute(500);
    }

    @Override
    public void showResources(FullDataInfo dataInfo) {

    }

    @Override
    public void showFullDataLoad(FullDataInfo dataInfo) {
        RequestResourcesBean requestResourcesBean = new RequestResourcesBean();
        requestResourcesBean.setMjseq(device_id);
        requestResourcesBean.setToken(dataInfo.getTOKEN());
        requestResourcesBean.setRes_type("4");
        mPresenter.resources(requestResourcesBean);
    }

    @Override
    public void showFullDataFail(HttpErrorException errorException) {
        if(errorException.getCode() == ConstantSys.HttpStatus.STATUS_NETWORK_EXCEPTION){
            // 网络错误
            ToastUtil.showMsg("网络连接错误，请检查您的网络");
        }
    }
}
