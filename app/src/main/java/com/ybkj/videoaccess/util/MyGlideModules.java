package com.ybkj.videoaccess.util;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.GlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.ybkj.videoaccess.app.ConstantSys;

/**
 * 自定义的GlideModules
 * <p>
 * Created by HH on 2016/8/27.
 */
public class MyGlideModules implements GlideModule {
    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide,
            @NonNull Registry registry) {
        //registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader());
        /*registry.replace(GlideUrl.class, InputStream.class,
                new ModelLoaderFactory<GlideUrl, InputStream>() {
                    @NonNull
                    @Override
                    public ModelLoader<GlideUrl, InputStream> build(
                            @NonNull MultiModelLoaderFactory multiFactory) {
                        return null;
                    }

                    @Override
                    public void teardown() {

                    }
                });*/
    }

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //创建缓存文件夹
        builder.setDefaultRequestOptions(new RequestOptions().format(DecodeFormat.PREFER_RGB_565));
        FileUtil.createMoreFiles(ConstantSys.CACHE_IMG);
        builder.setDiskCache(
                new DiskLruCacheFactory(FileUtil.getSdCardPath() + ConstantSys.CACHE_IMG, 104857600)
        );
    }
}
