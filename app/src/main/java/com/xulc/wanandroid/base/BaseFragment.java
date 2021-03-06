package com.xulc.wanandroid.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xulc.wanandroid.di.component.DaggerApplicationComponent;
import com.xulc.wanandroid.di.component.FragmentComponent;
import com.xulc.wanandroid.di.module.ApplicationModule;

import javax.inject.Inject;

/**
 * Date：2018/4/10
 * Desc：
 * Created by xuliangchun.
 */

public abstract class BaseFragment<T extends BaseContract.BasePresenter> extends Fragment implements BaseContract.BaseView{
    private View mRootView;
    @Inject
    protected T mPresenter;
    protected FragmentComponent mFragmentComponent;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mFragmentComponent = DaggerFragmentComponent.builder().build();
        mFragmentComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(App.getAppContext())).build().buildFragmentComponent().build();
        initInjector();
        attachView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        initView(mRootView);
        return mRootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        detachView();
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    protected abstract int getLayoutId();

    protected abstract void initView(View mRootView);


    protected abstract void initInjector();

    /**
     * 贴上view
     */
    private void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    /**
     * 分离view
     */
    private void detachView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

}
