package com.xulc.wanandroid.utils;

import com.blankj.utilcode.util.SPUtils;
import com.xulc.wanandroid.bean.BaseResponse;
import com.xulc.wanandroid.bean.User;
import com.xulc.wanandroid.net.ApiService;
import com.xulc.wanandroid.net.Constant;
import com.xulc.wanandroid.net.RetrofitManager;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;


/**
 * Date：2018/4/18
 * Desc：
 * Created by xuliangchun.
 */

public class LoginUtil {
    private LoginUtil(){

    }
    private static class LoginUtilInstance{
        public static LoginUtil instance = new LoginUtil();
    }

    public static LoginUtil getInstance(){
        return LoginUtilInstance.instance;
    }

    private void login(){
        if (UserUtil.getInstance().getUser() == null){
            return;
        }
        RetrofitManager.create(ApiService.class).loginAccount(UserUtil.getInstance().getUser().getUsername(),UserUtil.getInstance().getUser().getPassword())
                .compose(RxSchedulers.<BaseResponse<User>>applySchedulers())
                .subscribe(new Consumer<BaseResponse<User>>() {
                    @Override
                    public void accept(@NonNull BaseResponse<User> userBaseResponse) throws Exception {
                        if (userBaseResponse.getErrorCode()==0){
                            UserUtil.getInstance().setUser(userBaseResponse.getData());
                            SPUtils.getInstance().put(Constant.IS_LOGIN,true);
                        }else {

                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                });
    }
}
