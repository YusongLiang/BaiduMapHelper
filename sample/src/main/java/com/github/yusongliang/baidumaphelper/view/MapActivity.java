package com.github.yusongliang.baidumaphelper.view;

import com.baidu.mapapi.map.MapView;
import com.github.yusongliang.baidumaphelper.R;
import com.github.yusongliang.library.app.BaseMapActivity;

/**
 * 基础地图功能页面
 */
public class MapActivity extends BaseMapActivity {

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
}
