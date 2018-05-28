package com.xulc.wanandroid.di.module;

import dagger.Module;

/**
 * Date：2018/5/17
 * Desc：提供依赖方法时，只有当需要这个依赖时才会去判断该方法的参数是否能够找到依赖，
 * 也就是说如果该依赖方法并未被使用，那么方法的参数就不会去校验合法性，没有人为其提供依赖也不会有问题
 * Created by xuliangchun.
 */
@Module
public class ActivityModule {

}
