package com.xulc.wanandroid.ui.KnowledgeSystemArticle;

import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.xulc.wanandroid.R;
import com.xulc.wanandroid.base.BaseActivity;
import com.xulc.wanandroid.bean.KnowledgeSystem;
import com.xulc.wanandroid.view.PageTabIndicator;

import java.util.ArrayList;
import java.util.List;


/**
 * Date：2018/4/16
 * Desc：
 * Created by xuliangchun.
 */

public class KnowledgeSystemArticleActivity extends BaseActivity{
    private TextView tvTitle;
    private PageTabIndicator tabIndicator;
    private ViewPager viewPager;


    @Override
    protected void initInjector() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_knowledge_system_article;
    }

    @Override
    protected void initView() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tabIndicator = (PageTabIndicator) findViewById(R.id.tabIndicator);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        String name = getIntent().getStringExtra("name");
        List<KnowledgeSystem> childs = (List<KnowledgeSystem>) getIntent().getSerializableExtra("childs");
        tvTitle.setText(name);
        ArticleFragmentAdapter fragmentAdapter = new ArticleFragmentAdapter(getSupportFragmentManager(),childs);
        viewPager.setAdapter(fragmentAdapter);
        tabIndicator.setViewPager(viewPager);
        List<String> titles = new ArrayList<>();
        for (KnowledgeSystem it : childs){
            titles.add(it.getName());
        }
        tabIndicator.setTitles(titles);

        //延迟 避免tab未居中
        tabIndicator.postDelayed(new Runnable() {
            @Override
            public void run() {
                viewPager.setCurrentItem(getIntent().getIntExtra("position",0));
            }
        },100);

    }
}
