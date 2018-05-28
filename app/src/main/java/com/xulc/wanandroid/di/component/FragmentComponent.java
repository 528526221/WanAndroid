package com.xulc.wanandroid.di.component;

import com.xulc.wanandroid.di.module.FragmentModule;
import com.xulc.wanandroid.di.scope.PerFragment;
import com.xulc.wanandroid.ui.KnowledgeSystemArticle.KnowledgeSystemArticleFragment;
import com.xulc.wanandroid.ui.index.IndexFragment;
import com.xulc.wanandroid.ui.knowledgesystem.KnowledgeSystemFragment;
import com.xulc.wanandroid.ui.my.MyFragment;

import dagger.Subcomponent;

/**
 * Date：2018/5/17
 * Desc：
 * Created by xuliangchun.
 */
@PerFragment
@Subcomponent(modules = {FragmentModule.class})
public interface FragmentComponent {
    void inject(IndexFragment fragment);
    void inject(KnowledgeSystemFragment fragment);
    void inject(MyFragment fragment);
    void inject(KnowledgeSystemArticleFragment fragment);

    @Subcomponent.Builder
    interface Builder{
        FragmentComponent build();
        Builder fragmentModule(FragmentModule fragmentModule);
    }
}
