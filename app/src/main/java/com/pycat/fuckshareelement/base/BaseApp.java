package com.pycat.fuckshareelement.base;

import android.app.Application;

import com.apkfuns.logutils.LogUtils;

public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    static {
        LogUtils.getLogConfig().configShowBorders(false);
    }
}
