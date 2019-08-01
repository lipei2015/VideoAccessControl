package com.ybkj.videoaccess.weight.refresh_loadmore;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.PtrUIHandler;
import com.chanven.lib.cptr.indicator.PtrIndicator;
import com.ybkj.videoaccess.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自定义下拉刷新头部
 * <p>
 * Created by HH on 2016/9/6.
 */
public class PtrClassicMyHeader extends FrameLayout implements PtrUIHandler {

    private final static String KEY_SharedPreferences = "cube_ptr_classic_last_update";
    private static SimpleDateFormat sDataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private int mRotateAniTime = 150;
    /*private RotateAnimation mFlipAnimation;
    private RotateAnimation mReverseFlipAnimation;
    private TextView mTitleTextView;
    private View mRotateView;
    private ImageView mProgressBar;
    private TextView mLastUpdateTextView;*/
    private ImageView startImg;
    private ImageView loadImg;
    private AnimationDrawable refreshDrawable;

    private long mLastUpdateTime = -1;
    private String mLastUpdateTimeKey;
    private boolean mShouldShowLastUpdate;

    private LastUpdateTimeUpdater mLastUpdateTimeUpdater = new LastUpdateTimeUpdater();

    public PtrClassicMyHeader(Context context) {
        super(context);
        initViews();
    }

