package com.xulc.wanandroid.ui.register;

import com.xulc.wanandroid.base.BaseContract;

/**
 * Date：2018/4/16
 * Desc：
 * Created by xuliangchun.
 */

public interface RegisterContract {
    interface View extends BaseContract.BaseView{
        void registerSuccess();
        void registerFail(String msg);
        void inputError(String error);
    }
    interface Presenter extends BaseContract.BasePresenter<RegisterContract.View> {
        void registerAccount(String userName, String password, String repassword);
    }
}
