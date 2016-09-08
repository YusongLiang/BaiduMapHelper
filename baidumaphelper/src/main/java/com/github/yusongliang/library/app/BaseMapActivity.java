package com.github.yusongliang.library.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.github.yusongliang.library.BuildConfig;
import com.github.yusongliang.library.config.TagConfig;

/**
 * 基础定位Activity
 */
public abstract class BaseMapActivity extends AppCompatActivity implements TagConfig {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(initContentView());
        if (BuildConfig.DEBUG) Log.d(LOG_TAG, getClass().getSimpleName());
        initView();
        initBaiduMap();
        initListener();
    }

    protected abstract int initContentView();

    protected abstract void initView();

    protected abstract BaiduMap initBaiduMap();

    protected abstract void initListener();
}
