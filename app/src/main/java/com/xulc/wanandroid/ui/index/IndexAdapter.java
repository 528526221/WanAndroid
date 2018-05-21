package com.xulc.wanandroid.ui.index;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Html;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xulc.wanandroid.R;
import com.xulc.wanandroid.bean.ArticleData;

/**
 * Date：2018/4/11
 * Desc：
 * Created by xuliangchun.
 */

public class IndexAdapter extends BaseQuickAdapter<ArticleData.Article, BaseViewHolder> {
    private Context context;

    public IndexAdapter(Context context) {
        super(R.layout.item_index);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleData.Article item) {
        helper.setText(R.id.tvAuthor, item.getAuthor())
                .setText(R.id.tvDate, item.getNiceDate())
                .setText(R.id.tvTitle, Html.fromHtml(item.getTitle()))
                .setText(R.id.tvChapter, item.getSuperChapterName() + "/" + item.getChapterName())
                .setVisible(R.id.tvIdentify, item.isFresh() || item.getTags().size() > 0)
                .setText(R.id.tvIdentify, item.isFresh() ? "新" : (item.getTags().size() > 0 ? item.getTags().get(0).getName() : ""))
                .setImageResource(R.id.ivCollect, item.isCollect() ? R.mipmap.collect_focus : R.mipmap.collect)
                .setBackgroundRes(R.id.tvIdentify, item.isFresh() ? R.drawable.shape_identify_red : R.drawable.shape_identify_green)
                .setTextColor(R.id.tvIdentify, item.isFresh() ? ContextCompat.getColor(context, R.color.red) : ContextCompat.getColor(context, R.color.green))
                .addOnClickListener(R.id.ivCollect)
                .addOnClickListener(R.id.tvIdentify);


    }
}
