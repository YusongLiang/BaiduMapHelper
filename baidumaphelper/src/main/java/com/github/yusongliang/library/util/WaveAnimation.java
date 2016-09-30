package com.github.yusongliang.library.util;

import android.app.Activity;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;

/**
 * 波浪动画
 *
 * @author Yusong.Liang
 */
public class WaveAnimation {

    /**
     * 波浪默认的填充颜色
     */
    private static final int DEFAULT_FILL_COLOR = 0x2200A3F7;

    /**
     * 波浪默认的边框颜色
     */
    private static final int DEFAULT_STROKE_COLOR = 0x880B67EB;

    /**
     * 波浪默认的最大半径
     */
    private static final int DEFAULT_RADIUS = 1000;

    /**
     * 默认的重复次数
     */
    private static final int DEFAULT_REPEATED_TIME = 10;

    /**
     * 波浪最大半径
     */
    private int mMaxRadius;

    /**
     * {@link BaiduMap}对象
     */
    private BaiduMap mBaiduMap;

    /**
     * 波浪所在{@link Activity}
     */
    private Activity mActivity;

    /**
     * 中心位置{@link LatLng}对象
     */
    private LatLng mCenter;

    /**
     * 波浪填充颜色
     */
    private int mFillColor = -1;

    /**
     * 更新波浪的{@link UpdateCircleThread}线程对象
     */
    private UpdateCircleThread mThread;

    /**
     * 波浪边框颜色
     */
    private int mStrokeColor = -1;

    /**
     * 波浪边框宽度(单位:px)
     */
    private int mStrokeWidth = 1;

    /**
     * 波浪的{@link Overlay}对象
     */
    private Overlay mCircleOverlay;

    /**
     * 波浪重复次数
     */
    private int mRepeatedTime;

    /**
     * 是不停的
     */
    private boolean mIsContinual = true;

    /**
     * @param context  当前页面的{@link Activity}
     * @param baiduMap {@link BaiduMap}对象
     * @param center   中心位置的{@link LatLng}对象
     */
    public WaveAnimation(Activity context, BaiduMap baiduMap, LatLng center) {
        this(context, baiduMap, center, DEFAULT_RADIUS);
    }

    /**
     * @param context  当前页面的{@link Activity}
     * @param baiduMap {@link BaiduMap}对象
     * @param cLat     中心位置的纬度
     * @param cLng     中心位置的经度
     */
    public WaveAnimation(Activity context, BaiduMap baiduMap, float cLat, float cLng) {
        this(context, baiduMap, cLat, cLng, DEFAULT_RADIUS);
    }

    /**
     * @param context  当前页面的{@link Activity}
     * @param baiduMap {@link BaiduMap}对象
     * @param center   中心位置的{@link LatLng}对象
     * @param radius   波浪的最大半径(单位:米)
     */
    public WaveAnimation(Activity context, BaiduMap baiduMap, LatLng center, int radius) {
        mActivity = context;
        mBaiduMap = baiduMap;
        mCenter = center;
        mMaxRadius = radius;
    }

    /**
     * @param context  当前页面的{@link Activity}
     * @param baiduMap {@link BaiduMap}对象
     * @param cLat     中心位置的纬度
     * @param cLng     中心位置的经度
     * @param radius   波浪的最大半径(单位:米)
     */
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
     * 设置波浪重复次数，需将{@link #setIsContinual(boolean)}设置为false才能发挥作用
     *
     * @param repeatedTime 重复次数
     */
    public void setRepeatedTime(int repeatedTime) {
        mRepeatedTime = repeatedTime;
    }

    /**
     * 动画持续不断
     *
     * @param isContinual 是否持续不断
     */
    public void setIsContinual(boolean isContinual) {
        mIsContinual = isContinual;
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
     * 更新圆圈半径的线程
     */
    private class UpdateCircleThread extends Thread {
        private int mRadius;
        private long mDelayTime = 50;
        private boolean mIsInterrupted;
        private Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (!mIsInterrupted) drawCircle(mRadius);
            }
        };

        @Override
        public void run() {
            int time = 0;
            outer:
            while (time < mRepeatedTime || mIsContinual) {
                for (mRadius = 0; mRadius <= mMaxRadius; mRadius += mMaxRadius / 50) {
                    try {
                        mActivity.runOnUiThread(runnable);
                        Thread.sleep(mDelayTime);
                    } catch (InterruptedException e) {
                        mIsInterrupted = true;
                        break outer;
                    }
                }
                mRadius = 0;
                time++;
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
