package com.xulc.wanandroid.di.component;

import com.xulc.wanandroid.di.module.ActivityModule;
import com.xulc.wanandroid.ui.Collect.CollectActivity;
import com.xulc.wanandroid.ui.login.LoginActivity;
import com.xulc.wanandroid.ui.register.RegisterActivity;
import com.xulc.wanandroid.ui.splash.SplashActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Date：2018/5/17
 * Desc：
 * Created by xuliangchun.
 */

@Singleton
@Component(modules = {ActivityModule.class})
public interface ActivityComponent {
    void inject(SplashActivity activity);
    void inject(LoginActivity activity);
    void inject(RegisterActivity activity);
    void inject(CollectActivity activity);

}
