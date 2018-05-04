package com.xulc.wanandroid.utils;

import com.blankj.utilcode.util.SPUtils;
import com.xulc.wanandroid.base.BaseObserver;
import com.xulc.wanandroid.base.BaseResponse;
import com.xulc.wanandroid.bean.RxLoginEvent;
import com.xulc.wanandroid.bean.User;
import com.xulc.wanandroid.net.Constant;
import com.xulc.wanandroid.net.RetrofitManager;


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

    public void login(){
        if (UserUtil.getInstance().getUser() == null){
            return;
        }
        RetrofitManager.getApiService().loginAccount(UserUtil.getInstance().getUser().getUsername(),UserUtil.getInstance().getUser().getPassword())
                .compose(RxSchedulers.<BaseResponse<User>>applySchedulers())
                .subscribe(new BaseObserver<User>() {
                    @Override
                    protected void onSuccess(BaseResponse<User> userBaseResponse) {
                        UserUtil.getInstance().setUser(userBaseResponse.getData());
                        SPUtils.getInstance().put(Constant.IS_LOGIN,true);
                        RxBus.getInstance().post(new RxLoginEvent(""));
                    }

                    @Override
                    protected void onFail(BaseResponse<User> userBaseResponse) {

                    }
                });
    }
}
