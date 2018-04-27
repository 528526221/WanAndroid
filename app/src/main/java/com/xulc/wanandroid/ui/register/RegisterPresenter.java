package com.xulc.wanandroid.ui.register;

import android.text.TextUtils;

import com.xulc.wanandroid.base.BasePresenter;
import com.xulc.wanandroid.bean.BaseResponse;
import com.xulc.wanandroid.bean.User;
import com.xulc.wanandroid.net.ApiService;
import com.xulc.wanandroid.net.RetrofitManager;
import com.xulc.wanandroid.utils.RxSchedulers;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Date：2018/4/16
 * Desc：
 * Created by xuliangchun.
 */

public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter{
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
        RetrofitManager.create(ApiService.class).registerAccount(userName,password,repassword)
                .compose(RxSchedulers.<BaseResponse<User>>applySchedulers())
                .subscribe(new Consumer<BaseResponse<User>>() {
                    @Override
                    public void accept(@NonNull BaseResponse<User> registerBaseResponse) throws Exception {
                        if (registerBaseResponse.getErrorCode() == 0 ){
                            mView.registerSuccess();
                        }else {
                            mView.registerFail(registerBaseResponse.getErrorMsg());

                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                });
    }
}
