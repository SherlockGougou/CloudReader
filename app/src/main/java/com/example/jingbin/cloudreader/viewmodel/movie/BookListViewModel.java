package com.example.jingbin.cloudreader.viewmodel.movie;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import com.example.jingbin.cloudreader.bean.book.BookBean;
import com.example.jingbin.cloudreader.http.HttpClient;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author jingbin
 * @data 2018/12/23
 */

public class BookListViewModel extends AndroidViewModel {

    public final ObservableField<String> bookType = new ObservableField<>();
    // 开始请求的角标
    private int mStart = 0;
    // 一次请求的数量
    private int mCount = 18;

    public BookListViewModel(@NonNull Application application) {
        super(application);
    }

    @BindingAdapter("android:textSelection")
    public static void textSelection(AppCompatEditText tv, ObservableField<String> value) {
        if (!TextUtils.isEmpty(tv.getText())) {
            //            tv.setText(value.get());
            // Set the cursor to the end of the text
            tv.setSelection(tv.getText().length());
        }
    }

    public int getStart() {
        return mStart;
    }

    public void setStart(int mStart) {
        this.mStart = mStart;
    }

    public MutableLiveData<BookBean> getBook() {
        final MutableLiveData<BookBean> data = new MutableLiveData<>();
        HttpClient.Builder.getDouBanService()
            .getBook(bookType.get(), mStart, mCount)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<BookBean>() {
                @Override public void onCompleted() {
                }

                @Override public void onError(Throwable e) {
                    data.setValue(null);
                }

                @Override public void onNext(BookBean bookBean) {
                    data.setValue(bookBean);
                }
            });
        return data;
    }

    public void handleNextStart() {
        mStart += mCount;
    }
}
