package com.example.jingbin.cloudreader.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import cc.shinichi.library.glide.sunfusheng.progress.ProgressManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import java.io.InputStream;

/**
 * @author 工藤
 * @email gougou@16fan.com
 * com.example.jingbin.cloudreader.utils
 * create at 2019/1/8  15:47
 * description:
 */

@GlideModule public class MyGlideModule extends AppGlideModule {

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);

        // 替换底层网络框架为okhttp3，这步很重要！
        // 如果您的app中已经存在了自定义的GlideModule，您只需要把这一行代码，添加到对应的重载方法中即可。
        registry.replace(GlideUrl.class, InputStream.class,
            new OkHttpUrlLoader.Factory(ProgressManager.getOkHttpClient()));
    }
}