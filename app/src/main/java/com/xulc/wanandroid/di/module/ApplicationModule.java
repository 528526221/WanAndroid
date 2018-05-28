package com.xulc.wanandroid.di.module;

import android.content.Context;

import com.xulc.wanandroid.di.component.ActivityComponent;
import com.xulc.wanandroid.di.component.FragmentComponent;

import dagger.Module;
import dagger.Provides;

/**
 * Date：2018/5/28
 * Desc：
 * Created by xuliangchun.
 */
@Module(subcomponents = {ActivityComponent.class,FragmentComponent.class})
public class ApplicationModule {
    private Context context;

    public ApplicationModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context provideApplicationContext(){
        return context;
    }
}
