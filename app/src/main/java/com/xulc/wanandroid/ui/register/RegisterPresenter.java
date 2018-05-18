package com.xulc.wanandroid.ui.register;

import android.text.TextUtils;

import com.xulc.wanandroid.base.BaseObserver;
import com.xulc.wanandroid.base.BasePresenter;
import com.xulc.wanandroid.base.BaseResponse;
import com.xulc.wanandroid.bean.User;
import com.xulc.wanandroid.net.RetrofitManager;
import com.xulc.wanandroid.utils.RxSchedulers;

import javax.inject.Inject;

/**
 * Date：2018/4/16
 * Desc：
 * Created by xuliangchun.
 */

public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter{

    @Inject
    public RegisterPresenter() {
    }

    @Override
    public void registerAccount(String userName, String password, String repassword) {
        if (TextUtils.isEmpty(userName)){
            mView.inputError("用户名嘞~");
            return;
        }
        if (TextUtils.isEmpty(password)){
            mView.inputError("密码嘞~");
            return;
        }
        if (TextUtils.isEmpty(repassword)){
            mView.inputError("确认密码嘞~");
            return;
        }
        if (!password.equals(repassword)){
            mView.inputError("两次密码不一样哦~");
            return;
        }
        RetrofitManager.getApiService().registerAccount(userName,password,repassword)
                .compose(RxSchedulers.<BaseResponse<User>>applySchedulers())
                .subscribe(new BaseObserver<User>() {
                    @Override
                    protected void onSuccess(BaseResponse<User> userBaseResponse) {
                        mView.registerSuccess();
                    }

                    @Override
                    protected void onFail(BaseResponse<User> userBaseResponse) {
                        mView.registerFail(userBaseResponse.getErrorMsg());
                    }
                });
    }
}
