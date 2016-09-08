package com.github.yusongliang.library.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.mapapi.map.BaiduMap;

/**
 * 基础定位Fragment
 */
public abstract class BaseMapFragment extends Fragment implements BaiduMap.OnMapLoadedCallback {
    private BaiduMap mBaiduMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView(inflater, container);
    }

    protected abstract View initView(LayoutInflater inflater, ViewGroup container);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBaiduMap = initBaiduMap();
        initData();
        initListener();
    }

    protected abstract BaiduMap initBaiduMap();

    protected void initData() {
    }

    protected void initListener(){
        mBaiduMap.setOnMapLoadedCallback(this);
    }

    @Override
    public void onMapLoaded() {
    }
}
