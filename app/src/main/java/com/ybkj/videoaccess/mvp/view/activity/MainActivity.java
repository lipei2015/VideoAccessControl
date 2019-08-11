package com.ybkj.videoaccess.mvp.view.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.app.ConstantApi;
import com.ybkj.videoaccess.eventbus.EventBusEmpty;
import com.ybkj.videoaccess.mvp.base.BaseFragmentActivity;
import com.ybkj.videoaccess.mvp.view.fragment.HomeTab1Fragment;
import com.ybkj.videoaccess.mvp.view.fragment.HomeTab3Fragment;
import com.ybkj.videoaccess.mvp.view.fragment.HomeTab2Fragment;
import com.ybkj.videoaccess.mvp.view.fragment.HomeTab4Fragment;
import com.ybkj.videoaccess.util.MyDeviceInfo;
import com.ybkj.videoaccess.util.ToastUtil;
import com.ybkj.videoaccess.util.UpgradeUtil;
import com.ybkj.videoaccess.util.ViewUtil;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.android.service.MqttService;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * 主页FragmentActivity
 * <p>
 * Created by HH on 2017/2/9
 */
public class MainActivity extends BaseFragmentActivity {
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;

    private UpgradeUtil upgradeUtil;

    private String TAG = "MainActivity";
    private MqttAndroidClient mqttAndroidClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //取消返回按钮，取消动画，取消滑动返回手势
        setShowBackBtn(false);
        super.onCreate(savedInstanceState);
        setSwipeBackEnable(false);
    }

    @Override
    protected int getFragmentViewId() {
        return R.id.frameLayout;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected String setTitle() {
        return null;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        setmRadioGroup(radioGroup);
        addFragment(new HomeTab1Fragment());
        addFragment(new HomeTab2Fragment());
        addFragment(new HomeTab3Fragment());
        addFragment(new HomeTab4Fragment());
        showFragment();

        upgradeUtil = new UpgradeUtil(this);
        checkPermission();

//        initMqtt();
    }

    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public void initMqtt() {
//        Intent serviceIntent = new Intent(MainActivity.this,MqttService.class);
//        startService(serviceIntent);

//        mqttAndroidClient = new MqttAndroidClient(getApplicationContext(), ConstantApi.IP, "00000001004");

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        String imei = telephonyManager.getDeviceId();*/

//        mqttAndroidClient = new MqttAndroidClient(getApplicationContext(), "tcp://132.232.146.67:8989", "00000001004");
//        mqttAndroidClient = new MqttAndroidClient(getApplicationContext(), "tcp://132.232.146.67:8989", imei);
        mqttAndroidClient = new MqttAndroidClient(getApplicationContext(), "tcp://132.232.146.67:8989",
                getAndroidId(getApplicationContext()));
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();

        // 配置MQTT连接
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);
        mqttConnectOptions.setUserName(null);
        mqttConnectOptions.setPassword(null);
        mqttConnectOptions.setConnectionTimeout(10);  //超时时间
        mqttConnectOptions.setKeepAliveInterval(15); //心跳时间,单位秒
        mqttConnectOptions.setAutomaticReconnect(true);//自动重连

        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                Log.i(TAG, "reconnect ---> " + reconnect + "       serverURI--->" + serverURI);
                subscribeToTopic();
            }

            @Override
            public void connectionLost(Throwable cause) {
                Log.i(TAG, "cause ---> " + cause);
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.i(TAG, "messageArrived topic ---> " + topic + "       message--->" + message);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                Log.i(TAG, "token ---> " + token);
            }
        });

        try {
            mqttAndroidClient.connect(mqttConnectOptions);
            Log.e(TAG, "MQTT start connect");
        } catch (MqttException e) {
            e.printStackTrace();
            Log.e(TAG, "MQTT connect error");
        }
    }

//    final String subscriptionTopic = "exampleAndroidTopic";
    final String subscriptionTopic = "remoteOpenDebug";

    private void subscribeToTopic() {
        try {
            mqttAndroidClient.subscribe(subscriptionTopic, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.e(TAG, "onFailure ---> " + asyncActionToken);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.e(TAG, "onFailure ---> " + exception);
                }
            });
        } catch (MqttException e) {
            Log.e(TAG, "subscribeToTopic is error");
            e.printStackTrace();
        }
    }

    private void checkPermission() {
        requestPermission(1, Manifest.permission.WRITE_EXTERNAL_STORAGE, new OnRequestPermissionListener() {
            @Override
            public void onPermissionSuccess(int requestId) {
                upgradeUtil.checkUpgrade();
            }

            @Override
            public void onPermissionFailure(int requestId) {
                ToastUtil.showMsg(getString(R.string.promet_no_permission_promet));
            }
        });
    }

    @Override
    public void onChecked(int checkId) {
        super.onChecked(checkId);
    }

    /**
     * 再按一次返回键则退出应用程序
     * <p>
     * 超过两秒则擦除第一次操作，两秒内才退出应用程序
     */
    private boolean mBackKeyPressed;

    /*@Override
    public void onBackPressed() {
        if (!mBackKeyPressed) {
            ToastUtil.showMsg(getString(R.string.exit_promet));
            mBackKeyPressed = true;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    mBackKeyPressed = false;
                }
            }, 2000);
        } else {
            onFinishActivity();
        }
    }*/

    /**
     * 跳转显示对应Fragment
     *
     * @param navFragmentEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShowFragment(EventBusEmpty.NavFragmentEvent navFragmentEvent) {
        Bundle bundle = navFragmentEvent.bundle;
        int position = bundle.getInt("position");
        jump(position);
    }

    @Override
    public void onDestroy() {
        upgradeUtil.destroy();
        EventBus.getDefault().unregister(this);
        ViewUtil.fixInputMethodManagerLeak(this);  //防止输入法内存溢出【输入法内部Bug】
        super.onDestroy();
    }

    /**
     * 监听号码输入
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        ToastUtil.showMsg(keyCode+"--");
        if(keyCode != KeyEvent.KEYCODE_BACK) {
            switch (keyCode){

                /*case KEYCODE_0:

                    break;*/
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
