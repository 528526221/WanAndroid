package com.xulc.wanandroid.utils;

import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.xulc.wanandroid.bean.User;
import com.xulc.wanandroid.net.Constant;

/**
 * Date：2018/4/17
 * Desc：登录用户信息工具类
 * Created by xuliangchun.
 */

public class UserUtil {
    private User user;
    private UserUtil(){

    }
    private static class UserUtilInstance{
        public static UserUtil instance = new UserUtil();
    }

    public static UserUtil getInstance(){
        return UserUtilInstance.instance;
    }


    public User getUser(){
        if (user == null){
            String s = SPUtils.getInstance().getString(Constant.USER_INFO);
            if (TextUtils.isEmpty(s))
                return null;
            user = GsonUtil.parseJsonWithGson(s,User.class);
        }
        return user;
    }

    public void setUser(User user){
        this.user = user;
        SPUtils.getInstance().put(Constant.USER_INFO,GsonUtil.beanToJson(user));
    }

}
