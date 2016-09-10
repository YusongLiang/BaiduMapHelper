package com.github.yusongliang.baidumaphelper.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.baidu.mapapi.SDKInitializer;
import com.github.yusongliang.baidumaphelper.R;
import com.github.yusongliang.baidumaphelper.fragment.MapFragment;

import java.util.List;
import java.util.Locale;

/**
 * 演示一般嵌套地图Fragment的Activity
 */
public class MapFragmentActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private FragmentManager mManager;
    private TabLayout.OnTabSelectedListener onTabSelectedListener = new TabLayout.OnTabSelectedListener() {

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            FragmentTransaction transaction = mManager.beginTransaction();
            List<Fragment> fragments = mManager.getFragments();
            if (fragments != null) {
                for (Fragment fragment : fragments) {
                    transaction.hide(fragment);
                }
            }
            Fragment fragment = (Fragment) tab.getTag();
            if (fragment == null) {
                fragment = new MapFragment();
                tab.setTag(fragment);
                transaction.add(R.id.fl_container, fragment);
            }
            transaction.show(fragment);
            transaction.commit();
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_normal_map_fragement);
        initView();
        initData();
        initListener();
        setSelectedTab();
    }

    private void setSelectedTab() {
        TabLayout.Tab tab = mTabLayout.getTabAt(0);
        if (tab != null) {
            tab.select();
            onTabSelectedListener.onTabSelected(tab);
        }
    }

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
    }

    private void initData() {
        mManager = getSupportFragmentManager();
        for (int i = 1; i <= 3; i++) {
            String tag = String.format(Locale.CHINA, "第%d个", i);
            TabLayout.Tab tab = mTabLayout.newTab().setText(tag);
            mTabLayout.addTab(tab);
        }
    }

    private void initListener() {
        mTabLayout.addOnTabSelectedListener(onTabSelectedListener);
    }
}
