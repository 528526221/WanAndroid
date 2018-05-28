package com.xulc.wanandroid.di.component;

import com.xulc.wanandroid.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Date：2018/5/28
 * Desc：
 * Created by xuliangchun.
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    FragmentComponent.Builder buildFragmentComponent();
    ActivityComponent.Builder buildActivityComponent();
}
