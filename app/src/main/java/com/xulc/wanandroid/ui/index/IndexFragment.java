package com.xulc.wanandroid.ui.index;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xulc.wanandroid.R;
import com.xulc.wanandroid.base.BaseLazyFragment;
import com.xulc.wanandroid.bean.ArticleData;
import com.xulc.wanandroid.bean.Banner;
import com.xulc.wanandroid.net.Constant;
import com.xulc.wanandroid.ui.article.ArticleActivity;
import com.xulc.wanandroid.view.CyclicViewPager;

import java.util.List;

/**
 * Date：2018/4/10
 * Desc：首页
 * Created by xuliangchun.
 */

public class IndexFragment extends BaseLazyFragment<IndexPresenter> implements IndexContract.View, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener, SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private IndexAdapter indexAdapter;
    private CyclicViewPager bannerView;
    private LinearLayout lyIndicator;
    private LayoutInflater mLayoutInflater;

    public static IndexFragment newInstance(){
        return new IndexFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_index;

    }

    @Override
    protected void initView(View mRootView) {
        mLayoutInflater = LayoutInflater.from(getContext());
        swipeRefreshLayout = mRootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,R.color.black,R.color.red);
//        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.white);
        recyclerView = mRootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new IndexDecoration());
        indexAdapter = new IndexAdapter(getContext());
        recyclerView.setAdapter(indexAdapter);
        indexAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);

        indexAdapter.setOnItemClickListener(this);
        indexAdapter.setOnItemChildClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);

        View view = mLayoutInflater.inflate(R.layout.layout_index_banner,null);
        bannerView = view.findViewById(R.id.cyclicViewPager);
        lyIndicator = view.findViewById(R.id.lyIndicator);

        indexAdapter.addHeaderView(view);

        mPresenter.refresh();

        indexAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.loadMore();
            }
        },recyclerView);

        swipeRefreshLayout.setRefreshing(true);

    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }


    @Override
    public void setHomeArticles(boolean isRefresh,ArticleData data) {
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
    public void setHomeBanners(final List<Banner> banners) {
        bannerView.setCyclicModels(banners, new CyclicViewPager.OnCreateViewListener() {
            @Override
            public View createChildView(List list, final int position, LayoutInflater mLayoutInflater) {
                View child = mLayoutInflater.inflate(R.layout.item_banner,null);
                ImageView ivBanner = child.findViewById(R.id.ivBanner);

                Glide.with(getContext())
                        .load(banners.get(position).getImagePath())
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .into(ivBanner);
                child.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startArticleDetail(banners.get(position).getTitle(),banners.get(position).getUrl());
                    }
                });
                return child;
            }
        });

        lyIndicator.removeAllViews();
        for (int i=0;i<banners.size();i++){
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(R.drawable.banner_indicator_selector);
            LinearLayoutCompat.LayoutParams lp = new LinearLayoutCompat.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i>0){
                lp.leftMargin = 20;
            }
            lyIndicator.addView(imageView,lp);
        }
        lyIndicator.getChildAt(0).setSelected(true);

        bannerView.setCyclicPageChangeListener(new CyclicViewPager.CyclicPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i=0;i<lyIndicator.getChildCount();i++){
                    if (i == position){
                        lyIndicator.getChildAt(i).setSelected(true);
                    }else {
                        lyIndicator.getChildAt(i).setSelected(false);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bannerView.startAutoScroll();

    }


    @Override
    public void updateCollectArticle(int position, ArticleData.Article item) {
        indexAdapter.setData(position,item);
    }

    @Override
    public void collectError(String error) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        startArticleDetail(indexAdapter.getItem(position).getTitle(),indexAdapter.getItem(position).getLink());
    }

    private void startArticleDetail(String title, String link) {
        Intent intent = new Intent(getActivity(), ArticleActivity.class);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bannerView.stopAutoScroll();

    }

    @Override
    public void onRefresh() {
        mPresenter.refresh();
    }
}
