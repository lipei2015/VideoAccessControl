package com.ybkj.videoaccess.mvp.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

/**
 * 自定义的FragmentActivity基类
 * ● 实现初始化FragmentManager
 * ● 实现添加Fragment
 * ● 实现设置显示Fragment
 *
 * @author HH
 */
public abstract class BaseFragmentActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    private RadioGroup group;
    protected FragmentManager fm = null;
    protected ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private int currFragmentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            //取得上次显示的fragment，activity销毁时可以恢复界面状态
            currFragmentIndex = savedInstanceState.getInt("position");
        }

        fm = getSupportFragmentManager();
    }

    protected abstract int getFragmentViewId();

    protected void setmRadioGroup(RadioGroup mRadioGroup) {
        this.group = mRadioGroup;
        mRadioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        for (int i = 0, len = group.getChildCount(); i < len; i++) {
            if (group.getChildAt(i).getId() == checkedId) {

                Fragment fragment = mFragmentList.get(i);
                FragmentTransaction ft = fm.beginTransaction();
                //将上一个Fragment设置为OnPause 将目标Fragment设置为OnResume，若目标Fragment不在，则添加
                mFragmentList.get(currFragmentIndex).onPause();
                if (fragment.isAdded()) {
                    fragment.onResume();
                } else {
                    ft.add(getFragmentViewId(), fragment);
                }

                // 显示目标Fragment
                setFragment(i);
                currFragmentIndex = i;
                ft.commitAllowingStateLoss();

                onChecked(checkedId);
                break;
            }
        }
    }

    public void onChecked(int checkId){

    }

    /**
     * 添加Fragment
     */
    public void addFragment(Fragment fragment) {
        if (!fragment.isAdded()) {
            mFragmentList.add(fragment);
        }

    }

    public void showFragment() {
        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment = mFragmentList.get(currFragmentIndex);
        if (fragment.isAdded()) {
            fragment.onResume();
        } else {
            ft.add(getFragmentViewId(), fragment);
        }
        ft.commit();
    }

    /**
     * 设置Fragment 隐藏上一个Fragment，显示下一个Fragment
     */
    private void setFragment(int position) {
        Fragment fragment;
        for (int i = 0, len = mFragmentList.size(); i < len; i++) {
            fragment = mFragmentList.get(i);
            FragmentTransaction ft = fm.beginTransaction();
            if (position == i) {
                ft.show(fragment);
            } else {
                ft.hide(fragment);
            }
            ft.commitAllowingStateLoss();
        }
    }

    /**
     * 通过RadioGroup跳转
     *
     * @param position
     */
    protected void jump(int position) {
        if (group != null) {
            ((RadioButton) group.getChildAt(position)).setChecked(true);
            setFragment(position);
        }
    }

}
