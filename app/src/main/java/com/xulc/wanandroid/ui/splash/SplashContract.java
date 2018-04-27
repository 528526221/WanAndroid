package com.xulc.wanandroid.ui.splash;

import com.xulc.wanandroid.base.BaseContract;

/**
 * Date：2018/2/28
 * Desc：契约类
 * Created by xuliangchun.
 */

public interface SplashContract {
    interface View extends BaseContract.BaseView{
        void updateTip(String tip);
        void skipToMain();
    }
    interface Presenter extends BaseContract.BasePresenter<SplashContract.View>{
        void startCountDown();
    }
}
