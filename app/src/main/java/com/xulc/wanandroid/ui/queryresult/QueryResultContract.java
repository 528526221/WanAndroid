package com.xulc.wanandroid.ui.queryresult;

import com.xulc.wanandroid.base.BaseContract;
import com.xulc.wanandroid.bean.ArticleData;

/**
 * Date：2018/5/21
 * Desc：
 * Created by xuliangchun.
 */

public interface QueryResultContract {
    interface View extends BaseContract.BaseView{
        void setQueryResult(boolean isRefresh,ArticleData data);
        void updateCollectArticle(int position, ArticleData.Article item);
    }

    interface Presenter extends BaseContract.BasePresenter<QueryResultContract.View>{
        void refresh(String k);
        void loadMore(String k);
        void collectArticle(int position, ArticleData.Article item);

    }
}
