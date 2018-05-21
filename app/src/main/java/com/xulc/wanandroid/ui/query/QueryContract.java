package com.xulc.wanandroid.ui.query;

import com.xulc.wanandroid.base.BaseContract;
import com.xulc.wanandroid.bean.HotKey;

import java.util.List;

/**
 * Date：2018/5/21
 * Desc：
 * Created by xuliangchun.
 */
public interface QueryContract {
    interface View extends BaseContract.BaseView{
        void setHotKey(List<HotKey> hotKeys);
    }
    interface Presenter extends BaseContract.BasePresenter<QueryContract.View>{
        void loadHotKey();
    }

}
