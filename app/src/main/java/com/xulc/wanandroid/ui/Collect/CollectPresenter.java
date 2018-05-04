package com.xulc.wanandroid.ui.Collect;

import com.xulc.wanandroid.base.BaseObserver;
import com.xulc.wanandroid.base.BasePresenter;
import com.xulc.wanandroid.base.BaseResponse;
import com.xulc.wanandroid.bean.ArticleData;
import com.xulc.wanandroid.net.RetrofitManager;
import com.xulc.wanandroid.utils.RxSchedulers;

/**
 * Date：2018/5/3
 * Desc：
 * Created by xuliangchun.
 */

public class CollectPresenter extends BasePresenter<CollectContract.View> implements CollectContract.Presenter {
    private int mPage = 0;
    @Override
    public void loadCollectList() {
        RetrofitManager.getApiService().getCollectList(mPage)
                .compose(RxSchedulers.<BaseResponse<ArticleData>>applySchedulers())
                .subscribe(new BaseObserver<ArticleData>() {
                    @Override
                    protected void onSuccess(BaseResponse<ArticleData> articleDataBaseResponse) {
                        mView.setCollectList(mPage == 0,articleDataBaseResponse.getData());
                    }

                    @Override
                    protected void onFail(BaseResponse<ArticleData> articleDataBaseResponse) {

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

    @SuppressWarnings("unchecked")
    @Override
    public void removeCollect(final int position, ArticleData.Article item) {
        RetrofitManager.getApiService().removeCollectArticle(item.getId(),item.getOriginId())
                .compose(RxSchedulers.<BaseResponse>applySchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    protected void onSuccess(BaseResponse baseResponse) {
                        mView.removeCollect(position);
                    }

                    @Override
                    protected void onFail(BaseResponse baseResponse) {

                    }
                });
    }
}
