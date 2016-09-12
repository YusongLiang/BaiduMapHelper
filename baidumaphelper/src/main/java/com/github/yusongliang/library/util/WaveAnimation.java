package com.github.yusongliang.library.util;

import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.model.LatLng;

/**
 * 波浪动画
 */
public class WaveAnimation {
    private LatLng mCenter;
    private int mFillColor;
    private UpdateCircleThread mThread;

    public WaveAnimation(LatLng center, int fillColor) {
        mCenter = center;
        mFillColor = fillColor;
    }

    public WaveAnimation(float cLat, float cLng, int fillColor) {
        mCenter = new LatLng(cLat, cLng);
        mFillColor = fillColor;
    }

    /**
     * 画圆
     *
     * @param radius 圆半径
     */
    private void drawCircle(int radius) {
        CircleOptions circleOptions = new CircleOptions()
                .center(mCenter)
                .fillColor(mFillColor)
                .radius(radius);
    }

    /**
     * 更新圆圈半径
     */
    private class UpdateCircleThread extends Thread {

    }
}
