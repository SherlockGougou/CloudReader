package com.example.jingbin.cloudreader.viewmodel.wan;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import com.example.jingbin.cloudreader.bean.wanandroid.DuanZiBean;
import com.example.jingbin.cloudreader.data.model.JokeModel;
import java.util.List;
import rx.Subscription;

/**
 * @author jingbin
 * @data 2018/2/8
 * @Description 玩安卓ViewModel
 */

public class JokeViewModel extends AndroidViewModel {

    private final JokeModel mModel;
    private final MutableLiveData<List<DuanZiBean>> data = new MutableLiveData<>();
    private int mPage = 1;
    // 刷新糗事百科
    private boolean isRefreshBK = false;
    private WanNavigator.JokeModelNavigator navigator = new WanNavigator.JokeModelNavigator() {
        @Override public void loadSuccess(List<DuanZiBean> lists) {
            data.setValue(lists);
        }

        @Override public void loadFailed() {
            data.setValue(null);
        }

        @Override public void addSubscription(Subscription subscription) {
        }
    };

    public JokeViewModel(@NonNull Application application) {
        super(application);
        mModel = new JokeModel();
    }

    public MutableLiveData<List<DuanZiBean>> getData() {
        return data;
    }

    public void showQSBKList() {
        mModel.showQSBKList(navigator, mPage);
    }

    public boolean isRefreshBK() {
        return isRefreshBK;
    }

    public void setRefreshBK(boolean refreshBK) {
        isRefreshBK = refreshBK;
    }

    public int getPage() {
        return mPage;
    }

    public void setPage(int mPage) {
        this.mPage = mPage;
    }
}
