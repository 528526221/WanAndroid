package com.xulc.wanandroid.ui.index;

import com.xulc.wanandroid.base.BaseObserver;
import com.xulc.wanandroid.base.BasePresenter;
import com.xulc.wanandroid.base.BaseResponse;
import com.xulc.wanandroid.bean.ArticleData;
import com.xulc.wanandroid.bean.Banner;
import com.xulc.wanandroid.net.RetrofitManager;
import com.xulc.wanandroid.utils.RxSchedulers;

import java.util.List;

/**
 * Date：2018/4/11
 * Desc：
 * Created by xuliangchun.
 */

public class IndexPresenter extends BasePresenter<IndexContract.View> implements IndexContract.Presenter {
    private int mPage = 0;

    @Override
    public void loadHomeArticles() {
        RetrofitManager.getApiService().getHomeArticles(mPage)
                .compose(RxSchedulers.<BaseResponse<ArticleData>>applySchedulers())
                .subscribe(new BaseObserver<ArticleData>() {
                    @Override
                    protected void onSuccess(BaseResponse<ArticleData> articleDataBaseResponse) {
                        mView.setHomeArticles(mPage == 0, articleDataBaseResponse.getData());
                    }

                    @Override
                    protected void onFail(BaseResponse<ArticleData> articleDataBaseResponse) {

                    }
                });
    }

    @Override
    public void loadHomeBanners() {
        RetrofitManager.getApiService().getHomeBanners()
                .compose(RxSchedulers.<BaseResponse<List<Banner>>>applySchedulers())
                .subscribe(new BaseObserver<List<Banner>>() {
                    @Override
                    protected void onSuccess(BaseResponse<List<Banner>> listBaseResponse) {
                        mView.setHomeBanners(listBaseResponse.getData());
                    }

                    @Override
                    protected void onFail(BaseResponse<List<Banner>> listBaseResponse) {
                    }
                });
    }

    @SuppressWarnings("unchecked")
    @Override
    public void collectArticle(final int position, final ArticleData.Article item) {

        if (item.isCollect()) {
            RetrofitManager.getApiService().removeCollectArticle(item.getId())
                    .compose(RxSchedulers.<BaseResponse>applySchedulers())
                    .subscribe(new BaseObserver() {
                        @Override
                        protected void onSuccess(BaseResponse baseResponse) {
                            item.setCollect(false);
                            mView.updateCollectArticle(position, item);
                        }

                        @Override
                        protected void onFail(BaseResponse baseResponse) {
                            mView.collectError(baseResponse.getErrorMsg());

                        }
                    });
        } else {
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
    public void refresh() {
        mPage = 0;
        loadHomeBanners();
        loadHomeArticles();
    }

    @Override
    public void loadMore() {
        mPage++;
        loadHomeArticles();
    }


}
