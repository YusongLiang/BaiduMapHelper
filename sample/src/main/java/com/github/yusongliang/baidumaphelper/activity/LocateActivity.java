package com.github.yusongliang.baidumaphelper.activity;

import android.Manifest;
import android.content.Context;
import android.location.LocationManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.github.yusongliang.baidumaphelper.R;
import com.github.yusongliang.library.app.BaseActivity;
import com.github.yusongliang.library.config.TagConfig;
import com.github.yusongliang.library.util.Locator;

/**
 * 定位演示页面
 */
public class LocateActivity extends BaseActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_LOCATION = 0x00000001;
    private String[] mPermissions = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private TextView mTvLocateMsg;
    private Button mBtLocate;
    private Locator mLocator;
    private Locator.OnLocatedListener onLocatedListener = new Locator.OnLocatedListener() {
        @Override
        protected void onFirstLocate(BDLocation bdLocation) {
            super.onFirstLocate(bdLocation);
            String msg = "地址：" + bdLocation.getAddrStr() +
                    "\n经度：" + bdLocation.getLongitude() +
                    "\n纬度：" + bdLocation.getLatitude();
            mTvLocateMsg.setText(msg);
            mLocator.stop();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        mTvLocateMsg = (TextView) findViewById(R.id.tv_locate_message);
        mBtLocate = (Button) findViewById(R.id.bt_locate);
    }

    private void initData() {
        mLocator = Locator.getInstance(this);
    }

    private void initListener() {
        mBtLocate.setOnClickListener(this);
        mLocator.addOnLocatedListener(onLocatedListener);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_locate:
                checkMPermissions(REQUEST_CODE_LOCATION, mPermissions);
                break;
        }
    }

    @Override
    public void onGranted(int requestCode, String[] permissions) {
        if (requestCode == REQUEST_CODE_LOCATION) {
            if (permissions.length == mPermissions.length) {
                Log.d(TagConfig.LOG_TAG_LOCATE, "onGranted");
                LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                boolean isEnableGPS = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                boolean isEnableNTW = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                Log.d(TagConfig.LOG_TAG_LOCATE, "isEnableGPS：" + isEnableGPS + ",isEnableNTW：" + isEnableNTW);
                mLocator.start();
            }
        }
    }

    @Override
    public void onRequestPermissionsFail(int requestCode, String[] permissions) {
        if (requestCode == REQUEST_CODE_LOCATION) {
            if (permissions.length == 0) {
                Log.d(TagConfig.LOG_TAG_LOCATE, "onRequestPermissionsFail");
                mLocator.start();
            }
        }
    }

    @Override
    protected void onDestroy() {
        mLocator.stop();
        mLocator = null;
        super.onDestroy();
    }
}
