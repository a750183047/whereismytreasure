package com.yan.whereismytreasure.UI.ViewActivity;

import android.Manifest;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.thinkland.sdk.android.JuheSDKInitializer;
import com.yan.whereismytreasure.App.Global;
import com.yan.whereismytreasure.Modle.Bean.UserBean.MyUser;
import com.yan.whereismytreasure.R;
import com.yan.whereismytreasure.Utils.PermissionsChecker;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.BmobUser;

public class SplashActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 0; // 请求码

    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private PermissionsChecker mPermissionsChecker; // 权限检测器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPermissionsChecker = new PermissionsChecker(this);

        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            startPermissionsActivity();
        }else {
            //授权通过
            JuheSDKInitializer.initialize(getApplicationContext());  //初始化聚合SDK

            BmobConfig config = new BmobConfig.Builder(Global.mContext)  //初始化Bmob
                    .setApplicationId("3741c7b73ae0b1670822d107021ae3b8")
                    .setConnectTimeout(15)
                    .build();
            Bmob.initialize(config);

            new Thread(){
                @Override
                public void run() {
                    SystemClock.sleep(2000);
                    MyUser userInfo = BmobUser.getCurrentUser(Global.mContext,MyUser.class);
                    if (userInfo != null){
                        startActivity(new Intent(SplashActivity.this,MainActivity.class));
                        finish();
                    }else {
                        startActivity(new Intent(SplashActivity.this,FirstPageActivity.class));
                        finish();
                    }

                }
            }.start();
        }



    }

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(SplashActivity.this, REQUEST_CODE, PERMISSIONS);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        }
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_GRANTED){
            //授权通过
            JuheSDKInitializer.initialize(Global.mContext);  //初始化聚合SDK

            BmobConfig config = new BmobConfig.Builder(Global.mContext)  //初始化Bmob
                    .setApplicationId("3741c7b73ae0b1670822d107021ae3b8")
                    .setConnectTimeout(15)
                    .build();
            Bmob.initialize(config);

            new Thread(){
                @Override
                public void run() {
                    SystemClock.sleep(2000);
                    startActivity(new Intent(SplashActivity.this,FirstPageActivity.class));
                    finish();
                }
            }.start();
        }
    }
}
