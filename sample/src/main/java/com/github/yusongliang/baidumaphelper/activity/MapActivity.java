package com.github.yusongliang.baidumaphelper.activity;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.github.yusongliang.baidumaphelper.R;
import com.github.yusongliang.baidumaphelper.app.SampleApplication;
import com.github.yusongliang.baidumaphelper.util.PermissionRes;
import com.github.yusongliang.library.app.BaseMapActivity;
import com.github.yusongliang.library.util.Locator;

/**
 * 基础地图功能页面
 */
public class MapActivity extends BaseMapActivity {

    /**
     * 定位器对象
     */
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
    protected void initMapState() {
        super.initMapState();
        checkMPermissions(PermissionRes.RequestCode.REQUEST_PERMISSIONS_LOCATE,
                PermissionRes.PermissionGroup.PERMISSIONS_LOCATE);
    }

    @Override
    public void onRequestPermissionsSuccess(int requestCode, String[] successPermissions) {
        switch (requestCode) {
            case PermissionRes.RequestCode.REQUEST_PERMISSIONS_LOCATE:
                if (successPermissions.length == PermissionRes.PermissionGroup.PERMISSIONS_LOCATE.length) {
                    mLocator = Locator.getInstance(SampleApplication.getContext(), getBaiduMap(), new Locator.OnLocatedListener() {
                        @Override
                        protected void onFirstLocate(BDLocation bdLocation) {
                            getBaiduMap().animateMapStatus(MapStatusUpdateFactory.newLatLngZoom(
                                    new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude()), 18));
                            mLocator.stop();
                        }
                    });
                    mLocator.start();
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsFail(int requestCode, String[] failPermissions) {
        switch (requestCode) {
            case PermissionRes.RequestCode.REQUEST_PERMISSIONS_LOCATE:
                showRationaleDialog(R.string.request_location_permissions_rationale);
                break;
        }
    }
}
