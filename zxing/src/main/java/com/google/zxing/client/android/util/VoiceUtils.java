package com.google.zxing.client.android.util;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by Mr.Li on 2018/11/2.
 */

public class VoiceUtils {
    private static MediaPlayer mMediaPlayer;
    private static VoiceUtils voiceUtils;
    private VoiceUtils(){

    }

    public static VoiceUtils getInstance(){
        if(voiceUtils == null){
            voiceUtils = new VoiceUtils();
        }
        return voiceUtils;
    }

    public void playVoice(Context context,int musicId){
        // 播放提示音
        try {
            if (mMediaPlayer != null) {
                mMediaPlayer.release();
            }

            if (musicId >= 0) {
                if (musicId > 0) {
                    mMediaPlayer = MediaPlayer.create(context, musicId);
                }
                mMediaPlayer.start();
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
            } else {

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void release(){
        try {
            if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
                mMediaPlayer.release();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
