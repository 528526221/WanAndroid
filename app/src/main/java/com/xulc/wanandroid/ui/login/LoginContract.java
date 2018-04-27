package com.xulc.wanandroid.ui.login;

import com.xulc.wanandroid.base.BaseContract;

/**
 * Date：2018/4/17
 * Desc：
 * Created by xuliangchun.
 */

public interface LoginContract {
    interface View extends BaseContract.BaseView{
        void loginSuccess();
        void loginFail(String error);
        void inputError(String error);
    }
    interface Presenter extends BaseContract.BasePresenter<LoginContract.View>{
        void login(String username,String password);
    }
}
