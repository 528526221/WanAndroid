package com.xulc.wanandroid.ui.knowledgesystem;

import com.xulc.wanandroid.base.BaseContract;
import com.xulc.wanandroid.bean.KnowledgeSystem;

import java.util.List;

/**
 * Date：2018/4/12
 * Desc：
 * Created by xuliangchun.
 */

public interface KnowledgeSystemContract {
    interface View extends BaseContract.BaseView{
        void setKnowledgeSystem(List<KnowledgeSystem> systemList);
    }

    interface Presenter extends BaseContract.BasePresenter<KnowledgeSystemContract.View>{
        void loadKnowledgeSystem();
    }
}
