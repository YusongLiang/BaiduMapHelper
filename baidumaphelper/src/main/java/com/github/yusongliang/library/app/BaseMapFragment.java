package com.github.yusongliang.library.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;

/**
 * 基础定位Fragment
 */
public abstract class BaseMapFragment extends Fragment implements BaiduMap.OnMapLoadedCallback, BaiduMap.OnMapClickListener, BaiduMap.OnMarkerClickListener, BaiduMap.OnMyLocationClickListener {
    private static final float MAP_MAX_ZOOM = 21.0f;
    private static final float MAP_MIN_ZOOM = 5.0f;
    protected View mView;
    private TextureMapView mTextureMapView;
    private BaiduMap mBaiduMap;
    private BitmapDescriptor mMyLocBmpDescriptor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getContentViewResId(), container, false);
        initView(mView);
        return mView;
    }

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


    protected void initMapUi(UiSettings uiSettings) {
        mBaiduMap.setMaxAndMinZoomLevel(MAP_MAX_ZOOM, MAP_MIN_ZOOM);//设置最大、最小缩放
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);//基本地图
        mBaiduMap.setMyLocationEnabled(true);
        setLocateData();
    }

    /**
     * 设置我的位置图标
     *
     * @param myLocBmpDescriptor 设置我的位置图标,为null的话显示默认图标
     */
    public void setMyLocBmpDescriptor(@Nullable BitmapDescriptor myLocBmpDescriptor) {
        mMyLocBmpDescriptor = myLocBmpDescriptor;
    }

    /**
     * 设置定位数据
     */
    private void setLocateData() {
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.NORMAL, true, mMyLocBmpDescriptor
        ));
    }

    /**
     * 设置地图其他状态
     */
    protected void initMapState() {
        mBaiduMap.setMaxAndMinZoomLevel(MAP_MAX_ZOOM, MAP_MIN_ZOOM);//设置最大、最小缩放
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);//基本地图
        mBaiduMap.setMyLocationEnabled(true);
        setLocateData();
    }

    /**
     * 获取布局文件id
     *
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
}
