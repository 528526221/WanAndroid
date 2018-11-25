package com.xulc.wanandroid.ui.KnowledgeSystemArticle;

import android.util.Log;

import com.xulc.wanandroid.base.BaseObserver;
import com.xulc.wanandroid.base.BasePresenter;
import com.xulc.wanandroid.base.BaseResponse;
import com.xulc.wanandroid.bean.ArticleData;
import com.xulc.wanandroid.net.RetrofitManager;
import com.xulc.wanandroid.utils.RxSchedulers;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Date：2018/4/16
 * Desc：
 * Created by xuliangchun.
 */

public class KnowledgeSystemArticlePresenter extends BasePresenter<KnowledgeSystemArticleContract.View> implements KnowledgeSystemArticleContract.Presenter{
    private int page;
    private boolean isRefresh;

    @Inject
    public KnowledgeSystemArticlePresenter() {
    }

    private void loadKnowledgeSystemArticles(int cid) {
//        RetrofitManager.getApiService().getKnowledgeArticles(page,cid)
//                .compose(RxSchedulers.<BaseResponse<ArticleData>>applySchedulers())
//                .subscribe(new BaseObserver<ArticleData>() {
//                    @Override
//                    protected void onSuccess(BaseResponse<ArticleData> articleDataBaseResponse) {
//                        mView.setArticles(articleDataBaseResponse.getData(), isRefresh);
//                    }
//
//                    @Override
//                    protected void onFail(BaseResponse<ArticleData> articleDataBaseResponse) {
//
//                    }
//                });
        RetrofitManager.getApiService().getKnowledgeArticles(page,cid)
                .compose(RxSchedulers.<BaseResponse<ArticleData>>applySchedulers())
                .subscribe(new Consumer<BaseResponse<ArticleData>>() {
                    @Override
                    public void accept(@NonNull BaseResponse<ArticleData> articleDataBaseResponse) throws Exception {
                        mView.setArticles(articleDataBaseResponse.getData(), page == 1);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.i("xlc","我们出错了！！！！！！！！！！");
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

    @Override
    public void detachView() {
        super.detachView();
    }
}
