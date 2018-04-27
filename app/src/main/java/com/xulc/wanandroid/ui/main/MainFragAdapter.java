package com.xulc.wanandroid.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Date：2018/4/10
 * Desc：
 * Created by xuliangchun.
 */

public class MainFragAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;


    public MainFragAdapter(FragmentManager fm,List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
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
