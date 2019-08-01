package com.ybkj.videoaccess.util;

import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.app.MyApp;
import com.ybkj.videoaccess.mvp.view.dialog.SelectImgDialog;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * 选择图片
 * <p>
 * Created by HH on 2016/9/24.
 */

public class SelectImgHelper {
    private static final int REQUEST_CODE_GALLERY = 0x01;//单选选择相册
    private static final int REQUEST_CODE_GALLERY_MULTIPLE = 0x002;//多选选择图片
    private static final int REQUEST_CODE_CAMERA = 0x003;//使用照相机

    private static Builder builder;
    private static MyOnHelperResultCallback myOnHelperResultCallback;
    private static SelectImgDialog.OnSelectType onSelectType;

    public SelectImgHelper(Builder builder, SelectImgDialog.OnSelectType onSelectType) {
        this.builder = builder;
        this.onSelectType = onSelectType;
        this.myOnHelperResultCallback = new MyOnHelperResultCallback();
    }

    public void setBuilder(Builder builder) {
        this.builder = builder;
    }

    /**
     * 配置类
     */
    public static class Builder {
        private boolean isSingle = false;  //单选还是多选
        private boolean isCrop = false;  //是否裁剪
        private int maxLength = 0;  //最大图片数

        public boolean isSingle() {
            return isSingle;
        }

        public void setSingle(boolean single) {
            isSingle = single;
        }

        public boolean isCrop() {
            return isCrop;
        }

        public void setCrop(boolean crop) {
            isCrop = crop;
        }

        public int getMaxLength() {
            return maxLength;
        }

        public void setMaxLength(int maxLength) {
            this.maxLength = maxLength;
        }
    }

    public void openSingle() {
        GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, myOnHelperResultCallback);
    }

    public void openMultiple() {
        //带配置
        FunctionConfig config = new FunctionConfig.Builder()
                .setMutiSelectMaxSize(builder.getMaxLength())
                .setEnableCrop(builder.isCrop())  //是否支持裁剪
                .build();
        GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY_MULTIPLE, config, myOnHelperResultCallback);
    }

    public void openCamera() {
        //带配置
        FunctionConfig config = new FunctionConfig.Builder()
                .setEnableCrop(builder.isCrop())  //是否支持裁剪
                .setEnableEdit(false)
                .setCropSquare(true)
                .setForceCrop(builder.isCrop())
                .build();
        GalleryFinal.openCamera(REQUEST_CODE_CAMERA, config, myOnHelperResultCallback);
    }

    /**
     * 使用裁剪
     */
    private static final int REQUEST_CODE_CROP = 0x004;

    public static void openCrop(String imgPath) {
        GalleryFinal.openCrop(REQUEST_CODE_CROP, imgPath, myOnHelperResultCallback);
    }

    /**
     * 选择结果监听
     */
    protected static class MyOnHelperResultCallback implements GalleryFinal.OnHanlderResultCallback {

        @Override
        public void onHanlderSuccess(int requestCode, List<PhotoInfo> results) {
            if ((requestCode == REQUEST_CODE_CAMERA || requestCode == REQUEST_CODE_GALLERY) && builder.isCrop()) {
                openCrop(results.get(0).getPhotoPath());
            } else {
                List<String> imgList = new ArrayList<>();
                for (PhotoInfo info : results) {
                    imgList.add(info.getPhotoPath());
                }

                onSelectType.onSelectImg(imgList);
            }

        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            onSelectType.onSelectFiler(MyApp.getAppContext().getString(R.string.pictureSelectFailure));
        }
    }

}
