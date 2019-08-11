package com.ybkj.videoaccess.mvp.view.activity;

import android.view.KeyEvent;

import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.mvp.base.BaseActivity;
import com.ybkj.videoaccess.mvp.data.model.HomeModel;
import com.ybkj.videoaccess.mvp.presenter.HomePresenter;
import com.ybkj.videoaccess.util.ToastUtil;

public class HomeActivity extends BaseActivity<HomePresenter, HomeModel>{
    @Override
    protected int setLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected String setTitle() {
        return null;
    }

    @Override
    protected void initView() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        ToastUtil.showMsg(keyCode+"--------");
        switch (keyCode){
            case 7:
                // 0
                break;
            case 8:
                // 1
                break;
            case 9:
                // 2
                break;
            case 10:
                // 3
                break;
            case 11:
                // 4

                break;
            case 12:
                // 5

                break;
            case 13:
                // 6
                break;
            case 14:
                // 7
                break;
            case 15:
                // 8

                break;
            case 16:
                // 9
                break;
            case 135:
                // *
                break;
            case 136:
                // #
                break;

        }
        return super.onKeyDown(keyCode, event);
    }
}
