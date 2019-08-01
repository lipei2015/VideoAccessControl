package com.ybkj.videoaccess.util;

import android.content.Context;
import android.graphics.Bitmap;

import com.google.gson.Gson;
import com.ybkj.videoaccess.app.ConstantSys;
import com.ybkj.videoaccess.mvp.data.service.SystemAPIService;
import com.ybkj.videoaccess.util.http.HttpErrorException;
import com.ybkj.videoaccess.util.http.HttpSubscriber;
import com.ybkj.videoaccess.util.http.HttpUtil;
import com.ybkj.videoaccess.util.http.RxUtil;
import com.ybkj.videoaccess.util.http.SubscriberResultListener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * 图片上传工具类
 * 每上传一张图片，需要请求一次授权链接
 */
public class UploadImageUtil {
    private Context context;
    public OnUpload onUpload;
    private CompositeSubscription mCompositeSubscription;
    private List<String> uploadUrls;
    private Gson mGson ;

    public UploadImageUtil(OnUpload onUpload) {
        this.onUpload = onUpload;
        FileUtil.createMoreFiles(ConstantSys.FILE_TEMP);
        uploadUrls = new ArrayList<>();
        mGson = new Gson();
    }

    public synchronized void uploadImg(Context context, List<String> imgPaths, String t,String url) {
        this.context = context;

        /*MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);//表单类型
        //进行裁剪
        for (int i = 0; i < imgPaths.size(); i++) {
            File file = clipImgToFile(imgPaths.get(i));
            //构建body
            RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            builder.addFormDataPart("imgfile"+i, file.getName(), imageBody);//"imgfile"+i 后台接收图片流的参数名
        }
        List<MultipartBody.Part> parts = builder.build().parts();*/

        //进行裁剪
        Map<String, RequestBody> maps = new HashMap<>();
        for (int i = 0; i < imgPaths.size(); i++) {
            File file = clipImgToFile(imgPaths.get(i));
            //构建body
            maps.put("file" + (i + 1) + "\"; filename=\"" + file.getName(),  RequestBody.create(MediaType.parse("image/png"), file));
        }

        //上传单文件 -- 头像
        if (ConstantSys.UpdateImageType.UPDATE_HEAD_IMAGE.equals(t)){
            addSubscription(HttpUtil.getInstance().getRetrofit().create(SystemAPIService.class)
                    .uploadImg(url, maps)
                    .map(new HttpUtil.HttpResultFunc<>())
                    .compose(RxUtil.rxSchedulerHelper()).subscribe(new HttpSubscriber<>(new SubscriberResultListener() {
                        @Override
                        public void onSuccess(Object o) {
                            uploadUrls.add(o.toString());
                            onUpload.onSuccess(uploadUrls);
                        }

                        @Override
                        public void onError(HttpErrorException errorException) {
                            onUpload.onFailure();
                        }
                    })));

        }else { // 上传多文件 -- 意见反馈
            addSubscription(HttpUtil.getInstance().getRetrofit().create(SystemAPIService.class)
                    .uploadImgMore(url, maps)
                    .map(new HttpUtil.HttpResultFunc<>())
                    .compose(RxUtil.rxSchedulerHelper()).subscribe(new HttpSubscriber<>(new SubscriberResultListener<List<String>>() {
                        @Override
                        public void onSuccess(List<String> o) {
                            onUpload.onSuccess(o);
                        }

                        @Override
                        public void onError(HttpErrorException errorException) {
                            onUpload.onFailure();
                        }
                    })));
        }
    }

    public void addSubscription(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    //RxJava取消注册，以避免内存泄露
    private void unSubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }

    /**
     * 上传图片
     *
     * @param filePath
     */
    private File clipImgToFile(String filePath) {
        //裁剪上传的图片质量
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length());
        String thumbnailPath = FileUtil.getSdCardPath() + ConstantSys.FILE_TEMP + "/thumbnail_" + fileName;           //uri.getLastPathSegment();
        Bitmap bitmap = BitmapUtils.saveThumbnaiImage(filePath, thumbnailPath,
                BitmapUtils.getInSampleSize(ViewUtil.getScreenWidth(context), ViewUtil.getScreenHeight(context), filePath));
        // 部分手机旋转了一定角度，这里把他转回来
        int degree = PictureUtil.getBitmapDegree(filePath);
        if (degree > 0) {
            PictureUtil.rotateBitmapByDegree(bitmap, degree);
        }

        return new File(thumbnailPath);
    }

    public interface OnUpload {
        /**
         * 图片上传成功
         *
         * @param ossImgPath
         */
        void onSuccess(List<String> ossImgPath);

        /**
         * 图片上传失败
         */
        void onFailure();
    }

    /**
     * 停止上传，防止内存泄漏
     */
    public void onStop() {
        unSubscribe();
        mCompositeSubscription = null;
    }

}
