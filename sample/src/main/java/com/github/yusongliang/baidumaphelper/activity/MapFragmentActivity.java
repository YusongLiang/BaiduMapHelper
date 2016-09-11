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
 * 演示一般嵌套地图Fragment的页面
 */
public class MapFragmentActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private TabLayout.OnTabSelectedListener onTabSelectedListener = new TabLayout.OnTabSelectedListener() {

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            List<Fragment> fragments = manager.getFragments();
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

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
    }

    private void initData() {
        for (int i = 1; i <= 3; i++) {
            String tabName = String.format(Locale.CHINA, "第%d个", i);
            TabLayout.Tab tab = mTabLayout.newTab().setText(tabName);
            mTabLayout.addTab(tab);
        }
    }

    private void initListener() {
        mTabLayout.addOnTabSelectedListener(onTabSelectedListener);
    }

    /**
     * 设置选中的tab
     */
    private void setSelectedTab() {
        TabLayout.Tab tab = mTabLayout.getTabAt(0);
        if (tab != null) {
            tab.select();//该方法不会触发onTabSelected(),因此需要手动调用
            onTabSelectedListener.onTabSelected(tab);
        }
    }
}
