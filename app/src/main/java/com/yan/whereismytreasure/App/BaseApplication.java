package com.yan.whereismytreasure.App;

import android.app.Application;

import com.thinkland.sdk.android.JuheSDKInitializer;

/**
 * BaseApplication
 * Created by a7501 on 2016/5/22.
 */
public class BaseApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Global.initialize(this);
        JuheSDKInitializer.initialize(this);  //初始化聚合SDK
    }
}
