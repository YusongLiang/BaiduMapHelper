package com.github.yusongliang.library.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;

/**
 * 基础定位Fragment
 *
 * @author Yusong.Liang
 */
public abstract class BaseMapFragment extends BaseFragment implements BaiduMap.OnMapLoadedCallback, BaiduMap.OnMapClickListener, BaiduMap.OnMarkerClickListener, BaiduMap.OnMyLocationClickListener {
    private static final float MAP_MAX_ZOOM = 21.0f;
    private static final float MAP_MIN_ZOOM = 5.0f;
    protected View mView;
    private TextureMapView mTextureMapView;
    private BaiduMap mBaiduMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getContentViewResId(), container, false);
        initView(mView);
        return mView;
    }

    /**
     * 获取布局文件id
     *
     * @return 布局文件id
     */
    protected abstract int getContentViewResId();

    /**
     * 声明控件
     *
     * @param v 页面的View对象
     */
    protected void initView(View v) {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initMap(mView);
        initData();
        initListener();
    }

    /**
     * 初始化地图
     *
     * @param v 页面的View对象
     */
    protected void initMap(View v) {
        mTextureMapView = initMapView(v);
        if (mTextureMapView == null) throw new IllegalArgumentException("MapView不能为空");
        mBaiduMap = mTextureMapView.getMap();
        initMapUi(mBaiduMap.getUiSettings());
        initMapState();
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
     * 设置地图其他状态
     */
    protected void initMapState() {
        mBaiduMap.setMaxAndMinZoomLevel(MAP_MAX_ZOOM, MAP_MIN_ZOOM);//设置最大、最小缩放
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);//基本地图
    }

    /**
     * 获取布局文件id
     *
     * @param v 页面的View对象
     * @return 布局文件id
     */
    protected abstract TextureMapView initMapView(View v);

    protected void initData() {
    }

    protected void initListener() {
        mBaiduMap.setOnMapLoadedCallback(this);
        mBaiduMap.setOnMapClickListener(this);
        mBaiduMap.setOnMarkerClickListener(this);
        mBaiduMap.setOnMyLocationClickListener(this);
    }

    public BaiduMap getBaiduMap() {
        return mBaiduMap;
    }

    @Override
    public void onMapLoaded() {
    }

    @Override
    public void onMapClick(LatLng latLng) {
    }

    @Override
    public boolean onMapPoiClick(MapPoi mapPoi) {
        return false;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public boolean onMyLocationClick() {
        return false;
    }

    @Override
    public void onResume() {
        mTextureMapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        mTextureMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mTextureMapView.onDestroy();
        super.onDestroy();
    }
}
