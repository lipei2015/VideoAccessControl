package com.ybkj.videoaccess.mvp.view.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.eventbus.EventBusEmpty;
import com.ybkj.videoaccess.mvp.base.BaseFragmentActivity;
import com.ybkj.videoaccess.mvp.view.fragment.HomeTab1Fragment;
import com.ybkj.videoaccess.mvp.view.fragment.HomeTab3Fragment;
import com.ybkj.videoaccess.mvp.view.fragment.HomeTab2Fragment;
import com.ybkj.videoaccess.mvp.view.fragment.HomeTab4Fragment;
import com.ybkj.videoaccess.util.ToastUtil;
import com.ybkj.videoaccess.util.UpgradeUtil;
import com.ybkj.videoaccess.util.ViewUtil;

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

    }

    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
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
