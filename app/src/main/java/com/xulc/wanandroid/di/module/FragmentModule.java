package com.xulc.wanandroid.di.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Date：2018/5/17
 * Desc：
 * Created by xuliangchun.
 */

@Module
public class FragmentModule {
    @Provides
    public String providePackageName(Context context){
        return context.getPackageName();
    }
}
