package com.xulc.wanandroid.ui.KnowledgeSystemArticle;

import com.xulc.wanandroid.base.BaseObserver;
import com.xulc.wanandroid.base.BasePresenter;
import com.xulc.wanandroid.base.BaseResponse;
import com.xulc.wanandroid.bean.ArticleData;
import com.xulc.wanandroid.net.RetrofitManager;
import com.xulc.wanandroid.utils.RxSchedulers;

/**
 * Date：2018/4/16
 * Desc：
 * Created by xuliangchun.
 */

public class KnowledgeSystemArticlePresenter extends BasePresenter<KnowledgeSystemArticleContract.View> implements KnowledgeSystemArticleContract.Presenter{
    private int page;
    private boolean isRefresh;


    private void loadKnowledgeSystemArticles(int cid) {
        RetrofitManager.getApiService().getKnowledgeArticles(page,cid)
                .compose(RxSchedulers.<BaseResponse<ArticleData>>applySchedulers())
                .subscribe(new BaseObserver<ArticleData>() {
                    @Override
                    protected void onSuccess(BaseResponse<ArticleData> articleDataBaseResponse) {
                        mView.setArticles(articleDataBaseResponse.getData(),isRefresh);
                    }

                    @Override
                    protected void onFail(BaseResponse<ArticleData> articleDataBaseResponse) {

                    }
                });

    }

    @Override
    public void refresh(int cid) {
        isRefresh = true;
        page = 0;
        loadKnowledgeSystemArticles(cid);
    }

    @Override
    public void loadMore(int cid) {
        isRefresh = false;
        page++;
        loadKnowledgeSystemArticles(cid);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void collectArticle(final int position, final ArticleData.Article item) {
        if (item.isCollect()){
            RetrofitManager.getApiService().removeCollectArticle(item.getId())
                    .compose(RxSchedulers.<BaseResponse>applySchedulers())
                    .subscribe(new BaseObserver() {
                        @Override
                        protected void onSuccess(BaseResponse baseResponse) {
                            item.setCollect(false);
                            mView.updateCollectArticle(position,item);
                        }

                        @Override
                        protected void onFail(BaseResponse baseResponse) {

                        }
                    });
        }else {
            RetrofitManager.getApiService().addCollectArticle(item.getId())
                    .compose(RxSchedulers.<BaseResponse>applySchedulers())
                    .subscribe(new BaseObserver() {
                        @Override
                        protected void onSuccess(BaseResponse baseResponse) {
                            item.setCollect(true);
                            mView.updateCollectArticle(position, item);
                        }

                        @Override
                        protected void onFail(BaseResponse baseResponse) {

                        }
                    });
        }
    }


}
