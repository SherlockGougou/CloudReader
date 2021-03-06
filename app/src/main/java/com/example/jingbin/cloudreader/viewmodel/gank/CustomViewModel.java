package com.example.jingbin.cloudreader.viewmodel.gank;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import com.example.http.HttpUtils;
import com.example.jingbin.cloudreader.bean.GankIoDataBean;
import com.example.jingbin.cloudreader.data.model.GankOtherModel;
import com.example.jingbin.cloudreader.http.RequestImpl;
import rx.Subscription;

/**
 * 干货订制页面ViewModel
 *
 * @author jingbin
 * @data 2018/1/18
 */

public class CustomViewModel extends AndroidViewModel {

    private final GankOtherModel mModel;
    private int mPage = 1;
    private String mType;

    public CustomViewModel(@NonNull Application application) {
        super(application);
        mModel = new GankOtherModel();
    }

    public MutableLiveData<GankIoDataBean> loadCustomData() {
        final MutableLiveData<GankIoDataBean> data = new MutableLiveData<>();
        mModel.setData(mType, mPage, HttpUtils.per_page_more);
        mModel.getGankIoData(new RequestImpl() {
            @Override public void loadSuccess(Object object) {
                data.setValue((GankIoDataBean) object);
            }

            @Override public void loadFailed() {
                if (mPage > 1) {
                    mPage--;
                }
                data.setValue(null);
            }

            @Override public void addSubscription(Subscription subscription) {
            }
        });
        return data;
    }

    public int getPage() {
        return mPage;
    }

    public void setPage(int mPage) {
        this.mPage = mPage;
    }

    public void setType(String mType) {
        this.mType = mType;
    }
}
