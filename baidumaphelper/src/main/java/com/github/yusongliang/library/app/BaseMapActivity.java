package com.github.yusongliang.library.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.UiSettings;
import com.github.yusongliang.library.BuildConfig;
import com.github.yusongliang.library.config.TagConfig;

/**
 * 基础定位Activity
 */
public abstract class BaseMapActivity extends AppCompatActivity implements BaiduMap.OnMapLoadedCallback, BaiduMap.OnMapTouchListener, BaiduMap.OnMarkerClickListener {
    private MapView mMapView;
    private BaiduMap mBaiduMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(getContentView());
        if (BuildConfig.DEBUG) Log.d(TagConfig.LOG_TAG, getClass().getSimpleName());
        initView();
        initMap();
        initListener();
    }

    /**
     * 获取布局文件id
     *
     * @return 布局文件id
     */
    protected abstract int getContentView();

    /**
     * 声明控件
     */
    protected void initView() {
    }

    /**
     * 初始化地图
     */
    private void initMap() {
        mMapView = initMapView();
        if (mMapView == null) throw new IllegalArgumentException("MapView不能为空");
        mBaiduMap = mMapView.getMap();
        initMapUi(mBaiduMap.getUiSettings());
    }

    /**
     * 设置地图ui
     *
     * @param uiSettings 用于设置ui的UiSettings对象
     */
    protected void initMapUi(UiSettings uiSettings) {
        uiSettings.setOverlookingGesturesEnabled(false);//俯瞰手势关闭
        uiSettings.setRotateGesturesEnabled(false);//旋转手势关闭
        uiSettings.setCompassEnabled(false);//指南针关闭
        uiSettings.setScrollGesturesEnabled(true);//滑动手势开启
        uiSettings.setZoomGesturesEnabled(true);//缩放手势开启
    }

    /**
     * 初始化MapView对象
     *
     * @return MapView对象
     */
    protected abstract MapView initMapView();

    protected void initListener() {
        mBaiduMap.setOnMapLoadedCallback(this);
        mBaiduMap.setOnMapTouchListener(this);
        mBaiduMap.setOnMarkerClickListener(this);
    }

    @Override
    public void onMapLoaded() {
    }

    @Override
    public void onTouch(MotionEvent motionEvent) {
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }
}
