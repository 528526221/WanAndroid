package com.xulc.wanandroid.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Date：2018/4/16
 * Desc：
 * Created by xuliangchun.
 */

public abstract class BaseLazyFragment<T extends BaseContract.BasePresenter> extends Fragment implements BaseContract.BaseView {
    private View mRootView;
    protected T mPresenter;
    private boolean isFirst = true;//fragemnt是否第一次可见
    private boolean isViewCreated = false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = getPresenter();
        attachView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isFirst = true;
        isViewCreated = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()){
            lazyLoad();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        detachView();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            lazyLoad();
        }
    }

    private void lazyLoad() {
        if (isFirst && isViewCreated){
            isFirst = false;
            initView(mRootView);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    protected abstract int getLayoutId();

    protected abstract void initView(View mRootView);


    protected abstract T getPresenter();

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
