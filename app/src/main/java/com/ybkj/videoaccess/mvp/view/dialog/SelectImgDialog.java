package com.ybkj.videoaccess.mvp.view.dialog;

import android.content.Context;
import android.view.View;
import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.mvp.base.mvpBase.BaseAnimDialog;
import com.ybkj.videoaccess.util.SelectImgHelper;
import java.util.List;

import butterknife.OnClick;

/**
 * <p>
 * Created by lp
 */
public class SelectImgDialog extends BaseAnimDialog {
    private SelectImgHelper.Builder builder;
    private SelectImgHelper selectImgHelper;

    public SelectImgDialog(Context context, OnSelectType onSelectType) {
        super(context);
        builder = createHeader();
        selectImgHelper = new SelectImgHelper(builder, onSelectType);
    }

    @Override
    protected View setView() {
        return View.inflate(getContext(), R.layout.dialog_select_img, null);
    }

    /**
     * 选择类型回调接口
     */
    public interface OnSelectType {
        void onSelectImg(List<String> imgList);

        void onSelectFiler(String msg);
    }

    @OnClick({R.id.fromTakeImg, R.id.fromAlbum, R.id.cancel, R.id.rootView})
    protected void onClick(View v) {
        switch (v.getId()) {
            case R.id.fromTakeImg:
                selectImgHelper.openCamera();
                dismiss();
                break;

            case R.id.fromAlbum:
                if (builder.isSingle()) {
                    selectImgHelper.openSingle();
                } else {
                    selectImgHelper.openMultiple();
                }
                dismiss();
                break;

            case R.id.cancel:
                dismiss();
                break;

            case R.id.rootView:
                dismiss();
                break;
        }
    }

    public void setBuilder(SelectImgHelper.Builder builder) {
        this.builder = builder;
        selectImgHelper.setBuilder(builder);
    }

    /**
     * 选择头像配置
     *
     * @return SelectImgHelper.Builder
     */
    public SelectImgHelper.Builder createHeader() {
        SelectImgHelper.Builder builder = new SelectImgHelper.Builder();
        builder.setSingle(true);
        builder.setCrop(true);
        return builder;
    }

    /**
     * 图片多选配置
     *
     * @param : maxLength
     * @return : SelectImgHelper.Builder
     */
    public SelectImgHelper.Builder createMultiple(int maxLength) {
        SelectImgHelper.Builder builder = new SelectImgHelper.Builder();
        builder.setSingle(false);
        builder.setCrop(false);
        builder.setMaxLength(maxLength);
        return builder;
    }

    /**
     * 设置最大选择张数
     *
     * @param maxLength
     */
    public void setMaxLength(int maxLength) {
        if (builder != null) {
            builder.setMaxLength(maxLength);
        }
    }
}
