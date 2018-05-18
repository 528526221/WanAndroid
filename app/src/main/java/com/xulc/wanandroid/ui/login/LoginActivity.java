package com.xulc.wanandroid.ui.login;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.xulc.wanandroid.R;
import com.xulc.wanandroid.base.BaseActivity;
import com.xulc.wanandroid.bean.RxLoginEvent;
import com.xulc.wanandroid.utils.RxBus;

/**
 * Date：2018/4/17
 * Desc：
 * Created by xuliangchun.
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View{
    private EditText etUserName;
    private EditText etPassword;
    private Button btnLogin;

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        etUserName = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.login(etUserName.getText().toString(),etPassword.getText().toString());
            }
        });

    }

    @Override
    public void loginSuccess() {
        ToastUtils.showShort("登录成功");
        RxBus.getInstance().post(new RxLoginEvent("嘿嘿嘿~登录成功"));
        finish();
    }

    @Override
    public void loginFail(String error) {
        ToastUtils.showShort(error);
    }

    @Override
    public void inputError(String error) {
        ToastUtils.showShort(error);
    }
}
