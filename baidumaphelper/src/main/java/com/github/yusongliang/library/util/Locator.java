package com.github.yusongliang.library.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.github.yusongliang.library.config.TagConfig;

/**
 * 定位器
 *
 * @author Yusong.Liang
 */
public class Locator {

    /**
     * 默认定位时间间隔
     */
    private static final int DEFAULT_LOCATE_SPAN = 1000;

    private static BaiduMap mBaiduMap;

    private final Context mContext;

    private LocationClient mLocationClient;

    private boolean isFirst = true;

    @SuppressLint("StaticFieldLeak")
    private static Locator mLocator;

    private static OnLocatedListener mOnLocatedListener;

    private BitmapDescriptor mMyLocBmpDescriptor;

    private Locator(Context context) {
        mContext = context;
    }

    /**
     * 获取定位器实例
     *
     * @param context 传入Context
     * @return 定位器实例
     */
    public static Locator getInstance(Context context) {
        return getInstance(context, null, null);
    }

    /**
     * 获取定位器实例
     *
     * @param context  传入Context
     * @param baiduMap BaiduMap对象
     * @return 定位器实例
     */
    public static Locator getInstance(Context context, BaiduMap baiduMap) {
        return getInstance(context, baiduMap, null);
    }

    /**
     * 获取定位器实例
     *
     * @param context           传入Context
     * @param baiduMap          BaiduMap对象
     * @param onLocatedListener 定位监听器
     * @return 定位器实例
     */
    public static Locator getInstance(Context context, BaiduMap baiduMap, OnLocatedListener onLocatedListener) {
        Log.d(TagConfig.LOG_TAG_LOCATE, "创建定位器实例");
        mBaiduMap = baiduMap;
        mOnLocatedListener = onLocatedListener;
        if (mLocator == null) {
            mLocator = new Locator(context.getApplicationContext());//传入ApplicationContext,防止出现内存泄漏问题
        } else {
            mLocator.stop();
        }
        return mLocator;
    }

    /**
     * 添加定位监听器
     *
     * @param onLocatedListener 定位监听器对象
     */
    public void addOnLocatedListener(OnLocatedListener onLocatedListener) {
        mOnLocatedListener = onLocatedListener;
    }

    /**
     * 定位当前位置
     */
    private void initLocation() {
        Log.d(TagConfig.LOG_TAG_LOCATE, "initLocation");
        //声明LocationClient类
        mLocationClient = new LocationClient(mContext);
        MyLocationListener locationListener = new MyLocationListener();
        //注册监听函数
        mLocationClient.registerLocationListener(locationListener);
        //配置定位SDK参数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        option.setScanSpan(DEFAULT_LOCATE_SPAN);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
        Log.d(TagConfig.LOG_TAG_LOCATE, "mLocationClient完成");
    }

    /**
     * 处理定位事件的监听器
     */
    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            Log.d(TagConfig.LOG_TAG_LOCATE, "获取到位置，type:" + bdLocation.getLocType() + ",desc:" + bdLocation.getLocTypeDescription());
            if (mBaiduMap != null) {
                MyLocationData data = new MyLocationData.Builder()//
                        .accuracy(bdLocation.getRadius())//
                        .latitude(bdLocation.getLatitude())//
                        .longitude(bdLocation.getLongitude())//
                        .build();
                mBaiduMap.setMyLocationData(data);
            }
            mOnLocatedListener.onLocated(bdLocation);
            if (isFirst) {
                isFirst = false;
                mOnLocatedListener.onFirstLocate(bdLocation);
            }
        }
    }

    /**
     * 开启定位
     */
    public void start() {
        if (mBaiduMap != null) {
            mBaiduMap.setMyLocationEnabled(true);
            setLocateData();
        }
        if (mLocationClient == null) initLocation();
        if (!mLocationClient.isStarted()) {
            Log.d(TagConfig.LOG_TAG_LOCATE, "开启定位");
            mLocationClient.start();
        }
    }

    /**
     * 终止定位
     */
    public void stop() {
        Log.d(TagConfig.LOG_TAG_LOCATE, "终止定位");
        if (mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.stop();
            mLocationClient = null;
            isFirst = true;
        }
    }

    /**
     * 设置我的位置图标
     *
     * @param myLocBmpDescriptor 设置我的位置图标,为null的话显示默认图标
     */
    public void setMyLocBmpDescriptor(@Nullable BitmapDescriptor myLocBmpDescriptor) {
        mMyLocBmpDescriptor = myLocBmpDescriptor;
    }

    /**
     * 设置定位数据
     */
    protected void setLocateData() {
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.NORMAL, true, mMyLocBmpDescriptor
        ));
    }

    /**
     * 定位监听器
     */
    public static abstract class OnLocatedListener {

        /**
         * 初次定位时执行的操作
         */
        protected void onFirstLocate(BDLocation bdLocation) {
        }

        /**
         * 每次获取到位置后执行的操作
         *
         * @param bdLocation 当前位置的BDLocation对象
         */
        protected void onLocated(BDLocation bdLocation) {
        }
    }
}
