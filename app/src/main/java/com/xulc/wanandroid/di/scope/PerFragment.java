package com.xulc.wanandroid.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Date：2018/5/28
 * Desc：
 * Created by xuliangchun.
 */

@Scope
@Documented
@Retention(RUNTIME)
public @interface PerFragment {}
