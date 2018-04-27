package com.xulc.wanandroid.ui.KnowledgeSystemArticle;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xulc.wanandroid.R;
import com.xulc.wanandroid.base.BaseLazyFragment;
import com.xulc.wanandroid.bean.ArticleData;
import com.xulc.wanandroid.ui.article.ArticleActivity;
import com.xulc.wanandroid.ui.index.IndexAdapter;
import com.xulc.wanandroid.ui.index.IndexDecoration;

/**
 * Date：2018/4/16
 * Desc：
 * Created by xuliangchun.
 */

public class KnowledgeSystemArticleFragment extends BaseLazyFragment<KnowledgeSystemArticlePresenter> implements KnowledgeSystemArticleContract.View, BaseQuickAdapter.OnItemChildClickListener {
    private int cid;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private IndexAdapter adapter;
    public static KnowledgeSystemArticleFragment getFragment(int cid){
        KnowledgeSystemArticleFragment fragment = new KnowledgeSystemArticleFragment();
        fragment.cid = cid;
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_knowledge_system_article;
    }

    @Override
    protected void initView(View mRootView) {
        swipeRefreshLayout = mRootView.findViewById(R.id.swipeRefreshLayout);
        recyclerView = mRootView.findViewById(R.id.recyclerView);
        adapter = new IndexAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new IndexDecoration());
        recyclerView.setAdapter(adapter);
        mPresenter.refresh(cid);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refresh(cid);
            }
        });
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.loadMore(cid);
            }
        },recyclerView);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                startArticleDetail(adapter.getItem(position).getTitle(),adapter.getItem(position).getLink());

            }
        });
        adapter.setOnItemChildClickListener(this);

    }

    @Override
    protected KnowledgeSystemArticlePresenter getPresenter() {
        return new KnowledgeSystemArticlePresenter();
    }


    @Override
    public void setArticles(ArticleData data, boolean isRefresh) {
        swipeRefreshLayout.setRefreshing(false);
        if (isRefresh){
            adapter.setNewData(data.getDatas());
        }else {
            adapter.addData(data.getDatas());
            if (data.getDatas().size()<data.getOffset()){
                adapter.loadMoreEnd();
            }else {
                adapter.loadMoreComplete();
            }
        }
    }

    @Override
    public void updateCollectArticle(int position, ArticleData.Article item) {
        adapter.setData(position,item);
    }

    private void startArticleDetail(String title, String link) {
        Intent intent = new Intent(getActivity(), ArticleActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("link",link);
        startActivity(intent);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
        switch (view.getId()){
            case R.id.tvIdentify:
                if (adapter.getItem(position).getTags().size()>0){
                    ToastUtils.showShort(adapter.getItem(position).getTags().get(0).getUrl());
                }
                break;
            case R.id.ivCollect:
                mPresenter.collectArticle(position,adapter.getItem(position));
                break;
            default:
                break;
        }
    }
}
