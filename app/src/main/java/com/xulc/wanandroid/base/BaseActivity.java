package com.xulc.wanandroid.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.xulc.wanandroid.R;
import com.xulc.wanandroid.di.component.ActivityComponent;
import com.xulc.wanandroid.di.component.DaggerActivityComponent;

import javax.inject.Inject;

/**
 * Date：2018/2/26
 * Desc：
 * Created by xuliangchun.
 */

public abstract class BaseActivity<T extends BaseContract.BasePresenter> extends AppCompatActivity implements BaseContract.BaseView {
    @Inject
    protected T mPresenter;
    protected ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.bottom_text_focus));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            //4.4以下不作处理
        }
        mActivityComponent = DaggerActivityComponent.builder().build();

        initInjector();

        attachView();

        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detachView();
    }

    protected abstract void initInjector();

    protected abstract int getLayoutId();

    protected abstract void initView();

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

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    public void finishBack(View view) {
        finish();
    }

}
