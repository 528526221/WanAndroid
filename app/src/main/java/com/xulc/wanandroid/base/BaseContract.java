package com.xulc.wanandroid.base;

/**
 * Date：2018/2/28
 * Desc：契约类
 * Created by xuliangchun.
 */

public interface BaseContract {
    interface BaseView{
        void showLoading();
        void hideLoading();
    }

    interface BasePresenter<T extends BaseContract.BaseView>{
        void attachView(T view);

        void detachView();
    }
}
