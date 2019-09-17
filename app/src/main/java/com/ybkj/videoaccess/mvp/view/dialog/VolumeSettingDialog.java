package com.ybkj.videoaccess.mvp.view.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.mvp.base.BaseDialog;
import com.ybkj.videoaccess.util.AudioMngHelper;
import com.ybkj.videoaccess.util.BrightnessTools;
import com.ybkj.videoaccess.util.ToastUtil;
import com.ybkj.videoaccess.weight.CustomProgress;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * <p>
 * Created by lp
 */
public class VolumeSettingDialog extends BaseDialog {
    private TextView title;
    private TextView tvPromet1;
    private View v;
    private SeekBar seekBar;
    private OnKeyDownListener onKeyDownListener;

    private DecimalFormat df = new DecimalFormat("######0.00");
    private int currentPercent = 0;
    private Activity activity;
    private AudioMngHelper audioMngHelper;

    public static final int TYPE_VOLUME_SET = 1;
    public static final int TYPE_BRIGHT_SET = 2;
    private int type = 1;   // 1为音量设置；2为屏幕亮度设置

    public VolumeSettingDialog(Context context, OnKeyDownListener onKeyDownListener) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        findView(context);
        setCancelable(true);
        this.onKeyDownListener = onKeyDownListener;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;

        if(type == TYPE_VOLUME_SET){
            tvPromet1.setText("请按 2 键调大音量或 8 键调小音量");
            if(audioMngHelper == null){
                audioMngHelper = new AudioMngHelper(activity);
                audioMngHelper.setAudioType(AudioMngHelper.TYPE_MUSIC);
            }
            currentPercent = audioMngHelper.get100CurrentVolume();
            Log.e("currentPercent",audioMngHelper.getSystemCurrentVolume()+"--"+currentPercent+"  "+audioMngHelper.getSystemMaxVolume());

            seekBar.setMax(100);
            seekBar.setProgress(currentPercent);
        }else{
            tvPromet1.setText("请按 2 键调大亮度或 8 键调小亮度");
            boolean isAuto = BrightnessTools.isAutoBrightness(activity);
            if(isAuto){
                BrightnessTools.stopAutoBrightness(activity);
            }
            seekBar.setMax(100);
            float current = BrightnessTools.getScreenBrightness(activity);

            NumberFormat numberFormat = NumberFormat.getInstance();
            // 设置精确到小数点后2位
            numberFormat.setMaximumFractionDigits(2);
            String result = numberFormat.format((float) current / (float) 70 * 100);   // 70是最大亮度值

            if(result.contains(".")){
                currentPercent = Integer.parseInt(result.substring(0,result.indexOf(".")));
            }else{
                currentPercent = Integer.parseInt(result);
            }
            currentPercent = 100 - currentPercent;

            Log.e("progress","current:"+current+"--"+"currentPercent:"+currentPercent);
            seekBar.setProgress(currentPercent);
        }
    }

    /**
     * 初始化
     */
    @SuppressLint("WrongViewCast")
    private void findView(Context context) {
        this.activity = (Activity) context;
        v = View.inflate(context, R.layout.dialog_volume, null);
        title = (TextView) v.findViewById(R.id.dialogTitle);
        tvPromet1 = (TextView) v.findViewById(R.id.tvPromet1);
        seekBar = (SeekBar) v.findViewById(R.id.seekBar);
        setContentView(v);

        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams attr = window.getAttributes();
            if (attr != null) {
                attr.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                attr.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                attr.gravity = Gravity.CENTER;//设置dialog 在布局中的位置
            }
        }
    }

    /**
     * 设置提示标题
     */
    public VolumeSettingDialog setTitle(String titleValue) {
        title.setText(titleValue);
        return this;
    }

    /**
     * 设置无标题
     */
    public VolumeSettingDialog setNoTitle(boolean hasTitle) {
        title.setVisibility(hasTitle ? View.GONE : View.VISIBLE);
        return this;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        ToastUtil.showMsg(keyCode+"++++++++");
        switch (keyCode){

            case 9:
                // 2 + 音量
                // 音量 + 键
                if (currentPercent + 10 > 100) {
                    currentPercent = 100;
                } else {
                    currentPercent += 10;
                }
                seekBar.setProgress(currentPercent);
                if(type == TYPE_VOLUME_SET){
                    audioMngHelper.setVoice100(currentPercent);
                }else {
                    Log.e("BrightnessTools","current:"+BrightnessTools.getScreenBrightness(activity)+"  currentPercent"+currentPercent);
                    BrightnessTools.saveBrightness(activity, (int) ((100-currentPercent) / 100f * 70)); // 设备的亮度和手机相反，所以要用100减去当前百分比

                    Log.e("BrightnessTools end",BrightnessTools.getScreenBrightness(activity)+"");
                }
                break;
            case 15:
                // 8 减音量
                // 音量 - 键
                if (currentPercent - 10 < 0) {
                    currentPercent = 0;
                } else {
                    currentPercent -= 10;
                }
                seekBar.setProgress(currentPercent);
                if(type == TYPE_VOLUME_SET){
                    audioMngHelper.setVoice100(currentPercent);
                }else {
                    Log.e("BrightnessTools","current:"+BrightnessTools.getScreenBrightness(activity)+"  currentPercent"+currentPercent);
                    BrightnessTools.saveBrightness(activity, (int) ((100-currentPercent) / 100f * 70)); // 设备的亮度和手机相反，所以要用100减去当前百分比

                    Log.e("BrightnessTools end",BrightnessTools.getScreenBrightness(activity)+"");
                }
                break;
            case 24:
                // 音量 + 键
                if (currentPercent + 10 > 100) {
                    currentPercent = 100;
                } else {
                    currentPercent += 10;
                }
                if(type == TYPE_VOLUME_SET){
                    audioMngHelper.setVoice100(currentPercent);
                }else {
//                    BrightnessTools.setBrightness(activity, currentPercent * 70 / 100);

                }
                break;
            /*case 25:
            case 82:
                // 音量 - 键
                if (currentPercent - 10 < 0) {
                    currentPercent = 0;
                } else {
                    currentPercent -= 10;
                }
                customProgress.setProgress(currentPercent);
                if(type == TYPE_VOLUME_SET){
                    audioMngHelper.setVoice100(currentPercent);
                }else {
                    BrightnessTools.setBrightness(activity, currentPercent * 70 / 100);
                }
                break;*/
            case 135:
                // * 确认提交密码
                break;
            case 136:
                // # 关闭
                dismiss();
                break;
        }

        /*if(keyCode == KeyEvent.KEYCODE_BACK) {
            if (currentPercent + 10 > 100) {
                currentPercent = 100;
            } else {
                currentPercent += 10;
            }
            customProgress.setProgress(currentPercent);
            if(type == TYPE_VOLUME_SET){
                audioMngHelper.setVoice100(currentPercent);
            }else {
                BrightnessTools.setBrightness(activity, currentPercent * 70 / 100);
            }
            return false;
        }*/
        return super.onKeyDown(keyCode, event);
    }

    public interface OnKeyDownListener {
        void onSubmit(String pwd);
    }
}
