package com.example.jingbin.cloudreader.adapter;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewAdapter;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewHolder;
import com.example.jingbin.cloudreader.bean.book.BooksBean;
import com.example.jingbin.cloudreader.databinding.ItemBookBinding;
import com.example.jingbin.cloudreader.utils.DensityUtil;
import com.example.jingbin.cloudreader.utils.DialogBuild;
import com.example.jingbin.cloudreader.utils.PerfectClickListener;
import java.util.List;

/**
 * Created by jingbin on 2016/11/25.
 */

public class WanBookAdapter extends BaseRecyclerViewAdapter<BooksBean> {

    private int width;
    private OnClickInterface listener;

    public WanBookAdapter() {
        int px = DensityUtil.dip2px(48);
        width = (DensityUtil.getDisplayWidth() - px) / 3;
    }

    @NonNull @Override public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_book);
    }

    public void setOnClickListener(OnClickInterface listener) {
        this.listener = listener;
    }

    public interface OnClickInterface {
        void onClick(BooksBean bean, ImageView view);
    }

    private class ViewHolder extends BaseRecyclerViewHolder<BooksBean, ItemBookBinding> {

        ViewHolder(ViewGroup context, int layoutId) {
            super(context, layoutId);
        }

        @Override public void onBindViewHolder(final BooksBean book, final int position) {
            if (book != null) {
                binding.setBean(book);
                DensityUtil.formatHeight(binding.ivTopPhoto, width, 0.703f, 1);
                binding.cvTopBook.setOnClickListener(new PerfectClickListener() {
                    @Override protected void onNoDoubleClick(View v) {
                        if (listener != null) {
                            listener.onClick(book, binding.ivTopPhoto);
                        }
                    }
                });
                binding.cvTopBook.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override public boolean onLongClick(View v) {
                        List<String> author = book.getAuthor();
                        String authorOne = "";
                        if (author != null && author.size() > 0) {
                            authorOne = author.get(0);
                        }
                        String title = "";
                        if (!TextUtils.isEmpty(authorOne)) {
                            title = authorOne + "： ";
                        }
                        title = title + "《" + book.getTitle() + "》";
                        DialogBuild.showCustom(v, title, "查看详情", new DialogInterface.OnClickListener() {
                            @Override public void onClick(DialogInterface dialog, int which) {
                                if (listener != null) {
                                    listener.onClick(book, binding.ivTopPhoto);
                                }
                            }
                        });
                        return false;
                    }
                });
            }
        }
    }
}
