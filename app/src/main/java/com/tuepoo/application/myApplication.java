package com.tuepoo.application;

import android.app.Application;

import com.tuepoo.core.AdSDKManager;
import com.tuepoo.share.ShareManager;

import cn.jpush.android.api.JPushInterface;


public class myApplication extends Application {

    private static myApplication mApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        initShareSDK();
        initJPush();
        initAdSDK();
    }

    public static myApplication getInstance() {
        return mApplication;
    }

    public void initShareSDK() {
        ShareManager.initSDK(this);
    }

    private void initJPush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    private void initAdSDK() {
        AdSDKManager.init(this);
    }
}