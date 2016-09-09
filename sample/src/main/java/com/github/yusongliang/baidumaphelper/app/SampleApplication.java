package com.github.yusongliang.baidumaphelper.app;

import android.app.Application;
import android.content.Context;

/**
 * 当前的Application对象
 */
public class SampleApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }
}
