package com.github.yusongliang.baidumaphelper.view;

import android.view.View;

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

        //当该Fragment对于用户首次可见时，定位到当前位置，并终止定位
        if (isVisibleToUser && isFirstVisible) {
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
}
