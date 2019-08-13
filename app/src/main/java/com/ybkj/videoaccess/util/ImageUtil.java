package com.ybkj.videoaccess.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.weight.CircleTransform;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片工具类
 * <p>
 * Created by HH on 2017/2/9.
 */
public class ImageUtil {

    /**
     * 加载网络图片
     *
     * @param context
     * @param ImgView
     * @param imgUrl
     */
    public static void loadImg(Context context, ImageView ImgView, String imgUrl) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.mipmap.loading_img);

        Glide.with(context).load(imgUrl)
                .apply(requestOptions)
                .into(ImgView);
    }

    /**
     * 加载网络图片
     *
     * @param context
     * @param ImgView
     * @param imgUrl
     */
    public static void loadImgNoCache(Context context, ImageView ImgView, String imgUrl) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.mipmap.loading_img)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .dontAnimate();
        Glide.with(context).load(imgUrl)
                .apply(requestOptions).into(ImgView);
    }

    /**
     * 加载头像
     *
     * @param context
     * @param ImgView
     * @param imgUrl
     */
    public static void loadHeadImg(Context context, ImageView ImgView, String imgUrl) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.mipmap.personal_touxiang_circle)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .transform(new CircleTransform(context))
                .dontAnimate();
        Glide.with(context).load(imgUrl).apply(requestOptions).into(ImgView);
    }

    /**
     * 加载网络图片
     *
     * @param context
     * @param ImgView
     * @param imgUrl
     * @param defaultImgId 设置占位图
     */
    public static void loadImg(Context context, ImageView ImgView, String imgUrl, int defaultImgId) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(defaultImgId)
                .dontAnimate();
        Glide.with(context).load(imgUrl).apply(requestOptions).into(ImgView);
    }

    /**
     * 加载本地图片
     *
     * @param context
     * @param ImgView
     * @param imgId   图片Id
     */
    public static void loadImg(Context context, ImageView ImgView, int imgId) {
        Glide.with(context).load(imgId).into(ImgView);
    }

    /**
     * 将图片转换成Base64编码的字符串
     */
    public static String imageToBase64(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try {
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }

}
