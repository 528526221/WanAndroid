package com.xulc.wanandroid.ui.my;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xulc.wanandroid.R;

/**
 * Date：2018/4/16
 * Desc：
 * Created by xuliangchun.
 */

public class MyAdapter extends BaseQuickAdapter<MyAdapter.Item,BaseViewHolder> {
    public MyAdapter() {
        super(R.layout.item_my);
    }

    @Override
    protected void convert(BaseViewHolder helper, Item item) {
        helper.setText(R.id.tvItem,item.name)
                .setImageResource(R.id.ivItem,item.icon);
    }


    public static class Item{
        private String name;
        private int icon;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        public Item(String name, int icon) {
            this.name = name;
            this.icon = icon;
        }
    }
}
