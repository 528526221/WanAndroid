package com.xulc.wanandroid.ui.knowledgesystem;

import android.util.Log;

import com.xulc.wanandroid.base.BasePresenter;
import com.xulc.wanandroid.bean.BaseResponse;
import com.xulc.wanandroid.bean.KnowledgeSystem;
import com.xulc.wanandroid.net.ApiService;
import com.xulc.wanandroid.net.RetrofitManager;
import com.xulc.wanandroid.utils.RxSchedulers;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Date：2018/4/12
 * Desc：
 * Created by xuliangchun.
 */

public class KnowledgeSystemPresenter extends BasePresenter<KnowledgeSystemContract.View> implements KnowledgeSystemContract.Presenter{

    @Override
    public void loadKnowledgeSystem() {
        RetrofitManager.create(ApiService.class).getKnowledgeSystem()
                .compose(RxSchedulers.<BaseResponse<List<KnowledgeSystem>>>applySchedulers())
                .subscribe(new Consumer<BaseResponse<List<KnowledgeSystem>>>() {
                    @Override
                    public void accept(@NonNull BaseResponse<List<KnowledgeSystem>> listBaseResponse) throws Exception {
                        for(KnowledgeSystem it : listBaseResponse.getData()){
                            for (KnowledgeSystem child : it.getChildren()){
                                child.setParentChapterName(it.getName());
                            }
                        }
                        mView.setKnowledgeSystem(listBaseResponse.getData());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.i("xlc", throwable.getMessage());
                    }
                });
    }
}
