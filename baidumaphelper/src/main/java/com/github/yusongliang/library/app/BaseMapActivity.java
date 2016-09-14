package com.github.yusongliang.library.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;

/**
 * 基础定位Activity
 *
 * @author Yusong.Liang
 */
public abstract class BaseMapActivity extends AppCompatActivity implements BaiduMap.OnMapLoadedCallback, BaiduMap.OnMarkerClickListener, BaiduMap.OnMapClickListener, BaiduMap.OnMyLocationClickListener {
    private static final float MAP_MAX_ZOOM = 21.0f;
    private static final float MAP_MIN_ZOOM = 5.0f;
    private MapView mMapView;
    private BaiduMap mBaiduMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(getContentViewResId());
        initView();
        initMap();
        initListener();
    }

    /**
     * 获取布局文件id
     *
     * @return 布局文件id
     */
    protected abstract int getContentViewResId();

    /**
     * 执行声明控件等先于地图设置的操作
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
        initMapState();
    }

    /**
     * 设置地图其他状态
     */
    protected void initMapState() {
        mBaiduMap.setMaxAndMinZoomLevel(MAP_MAX_ZOOM, MAP_MIN_ZOOM);//设置最大、最小缩放
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);//基本地图
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
        mBaiduMap.setOnMarkerClickListener(this);
        mBaiduMap.setOnMapClickListener(this);
        mBaiduMap.setOnMyLocationClickListener(this);
    }

    protected BaiduMap getBaiduMap() {
        return mBaiduMap;
    }

    @Override
    public void onMapLoaded() {
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {
    }

    @Override
    public boolean onMapPoiClick(MapPoi mapPoi) {
        return false;
    }

    @Override
    public boolean onMyLocationClick() {
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
