package com.xulc.wanandroid.di.component;

import com.xulc.wanandroid.di.module.ActivityModule;
import com.xulc.wanandroid.di.scope.PerActivity;
import com.xulc.wanandroid.ui.Collect.CollectActivity;
import com.xulc.wanandroid.ui.login.LoginActivity;
import com.xulc.wanandroid.ui.query.QueryActivity;
import com.xulc.wanandroid.ui.queryresult.QueryResultActivity;
import com.xulc.wanandroid.ui.register.RegisterActivity;
import com.xulc.wanandroid.ui.splash.SplashActivity;

import dagger.Subcomponent;

/**
 * Date：2018/5/17
 * Desc：
 * Created by xuliangchun.
 */

@PerActivity
@Subcomponent(modules = {ActivityModule.class})
public interface ActivityComponent {
    void inject(SplashActivity activity);
    void inject(LoginActivity activity);
    void inject(RegisterActivity activity);
    void inject(CollectActivity activity);
    void inject(QueryActivity activity);
    void inject(QueryResultActivity activity);
    @Subcomponent.Builder
    interface Builder{
        ActivityComponent build();
        Builder activityModule(ActivityModule activityModule);
    }
}
