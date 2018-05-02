package com.xulc.wanandroid.utils;

import android.util.Log;

import java.util.HashMap;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Date：2018/5/2
 * Desc：
 * Created by xuliangchun.
 */

public class RxBus {
    private HashMap<String, CompositeDisposable> mSubscriptionMap;
    private static volatile RxBus mRxBus;
    private final Subject<Object> mSubject;

    public static RxBus getInstance(){
        if (mRxBus==null){
            synchronized (RxBus.class){
                if(mRxBus==null){
                    mRxBus = new RxBus();
                }
            }
        }
        return mRxBus;
    }
    private RxBus(){
        mSubject = PublishSubject.create().toSerialized();
    }

    public void post(Object o){
        mSubject.onNext(o);
    }
    /**
     * 返回指定类型的带背压的Flowable实例
     * @param <T>
     * @param type
     * @return
     */
    public <T>Flowable<T> getObservable(Class<T> type){
        return mSubject.toFlowable(BackpressureStrategy.BUFFER)
                .ofType(type);
    }
    /**
     * 一个默认的订阅方法
     * @param <T>
     * @param type
     * @param next
     * @param error
     * @return
     */
    private <T> Disposable doSubscribe(Class<T> type, Consumer<T> next, Consumer<Throwable> error){
        return getObservable(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next,error);
    }
    /**
     * 是否已有观察者订阅
     * @return
     */
    public boolean hasObservers() {
        return mSubject.hasObservers();
    }


    /**
     * 订阅
     * @param o
     * @param type
     * @param next
     * @param <T>
     */
    public <T> void addSubscribe(Object o,Class<T> type,Consumer<T> next){
        addSubscription(o,doSubscribe(type,next,throwableConsumer));
    }


    /**
     * 保存订阅后的disposable
     * @param o
     * @param disposable
     */
    private void addSubscription(Object o, Disposable disposable) {
        if (mSubscriptionMap == null) {
            mSubscriptionMap = new HashMap<>();
        }
        String key = o.getClass().getName();
        if (mSubscriptionMap.get(key) != null) {
            mSubscriptionMap.get(key).add(disposable);
        } else {
            //一次性容器,可以持有多个并提供 添加和移除。
            CompositeDisposable disposables = new CompositeDisposable();
            disposables.add(disposable);
            mSubscriptionMap.put(key, disposables);
        }
    }

    /**
     * 取消订阅
     * @param o
     */
    public void unSubscribe(Object o) {
        if (mSubscriptionMap == null) {
            return;
        }

        String key = o.getClass().getName();
        if (!mSubscriptionMap.containsKey(key)){
            return;
        }
        if (mSubscriptionMap.get(key) != null) {
            mSubscriptionMap.get(key).dispose();
        }

        mSubscriptionMap.remove(key);
    }

    private Consumer<Throwable> throwableConsumer = new Consumer<Throwable>() {
        @Override
        public void accept(@NonNull Throwable throwable) throws Exception {
            Log.i("xlc",throwable.getMessage());
        }
    };
}
