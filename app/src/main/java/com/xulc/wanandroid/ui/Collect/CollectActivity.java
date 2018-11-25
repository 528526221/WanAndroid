package com.xulc.wanandroid.ui.Collect;

import android.content.Context;
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
import com.xulc.wanandroid.ui.index.IndexDecoration;

/**
 * Date：2018/5/3
 * Desc：收藏列表
 * Created by xuliangchun.
 */

public class CollectActivity extends BaseActivity<CollectPresenter> implements CollectContract.View,SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemChildClickListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private CollectAdapter collectAdapter;


    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collect;
    }

    @Override
    protected void initView() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new IndexDecoration());
        collectAdapter = new CollectAdapter(this);
        recyclerView.setAdapter(collectAdapter);

        swipeRefreshLayout.setOnRefreshListener(this);
        collectAdapter.setOnLoadMoreListener(this,recyclerView);

        collectAdapter.setOnItemChildClickListener(this);
        swipeRefreshLayout.setRefreshing(true);
        mPresenter.refresh();

    }

    @Override
    public void onRefresh() {
        mPresenter.refresh();
    }

    @Override
    public void setCollectList(boolean isRefresh, ArticleData data) {
        swipeRefreshLayout.setRefreshing(false);
        if (isRefresh){
            collectAdapter.setNewData(data.getDatas());
        }else {
            collectAdapter.addData(data.getDatas());
        }
        if (data.isOver()){
            collectAdapter.loadMoreEnd();
        }else {
            collectAdapter.loadMoreComplete();
        }
    }

    @Override
    public void removeCollect(int position) {
        collectAdapter.remove(position);
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.loadMore();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()){
            case R.id.tvIdentify:
                if (collectAdapter.getItem(position).getTags().size()>0){
                    startArticleDetail(collectAdapter.getItem(position).getTags().get(0).getName(), Constant.REQUEST_BASE_URL+collectAdapter.getItem(position).getTags().get(0).getUrl());
                }
                break;
            case R.id.tvRemove:
                mPresenter.removeCollect(position,collectAdapter.getItem(position));
                break;
            case R.id.content:
                //侧滑导致OnItemClickListener不生效，无所以采取该方式
                startArticleDetail(collectAdapter.getItem(position).getTitle(),collectAdapter.getItem(position).getLink());
                break;
            default:
                break;
        }
    }



    private void startArticleDetail(String title, String link) {
        Intent intent = new Intent(this, ArticleActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("link",link);
        startActivity(intent);
    }
}
