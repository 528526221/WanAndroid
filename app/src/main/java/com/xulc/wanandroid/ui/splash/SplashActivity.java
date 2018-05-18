package com.xulc.wanandroid.ui.splash;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.xulc.wanandroid.R;
import com.xulc.wanandroid.base.BaseActivity;
import com.xulc.wanandroid.ui.main.MainActivity;

public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.View {
    private TextView tvSkip;

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        tvSkip = (TextView) findViewById(R.id.tvSkip);
        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipToMain();
            }
        });
        mPresenter.startCountDown();
    }


    @Override
    public void updateTip(String tip) {
        tvSkip.setText(tip);
    }

    @Override
    public void skipToMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
