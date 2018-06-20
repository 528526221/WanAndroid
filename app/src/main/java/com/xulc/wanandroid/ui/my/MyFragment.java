package com.xulc.wanandroid.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xulc.wanandroid.R;
import com.xulc.wanandroid.base.BaseLazyFragment;
import com.xulc.wanandroid.bean.RxLoginEvent;
import com.xulc.wanandroid.net.Constant;
import com.xulc.wanandroid.ui.Collect.CollectActivity;
import com.xulc.wanandroid.ui.about.AboutActivity;
import com.xulc.wanandroid.ui.login.LoginActivity;
import com.xulc.wanandroid.utils.RxBus;
import com.xulc.wanandroid.utils.UserUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Date：2018/4/16
 * Desc：
 * Created by xuliangchun.
 */

public class MyFragment extends BaseLazyFragment<MyPresenter> implements View.OnClickListener {
    private LinearLayout lyPersonInfo;
    private TextView tvGoLogin;
    private LinearLayout lyLogout;
    private TextView tvName;
    private TextView tvEmail;
    private ImageView ivHead;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private List<MyAdapter.Item> items;


    public static MyFragment newInstance(){
        return new MyFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView(View mRootView) {
        lyPersonInfo = mRootView.findViewById(R.id.lyPersonInfo);
        lyLogout = mRootView.findViewById(R.id.lyLogout);
        tvGoLogin = mRootView.findViewById(R.id.tvGoLogin);
        tvName = mRootView.findViewById(R.id.tvName);
        tvEmail = mRootView.findViewById(R.id.tvEmail);
        ivHead = mRootView.findViewById(R.id.ivHead);
        recyclerView = mRootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new MyDecoration());
        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);
        items = new ArrayList<>();
        items.add(new MyAdapter.Item("我的收藏", R.mipmap.my_collect));
        items.add(new MyAdapter.Item("关于", R.mipmap.my_about));

        myAdapter.setNewData(items);

        myAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String name = myAdapter.getData().get(position).getName();
                if (name.equals("我的收藏")){
                    if (SPUtils.getInstance().getBoolean(Constant.IS_LOGIN)){
                        startActivity(new Intent(getActivity(), CollectActivity.class));
                    }else {
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                    }


                }else if (name.equals("关于")){
                    startActivity(new Intent(getActivity(), AboutActivity.class));
                }
            }
        });

        tvGoLogin.setOnClickListener(this);
        lyLogout.setOnClickListener(this);
        setUserText();
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }


    private void setUserText(){
        if (SPUtils.getInstance().getBoolean(Constant.IS_LOGIN)) {
            if (UserUtil.getInstance().getUser() != null) {
                tvName.setText(UserUtil.getInstance().getUser().getUsername());
                tvEmail.setText(UserUtil.getInstance().getUser().getEmail());
                Glide.with(getContext())
                        .load(UserUtil.getInstance().getUser().getIcon())
                        .placeholder(R.mipmap.head_portrait)
                        .error(R.mipmap.head_portrait)
                        .into(ivHead);
                lyPersonInfo.setVisibility(View.VISIBLE);
                lyLogout.setVisibility(View.VISIBLE);
                tvGoLogin.setVisibility(View.INVISIBLE);

            } else {
                SPUtils.getInstance().put(Constant.IS_LOGIN, false);
                lyPersonInfo.setVisibility(View.INVISIBLE);
                lyLogout.setVisibility(View.INVISIBLE);
                tvGoLogin.setVisibility(View.VISIBLE);
            }
        }else {
            lyPersonInfo.setVisibility(View.INVISIBLE);
            lyLogout.setVisibility(View.INVISIBLE);
            tvGoLogin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxBus.getInstance().addSubscribe(this, RxLoginEvent.class, new Consumer<RxLoginEvent>() {
            @Override
            public void accept(@NonNull RxLoginEvent rxLoginEvent) throws Exception {
                setUserText();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unSubscribe(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvGoLogin:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.lyLogout:
                mPresenter.logout();
                break;
        }
    }
}
