package com.xulc.wanandroid.ui.register;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.xulc.wanandroid.R;
import com.xulc.wanandroid.base.BaseActivity;
import com.xulc.wanandroid.ui.login.LoginActivity;
import com.xulc.wanandroid.view.TitleBar;

/**
 * Date：2018/4/16
 * Desc：
 * Created by xuliangchun.
 */

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View{

    private TitleBar titleBar;
    private EditText etUserName;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private Button btnRegister;

    @Override
    protected RegisterPresenter getPresenter() {
        return new RegisterPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        titleBar = (TitleBar) findViewById(R.id.titleBar);
        etUserName = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.registerAccount(etUserName.getText().toString(),etPassword.getText().toString(),etConfirmPassword.getText().toString());
            }
        });
        titleBar.setTitle(getString(R.string.register));
    }

    @Override
    public void registerSuccess() {
        ToastUtils.showShort("注册成功");
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void registerFail(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void inputError(String error) {
        ToastUtils.showShort(error);
    }
}
