package com.xulc.wanandroid.ui.knowledgesystem;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xulc.wanandroid.R;
import com.xulc.wanandroid.bean.KnowledgeSystem;

/**
 * Date：2018/4/12
 * Desc：
 * Created by xuliangchun.
 */

public class KnowledgeSystemTopAdapter extends BaseQuickAdapter<KnowledgeSystem,BaseViewHolder> {
    public KnowledgeSystemTopAdapter() {
        super(R.layout.item_knowledge_system_top);
    }

    @Override
    protected void convert(BaseViewHolder helper, KnowledgeSystem item) {
        helper.setText(R.id.tvTopCategoryName,item.getName())
                .setVisible(R.id.ivSelectedIcon,item.isSelected());

        if (item.isSelected()){
            helper.itemView.setBackgroundResource(R.color.white);
        }else {
            helper.itemView.setBackgroundResource(R.color.transparent);
        }
    }
}
