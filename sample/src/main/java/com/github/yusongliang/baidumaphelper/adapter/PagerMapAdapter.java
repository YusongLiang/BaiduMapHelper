package com.github.yusongliang.baidumaphelper.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;
import java.util.Locale;

/**
 * 用于演示Viewpager加载MapFragment的FragmentPagerAdapter
 */
public class PagerMapAdapter extends FragmentPagerAdapter {
    List<Fragment> mFragments;

    public PagerMapAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return String.format(Locale.CHINA, "第%d个", position + 1);
    }
}
