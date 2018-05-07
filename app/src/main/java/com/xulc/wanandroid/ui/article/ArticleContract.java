package com.xulc.wanandroid.ui.article;

import com.xulc.wanandroid.base.BaseContract;

/**
 * Date：2018/4/11
 * Desc：
 * Created by xuliangchun.
 */

public class ArticleContract {
    interface View extends BaseContract.BaseView{

    }

    interface Presenter extends BaseContract.BasePresenter<ArticleContract.View>{
        void addCollectOutSide(String title,String author,String link);
    }

}
