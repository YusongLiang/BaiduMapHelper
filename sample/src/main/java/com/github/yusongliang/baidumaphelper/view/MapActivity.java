package com.github.yusongliang.baidumaphelper.view;

import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.Point;
import com.github.yusongliang.baidumaphelper.R;
import com.github.yusongliang.library.app.BaseMapActivity;
import com.github.yusongliang.library.utils.Locator;

/**
 * 基础地图功能页面
 */
public class MapActivity extends BaseMapActivity {

    private Locator mLocator;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_base_map;
    }

    @Override
    protected MapView initMapView() {
        return (MapView) findViewById(R.id.map_view);
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    @Override
    protected void setLocateData() {
        super.setLocateData();
        mLocator = Locator.getInstance(getApplicationContext(), getBaiduMap(), new Locator.OnLocatedListener() {
            @Override
            protected void onFirstLocate(BDLocation bdLocation) {
                getBaiduMap().animateMapStatus(MapStatusUpdateFactory.newLatLngZoom(
                        new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude()), 18));
                mLocator.stop();
            }
        });
        mLocator.start();
    }
}
