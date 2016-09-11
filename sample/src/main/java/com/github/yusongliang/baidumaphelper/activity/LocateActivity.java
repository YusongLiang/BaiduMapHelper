package com.github.yusongliang.baidumaphelper.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.github.yusongliang.baidumaphelper.R;
import com.github.yusongliang.library.util.Locator;

/**
 * 定位演示页面
 */
public class LocateActivity extends AppCompatActivity implements View.OnClickListener {
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
                mLocator.start();
                break;
        }
    }
}
