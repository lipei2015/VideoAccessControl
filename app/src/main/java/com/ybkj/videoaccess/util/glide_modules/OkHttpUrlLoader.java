package com.ybkj.videoaccess.util.glide_modules;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;

import java.io.InputStream;

/**
 * http://blog.csdn.net/u012424449/article/details/53861203
 *
 * Createdby NETWORK on 2017/8/22.
 */

public class OkHttpUrlLoader implements ModelLoader<GlideUrl, InputStream> {

    @Nullable
    @Override
    public LoadData<InputStream> buildLoadData(@NonNull GlideUrl glideUrl, int width, int height,
            @NonNull Options options) {
        return null;
    }

    @Override
    public boolean handles(@NonNull GlideUrl glideUrl) {
        return false;
    }

}
