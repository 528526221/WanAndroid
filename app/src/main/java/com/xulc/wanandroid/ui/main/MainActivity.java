package com.xulc.wanandroid.ui.main;

import android.animation.ValueAnimator;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.xulc.wanandroid.R;
import com.xulc.wanandroid.base.BaseActivity;
import com.xulc.wanandroid.base.BasePresenter;
import com.xulc.wanandroid.ui.index.IndexFragment;
import com.xulc.wanandroid.ui.knowledgesystem.KnowledgeSystemFragment;
import com.xulc.wanandroid.ui.my.MyFragment;
import com.xulc.wanandroid.utils.LoginUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Date：2018/2/26
 * Desc：
 * Created by xuliangchun.
 */

public class MainActivity extends BaseActivity {
    private ViewPager viewPager;
    private LinearLayout lyBottom;
    private List<Fragment> fragments;
    private ValueAnimator animator;
    private boolean isExit = false;
    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        lyBottom = (LinearLayout) findViewById(R.id.lyBottom);
        fragments = new ArrayList<>();
        fragments.add(new IndexFragment());
        fragments.add(new KnowledgeSystemFragment());
        fragments.add(new MyFragment());
        MainFragAdapter adapter = new MainFragAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
        selectTab(0);

        for (int i=0;i<lyBottom.getChildCount();i++){

            View child = lyBottom.getChildAt(i);
            final int finalI = i;
            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectTab(finalI);
                }
            });
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        LoginUtil.getInstance().login();

    }

    private void selectTab(int position) {
        viewPager.setCurrentItem(position);
        for (int i=0;i<lyBottom.getChildCount();i++){
            if (i == position){
                lyBottom.getChildAt(i).setSelected(true);
            }else {
                lyBottom.getChildAt(i).setSelected(false);
            }
        }
        startIconAnimal(((ViewGroup)lyBottom.getChildAt(position)).getChildAt(0));

    }

    private void startIconAnimal(final View view) {
        if (animator == null){
            animator = ValueAnimator.ofFloat(1F,0.7F,1F);
            animator.setDuration(300);
        }else {
            animator.removeAllUpdateListeners();
            animator.cancel();
        }
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setScaleX((Float) animation.getAnimatedValue());
                view.setScaleY((Float) animation.getAnimatedValue());

            }
        });
        animator.start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (isExit){
                finish();
            }else {
                isExit = true;
                ToastUtils.showShort("再按一次退出应用");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                      isExit = false;
                    }
                },2000);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
