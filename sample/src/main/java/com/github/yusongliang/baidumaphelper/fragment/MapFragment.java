package com.github.yusongliang.baidumaphelper.fragment;

import android.view.View;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.github.yusongliang.baidumaphelper.R;
import com.github.yusongliang.baidumaphelper.app.SampleApplication;
import com.github.yusongliang.baidumaphelper.util.PermissionRes;
import com.github.yusongliang.library.app.BaseMapFragment;
import com.github.yusongliang.library.util.Locator;

/**
 * Viewpager加载地图Fragment
 */
public class MapFragment extends BaseMapFragment {

    /**
     * 定位器对象
     */
    private Locator mLocator;

    /**
     * 此Fragment是否首次可见
     */
    private boolean isFirstVisible = true;

    /**
     * 定位监听器
     */
    private Locator.OnLocatedListener onLocatedListener = new Locator.OnLocatedListener() {

        @Override
        protected void onFirstLocate(BDLocation bdLocation) {//首次获取位置后，移动到当前位置,将缩放设为18
            getBaiduMap().animateMapStatus(MapStatusUpdateFactory.newLatLngZoom(
                    new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude()), 18));
            mLocator.stop();
            isFirstVisible = false;
        }
    };

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
                && getBaiduMap() != null) //是否已创建BaiduMap对象（首页Fragment执行该方法会在onCreate之前，此时BaiduMap尚未创建，地图上不会显示位置图标）
            getCurrentLocation();
    }

    @Override
    protected void initLocate() {
        //当显示首页Fragment时，setUserVisibleHint中未执行getCurrentLocation，则会在此处执行
        if (getUserVisibleHint() && isFirstVisible) getCurrentLocation();
    }

    /**
     * 获取当前位置
     */
    private void getCurrentLocation() {
        checkMPermissions(PermissionRes.RequestCode.REQUEST_PERMISSIONS_LOCATE,
                PermissionRes.PermissionGroup.PERMISSIONS_LOCATE);
    }

    @Override
    public void onRequestPermissionsSuccess(int requestCode, String[] successPermissions) {
        switch (requestCode) {
            case PermissionRes.RequestCode.REQUEST_PERMISSIONS_LOCATE:
                if (successPermissions.length == PermissionRes.PermissionGroup.PERMISSIONS_LOCATE.length) {
                    mLocator = Locator.getInstance(SampleApplication.getContext(), getBaiduMap());
                    mLocator.addOnLocatedListener(onLocatedListener);
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
