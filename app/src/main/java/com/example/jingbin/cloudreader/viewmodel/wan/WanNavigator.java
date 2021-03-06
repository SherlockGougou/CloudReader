package com.example.jingbin.cloudreader.viewmodel.wan;

import com.example.jingbin.cloudreader.bean.wanandroid.DuanZiBean;
import java.util.List;
import rx.Subscription;

/**
 * @author jingbin
 * @data 2018/2/8
 * @Description
 */

public interface WanNavigator {

    interface JokeModelNavigator {
        void loadSuccess(List<DuanZiBean> lists);

        void loadFailed();

        void addSubscription(Subscription subscription);
    }

    /**
     * 收藏或取消收藏
     */
    interface OnCollectNavigator {
        void onSuccess();

        void onFailure();
    }
}
