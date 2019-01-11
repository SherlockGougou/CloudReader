package com.example.jingbin.cloudreader.ui.wan.child;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.CollectUrlAdapter;
import com.example.jingbin.cloudreader.base.BaseFragment;
import com.example.jingbin.cloudreader.bean.CollectUrlBean;
import com.example.jingbin.cloudreader.databinding.FragmentWanAndroidBinding;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.utils.ToastUtil;
import com.example.jingbin.cloudreader.viewmodel.wan.CollectLinkModel;

/**
 * @author jingbin
 * @date 2018/09/27.
 * @description 网址
 */
public class CollectLinkFragment extends BaseFragment<CollectLinkModel, FragmentWanAndroidBinding> {

    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    private CollectUrlAdapter mAdapter;

    public static CollectLinkFragment newInstance() {
        return new CollectLinkFragment();
    }

    @Override public int setContent() {
        return R.layout.fragment_wan_android;
    }

    @Override public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showContentView();
        initRefreshView();

        // 准备就绪
        mIsPrepared = true;
        loadData();
    }

    private void initRefreshView() {
        bindingView.srlWan.setColorSchemeColors(CommonUtils.getColor(R.color.colorTheme));
        bindingView.xrvWan.setLayoutManager(new LinearLayoutManager(parentActivity));
        bindingView.xrvWan.setPullRefreshEnabled(false);
        bindingView.xrvWan.clearHeader();
        mAdapter = new CollectUrlAdapter(parentActivity);
        bindingView.xrvWan.setAdapter(mAdapter);
        bindingView.srlWan.setOnRefreshListener(() -> bindingView.srlWan.postDelayed(this::getCollectUrlList, 300));
    }

    @Override protected void loadData() {
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }

        bindingView.srlWan.setRefreshing(true);
        bindingView.srlWan.postDelayed(this::getCollectUrlList, 150);
    }

    private void getCollectUrlList() {
        viewModel.getCollectUrlList().observe(this, new Observer<CollectUrlBean>() {
            @Override public void onChanged(@Nullable CollectUrlBean bean) {
                showContentView();
                if (bindingView.srlWan.isRefreshing()) {
                    bindingView.srlWan.setRefreshing(false);
                }
                if (bean != null && bean.getData() != null && bean.getData().size() > 0) {
                    mAdapter.clear();
                    mAdapter.addAll(bean.getData());
                    mAdapter.notifyDataSetChanged();
                    bindingView.xrvWan.refreshComplete();
                    bindingView.xrvWan.noMoreLoading();

                    mIsFirst = false;
                } else {
                    bindingView.xrvWan.refreshComplete();
                    ToastUtil.showToastLong("还没有收藏网址哦~");
                }
            }
        });
    }

    @Override protected void onRefresh() {
        bindingView.srlWan.setRefreshing(true);
        getCollectUrlList();
    }
}
