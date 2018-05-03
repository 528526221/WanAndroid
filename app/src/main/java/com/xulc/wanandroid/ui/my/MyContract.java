package com.xulc.wanandroid.ui.my;

import com.xulc.wanandroid.base.BaseContract;

/**
 * Date：2018/4/16
 * Desc：
 * Created by xuliangchun.
 */

public interface MyContract {
    interface View extends BaseContract.BaseView{
        void logoutSuccess();
        void logoutFail();
    }

    interface Presenter extends BaseContract.BasePresenter<MyContract.View>{
        void logout();
    }
}
