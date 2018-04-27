package com.xulc.wanandroid.base;

/**
 * Date：2018/2/26
 * Desc：Persenter关联了生命周期
 * Created by xuliangchun.
 */

public class BasePresenter<T extends BaseContract.BaseView> implements BaseContract.BasePresenter<T>{
    protected T mView;

    @Override
        public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        if (mView != null) {
            mView = null;
        }
    }
}
