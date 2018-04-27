package com.xulc.wanandroid.ui.KnowledgeSystemArticle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xulc.wanandroid.bean.KnowledgeSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Date：2018/4/16
 * Desc：
 * Created by xuliangchun.
 */

public class ArticleFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public ArticleFragmentAdapter(FragmentManager fm, List<KnowledgeSystem> knowledgeSystems) {
        super(fm);
        this.fragments = new ArrayList<>();
        for (KnowledgeSystem it : knowledgeSystems){
            fragments.add(KnowledgeSystemArticleFragment.getFragment(it.getId()));
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
