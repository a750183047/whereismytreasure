package com.yan.whereismytreasure.App;

import android.app.Application;

import com.thinkland.sdk.android.JuheSDKInitializer;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

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
        BmobConfig config = new BmobConfig.Builder(this)  //初始化Bmob
                .setApplicationId("3741c7b73ae0b1670822d107021ae3b8")
                .setConnectTimeout(15)
                .build();
        Bmob.initialize(config);
    }
}
