package com.xulc.wanandroid.ui.query;

import com.xulc.wanandroid.base.BaseObserver;
import com.xulc.wanandroid.base.BasePresenter;
import com.xulc.wanandroid.base.BaseResponse;
import com.xulc.wanandroid.bean.HotKey;
import com.xulc.wanandroid.net.RetrofitManager;
import com.xulc.wanandroid.utils.RxSchedulers;

import java.util.List;

import javax.inject.Inject;

/**
 * Date：2018/5/21
 * Desc：
 * Created by xuliangchun.
 */

public class QueryPresenter extends BasePresenter<QueryContract.View> implements QueryContract.Presenter {

    @Inject
    public QueryPresenter() {
    }

    @Override
    public void loadHotKey() {
        RetrofitManager.getApiService().getHotKey()
                .compose(RxSchedulers.<BaseResponse<List<HotKey>>>applySchedulers())
                .subscribe(new BaseObserver<List<HotKey>>() {
                    @Override
                    protected void onSuccess(BaseResponse<List<HotKey>> listBaseResponse) {
                        mView.setHotKey(listBaseResponse.getData());
                    }

                    @Override
                    protected void onFail(BaseResponse<List<HotKey>> listBaseResponse) {

                    }
                });
    }
}
