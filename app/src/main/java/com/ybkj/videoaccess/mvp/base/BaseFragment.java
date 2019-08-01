package com.ybkj.videoaccess.mvp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.mvp.base.mvpBase.MvpBaseModel;
import com.ybkj.videoaccess.mvp.base.mvpBase.MvpBasePresenter;
import com.ybkj.videoaccess.mvp.base.mvpBase.MvpBaseView;
import com.ybkj.videoaccess.mvp.view.dialog.LoadingDialog;
import com.ybkj.videoaccess.util.CommonUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * 所有Fragment基类
 * <p>
 * Created by HH on 2016/7/4.
 */
public abstract class BaseFragment<P extends MvpBasePresenter, M extends MvpBaseModel> extends Fragment {
    @Nullable
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Nullable
    @BindView(R.id.titleText)
    TextView titleTv;
    private LoadingDialog dialog;
    private View views;
    private boolean isLoad;
    private boolean isShowBackBtn = false;
    public P mPresenter;
    public M mModel;
    protected CompositeSubscription mCompositeSubscription;
    protected Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
    }

    protected void initToolbar() {
        if (toolbar != null) {
            if (titleTv != null) {
                toolbar.setTitle("");
                titleTv.setText(setTitle());
            }
            if (isShowBackBtn) {
                toolbar.setNavigationIcon(ContextCompat.getDrawable(getContext(), R.mipmap.iconfont_jiantou));
            }
        }
    }

    /**
     * 设置是否显示返回按钮，默认显示
     *
     * @param isShowBackBtn
     */
    protected void setShowBackBtn(boolean isShowBackBtn) {
        this.isShowBackBtn = isShowBackBtn;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (views == null && getLayoutId() != 0) {
            views = inflater.inflate(getLayoutId(), container, false);
            ButterKnife.bind(this, views);
            initToolbar();
            mPresenter = createPresenter();
            mModel = createModel();
            if (this instanceof MvpBaseView && mPresenter != null)
                mPresenter.attachVM(this, mModel);
            initView(views);
            if (getUserVisibleHint() && !isLoad) {
                //界面显示并且未加载
                isLoad = true;
                onFirstLoadData();
            }

        }

        if (getLayoutId() != 0) {
            //防止Fragment加载进父容器出错
            ViewGroup parent = (ViewGroup) views.getParent();
            if (null != parent) {
                parent.removeView(views);
            }
        }

        return views;
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
     * 布局Id
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 标题
     *
     * @return
     */
    protected abstract String setTitle();

    /**
     * 界面初始化
     *
     * @param : view 该Fragment的根容器
     */
    protected abstract void initView(View views);

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && !isLoad && null != views) {
            onFirstLoadData();
            isLoad = true;
        }
    }

    protected boolean isLoad() {
        return isLoad;
    }

    /**
     * 第一次加载数据
     * <p>
     * 主要针对ViewPager的预加载情况，防止每次请求数据
     */
    protected abstract void onFirstLoadData();

    public void hideWaitDialog() {
        if (null != dialog) {
            dialog.cancel();
        }
    }

    public void showWaitDialogs(int resId, boolean isCancle) {
        showWaitDialogs(getString(resId), isCancle);
    }

    public void showWaitDialogs(String info, boolean isCancle) {
        if (!CommonUtil.isNetwork(getContext())) {
            // 没有网络连接
            return;
        }

        if (null == dialog) {
            dialog = new LoadingDialog(getContext(), info);
            dialog.setCancelable(isCancle);
        }

        dialog.setText(info);
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    public void addSubscription(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) mPresenter.detachVM();
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
        super.onDestroyView();
    }

}
