package com.xulc.wanandroid.ui.KnowledgeSystemArticle;

import android.util.Log;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.xulc.wanandroid.base.BasePresenter;
import com.xulc.wanandroid.bean.ArticleData;
import com.xulc.wanandroid.bean.BaseResponse;
import com.xulc.wanandroid.net.ApiService;
import com.xulc.wanandroid.net.Constant;
import com.xulc.wanandroid.net.RetrofitManager;
import com.xulc.wanandroid.utils.RxSchedulers;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Date：2018/4/16
 * Desc：
 * Created by xuliangchun.
 */

public class KnowledgeSystemArticlePresenter extends BasePresenter<KnowledgeSystemArticleContract.View> implements KnowledgeSystemArticleContract.Presenter{
    private int page;
    private boolean isRefresh;


    private void loadKnowledgeSystemArticles(int cid) {
        RetrofitManager.create(ApiService.class).getKnowledgeArticles(page,cid)
                .compose(RxSchedulers.<BaseResponse<ArticleData>>applySchedulers())
                .subscribe(new Consumer<BaseResponse<ArticleData>>() {
                    @Override
                    public void accept(@NonNull BaseResponse<ArticleData> articleDataBaseResponse) throws Exception {
                        mView.setArticles(articleDataBaseResponse.getData(),isRefresh);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

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

    @Override
    public void collectArticle(final int position, final ArticleData.Article item) {
        if (SPUtils.getInstance().getBoolean(Constant.IS_LOGIN)){
            if (item.isCollect()){
                RetrofitManager.create(ApiService.class).removeCollectArticle(item.getId())
                        .compose(RxSchedulers.<BaseResponse>applySchedulers())
                        .subscribe(new Consumer<BaseResponse>() {
                            @Override
                            public void accept(@NonNull BaseResponse baseResponse) throws Exception {
                                item.setCollect(false);
                                mView.updateCollectArticle(position,item);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                Log.i("xlc", throwable.getMessage());
                            }
                        });
            }
        }else {
            ToastUtils.showShort("请先登录");
        }
    }


}
