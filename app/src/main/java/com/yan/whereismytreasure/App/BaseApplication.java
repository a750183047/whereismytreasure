package com.yan.whereismytreasure.App;

import android.Manifest;
import android.app.Application;

import com.thinkland.sdk.android.JuheSDKInitializer;
import com.yan.whereismytreasure.Utils.PermissionsChecker;

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

    }
}
