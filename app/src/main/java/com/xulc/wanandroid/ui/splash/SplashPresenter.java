package com.xulc.wanandroid.ui.splash;

import android.os.Handler;
import android.os.Message;

import com.xulc.wanandroid.base.BasePresenter;

import javax.inject.Inject;

/**
 * Date：2018/2/26
 * Desc：
 * Created by xuliangchun.
 */

public class SplashPresenter extends BasePresenter<SplashContract.View> implements SplashContract.Presenter {



    private Handler handler;
    private int count = 3;

    @Inject
    public SplashPresenter() {
        this.handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                startCountDown();
            }
        };
    }
    @Override
    public void startCountDown() {
        if (count == 0){
            mView.skipToMain();
        }
        mView.updateTip(String.format("跳过(%s)秒",count));
        handler.sendEmptyMessageDelayed(0,1000);
        count--;
    }

    @Override
    public void detachView() {
        super.detachView();
        handler.removeMessages(0);
        handler = null;
    }
}
