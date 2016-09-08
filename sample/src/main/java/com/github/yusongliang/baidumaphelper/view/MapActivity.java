package com.github.yusongliang.baidumaphelper.view;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.github.yusongliang.baidumaphelper.R;
import com.github.yusongliang.library.app.BaseMapActivity;

/**
 * 基础地图功能页面
 */
public class MapActivity extends BaseMapActivity {

    private MapView mMapView;

    @Override
    protected int initContentView() {
        return R.layout.activity_base_map;
    }

    @Override
    protected void initView() {
        mMapView = (MapView) findViewById(R.id.map_view);
    }

    @Override
    protected BaiduMap initBaiduMap() {
        return mMapView.getMap();
    }

    @Override
    protected void initListener() {

    }
}
