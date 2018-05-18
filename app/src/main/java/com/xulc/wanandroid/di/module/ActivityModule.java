package com.xulc.wanandroid.di.module;

import com.xulc.wanandroid.base.BaseContract;

import dagger.Module;
import dagger.Provides;

/**
 * Date：2018/5/17
 * Desc：
 * Created by xuliangchun.
 */

@Module
public class ActivityModule {
    @Provides
    public BaseContract.BasePresenter provideBasePresenter(){
        return null;
    }
}
