package com.github.yusongliang.baidumaphelper.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.github.yusongliang.baidumaphelper.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试地图Fragment的Activity
 */
public class MapFragmentActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewpager;
    private List<Integer> mIndexes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_fragement);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);
    }

    private void initData() {

    }

    private void initListener() {

    }
}
