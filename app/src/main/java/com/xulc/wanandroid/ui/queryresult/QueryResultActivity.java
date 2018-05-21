package com.xulc.wanandroid.ui.queryresult;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xulc.wanandroid.R;
import com.xulc.wanandroid.base.BaseActivity;
import com.xulc.wanandroid.bean.ArticleData;
import com.xulc.wanandroid.net.Constant;
import com.xulc.wanandroid.ui.article.ArticleActivity;
import com.xulc.wanandroid.ui.index.IndexAdapter;
import com.xulc.wanandroid.ui.index.IndexDecoration;
import com.xulc.wanandroid.view.TitleBar;

/**
 * Date：2018/5/21
 * Desc：
 * Created by xuliangchun.
 */

public class QueryResultActivity extends BaseActivity<QueryResultPresenter> implements QueryResultContract.View, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {
    private TitleBar titleBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private IndexAdapter indexAdapter;

    private String key;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_query_result;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        titleBar = (TitleBar) findViewById(R.id.titleBar);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,R.color.black,R.color.red);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new IndexDecoration());
        indexAdapter = new IndexAdapter(this);
        recyclerView.setAdapter(indexAdapter);
        indexAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);

        indexAdapter.setOnItemClickListener(this);
        indexAdapter.setOnItemChildClickListener(this);
        indexAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.loadMore(key);
            }
        },recyclerView);

        key = getIntent().getStringExtra("key");
        titleBar.setTitle(key);
        mPresenter.refresh(key);
        swipeRefreshLayout.setRefreshing(true);



    }

    @Override
    public void setQueryResult(boolean isRefresh, ArticleData data) {
        swipeRefreshLayout.setRefreshing(false);
        if (isRefresh){
            indexAdapter.setNewData(data.getDatas());
        }else {
            indexAdapter.addData(data.getDatas());
        }
        if (data.isOver()){
            indexAdapter.loadMoreEnd();
        }else {
            indexAdapter.loadMoreComplete();
        }
    }

    @Override
    public void updateCollectArticle(int position, ArticleData.Article item) {
        indexAdapter.setData(position,item);
    }

    @Override
    public void onRefresh() {
        mPresenter.refresh(key);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        startArticleDetail(indexAdapter.getItem(position).getTitle(),indexAdapter.getItem(position).getLink());
    }

    private void startArticleDetail(String title, String link) {
        Intent intent = new Intent(this, ArticleActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("link",link);
        startActivity(intent);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()){
            case R.id.tvIdentify:
                if (indexAdapter.getItem(position).getTags().size()>0){
                    startArticleDetail(indexAdapter.getItem(position).getTags().get(0).getName(), Constant.REQUEST_BASE_URL+indexAdapter.getItem(position).getTags().get(0).getUrl());
                }
                break;
            case R.id.ivCollect:
                mPresenter.collectArticle(position,indexAdapter.getItem(position));
                break;
            default:
                break;
        }
    }
}
