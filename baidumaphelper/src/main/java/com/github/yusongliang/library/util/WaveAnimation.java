package com.github.yusongliang.library.util;

import android.app.Activity;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;

/**
 * 波浪动画
 */
public class WaveAnimation {
    private static final int DEFAULT_FILL_COLOR = 0xFFFFFFFF;
    private static final int DEFAULT_STROKE_COLOR = 0xFFFFFFFF;
    private static final int DEFAULT_RADIUS = 100000;
    private int mMaxRadius;
    private BaiduMap mBaiduMap;
    private Activity mActivity;
    private LatLng mCenter;
    private int mFillColor = -1;
    private UpdateCircleThread mThread;
    private int mStrokeColor = -1;
    private int mStrokeWidth;
    private Overlay mCircleOverlay;

    public WaveAnimation(Activity context, BaiduMap baiduMap, LatLng center) {
        this(context, baiduMap, center, DEFAULT_RADIUS);
    }

    public WaveAnimation(Activity context, BaiduMap baiduMap, float cLat, float cLng) {
        this(context, baiduMap, cLat, cLng, DEFAULT_RADIUS);
    }

    public WaveAnimation(Activity context, BaiduMap baiduMap, LatLng center, int radius) {
        mActivity = context;
        mBaiduMap = baiduMap;
        mCenter = center;
        mMaxRadius = radius;
    }

    public WaveAnimation(Activity context, BaiduMap baiduMap, float cLat, float cLng, int radius) {
        mActivity = context;
        mBaiduMap = baiduMap;
        mCenter = new LatLng(cLat, cLng);
        mMaxRadius = radius;
    }

    /**
     * 设置边框颜色
     *
     * @param StrokeColor 边框颜色
     */
    public void setStrokeColor(int StrokeColor) {
        mStrokeColor = StrokeColor;
    }

    /**
     * 设置波浪边框宽度
     *
     * @param StrokeWidth 边框宽度
     */
    public void setStrokeWidth(int StrokeWidth) {
        mStrokeWidth = StrokeWidth;
    }

    /**
     * 设置波浪填充颜色
     *
     * @param fillColor 填充颜色
     */
    public void setFillColor(int fillColor) {
        mFillColor = fillColor;
    }

    /**
     * 开始波浪动画
     */
    public void start() {
        startUpdateCircle();
    }

    /**
     * 结束波浪动画
     */
    public void stop() {
        stopUpdateCircle();
    }

    /**
     * 绘制波浪圆圈
     *
     * @param radius 圆半径
     */
    private void drawCircle(int radius) {
        clearCircle();
        Stroke stroke = new Stroke(mStrokeWidth, mStrokeColor == -1 ? DEFAULT_STROKE_COLOR : mStrokeColor);
        CircleOptions circleOptions = new CircleOptions()
                .center(mCenter)
                .fillColor(mFillColor == -1 ? DEFAULT_FILL_COLOR : mFillColor)
                .radius(radius)
                .stroke(stroke);
        mCircleOverlay = mBaiduMap.addOverlay(circleOptions);
    }

    /**
     * 清除波浪圆圈
     */
    private void clearCircle() {
        if (mCircleOverlay != null) {
            mCircleOverlay.remove();
            mCircleOverlay = null;
        }
    }

    /**
     * 更新圆圈半径
     */
    private class UpdateCircleThread extends Thread {
        private int mRadius;
        private long mDelayTime;
        private Runnable runnable = new Runnable() {
            @Override
            public void run() {
                drawCircle(mRadius);
            }
        };

        @Override
        public void run() {
            while (true) {
                try {
                    mActivity.runOnUiThread(runnable);
                    Thread.sleep(mDelayTime);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

    /**
     * 开始更新圆圈
     */
    private void startUpdateCircle() {
        if (mThread == null) {
            mThread = new UpdateCircleThread();
            mThread.start();
        }
    }

    /**
     * 结束更新圆圈
     */
    private void stopUpdateCircle() {
        if (mThread != null) {
            mThread.interrupt();
            mThread = null;
        }
    }
}
