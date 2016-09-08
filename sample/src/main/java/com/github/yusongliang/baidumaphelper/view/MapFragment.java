package com.github.yusongliang.baidumaphelper.view;

import android.view.View;

import com.baidu.mapapi.map.TextureMapView;
import com.github.yusongliang.baidumaphelper.R;
import com.github.yusongliang.library.app.BaseMapFragment;

/**
 * 地图Fragment
 */
public class MapFragment extends BaseMapFragment {

    @Override
    protected int getContentViewResId() {
        return R.layout.fragment_map;
    }

    @Override
    protected TextureMapView initMapView(View v) {
        return (TextureMapView) v.findViewById(R.id.texture_map_view);
    }
}
