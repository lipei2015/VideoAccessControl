package com.ybkj.videoaccess.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import com.google.zxing.client.android.CaptureActivity;
import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.mvp.base.BaseActivity;

import butterknife.BindView;

public class BeginScanActivity extends BaseActivity {
    @BindView(R.id.tv_scan)
    TextView tv_scan;
    public final static int SCANNING_REQUEST_CODE = 1;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_begin_scan;
    }

    @Override
    protected String setTitle() {
        return null;
    }

    @Override
    protected void initView() {
        tv_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BeginScanActivity.this, CaptureActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, SCANNING_REQUEST_CODE);
            }
        });
    }

    @Override
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
                            tv_scan.setText(bundle.getString("result"));
                        }
                    });
                }
                break;
            default:
                break;
        }
    }

}
