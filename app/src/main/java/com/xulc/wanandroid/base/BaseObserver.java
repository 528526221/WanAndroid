package com.xulc.wanandroid.base;

import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Date：2018/5/4
 * Desc：
 * Created by xuliangchun.
 */

public abstract class BaseObserver<T> implements Observer<BaseResponse<T>> {
    private Long requestStartTime;

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        requestStartTime = System.nanoTime();
    }

    @Override
    public void onNext(@NonNull BaseResponse<T> tBaseResponse) {
        try {
            if (tBaseResponse.getErrorCode() == 0){
                onSuccess(tBaseResponse);
            }else {
                onFail(tBaseResponse);
                ToastUtils.showShort(tBaseResponse.getErrorMsg());

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        BaseResponse<T> errorResponse = new BaseResponse<>();
        errorResponse.setErrorCode(-999);
        errorResponse.setErrorMsg(e.getMessage());
        onFail(errorResponse);
        ToastUtils.showShort(errorResponse.getErrorMsg());

    }

    @Override
    public void onComplete() {
        Log.i("xlc",String.format("本次请求完整耗时：%.1fms",(System.nanoTime() -requestStartTime)/1e6d));
    }

    protected abstract void onSuccess(BaseResponse<T> tBaseResponse);

    protected abstract void onFail(BaseResponse<T> tBaseResponse);

}