    public PtrClassicMyHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public PtrClassicMyHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }

    protected void initViews() {
        buildAnimation();
        View header = LayoutInflater.from(getContext()).inflate(R.layout.refresh_header, this);

        /*mRotateView = header.findViewById(R.id.mRotateView);
        mTitleTextView = (TextView) header.findViewById(R.id.mTitleTextView);
        mLastUpdateTextView = (TextView) header.findViewById(R.id.mLastUpdateTextView);
        mProgressBar = (ImageView) header.findViewById(R.id.mProgressBar);
        ((AnimationDrawable) mProgressBar.getDrawable()).start();*/

        startImg = (ImageView) header.findViewById(R.id.startImg);
        loadImg = (ImageView) header.findViewById(R.id.loadImg);
        refreshDrawable = (AnimationDrawable) loadImg.getDrawable();
        refreshDrawable.start();
        resetView();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mLastUpdateTimeUpdater != null) {
            mLastUpdateTimeUpdater.stop();
        }
    }

    public void setRotateAniTime(int time) {
        if (time == mRotateAniTime || time == 0) {
            return;
        }
        mRotateAniTime = time;
        buildAnimation();
    }

    /**
     * Specify the last update time by this key string
     *
     * @param key
     */
    public void setLastUpdateTimeKey(String key) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        mLastUpdateTimeKey = key;
    }

    /**
     * Using an object to specify the last update time.
     *
     * @param object
     */
    public void setLastUpdateTimeRelateObject(Object object) {
        setLastUpdateTimeKey(object.getClass().getName());
    }

    private void buildAnimation() {

        /*mFlipAnimation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mFlipAnimation.setInterpolator(new LinearInterpolator());
        mFlipAnimation.setDuration(mRotateAniTime);
        mFlipAnimation.setFillAfter(true);

        mReverseFlipAnimation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mReverseFlipAnimation.setInterpolator(new LinearInterpolator());
        mReverseFlipAnimation.setDuration(mRotateAniTime);
        mReverseFlipAnimation.setFillAfter(true);*/
    }

    private void resetView() {
        lastPosition = 0;
        hideRotateView();
        //mProgressBar.setVisibility(INVISIBLE);
    }

    private void hideRotateView() {
        /*mRotateView.clearAnimation();
        mRotateView.setVisibility(INVISIBLE);*/
        loadImg.setVisibility(View.GONE);
        startImg.clearAnimation();
        startImg.setVisibility(View.GONE);
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        resetView();
        mShouldShowLastUpdate = true;
        tryUpdateLastUpdateTime();
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {

        mShouldShowLastUpdate = true;
        tryUpdateLastUpdateTime();
        mLastUpdateTimeUpdater.start();

        loadImg.setVisibility(View.GONE);
        startImg.setVisibility(View.VISIBLE);
        //mProgressBar.setVisibility(INVISIBLE);
        /*mRotateView.setVisibility(VISIBLE);
        mTitleTextView.setVisibility(VISIBLE);
        if (frame.isPullToRefresh()) {
            mTitleTextView.setText(getResources().getString(R.string.refresh_pull_downs));
        } else {
            mTitleTextView.setText(getResources().getString(R.string.refresh_pull_downs));
        }*/
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        mShouldShowLastUpdate = false;
        hideRotateView();
        /*mProgressBar.setVisibility(VISIBLE);
        mTitleTextView.setVisibility(VISIBLE);
        mTitleTextView.setText(R.string.refresh_refreshing);*/

        loadImg.setVisibility(VISIBLE);
        startImg.clearAnimation();
        startImg.setVisibility(View.GONE);
        tryUpdateLastUpdateTime();
        mLastUpdateTimeUpdater.stop();
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {

        hideRotateView();
        loadImg.setVisibility(VISIBLE);

        /*mProgressBar.setVisibility(INVISIBLE);
        mTitleTextView.setVisibility(VISIBLE);
        mTitleTextView.setText(getResources().getString(R.string.refresh_completes));*/

        // update last update time
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(KEY_SharedPreferences, 0);
        if (!TextUtils.isEmpty(mLastUpdateTimeKey)) {
            mLastUpdateTime = new Date().getTime();
            sharedPreferences.edit().putLong(mLastUpdateTimeKey, mLastUpdateTime).commit();
        }
    }

    private void tryUpdateLastUpdateTime() {
        /*if (TextUtils.isEmpty(mLastUpdateTimeKey) || !mShouldShowLastUpdate) {
            mLastUpdateTextView.setVisibility(GONE);
        } else {
            String time = getLastUpdateTime();
            if (TextUtils.isEmpty(time)) {
                mLastUpdateTextView.setVisibility(GONE);
            } else {
                mLastUpdateTextView.setVisibility(VISIBLE);
                mLastUpdateTextView.setText(time);
            }
        }*/
    }

    /*private String getLastUpdateTime() {

        if (mLastUpdateTime == -1 && !TextUtils.isEmpty(mLastUpdateTimeKey)) {
            mLastUpdateTime = getContext().getSharedPreferences(KEY_SharedPreferences, 0).getLong(mLastUpdateTimeKey, -1);
        }
        if (mLastUpdateTime == -1) {
            return null;
        }
        long diffTime = new Date().getTime() - mLastUpdateTime;
        int seconds = (int) (diffTime / 1000);
        if (diffTime < 0) {
            return null;
        }
        if (seconds <= 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(getContext().getString(R.string.refresh_last_update));

        if (seconds < 60) {
            sb.append(seconds + getContext().getString(R.string.refresh_seconds_ago));
        } else {
            int minutes = (seconds / 60);
            if (minutes > 60) {
                int hours = minutes / 60;
                if (hours > 24) {
                    Date date = new Date(mLastUpdateTime);
                    sb.append(sDataFormat.format(date));
                } else {
                    sb.append(hours + getContext().getString(R.string.refresh_hours_ago));
                }

            } else {
                sb.append(minutes + getContext().getString(R.string.refresh_minutes_ago));
            }
        }
        return sb.toString();
    }*/

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();
        final int lastPos = ptrIndicator.getLastPosY();

        if (currentPos < mOffsetToRefresh) {
            //可以刷新了，执行帧动画
            setScannel(((float) currentPos) / mOffsetToRefresh);
            /*if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                //crossRotateLineFromBottomUnderTouch(frame);
                *//*if (mRotateView != null) {
                    mRotateView.clearAnimation();
                    mRotateView.startAnimation(mReverseFlipAnimation);
                }*//*
            }*/
        } else if (currentPos > mOffsetToRefresh && lastPos <= mOffsetToRefresh) {
            //不刷新，执行缩放动画

            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                crossRotateLineFromTopUnderTouch(frame);
                /*if (mRotateView != null) {
                    mRotateView.clearAnimation();
                    mRotateView.startAnimation(mFlipAnimation);
                }*/
            }
        }
    }

    /**
     * 起始缩放动画
     *
     * @param position
     */
    private float lastPosition = 0;

    private void setScannel(float position) {
        if (startImg.getVisibility() == View.VISIBLE) {
            ScaleAnimation scaleAnimation = new ScaleAnimation(lastPosition, position, lastPosition, position);
            scaleAnimation.setDuration(10);
            scaleAnimation.setFillAfter(true);


            startImg.startAnimation(scaleAnimation);
            lastPosition = position;
        }

    }

    private void crossRotateLineFromTopUnderTouch(PtrFrameLayout frame) {
        /*if (!frame.isPullToRefresh()) {
            mTitleTextView.setVisibility(VISIBLE);
            mTitleTextView.setText(R.string.refresh_release_to_refresh);
        }*/
    }

    private void crossRotateLineFromBottomUnderTouch(PtrFrameLayout frame) {
        /*mTitleTextView.setVisibility(VISIBLE);
        if (frame.isPullToRefresh()) {
            mTitleTextView.setText(getResources().getString(R.string.refresh_pull_downs));
        } else {
            mTitleTextView.setText(getResources().getString(R.string.refresh_pull_downs));
        }*/
    }

    private class LastUpdateTimeUpdater implements Runnable {

        private boolean mRunning = false;

        private void start() {
            if (TextUtils.isEmpty(mLastUpdateTimeKey)) {
                return;
            }
            mRunning = true;
            run();
        }

        private void stop() {
            mRunning = false;
            removeCallbacks(this);
        }

        @Override
        public void run() {
            tryUpdateLastUpdateTime();
            if (mRunning) {
                postDelayed(this, 1000);
            }
        }
    }
}

