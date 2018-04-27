package com.xulc.wanandroid.ui.index;

import com.xulc.wanandroid.base.BaseContract;
import com.xulc.wanandroid.bean.ArticleData;
import com.xulc.wanandroid.bean.Banner;

import java.util.List;

/**
 * Date：2018/4/11
 * Desc：
 * Created by xuliangchun.
 */

public interface IndexContract {
    interface View extends BaseContract.BaseView{
        void setHomeArticles(boolean isRefresh,ArticleData data);
        void setHomeBanners(List<Banner> banners);

        void updateCollectArticle(int position, ArticleData.Article item);
        void collectError(String error);
    }

    interface Presenter extends BaseContract.BasePresenter<IndexContract.View>{
        void loadHomeArticles();
        void loadHomeBanners();
        void collectArticle(int position, ArticleData.Article item);
        void refresh();
        void loadMore();
    }
}
