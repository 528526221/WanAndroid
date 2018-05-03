package com.xulc.wanandroid.ui.index;

import android.util.Log;

import com.xulc.wanandroid.base.BasePresenter;
import com.xulc.wanandroid.bean.ArticleData;
import com.xulc.wanandroid.bean.Banner;
import com.xulc.wanandroid.bean.BaseResponse;
import com.xulc.wanandroid.net.ApiService;
import com.xulc.wanandroid.net.RetrofitManager;
import com.xulc.wanandroid.utils.RxSchedulers;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Date：2018/4/11
 * Desc：
 * Created by xuliangchun.
 */

public class IndexPresenter extends BasePresenter<IndexContract.View> implements IndexContract.Presenter{
    private int mPage = 0;

    @Override
    public void loadHomeArticles() {
        RetrofitManager.create(ApiService.class).getHomeArticles(mPage)
                .compose(RxSchedulers.<BaseResponse<ArticleData>>applySchedulers())
                .subscribe(new Consumer<BaseResponse<ArticleData>>() {
                    @Override
                    public void accept(@NonNull BaseResponse<ArticleData> articleDataBaseResponse) throws Exception {

                        mView.setHomeArticles(mPage == 0,articleDataBaseResponse.getData());

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.i("xlc", throwable.getMessage());

                    }
                });
    }

    @Override
    public void loadHomeBanners() {
        RetrofitManager.create(ApiService.class).getHomeBanners()
                .compose(RxSchedulers.<BaseResponse<List<Banner>>>applySchedulers())
                .subscribe(new Consumer<BaseResponse<List<Banner>>>() {
                    @Override
                    public void accept(@NonNull BaseResponse<List<Banner>> listBaseResponse) throws Exception {
                        mView.setHomeBanners(listBaseResponse.getData());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.i("xlc", throwable.getMessage());
                    }
                });
    }

    @Override
    public void collectArticle(final int position, final ArticleData.Article item) {

        if (item.isCollect()){
            RetrofitManager.create(ApiService.class).removeCollectArticle(item.getId())
                    .compose(RxSchedulers.<BaseResponse>applySchedulers())
                    .subscribe(new Consumer<BaseResponse>() {
                        @Override
                        public void accept(@NonNull BaseResponse baseResponse) throws Exception {
                            if (baseResponse.getErrorCode() == 0){
                                item.setCollect(false);
                                mView.updateCollectArticle(position,item);
                            }else {
                                mView.collectError(baseResponse.getErrorMsg());
                            }

                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            Log.i("xlc", throwable.getMessage());
                        }
                    });
        }else {
            RetrofitManager.create(ApiService.class).addCollectArticle(item.getId())
                    .compose(RxSchedulers.<BaseResponse>applySchedulers())
                    .subscribe(new Consumer<BaseResponse>() {
                        @Override
                        public void accept(@NonNull BaseResponse baseResponse) throws Exception {
                            if (baseResponse.getErrorCode() == 0){
                                item.setCollect(true);
                                mView.updateCollectArticle(position,item);
                            }else {
                                mView.collectError(baseResponse.getErrorMsg());
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            Log.i("xlc", throwable.getMessage());
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
