package com.xulc.wanandroid.ui.my;

import com.blankj.utilcode.util.ToastUtils;
import com.xulc.wanandroid.base.BasePresenter;

/**
 * Date：2018/5/3
 * Desc：
 * Created by xuliangchun.
 */

public class MyPresenter extends BasePresenter<MyContract.View> implements MyContract.Presenter {
    @Override
    public void logout() {
        ToastUtils.showShort("暂无接口！囧~");
    }
}
