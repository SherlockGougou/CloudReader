package com.example.jingbin.cloudreader.ui.wan.child;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.NaviAdapter;
import com.example.jingbin.cloudreader.adapter.NaviContentAdapter;
import com.example.jingbin.cloudreader.base.BaseFragment;
import com.example.jingbin.cloudreader.bean.wanandroid.NaviJsonBean;
import com.example.jingbin.cloudreader.databinding.FragmentNaviBinding;
import com.example.jingbin.cloudreader.viewmodel.wan.NaviViewModel;

/**
 * @author jingbin
 * @date 2018/10/8.
 * @description 导航数据
 */
public class NaviFragment extends BaseFragment<NaviViewModel, FragmentNaviBinding> {

    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    private NaviAdapter mNaviAdapter;
    private NaviContentAdapter mContentAdapter;
    private int oldPosition = 0;

    public static NaviFragment newInstance() {
        return new NaviFragment();
    }

    @Override public int setContent() {
        return R.layout.fragment_navi;
    }

    @Override public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRefreshView();

        // 准备就绪
        mIsPrepared = true;
        loadData();
    }

    private void initRefreshView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(parentActivity);
        bindingView.xrvNavi.setLayoutManager(layoutManager);
        mNaviAdapter = new NaviAdapter();
        bindingView.xrvNavi.setAdapter(mNaviAdapter);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(parentActivity);
        bindingView.xrvNaviDetail.setLayoutManager(layoutManager2);
        mContentAdapter = new NaviContentAdapter(parentActivity);
        bindingView.xrvNaviDetail.setAdapter(mContentAdapter);

        mNaviAdapter.setOnSelectListener(new NaviAdapter.OnSelectListener() {
            @Override public void onSelected(int position) {
                layoutManager2.scrollToPositionWithOffset(position, 0);
            }
        });
        bindingView.xrvNaviDetail.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstPosition = layoutManager2.findFirstVisibleItemPosition();
                if (oldPosition != firstPosition) {
                    bindingView.xrvNavi.smoothScrollToPosition(firstPosition);
                    mNaviAdapter.setSelected(firstPosition);
                    oldPosition = firstPosition;
                }
            }
        });
    }

    @Override protected void loadData() {
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }
        loadCustomData();
    }

    private void loadCustomData() {
        viewModel.getNaviJson().observe(this, new android.arch.lifecycle.Observer<NaviJsonBean>() {
            @Override public void onChanged(@Nullable NaviJsonBean naviJsonBean) {
                if (naviJsonBean != null && naviJsonBean.getData() != null && naviJsonBean.getData().size() > 0) {

                    showContentView();
                    mNaviAdapter.clear();
                    mNaviAdapter.addAll(naviJsonBean.getData());
                    mNaviAdapter.notifyDataSetChanged();
                    mNaviAdapter.setSelected(0);

                    mContentAdapter.clear();
                    mContentAdapter.addAll(naviJsonBean.getData());
                    mContentAdapter.notifyDataSetChanged();

                    mIsFirst = false;
                } else {
                    showError();
                }
            }
        });
    }

    @Override protected void onRefresh() {
        loadCustomData();
    }
}
