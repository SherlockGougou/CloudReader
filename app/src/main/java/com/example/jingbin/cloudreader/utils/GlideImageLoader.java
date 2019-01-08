package com.example.jingbin.cloudreader.utils;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.jingbin.cloudreader.R;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by jingbin on 2016/11/30.
 * 首页轮播图
 */

public class GlideImageLoader extends ImageLoader {
    @Override public void displayImage(Context context, Object url, ImageView imageView) {
        Glide.with(context)
            .load(url)
            .apply(new RequestOptions()
                .placeholder(R.drawable.shape_bg_loading)
                .error(R.drawable.shape_bg_loading))
            .into(imageView);
    }
}