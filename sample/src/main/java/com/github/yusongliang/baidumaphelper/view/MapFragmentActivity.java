package com.github.yusongliang.baidumaphelper.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.baidu.mapapi.SDKInitializer;
import com.github.yusongliang.baidumaphelper.R;
import com.github.yusongliang.baidumaphelper.adapter.MapPagerAdapter;

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
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map_fragement);
        initView();
        initAdapter();
    }

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);
    }

    private void initAdapter() {
        FragmentManager manager = getSupportFragmentManager();
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            fragments.add(new MapFragment());
        }
        MapPagerAdapter adapter = new MapPagerAdapter(manager, fragments);
        mViewpager.setAdapter(adapter);
        mViewpager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewpager);
    }
}
