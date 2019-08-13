package com.ybkj.videoaccess.mvp.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ybkj.videoaccess.R;

public class StartCameraActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startcamera);
    }

    public void  customCamera(View view){
        startActivity(new Intent(this, FaceCheckActivity.class));
    }
}
