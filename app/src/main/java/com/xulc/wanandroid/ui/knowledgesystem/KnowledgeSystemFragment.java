package com.xulc.wanandroid.ui.knowledgesystem;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xulc.wanandroid.R;
import com.xulc.wanandroid.base.BaseLazyFragment;
import com.xulc.wanandroid.bean.KnowledgeSystem;
import com.xulc.wanandroid.ui.KnowledgeSystemArticle.KnowledgeSystemArticleActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Date：2018/4/12
 * Desc：知识体系
 * Created by xuliangchun.
 */

public class KnowledgeSystemFragment extends BaseLazyFragment<KnowledgeSystemContract.Presenter> implements KnowledgeSystemContract.View {
    private RecyclerView recyclerViewTop;
    private RecyclerView recyclerViewSecond;
    private KnowledgeSystemTopAdapter topAdapter;
    private KnowledgeSystemSecondAdapter secondAdapter;
    private SecondDecoration secondDecoration;
    private int secondTitleHeight = 80;//右边的标题高度
    private boolean scrollToTop = false;
    private int scrollRightPosition;//右侧列表想要滚动到达的位置


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_knowledge_system;
    }

    @Override
    protected void initView(View mRootView) {
        recyclerViewTop = mRootView.findViewById(R.id.recyclerViewTop);
        recyclerViewSecond = mRootView.findViewById(R.id.recyclerViewSecond);
        topAdapter = new KnowledgeSystemTopAdapter();
        recyclerViewTop.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewTop.setAdapter(topAdapter);
        topAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                scrollToTop = false;

                leftContactRightPosition(position);

            }
        });

        secondAdapter = new KnowledgeSystemSecondAdapter();
        recyclerViewSecond.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewSecond.setAdapter(secondAdapter);
        secondDecoration = new SecondDecoration(secondTitleHeight);
        recyclerViewSecond.addItemDecoration(secondDecoration);


        mPresenter.loadKnowledgeSystem();

        recyclerViewSecond.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstItem = ((LinearLayoutManager) recyclerViewSecond.getLayoutManager()).findFirstVisibleItemPosition();
                rightContactLeftPosition(firstItem);
                if (scrollToTop) {
                    scrollToTop = false;
                    recyclerViewSecond.scrollBy(0, recyclerView.getChildAt(scrollRightPosition - firstItem).getTop() - secondTitleHeight);
                }

            }
        });

        secondAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                //计算出父类的position

                for (KnowledgeSystem it : topAdapter.getData()) {
                    if (it.getId() == secondAdapter.getData().get(position).getParentChapterId()) {
                        Intent intent = new Intent(getActivity(), KnowledgeSystemArticleActivity.class);
                        intent.putExtra("name", it.getName());
                        intent.putExtra("childs", (Serializable) it.getChildren());

                        for (int i=0;i<it.getChildren().size();i++){
                            if (it.getChildren().get(i).getId() == secondAdapter.getData().get(position).getId()){
                                intent.putExtra("position",i);
                            }
                        }
                        startActivity(intent);
                        break;
                    }
                }

            }
        });
    }


    @Override
    protected KnowledgeSystemContract.Presenter getPresenter() {
        return new KnowledgeSystemPresenter();
    }

    @Override
    public void setKnowledgeSystem(List<KnowledgeSystem> systemList) {

        systemList.get(0).setSelected(true);
        topAdapter.setNewData(systemList);
        List<KnowledgeSystem> childList = new ArrayList<>();
        for (int i = 0; i < systemList.size(); i++) {
            childList.addAll(systemList.get(i).getChildren());
        }
        secondDecoration.setSystemList(childList);
        secondAdapter.setNewData(childList);

    }

    /**
     * 左边联动右边
     *
     * @param position 左边点击的位置
     */
    private void leftContactRightPosition(int position) {

        //滑动定位的解决方案https://blog.csdn.net/tyzlmjj/article/details/49227601
        //计算右边想要置顶的position
        scrollRightPosition = 0;
        for (int i = 0; i < position; i++) {
            scrollRightPosition += topAdapter.getData().get(i).getChildren().size();
        }
        int firstItem = ((LinearLayoutManager) recyclerViewSecond.getLayoutManager()).findFirstVisibleItemPosition();
        int lastItem = ((LinearLayoutManager) recyclerViewSecond.getLayoutManager()).findLastVisibleItemPosition();
        if (scrollRightPosition <= firstItem) {
            //当要置顶的项在当前显示的第一个项的前面时
            recyclerViewSecond.scrollToPosition(scrollRightPosition);
            //这里使用smoothScrollToPosition时会有bug 未知
        } else if (scrollRightPosition <= lastItem) {
            //当要置顶的项已经在屏幕上显示时
            int top = recyclerViewSecond.getChildAt(scrollRightPosition - firstItem).getTop();
            recyclerViewSecond.scrollBy(0, top - secondTitleHeight);
        } else {
            //当要置顶的项在当前显示的最后一项的后面时
            recyclerViewSecond.scrollToPosition(scrollRightPosition);
            //这一步只是将item显示出来了，但是还需要计算位移将它置顶
            scrollToTop = true;
        }
    }

    /**
     * 右边联动左边
     *
     * @param position 右边第一项的位置
     */
    private void rightContactLeftPosition(int position) {
        int parentId = secondAdapter.getData().get(position).getParentChapterId();
        int leftPosition = 0;

        for (int i = 0; i < topAdapter.getData().size(); i++) {
            topAdapter.getData().get(i).setSelected(false);
            if (parentId == topAdapter.getData().get(i).getId()) {
                leftPosition = i;
            }
        }
        topAdapter.getData().get(leftPosition).setSelected(true);
        topAdapter.notifyDataSetChanged();

        recyclerViewTop.scrollToPosition(leftPosition);

    }
}
