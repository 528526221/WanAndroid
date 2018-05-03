package com.xulc.wanandroid.ui.Collect;

import com.xulc.wanandroid.base.BaseContract;
import com.xulc.wanandroid.bean.ArticleData;

/**
 * Date：2018/5/3
 * Desc：
 * Created by xuliangchun.
 */

public interface CollectContract {
    interface View extends BaseContract.BaseView{
        void setCollectList(boolean isRefresh, ArticleData data);
        void removeCollect(int position);
    }

    interface Presenter extends BaseContract.BasePresenter<CollectContract.View>{
        void loadCollectList();
        void refresh();
        void loadMore();
        void removeCollect(int position, ArticleData.Article item);
    }
}
