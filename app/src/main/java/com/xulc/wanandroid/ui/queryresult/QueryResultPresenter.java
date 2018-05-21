package com.xulc.wanandroid.ui.queryresult;

import com.xulc.wanandroid.base.BaseObserver;
import com.xulc.wanandroid.base.BasePresenter;
import com.xulc.wanandroid.base.BaseResponse;
import com.xulc.wanandroid.bean.ArticleData;
import com.xulc.wanandroid.net.RetrofitManager;
import com.xulc.wanandroid.utils.RxSchedulers;

import javax.inject.Inject;

/**
 * Date：2018/5/21
 * Desc：
 * Created by xuliangchun.
 */

public class QueryResultPresenter extends BasePresenter<QueryResultContract.View> implements QueryResultContract.Presenter {
    private int page;

    @Inject
    public QueryResultPresenter() {
        this.page = 0;
    }

    @Override
    public void refresh(String k) {
        page = 0;
        loadQueryResult(k);

    }

    @Override
    public void loadMore(String k) {
        page++;
        loadQueryResult(k);
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

    private void loadQueryResult(String k) {
        RetrofitManager.getApiService().query(page,k)
                .compose(RxSchedulers.<BaseResponse<ArticleData>>applySchedulers())
                .subscribe(new BaseObserver<ArticleData>() {
                    @Override
                    protected void onSuccess(BaseResponse<ArticleData> articleDataBaseResponse) {
                        mView.setQueryResult(page == 0,articleDataBaseResponse.getData());
                    }

                    @Override
                    protected void onFail(BaseResponse<ArticleData> articleDataBaseResponse) {

                    }
                });
    }
}
