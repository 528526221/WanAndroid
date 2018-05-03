package com.xulc.wanandroid.ui.Collect;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xulc.wanandroid.R;
import com.xulc.wanandroid.bean.ArticleData;

/**
 * Date：2018/5/3
 * Desc：
 * Created by xuliangchun.
 */

public class CollectAdapter extends BaseQuickAdapter<ArticleData.Article,BaseViewHolder> {
    private Context context;


    public CollectAdapter(Context context) {
        super(R.layout.item_collect,null);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleData.Article item) {
        helper.setText(R.id.tvAuthor, item.getAuthor())
                .setText(R.id.tvDate, item.getNiceDate())
                .setText(R.id.tvTitle, item.getTitle())
                .setText(R.id.tvChapter, item.getSuperChapterName() + "/" + item.getChapterName())
                .setVisible(R.id.tvIdentify, item.isFresh() || item.getTags().size() > 0)
                .setText(R.id.tvIdentify, item.isFresh() ? "新" : (item.getTags().size() > 0 ? item.getTags().get(0).getName() : ""))
                .setBackgroundRes(R.id.tvIdentify, item.isFresh() ? R.drawable.shape_identify_red : R.drawable.shape_identify_green)
                .setTextColor(R.id.tvIdentify, item.isFresh() ? ContextCompat.getColor(context, R.color.red) : ContextCompat.getColor(context, R.color.green))
                .addOnClickListener(R.id.tvIdentify)
                .addOnClickListener(R.id.content)
                .addOnClickListener(R.id.tvRemove);
    }
}
