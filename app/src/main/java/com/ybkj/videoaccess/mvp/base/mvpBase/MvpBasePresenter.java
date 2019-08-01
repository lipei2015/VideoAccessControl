package com.ybkj.videoaccess.mvp.base.mvpBase;

import android.content.Intent;
import android.os.Bundle;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * mvp P(View与Model交互)基类
 *
 * @author Created by CH L on 2017/3/21.
 */

public abstract class MvpBasePresenter<M, V> {
    public M mModel;
    public V mView;
    private CompositeSubscription mCompositeSubscription;

    public void attachVM(V v, M m) {
        this.mView = v;
        this.mModel = m;
    }

    public void detachVM() {
        unSubscribe();
        mView = null;
        mModel = null;
    }

    public abstract void start(Intent intent, Bundle bundle);

    public void addSubscription(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    //RxJava取消注册，以避免内存泄露
    private void unSubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }
}
