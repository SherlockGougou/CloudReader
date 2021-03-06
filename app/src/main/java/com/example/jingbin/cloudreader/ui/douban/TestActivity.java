package com.example.jingbin.cloudreader.ui.douban;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.MovieDetailPersonAdapter;
import com.example.jingbin.cloudreader.bean.moviechild.SubjectsBean;
import com.example.jingbin.cloudreader.databinding.ActivityTestBinding;
import com.example.jingbin.cloudreader.view.test.StatusBarUtils;
import java.util.ArrayList;
import java.util.List;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * （已使用：{@link OneMovieDetailActivity} 替代）
 * 第二种电影详情页
 */
@Deprecated public class TestActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Toolbar mToolbar;
    //    private ImageView iv_title_bg;
    //    private RecyclerView mRecyclerView;
    private MovieDetailPersonAdapter mMyAdapter;
    private SubjectsBean subjectsBean;
    private ActivityTestBinding binding;

    /**
     * @param context activity
     * @param positionData bean
     * @param imageView imageView
     */
    public static void start(Activity context, SubjectsBean positionData, ImageView imageView) {
        Intent intent = new Intent(context, TestActivity.class);
        intent.putExtra("bean", positionData);
        //        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(context,
        //                        imageView, CommonUtils.getString(R.string.transition_movie_img));//与xml文件对应

        //        ActivityOptionsCompat options = ActivityOptionsCompat.makeBasic();// 右边进右边出
        //        ActivityCompat.startActivity(context, intent, options.toBundle());
        context.startActivity(intent);
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test);
        if (getIntent() != null) {
            subjectsBean = (SubjectsBean) getIntent().getSerializableExtra("bean");
        }

        initView();
        setData();
    }

    private void setData() {
        if (subjectsBean != null) {

            // 高斯模糊背景
            Glide.with(this)
                .load(subjectsBean.getImages().getLarge())
                .apply(new RequestOptions().error(R.drawable.stackblur_default))
                .apply(new RequestOptions().transform(new BlurTransformation(this, 12, 5)))
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target,
                        boolean isFirstResource) {
                        return false;
                    }

                    @Override public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                        DataSource dataSource, boolean isFirstResource) {
                        mToolbar.setBackgroundColor(Color.TRANSPARENT);
                        binding.ivTitleBg.setImageAlpha(0);
                        binding.ivTitleBg.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                .into(binding.ivTitleBg);
        }
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setBackgroundColor(Color.TRANSPARENT);
        //        mToolbar.inflateMenu(R.menu.toolbar_right_menu);
        //        mToolbar.setTitle(R.string.title);
        mToolbar.setTitleTextColor(Color.WHITE);

        //        MovieDetailPersonAdapter adapter = new MovieDetailPersonAdapter();
        //        adapter.addAll(objects);

        mMyAdapter = new MovieDetailPersonAdapter();
        mMyAdapter.setData(getData(), subjectsBean);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.xrecyclerTest.setLayoutManager(layoutManager);
        binding.xrecyclerTest.setAdapter(mMyAdapter);

        // toolbar 的高
        int toolbarHeight = mToolbar.getLayoutParams().height;
        Log.i(TAG, "toolbar height:" + toolbarHeight);
        final int headerBgHeight = toolbarHeight + getStatusBarHeight(this);
        Log.i(TAG, "headerBgHeight:" + headerBgHeight);
        ViewGroup.LayoutParams params = binding.ivTitleBg.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = headerBgHeight;

        binding.ivTitleBg.setImageAlpha(0);
        //
        StatusBarUtils.setTranslucentImageHeader(this, 0, mToolbar);

        binding.xrecyclerTest.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                View headerView = null;

                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int firstVisibleItem = manager.findFirstVisibleItemPosition();
                if (firstVisibleItem == 0) {
                    headerView = recyclerView.getChildAt(firstVisibleItem);
                }
                if (headerView == null) {
                    return;
                }

                // 从头部高斯图底部到titlebar底部开始就设置不透明
                float alpha = Math.abs(headerView.getTop()) * 1.0f / (headerView.getHeight() - headerBgHeight);
                Log.e(TAG, "alpha:" + alpha + "top :" + headerView.getTop() + " height: " + (headerView.getHeight()
                    - 2 * headerBgHeight));

                Drawable drawable = binding.ivTitleBg.getDrawable();
                if (drawable != null && alpha <= 1) {
                    drawable.mutate().setAlpha((int) (alpha * 255));
                    binding.ivTitleBg.setImageDrawable(drawable);
                } else {
                    binding.ivTitleBg.setImageDrawable(drawable);
                }
            }
        });
    }

    public List<String> getData() {

        ArrayList<String> list = new ArrayList<>();
        list.add("你");
        list.add("是");
        list.add("导");
        list.add("演");
        list.add("演");
        list.add("演");
        list.add("演");
        list.add("演");
        list.add("演");
        list.add("演");
        list.add("演");
        return list;
    }

    private int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    @Override public void finish() {
        super.finish();
        //        overridePendingTransition(R.anim.push_fade_out, R.anim.push_fade_in);
    }
}
