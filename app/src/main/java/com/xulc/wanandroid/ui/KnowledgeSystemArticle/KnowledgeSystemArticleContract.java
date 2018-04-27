package com.xulc.wanandroid.ui.KnowledgeSystemArticle;

import com.xulc.wanandroid.base.BaseContract;
import com.xulc.wanandroid.bean.ArticleData;

/**
 * Date：2018/4/16
 * Desc：
 * Created by xuliangchun.
 */

public interface KnowledgeSystemArticleContract {
    interface View extends BaseContract.BaseView{

        void setArticles(ArticleData data,boolean isRefresh);

        void updateCollectArticle(int position, ArticleData.Article item);
    }

    interface Presenter extends BaseContract.BasePresenter<KnowledgeSystemArticleContract.View>{
        void refresh(int cid);
        void loadMore(int cid);

        void collectArticle(int position, ArticleData.Article item);
    }
}
