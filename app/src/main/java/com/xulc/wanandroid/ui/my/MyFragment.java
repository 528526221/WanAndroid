package com.xulc.wanandroid.ui.my;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xulc.wanandroid.R;
import com.xulc.wanandroid.base.BaseContract;
import com.xulc.wanandroid.base.BaseLazyFragment;
import com.xulc.wanandroid.net.Constant;
import com.xulc.wanandroid.ui.login.LoginActivity;
import com.xulc.wanandroid.utils.UserUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Date：2018/4/16
 * Desc：
 * Created by xuliangchun.
 */

public class MyFragment extends BaseLazyFragment {
    private TextView tvName;
    private TextView tvEmail;
    private ImageView ivHead;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private List<MyAdapter.Item> items;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView(View mRootView) {
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
//                if (SPUtils.getInstance().getBoolean(Constant.IS_LOGIN)){
//                    ToastUtils.showShort(myAdapter.getItem(position).getName());
//                }else {
//                    startActivity(new Intent(getActivity(), LoginActivity.class));
//                }

                startActivity(new Intent(getActivity(), LoginActivity.class));

            }
        });
        setUserText();

    }

    @Override
    protected BaseContract.BasePresenter getPresenter() {
        return null;
    }


    private void setUserText(){
        if (SPUtils.getInstance().getBoolean(Constant.IS_LOGIN)) {
            if (UserUtil.getInstance().getUser() != null) {
                tvName.setText(UserUtil.getInstance().getUser().getUsername());
                tvEmail.setText(UserUtil.getInstance().getUser().getEmail());
                if (TextUtils.isEmpty(UserUtil.getInstance().getUser().getEmail())) {
                    tvEmail.setVisibility(View.GONE);
                } else {
                    tvEmail.setVisibility(View.VISIBLE);

                }
                Glide.with(getContext())
                        .load(UserUtil.getInstance().getUser().getIcon())
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .into(ivHead);
            } else {
                SPUtils.getInstance().put(Constant.IS_LOGIN, false);
                tvEmail.setVisibility(View.VISIBLE);
                tvName.setText("未登录");
            }
        }else {
            tvEmail.setVisibility(View.VISIBLE);
            tvName.setText("未登录");
        }
    }
}
