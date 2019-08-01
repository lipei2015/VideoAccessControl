package com.ybkj.videoaccess.util;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

/**
 * 动画工具类
 * <p>
 * Created by HH on 2018/1/19
 */
public class AnimationUtil implements Animation.AnimationListener {
    private OnAnimationEnd onAnimationEndLintener;

    public AnimationUtil(OnAnimationEnd onAnimationEndLintener) {
        this.onAnimationEndLintener = onAnimationEndLintener;
    }

    /**
     * 渐变动画
     *
     * @param alphaStart 起始渐变值
     * @param alphaEnd   结束渐变值
     * @param duration   动画时间
     * @return anim
     */
    public Animation getAlphaAnimation(Float alphaStart, Float alphaEnd, int duration) {
        AlphaAnimation anim = new AlphaAnimation(alphaStart, alphaEnd);
        anim.setDuration(duration);
        anim.setAnimationListener(this);
        return anim;
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (null != onAnimationEndLintener) {
            onAnimationEndLintener.onAnimationEnd();
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    /**
     * 动画结束监听器
     */
    public interface OnAnimationEnd {
        void onAnimationEnd();
    }

}
