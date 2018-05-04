package com.xulc.wanandroid.ui.knowledgesystem;

import com.xulc.wanandroid.base.BaseObserver;
import com.xulc.wanandroid.base.BasePresenter;
import com.xulc.wanandroid.base.BaseResponse;
import com.xulc.wanandroid.bean.KnowledgeSystem;
import com.xulc.wanandroid.net.RetrofitManager;
import com.xulc.wanandroid.utils.RxSchedulers;

import java.util.List;

/**
 * Date：2018/4/12
 * Desc：
 * Created by xuliangchun.
 */

public class KnowledgeSystemPresenter extends BasePresenter<KnowledgeSystemContract.View> implements KnowledgeSystemContract.Presenter{

    @Override
    public void loadKnowledgeSystem() {
        RetrofitManager.getApiService().getKnowledgeSystem()
                .compose(RxSchedulers.<BaseResponse<List<KnowledgeSystem>>>applySchedulers())
                .subscribe(new BaseObserver<List<KnowledgeSystem>>() {
                    @Override
                    protected void onSuccess(BaseResponse<List<KnowledgeSystem>> listBaseResponse) {
                        for(KnowledgeSystem it : listBaseResponse.getData()){
                            for (KnowledgeSystem child : it.getChildren()){
                                child.setParentChapterName(it.getName());
                            }
                        }
                        mView.setKnowledgeSystem(listBaseResponse.getData());
                    }

                    @Override
                    protected void onFail(BaseResponse<List<KnowledgeSystem>> listBaseResponse) {

                    }
                });
    }
}
