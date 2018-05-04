package com.xulc.wanandroid.ui.login;

import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.xulc.wanandroid.base.BaseObserver;
import com.xulc.wanandroid.base.BasePresenter;
import com.xulc.wanandroid.base.BaseResponse;
import com.xulc.wanandroid.bean.User;
import com.xulc.wanandroid.net.Constant;
import com.xulc.wanandroid.net.RetrofitManager;
import com.xulc.wanandroid.utils.RxSchedulers;
import com.xulc.wanandroid.utils.UserUtil;

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
        RetrofitManager.getApiService().loginAccount(username,password)
                .compose(RxSchedulers.<BaseResponse<User>>applySchedulers())
                .subscribe(new BaseObserver<User>() {
                    @Override
                    protected void onSuccess(BaseResponse<User> userBaseResponse) {
                        if (userBaseResponse.getErrorCode()==0){
                            UserUtil.getInstance().setUser(userBaseResponse.getData());
                            SPUtils.getInstance().put(Constant.IS_LOGIN,true);
                            mView.loginSuccess();
                        }else {
                            mView.loginFail(userBaseResponse.getErrorMsg());
                        }
                    }

                    @Override
                    protected void onFail(BaseResponse<User> userBaseResponse) {

                    }
                });
    }
}
