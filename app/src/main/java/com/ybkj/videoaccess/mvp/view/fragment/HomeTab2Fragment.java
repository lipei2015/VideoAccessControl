package com.ybkj.videoaccess.mvp.view.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.google.zxing.client.android.CaptureActivity;
import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.mvp.base.BaseFragment;

import butterknife.BindView;

/**
 * 首页Tba2
 * <p>
 * Created by HH on 2019/02/27
 */
public class HomeTab2Fragment extends BaseFragment {
    @BindView(R.id.tv_scan) TextView tv_scan;
    public final static int SCANNING_REQUEST_CODE = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_tab2;
    }

    @Override
    protected String setTitle() {
        return null;
    }

    @Override
    protected void initView(View views) {
        tv_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CaptureActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, SCANNING_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onFirstLoadData() {

    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNING_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    final Bundle bundle = data.getExtras();
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(bundle.getString("result"));
                        }
                    });
                }
                break;
            default:
                break;
        }
    }*/
}
