package com.xulc.wanandroid.ui.Collect;

import com.xulc.wanandroid.base.BasePresenter;
import com.xulc.wanandroid.bean.ArticleData;
import com.xulc.wanandroid.bean.BaseResponse;
import com.xulc.wanandroid.net.ApiService;
import com.xulc.wanandroid.net.RetrofitManager;
import com.xulc.wanandroid.utils.RxSchedulers;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Date：2018/5/3
 * Desc：
 * Created by xuliangchun.
 */

public class CollectPresenter extends BasePresenter<CollectContract.View> implements CollectContract.Presenter {
    private int mPage = 0;
    @Override
    public void loadCollectList() {
        RetrofitManager.create(ApiService.class).getCollectList(mPage)
                .compose(RxSchedulers.<BaseResponse<ArticleData>>applySchedulers())
                .subscribe(new Consumer<BaseResponse<ArticleData>>() {
                    @Override
                    public void accept(@NonNull BaseResponse<ArticleData> articleDataBaseResponse) throws Exception {
                        mView.setCollectList(mPage == 0,articleDataBaseResponse.getData());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                });
    }

    @Override
    public void refresh() {
        mPage = 0;
        loadCollectList();
    }

    @Override
    public void loadMore() {
        mPage++;
        loadCollectList();

    }

    @Override
    public void removeCollect(final int position, ArticleData.Article item) {
        RetrofitManager.create(ApiService.class).removeCollectArticle(item.getId(),item.getOriginId())
                .compose(RxSchedulers.<BaseResponse>applySchedulers())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(@NonNull BaseResponse baseResponse) throws Exception {
                        mView.removeCollect(position);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                });
    }
}
