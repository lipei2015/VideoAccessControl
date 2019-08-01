package com.ybkj.videoaccess.mvp.base;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.app.ActivityManager;
import com.ybkj.videoaccess.app.MyApp;
import com.ybkj.videoaccess.mvp.base.mvpBase.MvpBaseModel;
import com.ybkj.videoaccess.mvp.base.mvpBase.MvpBasePresenter;
import com.ybkj.videoaccess.mvp.base.mvpBase.MvpBaseView;
import com.ybkj.videoaccess.mvp.view.dialog.LoadingDialog;
import com.ybkj.videoaccess.util.CommonUtil;
import com.ybkj.videoaccess.util.ViewUtil;
import com.ybkj.videoaccess.util.http.HttpUtil;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * 自定义基类Activity，所有Activity均继承该类
 * <p>
 * Created by HH on 2016/8/22.
 */
public abstract class BaseActivity<P extends MvpBasePresenter, M extends MvpBaseModel> extends SwipeBackToolbarActivity {
    @Nullable
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Nullable
    @BindView(R.id.titleText)
    TextView titleTv;
    private LoadingDialog dialog;
    private boolean isShowBackBtn = true;
    protected CompositeSubscription mCompositeSubscription;
    public P mPresenter;
    public M mModel;

    protected abstract int setLayoutId();

    protected abstract String setTitle();

    protected abstract void initView();

    /**
     * 设置是否显示返回按钮，默认显示
     *
     * @param isShowBackBtn
     */
    protected void setShowBackBtn(boolean isShowBackBtn) {
        this.isShowBackBtn = isShowBackBtn;
    }

    protected void initToolbar() {
        if (toolbar != null) {
            if (titleTv != null) {
                toolbar.setTitle("");
                titleTv.setText(setTitle());
            }
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.mipmap.iconfont_jiantou));
                getSupportActionBar().setDisplayHomeAsUpEnabled(isShowBackBtn);
            }
            toolbar.setNavigationOnClickListener(v -> onFinishActivity());
        }
    }

    protected Toolbar getToolbar() {
        return toolbar;
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        if (toolbar != null) {
            if (titleTv != null) {
                titleTv.setText(title);
            } else {
                toolbar.setTitle(title);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(setLayoutId());
        ButterKnife.bind(this);
        setTitle();
        initToolbar();

        //设置为屏幕中间可以滑动返回
        getSwipeBackLayout().setEdgeSize(ViewUtil.getScreenWidth(this) / 2);

        mPresenter = createPresenter();
        mModel = createModel();
        if (this instanceof MvpBaseView && mPresenter != null) mPresenter.attachVM(this, mModel);
        initView();
        ActivityManager.getInstance().pushActivity(this);

        //全局使用沉浸式状态栏
        ViewUtil.TranslucentStatusBar(this);
    }

    /**
     * 钩子方法，子类覆盖
     *
     * @return
     */
    protected P createPresenter() {
        return null;
    }

    /**
     * 钩子方法，子类覆盖
     *
     * @return
     */
    protected M createModel() {
        return null;
    }

    /**
     * 调用该方法销毁Activity
     */
    protected void onFinishActivity() {
        ActivityManager.getInstance().finishActivity(this);
    }

    @Override
    public void finish() {
        ActivityManager.getInstance().pupActivity(this);
        super.finish();
    }

    /**
     * 设置状态栏是否透明
     *
     * @param isTranslucent 是否透明（默认不透明）
     */
    protected void setTranslucentStatusBar(boolean isTranslucent) {
        if (isTranslucent) {
            //开启状态栏透明
            ViewUtil.TranslucentStatusBar(this);
        }
    }

    @Override
    public void onDestroy() {
        // 单击返回后取消该等待框，同时取消该次网络请求
        HttpUtil.getInstance().unSubscribed();
        if (mPresenter != null) mPresenter.detachVM();

        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }

        super.onDestroy();
    }

    public void hideWaitDialog() {
        if (null != dialog) {
            dialog.cancel();
        }
    }

    public void showWaitDialogs(int resId, boolean isCancel) {
        showWaitDialogs(getString(resId), isCancel);
    }

    public void showWaitDialogs(String info, boolean isTouchCancel) {
        if (!CommonUtil.isNetwork(this)) {
            // 没有网络连接
            return;
        }

        if (null == dialog) {
            dialog = new LoadingDialog(this, info);
            dialog.setCanceledOnTouchOutside(isTouchCancel);
            dialog.setOnCancelListener(dialogInterface -> {
                if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
                    mCompositeSubscription.unsubscribe();
                }
            });
        }

        dialog.setText(info);
        dialog.show();
    }

    public void setDialogsIsCancel(boolean isCancel) {
        if (null != dialog) {
            dialog.setCancelable(isCancel);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (MyApp.IS_RELEASE) {
            MobclickAgent.onResume(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (MyApp.IS_RELEASE) {
            MobclickAgent.onPause(this);
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    /**
     * @param requestId  请求授权的Id，唯一即可
     * @param permission 请求的授权
     **/
    protected void requestPermission(int requestId, String permission, OnRequestPermissionListener onRequestPermissionListener) {
        this.onRequestPermissionListener = onRequestPermissionListener;

        if (Build.VERSION.SDK_INT >= 23) {
            //检查是否拥有权限
            int checkPermission = ContextCompat.checkSelfPermission(getApplicationContext(), permission);
            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                //弹出对话框请求授权
                ActivityCompat.requestPermissions(this, new String[]{permission}, requestId);
                return;
            } else {
                onRequestPermissionListener.onPermissionSuccess(requestId);
            }
        } else {
            onRequestPermissionListener.onPermissionSuccess(requestId);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (onRequestPermissionListener == null || grantResults == null || grantResults.length == 0) {
            return;
        }

        if (grantResults.length < 0) {
            return;
        }

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            onRequestPermissionListener.onPermissionSuccess(requestCode);
        } else {
            onRequestPermissionListener.onPermissionFailure(requestCode);
        }
    }

    /**
     * 全选请求操作接口
     */
    protected OnRequestPermissionListener onRequestPermissionListener;

    public interface OnRequestPermissionListener {
        /**
         * 权限获取成功
         *
         * @param requestId
         */
        void onPermissionSuccess(int requestId);

        /**
         * 权限获取失败
         *
         * @param requestId
         */
        void onPermissionFailure(int requestId);
    }

    public void addSubscription(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    /**
     * 通过反射修改 MenuItem item颜色
     */
    public void setActionBarText() {
        try {
            final LayoutInflater inflater = getLayoutInflater();
            Field field = LayoutInflater.class.getDeclaredField("mFactorySet");
            field.setAccessible(true);
            field.setBoolean(inflater, false);
            LayoutInflaterCompat.setFactory(inflater, new LayoutInflaterFactory() {
                @Override
                public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                    if (name.equalsIgnoreCase("android.support.v7.view.menu.IconMenuItemView")
                            || name.equalsIgnoreCase("android.support.v7.view.menu.ActionMenuItemView")) {
                        final View view;
                        try {
                            view = inflater.createView(name, null, attrs);
                            if (view instanceof TextView)
                                ((TextView) view).setTextColor(getResources().getColor(R.color.white));
                            return view;
                        } catch (ClassNotFoundException | InflateException e) {
                            e.printStackTrace();
                        }
                    }
                    return null;
                }
            });
        } catch (Exception e) {

        }

    }
}

