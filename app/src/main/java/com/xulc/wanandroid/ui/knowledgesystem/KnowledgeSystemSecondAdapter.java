package com.xulc.wanandroid.ui.knowledgesystem;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xulc.wanandroid.R;
import com.xulc.wanandroid.bean.KnowledgeSystem;

/**
 * Date：2018/4/13
 * Desc：
 * Created by xuliangchun.
 */

public class KnowledgeSystemSecondAdapter extends BaseQuickAdapter<KnowledgeSystem,BaseViewHolder> {
    public KnowledgeSystemSecondAdapter() {
        super(R.layout.item_knowledge_system_second);
    }

    @Override
    protected void convert(BaseViewHolder helper, KnowledgeSystem item) {
        helper.setText(R.id.tvSecondCategoryName,item.getName());
    }
}
