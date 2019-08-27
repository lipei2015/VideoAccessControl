package com.ybkj.videoaccess.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.ybkj.videoaccess.R;

/**
 * 自定义加载过程View
 *
 * @author XHH
 */
public class LoadView extends FrameLayout implements OnClickListener {
    // 加载状态(加载中、加载失败，加载成功，加载成功没有数据，没有网络)
    public static final int NONE = 0x00;
    public static final int LOADING = 0x01;
    public static final int ERROR = 0x02;
    public static final int SUCCESS = 0x03;
    public static final int EMPTY_DATA = 0x04;
    public static final int EMPTY_NO_NETWORK = 0x05;
    private int currStatus = -1;       // 当前状态

    private Context mContext;
    private ImageView img;
    private TextView tryAgin;
    private View rootView;

    private String noDataInfo;  //没有数据提示信息

    public LoadView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public LoadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public LoadView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    /**
     * 初始化组件
     */
    private void init() {
        noDataInfo = mContext.getString(R.string.noData);
        rootView = (TextView)View.inflate(mContext, R.layout.include_loading, null);
        img = (ImageView)rootView.findViewById(R.id.progress);
        tryAgin = (TextView)rootView.findViewById(R.id.tryAgin);
        setVisibility(View.GONE);
        addView(rootView);
    }

    /**
     * 设置提示信息
     * @param noDataInfo
     * @return
     */
    public LoadView setNoDataInfo(String noDataInfo) {
        this.noDataInfo = noDataInfo;
        return this;
    }

    /**
     * 设置页面背景色
     * @return
     */
    public LoadView setLoadViewBg(int bg){
        rootView.setBackgroundColor(bg);
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tryAgin:
                if (onTryListener != null) {
                    onTryListener.onTry();
                }
                break;
        }
    }

    /**
     * 重新加载回调
     */
    private OnTryListener onTryListener;

    public void setOnTryListener(OnTryListener onTryListener) {
        this.onTryListener = onTryListener;
        tryAgin.setOnClickListener(this);
    }

    public interface OnTryListener {
        /**
         * 再次尝试
         */
        void onTry();
    }

    /**
     * 跳转回调
     */
    private ToIntentListener onToIntentListener;

    public interface ToIntentListener {
        /**
         * 点击跳转按钮
         */
        void onToIntent();
    }

    public int getStatus() {
        return currStatus;
    }

    /**
     * 设置加载状态
     */
    public void setStatus(int status) {
        currStatus = status;
        /** ---------加载失败----------- **/
        if (status == ERROR) {
            img.clearAnimation();
            img.setVisibility(View.GONE);
            tryAgin.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_launcher, 0, 0);
            tryAgin.setText("加载失败，点击重新加载");
            tryAgin.setEnabled(true);
            tryAgin.setVisibility(View.VISIBLE);
        }

        /** ---------加载成功----------- **/
        else if (status == SUCCESS) {
            setVisibility(View.GONE);
        }

        /** ---------没有数据----------- **/
        else if (status == EMPTY_DATA) {
            setVisibility(View.VISIBLE);
            img.clearAnimation();
            img.setVisibility(View.GONE);
            tryAgin.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.mipmap.ic_launcher, 0, 0);
            tryAgin.setText(noDataInfo);
            tryAgin.setVisibility(View.VISIBLE);
            tryAgin.setEnabled(false);
        }

        /** ---------正在加载----------- **/
        else if (status == LOADING) {
            setVisibility(View.VISIBLE);
            img.setVisibility(View.VISIBLE);
            tryAgin.setVisibility(View.GONE);
            img.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.load_progress_anim));
        }

        /** ---------没有网络----------- **/
        else if (status == EMPTY_NO_NETWORK) {
            img.clearAnimation();
            img.setVisibility(View.GONE);
            tryAgin.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_launcher, 0, 0);
            tryAgin.setVisibility(View.VISIBLE);
            tryAgin.setText("网络设置出错，点击重新加载");
            tryAgin.setEnabled(true);
        }
    }

}
