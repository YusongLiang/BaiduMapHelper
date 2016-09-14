package com.github.yusongliang.baidumaphelper.activity;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.github.yusongliang.baidumaphelper.R;
import com.github.yusongliang.library.app.BaseMapActivity;
import com.github.yusongliang.library.util.WaveAnimation;

import java.util.ArrayList;
import java.util.List;

/**
 * 波浪动画演示界面
 */
public class WaveAnimationActivity extends BaseMapActivity {
    private static final int MAX_ANIMATION_COUNT = 20;
    private List<WaveAnimation> mAnimList = new ArrayList<>();

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

    }

    @Override
    public void onMapClick(LatLng latLng) {
        super.onMapClick(latLng);
        if (mAnimList.size() < MAX_ANIMATION_COUNT) {
            WaveAnimation waveAnimation = new WaveAnimation(this, getBaiduMap(), latLng);
            waveAnimation.start();
            mAnimList.add(waveAnimation);
        }
    }

    @Override
    protected void onDestroy() {
        for (WaveAnimation waveAnimation : mAnimList) {
            waveAnimation.stop();
        }
        super.onDestroy();
    }
}
