package com.ybkj.videoaccess.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.weight.CircleTransform;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
                .placeholder(R.mipmap.ic_launcher);

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
                .placeholder(R.mipmap.ic_launcher)
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
                .placeholder(R.mipmap.ic_launcher)
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

    /**
     * 将Base64编码转换为图片
     * @param base64Str
     * @param path
     * @return true
     */
    public static boolean base64ToFile(String base64Str,String path) {
        byte[] data = Base64.decode(base64Str,Base64.NO_WRAP);
        for (int i = 0; i < data.length; i++) {
            if(data[i] < 0){
                //调整异常数据
                data[i] += 256;
            }
        }
        OutputStream os = null;
        try {
            os = new FileOutputStream(path);
            os.write(data);
            os.flush();
            os.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据坐标抠图
     * @param path
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     */
    public static Bitmap cutBitmap(String path,int x,int y,int width,int height){
        FileInputStream fis = null;//通过path把照片读到文件输入流中
        try {
            fis = new FileInputStream(path);
            Bitmap bitmap = BitmapFactory.decodeStream(fis);//将输入流解码为bitmap
            Matrix matrix = new Matrix();//新建一个矩阵对象
//            matrix.setRotate(270);//矩阵旋转操作让照片可以正对着你。但是还存在一个左右对称的问题

            //新建位图，第2个参数至第5个参数表示位图的大小，matrix中是旋转后的位图信息，并使bitmap变量指向新的位图对象
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            return Bitmap.createBitmap(bitmap,x,y,width,height);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
