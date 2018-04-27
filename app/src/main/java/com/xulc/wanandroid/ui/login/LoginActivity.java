package com.xulc.wanandroid.ui.login;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.xulc.wanandroid.R;
import com.xulc.wanandroid.base.BaseActivity;
import com.xulc.wanandroid.view.TitleBar;

/**
 * Date：2018/4/17
 * Desc：
 * Created by xuliangchun.
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View{
    private TitleBar titleBar;
    private EditText etUserName;
    private EditText etPassword;
    private Button btnLogin;
    @Override
    protected LoginPresenter getPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        titleBar = (TitleBar) findViewById(R.id.titleBar);
        etUserName = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.login(etUserName.getText().toString(),etPassword.getText().toString());
            }
        });
        titleBar.setTitle(getString(R.string.login));

    }

    @Override
    public void loginSuccess() {
        ToastUtils.showShort("登录成功");
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
