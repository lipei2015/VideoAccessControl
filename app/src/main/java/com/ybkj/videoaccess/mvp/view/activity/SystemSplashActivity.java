package com.ybkj.videoaccess.mvp.view.activity;

import static com.ybkj.videoaccess.app.ConstantSys.ConfigKey.PREFERENCE_SYSTEM_CONFIG;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.Gson;
//import com.wrtsz.api.IWrtdevManager;
//import com.wrtsz.api.WrtdevManager;
import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.app.ConstantSys;
import com.ybkj.videoaccess.mvp.base.BaseActivity;
import com.ybkj.videoaccess.mvp.control.SystemSplashControl;
import com.ybkj.videoaccess.mvp.data.bean.RequestRemoteOpen;
import com.ybkj.videoaccess.mvp.data.model.ConfigMode;
import com.ybkj.videoaccess.mvp.presenter.SystemSplashPresenter;
import com.ybkj.videoaccess.util.AnimationUtil;
import com.ybkj.videoaccess.util.ImageUtil;
import com.ybkj.videoaccess.util.PreferencesUtils;
import com.ybkj.videoaccess.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 启动页
 * <p>显示启动页透明度渐变动画</p>
 * <p>动画结束后获取彩种列表，失败退出APP</p>
 * <p>判断进入主页还是欢迎页</p>
 * <p>
 * Created by HH on 2018/1/19.
 */
public class SystemSplashActivity extends BaseActivity<SystemSplashPresenter, ConfigMode> implements SystemSplashControl.ISystemSplashView {
    @BindView(R.id.splashBg)
    ImageView img;
    @BindView(R.id.indicatorGroup)
    RadioGroup indicatorGroup;

    private static final int ANIMATION_TIME = 1000;  //渐变动画时间
    private static final int[] IMAGE_RESOURCE = new int[]{R.mipmap.guide_page01, R.mipmap.guide_page02, R.mipmap.guide_page03};  //滑动图片资源
    private ViewPager viewPager;
    private SystemSplashViewPagerAdapter adapter;
    private boolean misScrolled;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected String setTitle() {
        return null;
    }

    @Override
    protected SystemSplashPresenter createPresenter() {
        return new SystemSplashPresenter();
    }

    @Override
    protected ConfigMode createModel() {
        return new ConfigMode();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTranslucentStatusBar(true);
        super.onCreate(savedInstanceState);
        ViewUtil.isFull(this, true);
        setSwipeBackEnable(false);

        RequestRemoteOpen open = new RequestRemoteOpen("00000001004","123456","1","1");
        mPresenter.remoteOpenDebug(open);

        /*WrtdevManager wrtdevManager = new WrtdevManager(new IWrtdevManager() {
            @Override
            public IBinder asBinder() {
                return null;
            }

            *//**
             * 返回微波检测状态：1为有人，0为无人，-1为错误
             * @return
             * @throws RemoteException
             *//*
            @Override
            public int getMicroWaveState() throws RemoteException {
                return 0;
            }

            @Override
            public byte[] getIcCardNo() throws RemoteException {
                return new byte[0];
            }

            @Override
            public int openLed(int i) throws RemoteException {
                return 0;
            }

            @Override
            public int openDoor() throws RemoteException {
                return 0;
            }
        }, new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        });*/
    }

    @Override
    protected void initView() {
        mPresenter.start(null, null);
        //开启启动页透明度渐变动画
        img.startAnimation(new AnimationUtil(() -> {
            ViewUtil.isFull(this, false);
            mPresenter.endAnimation();
        }).getAlphaAnimation(0.7f, 1.0f, ANIMATION_TIME));
    }

    @Override
    public void onCheckShowFinish(boolean showWelcome) {
        //进入欢迎页还是启动页
        if (showWelcome) {
            showViewPager();
        } else {
            gotoMain();
        }
    }

    @Override
    public void gotoMain() {
        startActivity(new Intent(this, MainActivity.class));
        new Handler().postDelayed(() -> onFinishActivity(), 1000);
    }

    private void showViewPager() {
        img.setVisibility(View.GONE);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setVisibility(View.VISIBLE);

        indicatorGroup.setVisibility(View.VISIBLE);
        createRadioButton();

        adapter = new SystemSplashViewPagerAdapter(createAdapterView());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                indicatorGroup.check(indicatorGroup.getChildAt(position).getId());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        misScrolled = false;
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        misScrolled = true;
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1 && !misScrolled) {
                            // 设置不再显示欢迎页
                            PreferencesUtils.getInstance(PREFERENCE_SYSTEM_CONFIG).putBoolean(ConstantSys.PREFERENCE_SHOW_WELCOME, false);
                            gotoMain();
                        }
                        misScrolled = true;
                        break;
                }
            }
        });
    }

    /**
     * 创建滑动图片View
     *
     * @return
     */
    private List<View> createAdapterView() {
        List<View> viewList = new ArrayList<>();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        for (int imageResource : IMAGE_RESOURCE) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(layoutParams);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ImageUtil.loadImg(this, imageView, imageResource);
            viewList.add(imageView);
        }
        return viewList;
    }

    /**
     * 创建RadioButton
     */
    private void createRadioButton() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < IMAGE_RESOURCE.length; i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setLayoutParams(layoutParams);
            radioButton.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
            radioButton.setPadding(10, 10, 10, 10);
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.splash_tab_indicator_img_selector);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            radioButton.setCompoundDrawables(drawable, null, null, null);
            indicatorGroup.addView(radioButton);
        }
        //默认选择第0条
        indicatorGroup.check(indicatorGroup.getChildAt(0).getId());
    }

    /**
     * 图片PageView Adapter
     */
    public class SystemSplashViewPagerAdapter extends PagerAdapter {
        private List<View> mListViews;

        public SystemSplashViewPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //删除页卡
            container.removeView(mListViews.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //添加页卡
            container.addView(mListViews.get(position), 0);
            return mListViews.get(position);
        }

        @Override
        public int getCount() {
            return mListViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }
}
