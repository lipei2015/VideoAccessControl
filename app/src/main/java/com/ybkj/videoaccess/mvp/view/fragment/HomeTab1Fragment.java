package com.ybkj.videoaccess.mvp.view.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.mvp.base.BaseFragment;

import butterknife.BindView;

/**
 * 首页Tba1
 * <p>
 * Created by HH on 2019/02/27
 */
public class HomeTab1Fragment extends BaseFragment {
    @BindView(R.id.testBtn) TextView testBtn;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_tab1;
    }

    @Override
    protected String setTitle() {
        return null;
    }

    @Override
    protected void initView(View views) {
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*try {
                    Log.v("testBtn", "root Runtime->reboot");
                    Process proc =Runtime.getRuntime().exec(new String[]{"su","-c","reboot "});
                    proc.waitFor();
                }catch (Exception ex){
                    ex.printStackTrace();
                }*/

                /*// 系统级程序才能使用
                Log.v("testBtn", "broadcast->reboot");
                Intent intent2 = new Intent(Intent.ACTION_REBOOT);
                intent2.putExtra("nowait", 1);
                intent2.putExtra("interval", 1);
                intent2.putExtra("window", 0);
                context.sendBroadcast(intent2);*/

//                Intent intent4 = new Intent();
//                intent4.setClass(context, StartCameraActivity.class);
//                startActivity(intent4);

            }
        });
    }

    @Override
    protected void onFirstLoadData() {

    }
}
