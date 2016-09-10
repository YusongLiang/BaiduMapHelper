package com.github.yusongliang.baidumaphelper.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.github.yusongliang.baidumaphelper.R;
import com.github.yusongliang.baidumaphelper.app.SampleApplication;
import com.github.yusongliang.library.app.BaseMapFragment;
import com.github.yusongliang.library.utils.Locator;

/**
 * 地图Fragment
 */
public class MapFragment extends BaseMapFragment {

    private Locator mLocator;

    /**
     * 此Fragment是否首次可见
     */
    private boolean isFirstVisible = true;

    @Override
    protected int getContentViewResId() {
        return R.layout.fragment_map;
    }

    @Override
    protected TextureMapView initMapView(View v) {
        return (TextureMapView) v.findViewById(R.id.texture_map_view);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser //Fragment对于用户可见
                && isFirstVisible //是否为第一次可见
                && getBaiduMap() != null) //是否已创建BaiduMap对象（首页Fragment执行该方法会在onCreate之前，此时BaiduMap尚未创建，不会显示位置图标）
            animateToCurrentPosition();
    }

    @Override
    protected void initMapState() {
        super.initMapState();
        //当显示首页Fragment时，setUserVisibleHint中未执行animateToCurrentPosition，则会在此处执行
        if (isFirstVisible) animateToCurrentPosition();
    }

    /**
     * 移动到当前位置
     */
    private void animateToCurrentPosition() {
        mLocator = Locator.getInstance(SampleApplication.getContext(), getBaiduMap(), new Locator.OnLocatedListener() {

            @Override
            protected void onFirstLocate(BDLocation bdLocation) {
                getBaiduMap().animateMapStatus(MapStatusUpdateFactory.newLatLngZoom(
                        new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude()), 18));
                mLocator.stop();
            }
        });
        mLocator.start();
        isFirstVisible = false;
    }
}
