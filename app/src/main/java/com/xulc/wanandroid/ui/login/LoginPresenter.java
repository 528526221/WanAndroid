package com.xulc.wanandroid.ui.login;

import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.xulc.wanandroid.base.BasePresenter;
import com.xulc.wanandroid.bean.BaseResponse;
import com.xulc.wanandroid.bean.User;
import com.xulc.wanandroid.net.ApiService;
import com.xulc.wanandroid.net.Constant;
import com.xulc.wanandroid.net.RetrofitManager;
import com.xulc.wanandroid.utils.RxSchedulers;
import com.xulc.wanandroid.utils.UserUtil;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Date：2018/4/17
 * Desc：
 * Created by xuliangchun.
 */

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    @Override
    public void login(String username, String password) {
        if (TextUtils.isEmpty(username)){
            mView.inputError("用户名嘞~");
            return;
        }
        if (TextUtils.isEmpty(password)){
            mView.inputError("密码嘞~");
            return;
        }
        RetrofitManager.create(ApiService.class).loginAccount(username,password)
                .compose(RxSchedulers.<BaseResponse<User>>applySchedulers())
                .subscribe(new Consumer<BaseResponse<User>>() {
                    @Override
                    public void accept(@NonNull BaseResponse<User> userBaseResponse) throws Exception {
                        if (userBaseResponse.getErrorCode()==0){
                            UserUtil.getInstance().setUser(userBaseResponse.getData());
                            SPUtils.getInstance().put(Constant.IS_LOGIN,true);
                            mView.loginSuccess();
                        }else {
                            mView.loginFail(userBaseResponse.getErrorMsg());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                });
    }
}
